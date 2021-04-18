package utility.grpc

import com.google.protobuf.MessageLite
import io.micronaut.http.HttpStatus
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.protobuf.ProtoBuf
import org.slf4j.LoggerFactory
import proto.store.service.Response
import store.service.service.StoreResponseFactory.Companion.createResponse
import utility.request.Request
import utility.response.ResponseException

val LOGGER = LoggerFactory.getLogger("GrpcExecute")

@ExperimentalSerializationApi
suspend inline fun <T : MessageLite, reified U : Request<U>, R, PR : MessageLite> execute(
        request: T,
        crossinline callback: suspend (U) -> R,
        responseFactory: (R?, Response) -> PR
): PR = runCatching {
    val requestDecoded = decodeProtoRequest<T, U>(request)
    val response = executeCall(requestDecoded, callback)
    response.toSuccessFulResponse(requestDecoded, responseFactory)
}.getOrElse {
    LOGGER.error(it.message) // TODO move logging into failureResponse
    it.toFailureResponse(responseFactory)
}

inline fun <U : Request<U>, R, PR : MessageLite> R.toSuccessFulResponse(request: U, responseFactory: (R?, Response) -> PR) =
        responseFactory.invoke(this, createResponse(request.status))

inline fun <R, PR> Throwable.toFailureResponse(protoResponseFactoryMethod: (R?, Response) -> PR): PR =
        when (this) {
            is ResponseException.BadRequest -> createEvaluatedResponse(protoResponseFactoryMethod, HttpStatus.BAD_REQUEST.code, this.message)
            is ResponseException.NotFound -> createEvaluatedResponse(protoResponseFactoryMethod, HttpStatus.NOT_FOUND.code, this.message)
            else -> createEvaluatedResponse(protoResponseFactoryMethod, HttpStatus.INTERNAL_SERVER_ERROR.code, this.message
                    ?: HttpStatus.INTERNAL_SERVER_ERROR.reason)
        }

inline fun <R, PR> createEvaluatedResponse(protoResponseFactoryMethod: (R?, Response) -> PR, status: Int, message: String) =
        protoResponseFactoryMethod.invoke(null, createResponse(status, message))

@ExperimentalSerializationApi
inline fun <T : MessageLite, reified U : Request<U>> decodeProtoRequest(protoRequest: T): U =
        ProtoBuf.decodeFromByteArray(protoRequest.toByteArray())

suspend inline fun <reified U : Request<U>, R> executeCall(request: U, crossinline callback: suspend (U) -> R) =
        callback.invoke(request)
