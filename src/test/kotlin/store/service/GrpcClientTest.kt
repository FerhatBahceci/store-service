/*
package store.service

import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import kotlinx.serialization.ExperimentalSerializationApi
import org.junit.jupiter.api.Test
import proto.store.service.*
import proto.store.service.gateway.CreateStoreRequest
import proto.store.service.gateway.DeleteStoreByIdRequest
import proto.store.service.gateway.GetAllStoresRequest
import proto.store.service.gateway.GetStoreByNameRequest
import spock.lang.Specification
import store.service.DummyData.Companion.createId
import store.service.DummyData.Companion.createProtoStore
import store.service.DummyData.Companion.createStore
import store.service.gateway.StoreGatewayImpl
import javax.inject.Inject
import store.service.gateway.StoreGateway

@ExperimentalSerializationApi
@MicronautTest
class StoreServiceTest(@Inject private val blockingStub: StoreServiceGrpc.StoreServiceBlockingStub) : Specification() {

    @Inject
    lateinit var storeGateway: StoreGateway

    @MockBean(StoreGatewayImpl::class)
   fun storeGateway() {
        Mock(StoreGateway::class.java)
    }

    @Test
    suspend fun createStoreTest() {

        storeGateway.createStore(store.service.gateway.CreateStoreRequest(createStore("ICA")))

        val store = createProtoStore(name = "Walmart", id = createId())
        val request = CreateStoreRequest.newBuilder().setStore(store).build()

        storeGateway


        val response = blockingStub.createStore(request)
        assert(response.response.status == 201)
    }
*/
/*
    @Test
    fun getStoreByNameTest() {
        val request = GetStoreByNameRequest.newBuilder().setName("Wallmart").build()
        val response = blockingStub.getStoreByName(request)
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
        val id = createId()
        val store = createProtoStore(name = "DELETE_ME_STORE", id = id)
        val createRequest = CreateStoreRequest.newBuilder().setStore(store).build()
        val createResponse = blockingStub.createStore(createRequest)
        assert(createResponse.response.status == 201)

        val deleteRequest = DeleteStoreByIdRequest.newBuilder().setId(id).build()
        val deleteResponse = blockingStub.deleteStore(deleteRequest)
        assert(deleteResponse.response.status == 204)
    }

    @Test
    fun updateStoreById() {
        val id = createId()
        val storeName = "INSERT_ME_STORE"
        val store = createProtoStore(name = storeName, id = id)
        val createRequest = CreateStoreRequest.newBuilder().setStore(store).build()
        val createResponse = blockingStub.createStore(createRequest)
        assert(createResponse.response.status == 201)

        val deleteRequest = DeleteStoreByIdRequest.newBuilder().setId(id).build()
        val deleteResponse = blockingStub.deleteStore(deleteRequest)
        assert(deleteResponse.response.status == 204)

        val getStoreByNameRequest = GetStoreByNameRequest.newBuilder().setName(storeName).build()
        val getStoreByNameResponse = blockingStub.getStoreByName(getStoreByNameRequest)
        assert(getStoreByNameResponse == null)
    }*//*

}

*/
