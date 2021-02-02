package store.service

import com.mongodb.client.model.Filters
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoCollection
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.serialization.ExperimentalSerializationApi
import store.service.StoreCollection.Companion.mapToCollection
import utility.UnitData
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalSerializationApi
@Singleton
class StoreGatewayImpl(@Inject private val client: MongoClient) : StoreGateway {

    private val collection: MongoCollection<StoreCollection> =
        client.getDatabase("store-db").getCollection("store", StoreCollection::class.java)

    override suspend fun getAllStores(r: GetAllStoresRequest): List<Store> = collection.find().asFlow().toList().map { it.mapToStore() }

    override suspend fun getStoreById(r: GetStoreByIdRequest): Store =
        collection.find(Filters.eq("id", r.id)).awaitFirst().mapToStore()

    override suspend fun getStoreByType(r: GetStoreByTypeRequest): List<Store> =
        collection.find(Filters.eq("type", r.type)).asFlow().toList().map{it.mapToStore()}

    override suspend fun createStore(r: CreateStoreRequest): UnitData =
        collection.insertOne(r.store?.mapToCollection()).awaitFirst().let { UnitData() }

    override suspend fun deleteStore(r: DeleteStoreByIdRequest): UnitData =
        collection.deleteOne(Filters.eq("id", r.id)).awaitFirst().let { UnitData() }

    override suspend fun updateStore(r: UpdateStoreRequest): UnitData =
        collection.updateOne(Filters.eq("id"), Filters.eq(r.store)).awaitFirst().let { UnitData() }

    private fun StoreCollection.mapToStore() =
        Store(description = description, id = id , name = name, phoneNo = phoneNo, type = Store.Type.valueOf(type!!)) // TODO remove nullable???
}
