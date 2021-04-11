package store.service

import io.grpc.StatusRuntimeException
import io.kotlintest.shouldThrow
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.protobuf.ProtoBuf
import org.junit.jupiter.api.Test
import proto.store.service.*
import store.service.DummyData.Companion.createId
import store.service.DummyData.Companion.createProtoStore
import store.service.gateway.Store
import store.service.service.StoreMapper.Companion.mapToProtoStore
import javax.inject.Inject

@ExperimentalSerializationApi
@MicronautTest
class StoreServiceGrpcClientTest(@Inject private val storeServiceBlockingStub: StoreServiceGrpc.StoreServiceBlockingStub) {

    val storeName = "ICA"

    @Test
    fun createStoreTest() {
        val request = CreateStoreRequest.newBuilder().setStore(createProtoStore(storeName)).build()
        val response = storeServiceBlockingStub.createStore(request)
        response.also { assert(it.response.status == 201) }
    }

    @Test
    fun getStoreByNameTest() {
        createStoreTest()
        val request = GetStoreByNameRequest.newBuilder().setName(storeName).build()
        val response = storeServiceBlockingStub.getStoreByName(request)
        response.also {
            assert(it.response.status == 200)
            assert(it.store.name == request.name)
        }
    }

    @Test
    fun getAllStoresTest() {
        createStoreTest()
        createStoreTest()
        val request = GetAllStoresRequest.getDefaultInstance()
        val response = storeServiceBlockingStub.getAllStores(request)
        response.also {
            assert(it.stores.storesCount >= 2)
            assert(it.response.status == 200)
        }
    }

    @Test
    fun deleteStoreByIdTest() {
        val storeId = createId()
        val storeName = "DeleteMe"
        val createStoreRequest = CreateStoreRequest.newBuilder().setStore(createProtoStore(storeName, storeId)).build()
        storeServiceBlockingStub.createStore(createStoreRequest)

        val request = DeleteStoreByIdRequest.newBuilder().setId(storeId).build()
        val response = storeServiceBlockingStub.deleteStore(request)

        assert(response.response.status == 204)

        shouldThrow<StatusRuntimeException> {
            storeServiceBlockingStub.getStoreByName(GetStoreByNameRequest.newBuilder().setName(storeName).build())
        }
    }

    @Test
    fun updateStoreByIdTest() {
        val storeId = createId()
        val store = createProtoStore(storeName, storeId)
        val createStoreRequest = CreateStoreRequest.newBuilder().setStore(store).build()
        storeServiceBlockingStub.createStore(createStoreRequest)

        val newName = "Hemköp"
        val updatedStore = ProtoBuf.decodeFromByteArray<Store>(store.toByteArray()).copy(name = newName).mapToProtoStore()
        val request = UpdateStoreRequest.newBuilder().setUpdate(updatedStore).setId(storeId).build()
        val response = storeServiceBlockingStub.updateStore(request)

        assert(response.update.name == newName)
    }
}
