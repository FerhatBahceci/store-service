package store.service

import com.google.protobuf.Timestamp
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import kotlinx.serialization.ExperimentalSerializationApi
import org.junit.jupiter.api.Test
import proto.store.service.*
import proto.store.service.Coordinates
import proto.store.service.CreateStoreRequest
import proto.store.service.GetStoreByNameRequest
import proto.store.service.Store
import javax.inject.Inject

@ExperimentalSerializationApi
@MicronautTest
class StoreServiceTest(@Inject private val blockingStub: StoreServiceGrpc.StoreServiceBlockingStub) {
    /*   @MockBean(StoreGatewayImpl::class)
       fun mathService() {
       }
   */

/*    @Inject
    blockingStub: StoreServiceGrpc.StoreServiceBlockingStub*/

    @Test
    fun createStoreTest() {
        val response = blockingStub.createStore(PROTO_REQUEST)
    }

/*    @Test
    fun getAllStoresTest() {
        val r = GetAllStoresRequest.getDefaultInstance()
        val response = blockingStub.getAllStores(r)

        assert(response.stores.storesList.isNotEmpty())
        *//*   @Test
           fun getAllStoresTest() {
               val r = GetAllStoresRequest.getDefaultInstance()
               val response = blockingStub.getAllStores(r)
               assert(response is GetStoresResponse)
               assert(response == GetStoresResponse.getDefaultInstance())
           }*//*
    }*/

    @Test
    fun getStoreByNameTest() {
        val r = GetStoreByNameRequest.newBuilder().setName("Wallmart").build()
        val response = blockingStub.getStoreByName(r)
        assert(response is GetStoreResponse)
        assert(response == GetStoreResponse.getDefaultInstance())
    }

    val PROTO_STORE = Store.newBuilder()
        .putHours(
            "FRIDAY",
            OpeningHours.newBuilder()
                .setOpening(Timestamp.newBuilder().setNanos(0).setSeconds(37800L))  //10.30
                .setClosing(Timestamp.newBuilder().setNanos(0).setSeconds(79200L))    // 22.00
                .build()
        )
        .setCoordinates(Coordinates.newBuilder().setLatitude(420L).setLongitude(420L).build())
        .setDescription("DESCRIPTION")
        .setId("420".plus(Math.random()))
        .setName("Wallmart")
        .setPhoneNo("+46 111 111 11")
        .setType(Type.GROCERIES)
        .build()

    val PROTO_REQUEST = CreateStoreRequest.newBuilder().setStore(PROTO_STORE).build()

}

