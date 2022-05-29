package store.service.service

import io.micronaut.grpc.annotation.GrpcService
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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
import java.time.Instant

@ExperimentalSerializationApi
@GrpcService
class StoreServiceImpl constructor(
    @Inject private val gateway: StoreGateway,
    @Inject override val coroutineContext: CoroutineContext,
    @Inject val kafkaClient: KafkaClient<StoreSearchEvent>
) : StoreServiceGrpcKt.StoreServiceCoroutineImplBase(), CoroutineScope {

    private val LOGGER = LoggerFactory.getLogger(StoreServiceImpl::class.java)
    override suspend fun getStoreByName(request: GetStoreByNameRequest): GetStoreResponse =
        execute(request, ::getStoreByNameAndPublish, ::createGetStoreResponse)

    private suspend fun getStoreByNameAndPublish(request: store.service.service.GetStoreByNameRequest) =
        withContext(Dispatchers.IO) {
            kafkaClient.publish("store_search", request.name, request.createStoreSearchEvent())
        }
            .run {
                LOGGER.info("Recorded store search: ${request.name}, search_id: ${offset()}, time: ${Instant.ofEpochMilli(timestamp())}")
                getStoreByName(request.name)
            }

    override suspend fun getAllStores(request: GetAllStoresRequest): GetStoresResponse =
        execute(request, ::getAllStores, ::createGetStoresResponse)

    override suspend fun getStoreByType(request: GetStoreByTypeRequest): GetStoresResponse =
        execute(request, ::getStoreByType, ::createGetStoresResponse)

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

    private suspend fun getStoreByName(name: String) =
        gateway.getStoreByName(name)
}
