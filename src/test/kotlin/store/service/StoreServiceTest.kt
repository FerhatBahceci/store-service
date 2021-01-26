package store.service

import com.google.protobuf.StringValue
import com.google.protobuf.Timestamp
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import io.micronaut.grpc.server.GrpcServerChannel
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Test
import proto.store.service.*
import proto.store.service.CreateStoreRequest
import proto.store.service.Store
import javax.inject.Inject

@MicronautTest
class StoreServiceTest(@Inject private val blockingStub: StoreServiceGrpc.StoreServiceBlockingStub) {

    @Test
    fun createStoreTest() {


       val store =  Store.newBuilder()
                .setDayOfWeek(DayOfWeek.SATURDAY)
                .setDescription(StringValue.of("DESCRIPTION"))
                .setId(StringValue.of("ID"))
                .setName(StringValue.of("NAME"))
                .setPhoneNo(StringValue.of("112"))
                .setStoreType(Type.GROCERIES)
                .build()

        val r = CreateStoreRequest.newBuilder().setStore(store).build()
        blockingStub.createStore(r)
    }
}

@Factory
class Clients {

    @Bean
    fun blockingStub(@GrpcChannel(GrpcServerChannel.NAME) channel: ManagedChannel): StoreServiceGrpc.StoreServiceBlockingStub {
        return StoreServiceGrpc.newBlockingStub(channel)
    }
}
