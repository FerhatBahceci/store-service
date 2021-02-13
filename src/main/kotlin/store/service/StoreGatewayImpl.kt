package store.service

import com.mongodb.client.model.Filters
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoCollection
import kotlinx.coroutines.flow.toList
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.reactive.*
import java.lang.Exception

@ExperimentalSerializationApi
@Singleton
class StoreGatewayImpl(@Inject private val client: MongoClient) : StoreGateway {

    private val collection: MongoCollection<Store> =
        client.getDatabase("store-db").getCollection("store", Store::class.java)

    override suspend fun getAllStores(r: GetAllStoresRequest): List<Store> =
        collection.find().asFlow().toList()

    override suspend fun getStoreByName(r: GetStoreByNameRequest): Store =
        collection.find(Filters.eq("name", r.name)).awaitFirst()

    override suspend fun getStoreByType(r: GetStoreByTypeRequest): List<Store> =
        collection.find().asFlow().toList()

    override suspend fun createStore(r: CreateStoreRequest) {
        try {
            collection.insertOne(r.store).awaitFirst()
        } catch (e : Exception){
            print(e.message)
        }
    }

    override suspend fun deleteStore(r: DeleteStoreByIdRequest) {
        collection.deleteOne(Filters.eq("id", r.id)).awaitFirst()
    }

    override suspend fun updateStore(r: UpdateStoreRequest) {
        collection.updateOne(Filters.eq("id"), Filters.eq(r.store)).awaitFirst()
    }
}
