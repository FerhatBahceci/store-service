/*
package store.service

import com.google.protobuf.StringValue
import io.grpc.ManagedChannel
import io.kotest.core.spec.style.ShouldSpec
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import io.micronaut.grpc.server.GrpcServerChannel
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.protobuf.ProtoBuf
import org.junit.jupiter.api.Test
import proto.store.service.*
import proto.store.service.CreateStoreRequest
import proto.store.service.Store
import utility.UnitData
import javax.inject.Inject

@ExperimentalSerializationApi
@MicronautTest
class StoreServiceTest(@Inject private val blockingStub: StoreServiceGrpc.StoreServiceBlockingStub) : ShouldSpec({

    mockk<StoreGateway> {
        coEvery {
            createStore(ProtoBuf.decodeFromByteArray(PROTO_REQUEST.toByteArray()))
        } returns UnitData()
    }

    @Test
    fun createStoreTest() {
        val response = blockingStub.createStore(r)
        assert(response is CreatedStoreResponse)
    }
})

@Factory
class Clients {

    @Bean
    fun blockingStub(@GrpcChannel(GrpcServerChannel.NAME) channel: ManagedChannel): StoreServiceGrpc.StoreServiceBlockingStub {
        return StoreServiceGrpc.newBlockingStub(channel)
    }
}

val PROTO_STORE = Store.newBuilder()
        .setDayOfWeek(DayOfWeek.SATURDAY)
        .setDescription(StringValue.of("DESCRIPTION"))
        .setId(StringValue.of("ID"))
        .setName(StringValue.of("NAME"))
        .setPhoneNo(StringValue.of("112"))
        .setType(Type.GROCERIES)
        .build()

val PROTO_REQUEST = CreateStoreRequest.newBuilder().setStore(PROTO_STORE).build()

*/
