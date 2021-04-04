package store.service.gateway

import com.mongodb.MongoClientSettings
import com.mongodb.client.model.Filters.eq
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import com.mongodb.reactivestreams.client.MongoCollection
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import kotlinx.coroutines.flow.toList
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Singleton
import kotlinx.coroutines.reactive.*
import org.bson.codecs.configuration.CodecRegistries.*
import org.bson.codecs.pojo.PojoCodecProvider
import utility.bson.ProtoTimestampCodec
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

    override suspend fun getStoreByName(request: GetStoreByNameRequest): Store =
            collection.find(eq("name", request.name)).limit(1).awaitFirst()

    override suspend fun getStoreByType(request: GetStoreByTypeRequest): List<Store> =
            collection.find(eq("type", request.type)).asFlow().toList()

    override suspend fun createStore(request: CreateStoreRequest) {
        collection.insertOne(request.store).awaitFirst()
    }

    override suspend fun deleteStore(request: DeleteStoreByIdRequest) {
        collection.deleteOne(eq("id", request.id)).awaitFirst()
    }

    override suspend fun updateStore(request: UpdateStoreRequest) {
        collection.updateOne(eq("id"), eq(request.store)).awaitFirst()
    }
}

@Factory
private class MongoClientFactory {

    @ExperimentalSerializationApi
    @Bean
    private fun mongoClient(): MongoClient =
            MongoClients.create(
                    MongoClientSettings.builder()
                            .codecRegistry(
                                    fromRegistries(
                                            fromCodecs(ProtoTimestampCodec()),
                                            MongoClientSettings.getDefaultCodecRegistry(),
                                            fromProviders(PojoCodecProvider.builder().automatic(true).build())
                                    )
                            )
                            .build()
            )
}
