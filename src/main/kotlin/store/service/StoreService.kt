package store.service

import io.micronaut.grpc.annotation.GrpcService
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.protobuf.ProtoBuf.Default.decodeFromByteArray
import kotlinx.serialization.protobuf.ProtoBuf.Default.encodeToByteArray
import proto.store.service.*
import kotlin.coroutines.CoroutineContext

@ExperimentalSerializationApi
@GrpcService
class StoreServiceImpl(private val gateway: StoreGateway,
                       override val coroutineContext: CoroutineContext) :
        StoreServiceGrpcKt.StoreServiceCoroutineImplBase(), CoroutineScope {

    override suspend fun getStoreByType(request: GetStoreByTypeRequest): GetStoresResponse =
            GetStoresResponse.newBuilder().setStores(gateway.getStoreByType(request.type.name).mapToProto()).build()

    override suspend fun geAllStores(request: GetStoresRequest): GetStoresResponse = GetStoresResponse.newBuilder().setStores(gateway.getAllStores().mapToProto()).build()

    override suspend fun createStore(request: CreateStoreRequest): CreatedStoreResponse =
            gateway.createStore(request.store.mapFromProto()).let { CreatedStoreResponse.newBuilder().build() }

    override suspend fun getStoreById(request: GetStoreByIdRequest): GetStoreResponse =
            GetStoreResponse.newBuilder().setStore(gateway.getStoreById(request.id.toString()).mapToProto()).build()

    override suspend fun updateStore(request: proto.store.service.Store): UpdateStoreResponse =
            gateway.updateStore(request.mapFromProto()).let { UpdateStoreResponse.newBuilder().build() }

    override suspend fun deleteStore(request: DeleteStoreByIdRequest): DeleteStoreResponse =
            gateway.deleteStore(request.id.toString()).let { DeleteStoreResponse.newBuilder().build() }

    private fun List<Store>.mapToProto() = Stores.newBuilder().addAllStores(this.map { it.mapToProto() })

    private fun proto.store.service.Store.mapFromProto() = decodeFromByteArray(Store.serializer(), this.toByteArray())

    private fun Store.mapToProto() = proto.store.service.Store.parseFrom(encodeToByteArray(Store.serializer(), this))
}
