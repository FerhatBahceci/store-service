/*
package store.service

import io.grpc.StatusRuntimeException
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.protobuf.ProtoBuf
import org.junit.jupiter.api.Test
import proto.store.service.*
import store.service.DummyData.Companion.createStore
import store.service.gateway.Store
import store.service.service.mapToProtoStore
import javax.inject.Inject

//TODO should be refactored into a single BDD test
@ExperimentalSerializationApi
@MicronautTest
class StoreServiceGrpcClientTest(@Inject val storeServiceBlockingStub: StoreServiceGrpc.StoreServiceBlockingStub) {

    val STORE_NAME = "ICA"
    val STORE = createStore(name = STORE_NAME)

    @Test
    fun createStoreTest() {
        val response = storeServiceBlockingStub.create(STORE)
        assert(response?.response?.status == 201)
    }

    @Test
    fun getStoreByTypeTest() {
        createStoreTest()
        createStoreTest()
        val response = storeServiceBlockingStub.getByType(STORE.type)
        assert(response.stores.storesCount >= 2)
    }

    @Test
    fun getStoreByNameTest() {
        createStoreTest()
        storeServiceBlockingStub.getStoreByName(STORE.name).apply {
            assert(this.store.name == STORE.name)
            assert(this.response.status == 200)
        }
    }

    @Test
    fun getAllStoresTest() {
        createStoreTest()
        createStoreTest()
        val request = GetAllStoresRequest.getDefaultInstance()
        storeServiceBlockingStub.getAllStores(request).apply {
            assert(this.stores.storesCount >= 2)
            assert(this.response.status == 200)
        }
    }

    @Test
    fun deleteStoreByIdTest() {
        createStoreTest()
        storeServiceBlockingStub.deleteStore(STORE.id).apply {
            assert(this?.response?.status == 204)
        }
        shouldThrow<StatusRuntimeException> {
            storeServiceBlockingStub.getStoreByName(STORE.name)
        }
    }

    @Test
    fun updateStoreByIdTest() {
        createStoreTest()

        val NEW_NAME = "Hemköp"
        val UPDATE_STORE = STORE.copy(name = NEW_NAME)

        storeServiceBlockingStub.updateStore(UPDATE_STORE).apply {
            assert(this.update.name == NEW_NAME)
            assert(this.response.status == 204)
        }
    }
}

@ExperimentalSerializationApi
private fun StoreServiceGrpc.StoreServiceBlockingStub.deleteStore(id: String?): DeleteStoreResponse? {
    val deleteStoreByIdRequest = DeleteStoreByIdRequest.newBuilder()
        .setId(id)
        .build()
    return deleteStore(deleteStoreByIdRequest)
}

@ExperimentalSerializationApi
private fun StoreServiceGrpc.StoreServiceBlockingStub.create(store: Store): CreatedStoreResponse? {
    val createRequest = CreateStoreRequest
        .newBuilder()
        .setStore(store.mapToProtoStore())
        .build()
    return createStore(createRequest)
}

@ExperimentalSerializationApi
private fun StoreServiceGrpc.StoreServiceBlockingStub.getStoreByName(name: String?): GetStoreResponse {
    val getStoreByNameRequest = GetStoreByNameRequest.newBuilder()
        .setName(name)
        .build()
    return getStoreByName(getStoreByNameRequest)
}

@ExperimentalSerializationApi
private fun StoreServiceGrpc.StoreServiceBlockingStub.getByType(type: Store.Type?): GetStoresResponse {
    val getStoreByTypeRequest = GetStoreByTypeRequest.newBuilder()
        .setStoreType(proto.store.service.Store.Type.valueOf(type?.name ?: Store.Type.UNKNOWN.name))
        .build()
    return getStoreByType(getStoreByTypeRequest)
}

@ExperimentalSerializationApi
private fun StoreServiceGrpc.StoreServiceBlockingStub.getAllStores(): GetStoresResponse {
    val getAllStoresRequest = GetAllStoresRequest
        .newBuilder()
        .build()
    return getAllStores(getAllStoresRequest)
}

@ExperimentalSerializationApi
private fun StoreServiceGrpc.StoreServiceBlockingStub.updateStore(store: Store): UpdateStoreResponse {
    val updateStoreByIdRequest = UpdateStoreRequest
        .newBuilder()
        .setStore(store.mapToProtoStore())
        .setId(store.id)
        .build()
    return updateStore(updateStoreByIdRequest)
}

*/
