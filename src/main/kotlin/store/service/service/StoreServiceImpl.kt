package store.service.service

import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.serialization.*
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
import proto.store.service.Store
import store.service.gateway.StoreGateway

/*
TODO How to go from Kotlin --> Proto e.g  ProtoBuf.decodeFromByteArray<DayOfWeek>(ProtoBuf.encodeToByteArray(proto.store.service.DayOfWeek.forNumber(2))),
  since proto messages are not @Serializable, how will this be sovled?
*/

@ExperimentalSerializationApi
@GrpcService
class StoreServiceImpl constructor(
    @Inject private val gateway: StoreGateway,
    @Inject override val coroutineContext: CoroutineContext
) :
    StoreServiceGrpcKt.StoreServiceCoroutineImplBase(), CoroutineScope {

    override suspend fun getAllStores(request: GetAllStoresRequest): GetStoresResponse =
        execute(request, gateway::getAllStores).mapToProtoStores()

    override suspend fun getStoreByType(request: GetStoreByTypeRequest): GetStoresResponse =
        execute(request, gateway::getStoreByType).mapToProtoStores()

    override suspend fun getStoreByName(request: GetStoreByNameRequest): GetStoreResponse =
        execute(request, gateway::getStoreByName).mapToProtoStore().let {
            GetStoreResponse.newBuilder().setStore(it).build()
        }

    override suspend fun createStore(request: CreateStoreRequest): CreatedStoreResponse =
        execute(request, gateway::createStore).let { CreatedStoreResponse.getDefaultInstance() }

    override suspend fun updateStore(request: UpdateStoreRequest): UpdateStoreResponse =
        execute(request, gateway::updateStore).let { UpdateStoreResponse.getDefaultInstance() }

    override suspend fun deleteStore(request: DeleteStoreByIdRequest): DeleteStoreResponse =
        execute(request, gateway::deleteStore).let { DeleteStoreResponse.getDefaultInstance() }

    private fun List<store.service.gateway.Store>.mapToProtoStores() =
        this.map { it.mapToProtoStore() }.let {
            GetStoresResponse.newBuilder()
                .setStores(Stores.newBuilder().addAllStores(it).build())
                .build()
        }

    private fun store.service.gateway.Store.mapToProtoStore() =
        Store.newBuilder()
            .setCoordinates(coordinates?.mapToProtoCoordinates())
            .setDescription(description)
            .setId(id)
            .setName(name)
            .setPhoneNo(phoneNo)
            .setType(type?.mapToProtoStoreType())
            .build()

    private fun store.service.gateway.Store.Coordinates.mapToProtoCoordinates() =
        Coordinates.newBuilder()
            .setLatitude(latitude ?: 0)
            .setLongitude(longitude ?: 0)
            .build()

    private fun store.service.gateway.Store.Type.mapToProtoStoreType() =
        Type.forNumber(this.ordinal)
}

@ObsoleteCoroutinesApi
@Factory
private class CoroutineContextFactory {

    @Bean
    fun coroutineContext() = newFixedThreadPoolContext(4, "grpc-thread-pool")
}
