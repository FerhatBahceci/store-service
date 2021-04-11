package utility.grpc

import com.google.protobuf.MessageLite
import kotlinx.serialization.*
import kotlinx.serialization.protobuf.ProtoBuf
import org.slf4j.LoggerFactory
import utility.request.Request

val LOGGER = LoggerFactory.getLogger("GrpcExecute")

@ExperimentalSerializationApi
suspend inline fun <T : MessageLite, reified U : Request<U>, R, PR : MessageLite> execute(
        request: T,
        crossinline gatewayCallback: suspend (U) -> R,
        protoResponseFactoryMethod: (R, Int) -> PR
): PR = runCatching {
    val requestDecoded = ProtoBuf.decodeFromByteArray<U>(request.toByteArray())
    val response = gatewayCallback.invoke(requestDecoded)
    protoResponseFactoryMethod.invoke(response, requestDecoded.status)
}.getOrElse {
    LOGGER.error(it.message)
    throw it
    // TODO avoid logging all failed responses as error, NOT_FOUND(404) should for instance be logged as warning or something wiser than error. Fix LOGGING evaluation
    // TODO onError, package into a response with the error message, status etc. instead of letting the service just throw exceptions abruptly crashing the call
}
