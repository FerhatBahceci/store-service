package utility.grpc

import com.google.protobuf.MessageLite
import kotlinx.serialization.*
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
        crossinline gatewayCallback: suspend (U) -> R,
        protoResponseFactoryMethod: (R?, Response) -> PR
): PR = runCatching {
    val requestDecoded = ProtoBuf.decodeFromByteArray<U>(request.toByteArray())
    val response = gatewayCallback.invoke(requestDecoded)
    protoResponseFactoryMethod.invoke(response, createResponse(requestDecoded.status))
}.getOrElse {
    LOGGER.error(it.message)
    it.toFailureResponse(protoResponseFactoryMethod)
}

inline fun <R, PR> Throwable.toFailureResponse(protoResponseFactoryMethod: (R?, Response) -> PR): PR =
        when (this) {
            is ResponseException.BadRequest -> protoResponseFactoryMethod.invoke(null, createResponse(400, this.message))
            is ResponseException.NotFound -> protoResponseFactoryMethod.invoke(null, createResponse(404, this.message))
            else -> protoResponseFactoryMethod.invoke(null, createResponse(500, this.message))
        }

