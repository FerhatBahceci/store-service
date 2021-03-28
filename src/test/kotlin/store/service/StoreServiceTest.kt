package store.service

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import kotlinx.serialization.ExperimentalSerializationApi
import org.junit.jupiter.api.Test
import proto.store.service.*
import proto.store.service.CreateStoreRequest
import proto.store.service.GetAllStoresRequest
import proto.store.service.GetStoreByNameRequest
import store.service.DummyData.Companion.createId
import store.service.DummyData.Companion.createProtoStore
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
        val store = createProtoStore(name = "Walmart", id = createId())
        val request = CreateStoreRequest.newBuilder().setStore(store).build()
        val response = blockingStub.createStore(request)
        assert(response is CreatedStoreResponse)
        assert(response.response.status == 201)
    }

    @Test
    fun getStoreByNameTest() {
        val request = GetStoreByNameRequest.newBuilder().setName("Wallmart").build()
        val response = blockingStub.getStoreByName(request)
        assert(response is GetStoreResponse)
        assert(response.response.status == 200)
        assert(response.store.name == request.name)
    }

    @Test
    fun getAllStoresTest() {
        createStoreTest()
        val request = GetAllStoresRequest.getDefaultInstance()
        val response = blockingStub.getAllStores(request)
        assert(response.stores.storesCount > 0)
        assert(response.response.status == 200)
    }

    @Test
    fun deleteStoreById() {
        createStoreTest()
        val request = GetAllStoresRequest.getDefaultInstance()
        val response = blockingStub.getAllStores(request)
        assert(response.stores.storesCount > 0)
        assert(response.response.status == 200)
    }
}

