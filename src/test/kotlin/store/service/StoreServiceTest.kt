package store.service

import com.google.protobuf.Timestamp
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import io.micronaut.grpc.server.GrpcServerChannel
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import kotlinx.serialization.ExperimentalSerializationApi
import org.junit.jupiter.api.Test
import proto.store.service.*
import proto.store.service.Coordinates
import proto.store.service.CreateStoreRequest
import proto.store.service.GetAllStoresRequest
import proto.store.service.Store
import javax.inject.Inject

@ExperimentalSerializationApi
@MicronautTest
class StoreServiceTest(@Inject private val blockingStub: StoreServiceGrpc.StoreServiceBlockingStub) {
    /*   @MockBean(StoreGatewayImpl::class)
       fun mathService() {
       }

   */
    @Test
    fun createStoreTest() {
        val response = blockingStub.createStore(PROTO_REQUEST)
        assert(response is CreatedStoreResponse)
        assert(response == CreatedStoreResponse.getDefaultInstance())
    }

    @Test
    fun getAllStoresTest() {
        val r = GetAllStoresRequest.getDefaultInstance()
        val response = blockingStub.getAllStores(r)
        assert(response is GetStoresResponse)
        assert(response == GetStoresResponse.getDefaultInstance())

        /*   @Test
           fun getAllStoresTest() {
               val r = GetAllStoresRequest.getDefaultInstance()
               val response = blockingStub.getAllStores(r)
               assert(response is GetStoresResponse)
               assert(response == GetStoresResponse.getDefaultInstance())
           }*/

/*    @Test
    fun getStoreByNameTest() {
        val r = GetStoreByNameRequest.newBuilder().setName("Wallmart").build()
        val response = blockingStub.getStoreByName(r)
        assert(response is GetStoreResponse)
        assert(response == GetStoreResponse.getDefaultInstance())
    }*/
    }

    val PROTO_STORE = Store.newBuilder()
            .putHours(
                    "FRIDAY",
                    OpeningHours.newBuilder()
                            .setOpening(Timestamp.newBuilder().setNanos(19).setSeconds(199L))
                            .setClosing(Timestamp.newBuilder().setNanos(19).setSeconds(199L))
                            .build()
            )
            .setCoordinates(Coordinates.newBuilder().setLatitude(420L).setLongitude(420L).build())
            .setDescription("DESCRIPTION")
            .setId("420".plus(Math.random()))
            .setName("Wallmart")
            .setPhoneNo("112")
            .setType(Type.GROCERIES)
            .build()

    val PROTO_REQUEST = CreateStoreRequest.newBuilder().setStore(PROTO_STORE).build()

}

