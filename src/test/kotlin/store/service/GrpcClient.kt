package store.service

import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import proto.store.service.StoreServiceGrpc

@Factory
class GrpcClient {

    @Bean
    fun storeServiceBlockingStub(@GrpcChannel channel: ManagedChannel): StoreServiceGrpc.StoreServiceBlockingStub {
        return StoreServiceGrpc.newBlockingStub(channel)
    }
}
