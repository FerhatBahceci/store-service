package store.service.service

import io.micronaut.grpc.annotation.GrpcService
import kotlinx.coroutines.CoroutineScope
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
import store.service.gateway.StoreGateway
import store.service.service.StoreResponse.Companion.createdStoreResponse
import store.service.service.StoreResponse.Companion.deletedStoreResponse
import store.service.service.StoreResponse.Companion.getStoreResponse
import store.service.service.StoreResponse.Companion.getStoresResponse
import store.service.service.StoreResponse.Companion.updatedStoreResponse

@ExperimentalSerializationApi
@GrpcService
class StoreServiceImpl constructor(
        @Inject private val gateway: StoreGateway,
        @Inject override val coroutineContext: CoroutineContext
) :
        StoreServiceGrpcKt.StoreServiceCoroutineImplBase(), CoroutineScope {

    override suspend fun getAllStores(request: GetAllStoresRequest): GetStoresResponse =
            execute(request, gateway::getAllStores, ::getStoresResponse)

    override suspend fun getStoreByType(request: GetStoreByTypeRequest): GetStoresResponse =
            execute(request, gateway::getStoreByType, ::getStoresResponse)

    override suspend fun getStoreByName(request: GetStoreByNameRequest): GetStoreResponse =
            execute(request, gateway::getStoreByName, ::getStoreResponse)

    override suspend fun createStore(request: CreateStoreRequest): CreatedStoreResponse =
            execute(request, gateway::createStore, ::createdStoreResponse)

    override suspend fun updateStore(request: UpdateStoreRequest): UpdateStoreResponse =
            execute(request, gateway::updateStore, ::updatedStoreResponse)

    override suspend fun deleteStore(request: DeleteStoreByIdRequest): DeleteStoreResponse =
            execute(request, gateway::deleteStore,::deletedStoreResponse)

}
