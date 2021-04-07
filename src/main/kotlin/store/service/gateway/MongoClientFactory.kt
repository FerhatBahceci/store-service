package store.service.gateway

import com.mongodb.MongoClientSettings
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import kotlinx.serialization.ExperimentalSerializationApi
import org.bson.codecs.configuration.CodecRegistries.*
import org.bson.codecs.pojo.PojoCodecProvider
import utility.bson.ProtoTimestampCodec

@Factory
class MongoClientFactory {

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
