package store.service.gateway

import com.mongodb.client.model.Filters.eq
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoCollection
import kotlinx.coroutines.flow.toList
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Singleton
import kotlinx.coroutines.reactive.*
import store.service.Store
import utility.response.ResponseException
import javax.inject.Inject

@ExperimentalSerializationApi
@Singleton
class StoreGatewayImpl(@Inject private val mongoClient: MongoClient) : StoreGateway {

    private val collection: MongoCollection<Store> =
            mongoClient
                    .getDatabase("store-db")
                    .getCollection("store", Store::class.java)

    override suspend fun getAllStores(): List<Store> =
            collection.find().asFlow().toList()

    override suspend fun getStoreByType(type: Store.Type): List<Store> =
            collection.find(eq("type", type.name)).asFlow().toList()

    override suspend fun createStore(store: Store) {
        collection.insertOne(store).awaitFirstOrElse {
            throw ResponseException.InternalError("Could not CREATE store: ${store.name} ")
        }
    }

    override suspend fun getStoreByName(name: String): Store =
            collection.find(eq("name", name)).limit(1)
                    .awaitFirstOrElse {
                        throw ResponseException.NotFound("Could not GET store by name: ${name}")
                    }

    override suspend fun deleteStore(id: String) {
        collection.deleteOne(eq("_id", id)).awaitFirstOrElse {
            throw ResponseException.InternalError("Could not DELETE store by id: ${id} ")
        }
    }

    override suspend fun updateStore(store: Store, id: String): Store =
            collection.replaceOne(eq("_id", id), store.copy(id = id)).awaitFirst()
                    .run {
                        if (!this.wasAcknowledged()) {
                            throw ResponseException.InternalError("Could not UPDATE store by id: ${id} ")
                        }
                        collection.find(eq("_id", id)).limit(1).awaitFirst()
                    }
}
