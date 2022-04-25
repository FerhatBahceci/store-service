package store.service.service

import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.KafkaKey
import io.micronaut.configuration.kafka.annotation.Topic
import org.apache.kafka.clients.producer.RecordMetadata
import reactor.core.publisher.Mono

@KafkaClient(
    acks = KafkaClient.Acknowledge.ALL,
)
interface ReactiveKafkaClient<T> {
    fun publish(@Topic topic: String, @KafkaKey key: String, value: T): Mono<RecordMetadata>
}