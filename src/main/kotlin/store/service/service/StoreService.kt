package store.service.service

import com.mongodb.client.result.InsertOneResult
import com.mongodb.client.result.UpdateResult
import io.grpc.Status
import io.grpc.StatusException
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray
import kotlinx.serialization.protobuf.ProtoBuf
import proto.store.service.*
import store.service.gateway.StoreGateway
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@ExperimentalSerializationApi
@GrpcService
class StoreServiceImpl constructor(@Inject private val gateway: StoreGateway,
                                   @Inject override val coroutineContext: CoroutineContext) :
        StoreServiceGrpcKt.StoreServiceCoroutineImplBase(), CoroutineScope {

    override suspend fun getStoreByType(request: GetStoreByTypeRequest): GetStoresResponse = throw
    StatusException(Status.UNIMPLEMENTED.withDescription("Method proto.store.service.StoreService.GetStoreByType is unimplemented"))

    override suspend fun geAllStores(request: GetStoresRequest): GetStoresResponse = {

    }

    override suspend fun createStore(request: CreateStoreRequest): CreatedStoreResponse =
            createCreatedStoreResponse(execute(encodeDecode(request), gateway::createStore))

    override suspend fun getStoreById(request: GetStoreByIdRequest): GetStoreResponse = createGetStoreResponse(execute(request.id.toString(), gateway::getStoreById))

    override suspend fun updateStore(request: Store): UpdateStoreResponse =
            createUpdateStoreResponse(execute(encodeDecode(request), gateway::updateStore))

    override suspend fun deleteStore(request: DeleteStoreByIdRequest): DeleteStoreResponse = throw
    StatusException(Status.UNIMPLEMENTED.withDescription("Method proto.store.service.StoreService.DeleteStore is unimplemented"))

    private suspend inline fun <reified PROTO, REQ, reified RESP> execute(req: REQ, crossinline callBack: suspend (REQ) -> RESP): PROTO =
            encodeDecode(callBack.invoke(req))

    private inline fun <reified ENCODE, reified DECODE> encodeDecode(value: ENCODE): DECODE =
            ProtoBuf.decodeFromByteArray(ProtoBuf.encodeToByteArray(value))

    private fun createGetStoreResponse(store: Store) =
            GetStoreResponse.newBuilder()
                    .setStore(store)
                    .build()

    private fun createGetStoresResponse(stores: List<Store>) =
            Stores.newBuilder()
                    .addAllStores(stores)
                    .build()

    private fun createCreatedStoreResponse(insertOneResult: InsertOneResult) =
            CreatedStoreResponse
                    .newBuilder()
                    .build()

    private fun createUpdateStoreResponse(updateResult: UpdateResult) =
            UpdateStoreResponse
                    .newBuilder()
                    .build()

    private fun createDeleteResponse() =
            DeleteStoreResponse
                    .newBuilder()
                    .build()
}

@ObsoleteCoroutinesApi
@Factory
class CoroutineContextFactory {

    @Bean
    fun coroutineContext() = newFixedThreadPoolContext(4, "grpc-thread-pool")
}
