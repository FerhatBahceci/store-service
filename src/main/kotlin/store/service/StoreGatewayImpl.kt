package store.service

import com.mongodb.MongoClientSettings
import com.mongodb.client.model.Filters
import com.mongodb.reactivestreams.client.MongoClients
import com.mongodb.reactivestreams.client.MongoCollection
import kotlinx.coroutines.flow.toList
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Singleton
import kotlinx.coroutines.reactive.*
import org.bson.codecs.configuration.CodecRegistries
import utility.bson.ProtoTimestampCodec

@ExperimentalSerializationApi
@Singleton
class StoreGatewayImpl : StoreGateway {

    private val collection: MongoCollection<Store> =
            createMongoClient()
                    .getDatabase("store-db")
                    .getCollection("store", Store::class.java)

    override suspend fun getAllStores(request: GetAllStoresRequest): List<Store> =
            collection.find().asFlow().toList()

    override suspend fun getStoreByName(request: GetStoreByNameRequest): Store =
            collection.find(Filters.eq("name", request.name)).awaitFirst()

    override suspend fun getStoreByType(request: GetStoreByTypeRequest): List<Store> =
            collection.find().asFlow().toList()

    override suspend fun createStore(request: CreateStoreRequest) {
        collection.insertOne(request.store).awaitFirst()
    }

    override suspend fun deleteStore(request: DeleteStoreByIdRequest) {
        collection.deleteOne(Filters.eq("id", request.id)).awaitFirst()
    }

    override suspend fun updateStore(request: UpdateStoreRequest) {
        collection.updateOne(Filters.eq("id"), Filters.eq(request.store)).awaitFirst()
    }

    private fun createMongoClient() =
            MongoClients.create(
                    MongoClientSettings.builder()
                            .codecRegistry(
                                    CodecRegistries.fromRegistries(
                                            CodecRegistries.fromCodecs(ProtoTimestampCodec())
                                    )
                            )
                            .build()
            )
}
