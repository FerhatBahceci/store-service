package store.service

import io.grpc.Status
import io.grpc.StatusException
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.serialization.*
import proto.store.service.*
import proto.store.service.DeleteStoreByIdRequest
import proto.store.service.GetStoreByIdRequest
import proto.store.service.UpdateStoreRequest
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import utility.execute

@ObsoleteCoroutinesApi
@Factory
private class CoroutineContextFactory {

    @Bean
    fun coroutineContext() = newFixedThreadPoolContext(4, "grpc-thread-pool")
}

/*
TODO How to go from Kotlin --> Proto e.g  ProtoBuf.decodeFromByteArray<DayOfWeek>(ProtoBuf.encodeToByteArray(proto.store.service.DayOfWeek.forNumber(2)))
*/


@ExperimentalSerializationApi
@GrpcService
class StoreServiceImpl constructor(@Inject private val gateway: StoreGateway,
                                   @Inject override val coroutineContext: CoroutineContext) :
        StoreServiceGrpcKt.StoreServiceCoroutineImplBase(), CoroutineScope {

    override suspend fun getStoreByType(request: proto.store.service.GetStoreByTypeRequest): GetStoresResponse = throw
    StatusException(Status.UNIMPLEMENTED.withDescription("Method proto.store.service.StoreService.GetStoreByType is unimplemented"))

    override suspend fun geAllStores(request: GetStoresRequest): GetStoresResponse = throw
    StatusException(Status.UNIMPLEMENTED.withDescription("Method proto.store.service.StoreService.GeAllStores is unimplemented"))

    override suspend fun createStore(request: proto.store.service.CreateStoreRequest): CreatedStoreResponse = execute(request, gateway::createStore).let { CreatedStoreResponse.newBuilder().build() }

    override suspend fun getStoreById(request: GetStoreByIdRequest): GetStoreResponse = throw
    StatusException(Status.UNIMPLEMENTED.withDescription("Method proto.store.service.StoreService.GetStoreById is unimplemented"))

    override suspend fun updateStore(request: UpdateStoreRequest): UpdateStoreResponse = throw
    StatusException(Status.UNIMPLEMENTED.withDescription("Method proto.store.service.StoreService.UpdateStore is unimplemented"))

    override suspend fun deleteStore(request: DeleteStoreByIdRequest): DeleteStoreResponse = throw
    StatusException(Status.UNIMPLEMENTED.withDescription("Method proto.store.service.StoreService.DeleteStore is unimplemented"))
}
