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
    suspend fun getAllStores(request: GetAllStoresRequest): List<Store>
    suspend fun getStoreById(request: GetStoreByIdRequest): Store
    suspend fun getStoreByType(request: GetStoreByTypeRequest): List<Store>
    suspend fun createStore(request: CreateStoreRequest): InsertOneResult
    suspend fun deleteStore(request: DeleteStoreByIdRequest): DeleteResult
    suspend fun updateStore(request: UpdateStoreRequest): UpdateResult
}

@ExperimentalSerializationApi
@Singleton
class StoreGatewayImpl(@Inject private val client: MongoClient) : StoreGateway {

    private val collection: MongoCollection<Store> = client.getDatabase("mongodb").getCollection("store", Store::class.java)

    override suspend fun getAllStores(request: GetAllStoresRequest): List<Store> = collection.find().asFlow().toList()

    override suspend fun getStoreById(request: GetStoreByIdRequest): Store = collection.find(Filters.eq("id", request.id)).awaitFirst()

    override suspend fun getStoreByType(request: GetStoreByTypeRequest): List<Store> = collection.find(Filters.eq("type", request.type)).asFlow().toList()

    override suspend fun createStore(request: CreateStoreRequest): InsertOneResult = collection.insertOne(request.store).awaitFirst()

    override suspend fun deleteStore(request: DeleteStoreByIdRequest): DeleteResult = collection.deleteOne(Filters.eq("id", request.id)).awaitFirst()

    override suspend fun updateStore(request: UpdateStoreRequest): UpdateResult = collection.updateOne(Filters.eq("id"), Filters.eq(request.store)).awaitFirst()
}
