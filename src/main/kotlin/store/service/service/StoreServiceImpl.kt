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
import store.service.gateway.StoreGateway
import store.service.service.StoreMapper.Companion.mapToProtoStore

/*
TODO How to go from Kotlin --> Proto e.g  ProtoBuf.decodeFromByteArray<DayOfWeek>(ProtoBuf.encodeToByteArray(proto.store.service.DayOfWeek.forNumber(2))),
  since proto messages are not @Serializable, how will this be sovled?

 evualateAndPackage to failed response with stacktrace message and status
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
        execute(request, gateway::getStoreByName).mapToProtoStore()
            .let { GetStoreResponse.newBuilder().setResponse(createResponse()).setStore(it).build() }

    override suspend fun createStore(request: CreateStoreRequest): CreatedStoreResponse =
        execute(request, gateway::createStore).let {
            CreatedStoreResponse.newBuilder().setResponse(createResponse(201)).build()
        }

    override suspend fun updateStore(request: UpdateStoreRequest): UpdateStoreResponse =
        execute(request, gateway::updateStore).let {
            UpdateStoreResponse.newBuilder().setResponse(createResponse(204)).build()
        }

    override suspend fun deleteStore(request: DeleteStoreByIdRequest): DeleteStoreResponse =
        execute(request, gateway::deleteStore).let {
            DeleteStoreResponse.newBuilder().setResponse(createResponse(204)).build()
        }

    private fun List<store.service.gateway.Store>.mapToProtoStores() =
        this.map { it.mapToProtoStore() }.let {
            GetStoresResponse.newBuilder()
                .setResponse(createResponse())  //TODO shoul be evaluated if exception is thrown, NOT_FOUND(404), INTERAL_ERROR(500), fix evaluation
                .setStores(Stores.newBuilder().addAllStores(it).build()).setResponse(createResponse())
                .build()
        }

    //TODO should be replaced once Response classes are fixed
    private fun createResponse(status: Int = 200, errorMessage: String = "") =
        Response.newBuilder().setStatus(status).setMessage(errorMessage).build()
}

@ObsoleteCoroutinesApi
@Factory
class CoroutineContextFactory {

    companion object {

        @Bean
        fun coroutineContext() = newFixedThreadPoolContext(4, "grpc-thread-pool")

    }
}
