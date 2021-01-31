package store.service

import com.mongodb.client.model.Filters
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoCollection
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.serialization.ExperimentalSerializationApi
import utility.UnitData
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalSerializationApi
@Singleton
class StoreGatewayImpl(@Inject private val client: MongoClient) : StoreGateway {

    private val collection: MongoCollection<Store> = client.getDatabase("store-db").getCollection("store", Store::class.java)

    override suspend fun getAllStores(r: GetAllStoresRequest): List<Store> = collection.find().asFlow().toList()

    override suspend fun getStoreById(r: GetStoreByIdRequest): Store = collection.find(Filters.eq("id", r.id)).awaitFirst()

    override suspend fun getStoreByType(r: GetStoreByTypeRequest): List<Store> = collection.find(Filters.eq("type", r.type)).asFlow().toList()

    override suspend fun createStore(r: CreateStoreRequest): UnitData =
            collection.insertOne(r.store).awaitFirst().let { UnitData() }

    override suspend fun deleteStore(r: DeleteStoreByIdRequest): UnitData = collection.deleteOne(Filters.eq("id", r.id)).awaitFirst().let { UnitData() }

    override suspend fun updateStore(r: UpdateStoreRequest): UnitData = collection.updateOne(Filters.eq("id"), Filters.eq(r.store)).awaitFirst().let { UnitData() }
}
