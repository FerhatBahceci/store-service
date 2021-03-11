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
import kotlinx.serialization.protobuf.ProtoBuf
import proto.store.service.*
import proto.store.service.DeleteStoreByIdRequest
import proto.store.service.GetAllStoresRequest
import proto.store.service.GetStoreByNameRequest
import proto.store.service.UpdateStoreRequest
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import utility.grpc.execute
import proto.store.service.CreateStoreRequest
import proto.store.service.GetStoreByTypeRequest

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
class StoreServiceImpl constructor(
        @Inject private val gateway: StoreGateway,
        @Inject override val coroutineContext: CoroutineContext
) :
        StoreServiceGrpcKt.StoreServiceCoroutineImplBase(), CoroutineScope {

    override suspend fun getStoreByType(request: GetStoreByTypeRequest): GetStoresResponse = throw
    StatusException(Status.UNIMPLEMENTED.withDescription("Method proto.store.service.StoreService.GetStoreByType is unimplemented"))

    override suspend fun getAllStores(request: GetAllStoresRequest): GetStoresResponse =
            execute(request, gateway::getAllStores).let {
                GetStoresResponse.getDefaultInstance()
            }

    override suspend fun createStore(request: CreateStoreRequest): CreatedStoreResponse =
            execute(request, gateway::createStore).let { CreatedStoreResponse.getDefaultInstance() }

    override suspend fun getStoreByName(request: GetStoreByNameRequest): GetStoreResponse =
            ProtoBuf.decodeFromByteArray(ProtoBuf.encodeToByteArray(execute(request, gateway::getStoreByName)))

    override suspend fun updateStore(request: UpdateStoreRequest): UpdateStoreResponse = throw
    StatusException(Status.UNIMPLEMENTED.withDescription("Method proto.store.service.StoreService.UpdateStore is unimplemented"))

    override suspend fun deleteStore(request: DeleteStoreByIdRequest): DeleteStoreResponse = throw
    StatusException(Status.UNIMPLEMENTED.withDescription("Method proto.store.service.StoreService.DeleteStore is unimplemented"))
}
