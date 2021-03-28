package store.service

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import kotlinx.serialization.ExperimentalSerializationApi
import org.junit.jupiter.api.Test
import proto.store.service.*
import proto.store.service.GetStoreByNameRequest
import store.service.DummyData.Companion.PROTO_REQUEST
import javax.inject.Inject

@ExperimentalSerializationApi
@MicronautTest
class StoreServiceTest(@Inject private val blockingStub: StoreServiceGrpc.StoreServiceBlockingStub) {
    /*   @MockBean(StoreGatewayImpl::class)
       fun mathService() {
       } //TODO once we now deserailizer and searilizer works properly, mock away the mongoclient
   */

    @Test
    fun createStoreTest() {
        val response = blockingStub.createStore(PROTO_REQUEST)
    }

    @Test
    fun getStoreByNameTest() {
        val r = GetStoreByNameRequest.newBuilder().setName("Wallmart").build()
        val response = blockingStub.getStoreByName(r)
        assert(response is GetStoreResponse)
        assert(response == GetStoreResponse.getDefaultInstance())
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
}

