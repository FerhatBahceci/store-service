package utility

import com.google.protobuf.MessageLite
import kotlinx.serialization.*
import kotlinx.serialization.protobuf.ProtoBuf
import org.slf4j.LoggerFactory

val LOGGER = LoggerFactory.getLogger("GrpcExecute")

@ExperimentalSerializationApi
suspend inline fun <T : MessageLite, reified U : Request<U>, V> execute(
    request: T,
    crossinline callBack: suspend (U) -> V
) {
    runCatching {
        val requestDecoded = ProtoBuf.decodeFromByteArray<U>(request.toByteArray())
        callBack.invoke(requestDecoded)
    }.getOrElse {
        LOGGER.error(it.message)
        throw it
        // TODO avoid logging all failed responses as error, NOT_FOUND(404) should for instance be logged as warning or something wiser than error. Fix LOGGING evaluation
        // TODO onError, package into a response with the error message, status etc. instead of letting the service just throw exceptions abruptly crashing the call
    }
}
