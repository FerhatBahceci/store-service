package store.service.service

import io.micronaut.grpc.annotation.GrpcService
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.*
import org.slf4j.LoggerFactory
import proto.store.service.*
import proto.store.service.DeleteStoreByIdRequest
import proto.store.service.GetAllStoresRequest
import proto.store.service.GetStoreByNameRequest
import proto.store.service.UpdateStoreRequest
import proto.store.service.CreateStoreRequest
import proto.store.service.GetStoreByTypeRequest
import kotlin.coroutines.CoroutineContext
import utility.grpc.execute
import store.service.gateway.Store
import store.service.gateway.StoreGateway

@ExperimentalSerializationApi
@GrpcService
class StoreServiceImpl constructor(
    @Inject private val gateway: StoreGateway,
    @Inject override val coroutineContext: CoroutineContext,
    @Inject val kafkaClient: ReactiveKafkaClient<StoreSearchEvent>
) : StoreServiceGrpcKt.StoreServiceCoroutineImplBase(), CoroutineScope {

    private val LOGGER = LoggerFactory.getLogger(StoreServiceImpl::class.java)
    override suspend fun getAllStores(request: GetAllStoresRequest): GetStoresResponse =
        execute(request, ::getAllStores, ::createGetStoresResponse)

    override suspend fun getStoreByType(request: GetStoreByTypeRequest): GetStoresResponse =
        execute(request, ::getStoreByType, ::createGetStoresResponse)

    override suspend fun getStoreByName(request: GetStoreByNameRequest): GetStoreResponse =
        execute(request, ::getStoreByName, ::createGetStoreResponse)

    override suspend fun createStore(request: CreateStoreRequest): CreatedStoreResponse =
        execute(request, ::createStore, ::createCreatedStoreResponse)

    override suspend fun updateStore(request: UpdateStoreRequest): UpdateStoreResponse =
        execute(request, ::updateStore, ::createUpdatedStoreResponse)

    override suspend fun deleteStore(request: DeleteStoreByIdRequest): DeleteStoreResponse =
        execute(request, ::deleteStore, ::createDeletedStoreResponse)

    private suspend fun getAllStores(request: store.service.service.GetAllStoresRequest) =
        gateway.getAllStores()

    private suspend fun getStoreByType(request: store.service.service.GetStoreByTypeRequest) =
        gateway.getStoreByType(Store.Type.valueOf(request.storeType.name))

    private suspend fun createStore(request: store.service.service.CreateStoreRequest) =
        gateway.createStore(request.store)

    private suspend fun deleteStore(request: store.service.service.DeleteStoreByIdRequest) =
        gateway.deleteStore(request.id)

    private suspend fun updateStore(request: store.service.service.UpdateStoreRequest) =
        gateway.updateStore(request.store, request.id)

    private suspend fun getStoreByName(request: store.service.service.GetStoreByNameRequest) =
        kafkaClient.publish("store_search", request.name, request.createStoreSearchEvent())
            .doOnSuccess {
                LOGGER.info("Recorded store search: ${request.name}, search_id: ${it.offset()}, time: ${it.timestamp()}")
            }.let {
                gateway.getStoreByName(request.name)
            }

}
