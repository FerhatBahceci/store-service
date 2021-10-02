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
import store.service.DummyData.Companion.createId
import store.service.DummyData.Companion.createProtoStore
import store.service.gateway.Store
import store.service.service.mapToProtoStore
import javax.inject.Inject

//TODO should be refactored into a single BDD test
@ExperimentalSerializationApi
@MicronautTest()
class StoreServiceGrpcClientTest(@Inject val storeServiceBlockingStub: StoreServiceGrpc.StoreServiceBlockingStub) {

    val storeName = "ICA"


    @Test
    fun createStoreTest() {
        val request = CreateStoreRequest.newBuilder().setStore(createProtoStore(storeName)).build()
        storeServiceBlockingStub.createStore(request)
    }

    @Test
    fun getStoreByTypeTest() {
        createStoreTest()
        createStoreTest()
        val request = GetStoreByTypeRequest.newBuilder().setStoreType(proto.store.service.Store.Type.GROCERIES).build()
        val response = storeServiceBlockingStub.getStoreByType(request)
        response.also {
            assert(it.stores.storesCount >= 2)
        }
    }

    @Test
    fun getStoreByNameTest() {
        createStoreTest()
        val request = GetStoreByNameRequest.newBuilder().setName(storeName).build()
        val response = storeServiceBlockingStub.getStoreByName(request)
        response.also {
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
        val updatedStore =
            ProtoBuf.decodeFromByteArray<Store>(store.toByteArray()).copy(name = newName).mapToProtoStore()
        val request = UpdateStoreRequest.newBuilder().setUpdate(updatedStore).setId(storeId).build()
        val response = storeServiceBlockingStub.updateStore(request)

        assert(response.update.name == newName)
    }
}

@ExperimentalSerializationApi
private suspend fun StoreServiceGrpcKt.StoreServiceCoroutineImplBase.deleteStore(id: String?) {
    val deleteStoreByIdRequest = DeleteStoreByIdRequest.newBuilder()
        .setId(id)
        .build()
    deleteStore(deleteStoreByIdRequest)
}

@ExperimentalSerializationApi
private suspend fun StoreServiceGrpcKt.StoreServiceCoroutineImplBase.createStore(store: Store) {
    val createRequest = CreateStoreRequest
        .newBuilder()
        .setStore(store.mapToProtoStore())
        .build()
    createStore(createRequest)
}

@ExperimentalSerializationApi
private suspend fun StoreServiceGrpcKt.StoreServiceCoroutineImplBase.getStoreByName(name: String?): GetStoreResponse {
    val getStoreByNameRequest = GetStoreByNameRequest.newBuilder()
        .setName(name)
        .build()
    return getStoreByName(getStoreByNameRequest)
}

@ExperimentalSerializationApi
private suspend fun StoreServiceGrpcKt.StoreServiceCoroutineImplBase.getStoreByType(type: Store.Type?): GetStoresResponse {
    val getStoreByTypeRequest = GetStoreByTypeRequest.newBuilder()
        .setStoreType(proto.store.service.Store.Type.valueOf(type?.name ?: Store.Type.UNKNOWN.name))
        .build()
    return getStoreByType(getStoreByTypeRequest)
}

@ExperimentalSerializationApi
private suspend fun StoreServiceGrpcKt.StoreServiceCoroutineImplBase.getAllStores(): GetStoresResponse {
    val getAllStoresRequest = GetAllStoresRequest
        .newBuilder()
        .build()
    return getAllStores(getAllStoresRequest)
}

@ExperimentalSerializationApi
private suspend fun StoreServiceGrpcKt.StoreServiceCoroutineImplBase.updateStore(store: Store): UpdateStoreResponse {
    val updateStoreByIdRequest = UpdateStoreRequest
        .newBuilder()
        .setUpdate(store.mapToProtoStore())
        .setId(store.id)
        .build()
    return updateStore(updateStoreByIdRequest)
}

@ExperimentalSerializationApi
private fun Store.assertStoresResponse(storesResponse: GetStoresResponse, expectedAmount: Int) {
    storesResponse.stores.storesCount shouldBe expectedAmount
    storesResponse.stores.storesList.map { ProtoBuf.decodeFromByteArray<Store>(it.toByteArray()) } shouldBe listOf(this)
}
