package store.service.service

import javax.inject.Inject
import io.micronaut.grpc.annotation.GrpcService
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.*
import proto.store.service.*
import proto.store.service.DeleteStoreByIdRequest
import proto.store.service.GetAllStoresRequest
import proto.store.service.GetStoreByNameRequest
import proto.store.service.UpdateStoreRequest
import kotlin.coroutines.CoroutineContext
import utility.grpc.execute
import proto.store.service.CreateStoreRequest
import proto.store.service.GetStoreByTypeRequest
import store.service.gateway.Store
import store.service.gateway.StoreGateway

@ExperimentalSerializationApi
@GrpcService
class StoreServiceImpl constructor(
    @Inject private val gateway: StoreGateway,
    @Inject override val coroutineContext: CoroutineContext
) : StoreServiceGrpcKt.StoreServiceCoroutineImplBase(), CoroutineScope {

    override suspend fun getAllStores(request: GetAllStoresRequest): GetStoresResponse =
        execute(request, ::getAllStores, ::getStoresResponse)

    override suspend fun getStoreByType(request: GetStoreByTypeRequest): GetStoresResponse =
        execute(request, ::getStoreByType, ::getStoresResponse)

    override suspend fun getStoreByName(request: GetStoreByNameRequest): GetStoreResponse =
        execute(request, ::getStoreByName, ::getStoreResponse)

    override suspend fun createStore(request: CreateStoreRequest): CreatedStoreResponse =
        execute(request, ::createStore, ::createdStoreResponse)

    override suspend fun updateStore(request: UpdateStoreRequest): UpdateStoreResponse =
        execute(request, ::updateStore, ::updatedStoreResponse)

    override suspend fun deleteStore(request: DeleteStoreByIdRequest): DeleteStoreResponse =
        execute(request, ::deleteStore, ::deletedStoreResponse)

    private suspend fun getAllStores(request: store.service.service.GetAllStoresRequest) =
        gateway.getAllStores()

    private suspend fun getStoreByName(request: store.service.service.GetStoreByNameRequest) =
        gateway.getStoreByName(request.name)

    private suspend fun getStoreByType(request: store.service.service.GetStoreByTypeRequest) =
        gateway.getStoreByType(Store.Type.valueOf(request.storeType.name))

    private suspend fun createStore(request: store.service.service.CreateStoreRequest) =
        gateway.createStore(request.store)

    private suspend fun deleteStore(request: store.service.service.DeleteStoreByIdRequest) =
        gateway.deleteStore(request.id)

    private suspend fun updateStore(request: store.service.service.UpdateStoreRequest) =
        gateway.updateStore(request.store, request.id)
}
