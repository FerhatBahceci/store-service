package store.service

import io.kotlintest.shouldBe
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import kotlinx.serialization.ExperimentalSerializationApi
import io.kotlintest.specs.ShouldSpec
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.protobuf.ProtoBuf
import proto.store.service.*
import proto.store.service.CreateStoreRequest
import proto.store.service.DeleteStoreByIdRequest
import proto.store.service.GetAllStoresRequest
import proto.store.service.GetStoreByNameRequest
import proto.store.service.UpdateStoreRequest
import store.service.gateway.Store
import store.service.gateway.StoreGateway
import store.service.service.StoreMapper.Companion.mapToProtoStore
import store.service.service.StoreServiceImpl

@ObsoleteCoroutinesApi
@ExperimentalSerializationApi
@MicronautTest
class StoreServiceImplTest : ShouldSpec({

    val store = DummyData.createStore("IKEA")

    val storeGateway = mockk<StoreGateway> {
        coEvery {
            createStore(CreateStoreRequest(store))
        } returns Unit
        coEvery {
            getStoreByName(GetStoreByNameRequest(store.name))
        } returns store
        coEvery {
            getAllStores(GetAllStoresRequest())
        } returns listOf(store, store)
        coEvery {
            getStoreByType(GetStoreByTypeRequest(store.type))
        } returns listOf(store)
        coEvery {
            deleteStore(DeleteStoreByIdRequest(store.id))
        } returns Unit
        coEvery {
            updateStore(UpdateStoreRequest(store.id, store))
        } returns Unit
    }

    val storeService = StoreServiceImpl(gateway = storeGateway, coroutineContext = newSingleThreadContext("gprc-test"))

    should("create a store") {
        val createRequest = CreateStoreRequest.newBuilder().setStore(store.mapToProtoStore()).build()
        val createResponse = storeService.createStore(createRequest)
        createResponse.response.status shouldBe 201
    }

    should("fetch a store by name") {
        val getStoreByNameRequest = GetStoreByNameRequest.newBuilder()
                .setName(store.name)
                .build()
        val getStoreByNameResponse = storeService.getStoreByName(getStoreByNameRequest)
        ProtoBuf.decodeFromByteArray<Store>(getStoreByNameResponse.store.toByteArray()) shouldBe store
    }

    should("fetch all stores") {
        val getAllStoresRequest = GetAllStoresRequest.getDefaultInstance()
        val getAllStoresResponse = storeService.getAllStores(getAllStoresRequest)
        getAllStoresResponse.stores.storesCount shouldBe 2
        getAllStoresResponse.stores.storesList.map { ProtoBuf.decodeFromByteArray<Store>(it.toByteArray()) } shouldBe listOf(store, store)
    }

    should("fetch all stores by type") {
        val getStoreByTypeRequest = proto.store.service.GetStoreByTypeRequest.newBuilder()
                .setType(Type.valueOf(store.type?.name ?: Store.Type.UNKNOWN.name))
                .build()
        val getAllStoresResponse = storeService.getStoreByType(getStoreByTypeRequest)
        getAllStoresResponse.stores.storesCount shouldBe 1
        getAllStoresResponse.stores.storesList.map { ProtoBuf.decodeFromByteArray<Store>(it.toByteArray()) } shouldBe listOf(store)
    }

    should("be able to delete a store by id") {
        val deleteStoreByIdRequest = DeleteStoreByIdRequest.newBuilder()
                .setId(store.id)
                .build()
        val getStoreByNameResponse = storeService.deleteStore(deleteStoreByIdRequest)
        getStoreByNameResponse.response.status shouldBe 204
    }

    should("be able to update a store by id") {
        val updateStoreByIdRequest = UpdateStoreRequest.newBuilder().setUpdate(store.mapToProtoStore()).setId(store.id).build()
        val getStoreByIdResponse = storeService.updateStore(updateStoreByIdRequest)
        getStoreByIdResponse.response.status shouldBe 204
    }
})
