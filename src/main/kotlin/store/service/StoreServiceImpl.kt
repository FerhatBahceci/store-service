package store.service

import com.google.protobuf.MessageLite
import io.grpc.Status
import io.grpc.StatusException
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.serialization.*
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.protobuf.ProtoBuf
import org.slf4j.LoggerFactory
import proto.store.service.*
import proto.store.service.DeleteStoreByIdRequest
import proto.store.service.GetStoreByIdRequest
import proto.store.service.Store
import proto.store.service.UpdateStoreRequest
import utility.Request
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


@ObsoleteCoroutinesApi
@Factory
private class CoroutineContextFactory {

    @Bean
    fun coroutineContext() = newFixedThreadPoolContext(4, "grpc-thread-pool")
}

@ExperimentalSerializationApi
@GrpcService
class StoreServiceImpl constructor(@Inject private val gateway: StoreGateway,
                                   @Inject override val coroutineContext: CoroutineContext) :
        StoreServiceGrpcKt.StoreServiceCoroutineImplBase(), CoroutineScope {

    private val LOGGER = LoggerFactory.getLogger(StoreServiceImpl::class.java)

    override suspend fun getStoreByType(request: proto.store.service.GetStoreByTypeRequest): GetStoresResponse = throw
    StatusException(Status.UNIMPLEMENTED.withDescription("Method proto.store.service.StoreService.GetStoreByType is unimplemented"))

    override suspend fun geAllStores(request: GetStoresRequest): GetStoresResponse = throw
    StatusException(Status.UNIMPLEMENTED.withDescription("Method proto.store.service.StoreService.GeAllStores is unimplemented"))

    override suspend fun createStore(request: proto.store.service.CreateStoreRequest): CreatedStoreResponse {
        val doesItWork = execute(request, gateway::createStore)
        val encoded = ProtoBuf.encodeToByteArray(doesItWork)
        val decoded = ProtoBuf.decodeFromByteArray<CreatedStoreResponse>(encoded)

/*

TODO How to go from Kotlin --> Proto
       e.g  ProtoBuf.decodeFromByteArray<DayOfWeek>(ProtoBuf.encodeToByteArray(proto.store.service.DayOfWeek.forNumber(2)))
*/

        return decoded
    }

    override suspend fun getStoreById(request: GetStoreByIdRequest): GetStoreResponse = throw
    StatusException(Status.UNIMPLEMENTED.withDescription("Method proto.store.service.StoreService.GetStoreById is unimplemented"))

    override suspend fun updateStore(request: UpdateStoreRequest): UpdateStoreResponse = throw
    StatusException(Status.UNIMPLEMENTED.withDescription("Method proto.store.service.StoreService.UpdateStore is unimplemented"))

    override suspend fun deleteStore(request: DeleteStoreByIdRequest): DeleteStoreResponse = throw
    StatusException(Status.UNIMPLEMENTED.withDescription("Method proto.store.service.StoreService.DeleteStore is unimplemented"))

    private suspend inline fun <T : MessageLite, reified U : Request<U>, V> execute(request: T, crossinline callBack: suspend (U) -> V) {
        runCatching {
            val requestDecoded = ProtoBuf.decodeFromByteArray<U>(request.toByteArray())
            val response = callBack.invoke(requestDecoded)
        }.getOrElse {
            LOGGER.error(it.message)
            // TODO avoid logging all failed responses as error, NOT_FOUND(404) should for instance be logged as warning or something wiser than error. Fix LOGGING evaluation
            // TODO onError, package into a response with the error message, status etc. instead of letting the service just throw exceptions abruptly crashing the call
        }
    }

    @ExperimentalSerializationApi
    @Serializer(forClass = proto.store.service.Store::class)
    object StoreProtoSerializer : DeserializationStrategy<Store> {

        override fun serialize(encoder: Encoder, value: Store) {
            TODO("Not yet implemented")
        }

        override fun deserialize(decoder: Decoder): Store {
            TODO("Not yet implemented")
        }
    }
}
