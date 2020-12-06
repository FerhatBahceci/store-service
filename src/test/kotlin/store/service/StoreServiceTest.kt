package store.service

import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import io.micronaut.grpc.server.GrpcServerChannel
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Test
import proto.store.service.StoreServiceGrpc

@MicronautTest
class StoreServiceTest() {

    @Test
    fun testHelloWorld() {

    }
}

@Factory
class Clients {
    @Bean
    fun blockingStub(@GrpcChannel(GrpcServerChannel.NAME) channel: ManagedChannel): StoreServiceGrpc.StoreServiceBlockingStub {
        return StoreServiceGrpc.newBlockingStub(channel)
    }
}
