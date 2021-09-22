package utility.grpc

import com.google.protobuf.MessageLite
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.protobuf.ProtoBuf
import utility.request.Request

@ExperimentalSerializationApi
suspend inline fun <T : MessageLite, reified U : Request<U>, R, PR : MessageLite> execute(
        request: T,
        crossinline callback: suspend (U) -> R,
        responseFactory: (R?) -> PR
): PR = runCatching {
    val requestDecoded = decodeProtoRequest<T, U>(request)
    val response = callback.invoke(requestDecoded)
    responseFactory.invoke(response)
}.getOrThrow() // TODO handle exception handling

@ExperimentalSerializationApi
inline fun <T : MessageLite, reified U : Request<U>> decodeProtoRequest(protoRequest: T): U =
    ProtoBuf.decodeFromByteArray(protoRequest.toByteArray())
