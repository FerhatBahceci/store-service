package store.service.service

import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.KafkaKey
import io.micronaut.configuration.kafka.annotation.Topic
import io.micronaut.context.annotation.Property
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.RecordMetadata

@KafkaClient(
    acks = KafkaClient.Acknowledge.ALL,
    properties = [
        Property(name = ProducerConfig.RETRIES_CONFIG, value = "5"),
        Property(
            name = ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
            value = "org.apache.kafka.common.serialization.StringSerializer"
        ),
        Property(
            name = ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
            value = "io.confluent.kafka.serializers.protobuf.KafkaProtobufSerializer"
        ),
        Property(
            name = "schema.registry.url",
            value = "http://localhost:8081"
        )
    ]
)
interface ReactiveKafkaClient<T> {
    fun publish(@Topic topic: String, @KafkaKey key: String, value: T): RecordMetadata
}