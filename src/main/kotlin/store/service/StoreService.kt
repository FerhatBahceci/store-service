/*
package store.service

import io.micronaut.context.annotation.Bean
import io.micronaut.grpc.annotation.GrpcService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.protobuf.ProtoBuf.Default.decodeFromByteArray
import kotlinx.serialization.protobuf.ProtoBuf.Default.encodeToByteArray
import proto.store.service.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@Bean
fun coroutineContext() = newFixedThreadPoolContext(4, "grpc-server")

@ExperimentalSerializationApi
@GrpcService
class StoreServiceImpl @ObsoleteCoroutinesApi constructor(private val gateway: StoreGateway,
                                                          @Inject override val coroutineContext: CoroutineContext = newFixedThreadPoolContext(4, "grpc-server")) :
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
*/
