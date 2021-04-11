package store.service.gateway

import com.mongodb.client.model.Filters.eq
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoCollection
import kotlinx.coroutines.flow.toList
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Singleton
import kotlinx.coroutines.reactive.*
import utility.response.ResponseException
import javax.inject.Inject

@ExperimentalSerializationApi
@Singleton
class StoreGatewayImpl(@Inject private val mongoClient: MongoClient) : StoreGateway {

    private val collection: MongoCollection<Store> =
            mongoClient
                    .getDatabase("store-db")
                    .getCollection("store", Store::class.java)

    override suspend fun getAllStores(request: GetAllStoresRequest): List<Store> =
            collection.find().asFlow().toList()

    override suspend fun getStoreByType(request: GetStoreByTypeRequest): List<Store> =
            collection.find(eq("type", request.storeType?.name)).asFlow().toList()

    override suspend fun createStore(request: CreateStoreRequest) {
        collection.insertOne(request.store).awaitFirstOrElse {
            throw ResponseException.InternalError("Could not CREATE store: ${request.store.name} ")
        }
    }

    override suspend fun getStoreByName(request: GetStoreByNameRequest): Store =
            collection.find(eq("name", request.name)).limit(1)
                    .awaitFirstOrElse {
                        throw ResponseException.NotFound("Could not GET store by name: ${request.name}")
                    }

    override suspend fun deleteStore(request: DeleteStoreByIdRequest) {
        collection.deleteOne(eq("_id", request.id)).awaitFirstOrElse {
            throw ResponseException.InternalError("Could not DELETE store by id: ${request.id} ")
        }
    }

    override suspend fun updateStore(request: UpdateStoreRequest): Store =
            collection.replaceOne(eq("_id", request.id), request.store.copy(id = request.id)).awaitFirst()
                    .run {
                        if (!this.wasAcknowledged()) {
                            throw ResponseException.InternalError("Could not UPDATE store by id: ${request.id} ")
                        }
                        collection.find(eq("_id", request.id)).limit(1).awaitFirst()
                    }
}
