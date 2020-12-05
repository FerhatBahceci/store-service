package store.service

import com.mongodb.client.model.Filters
import com.mongodb.client.result.DeleteResult
import com.mongodb.client.result.InsertOneResult
import com.mongodb.client.result.UpdateResult
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoCollection
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalSerializationApi
interface StoreGateway {
    suspend fun getAllStores(): List<Store>
    suspend fun getStoreById(id: String): Store
    suspend fun getStoreByType(type: String): List<Store>
    suspend fun createStore(store: Store): InsertOneResult
    suspend fun deleteStore(id: String): DeleteResult
    suspend fun updateStore(store: Store): UpdateResult
}

@ExperimentalSerializationApi
@Singleton
class StoreGatewayImpl(@Inject private val client : MongoClient) : StoreGateway {

    private val collection : MongoCollection<Store> = client.getDatabase("mongodb").getCollection("store", Store::class.java)

    override suspend fun getAllStores() = collection.find().asFlow().toList()

    override suspend fun getStoreById(id: String) = collection.find(Filters.eq("id", id)).awaitFirst()

    override suspend fun getStoreByType(type: String) = collection.find(Filters.eq("type", type)).asFlow().toList()

    override suspend fun createStore(store: Store) = collection.insertOne(store).awaitFirst()

    override suspend fun deleteStore(id: String) = collection.deleteOne(Filters.eq("id", id)).awaitFirst()

    override suspend fun updateStore(store: Store) = collection.updateOne(Filters.eq("id"), Filters.eq(store)).awaitFirst()
}