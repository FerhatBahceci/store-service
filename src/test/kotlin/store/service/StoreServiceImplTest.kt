package store.service

import io.kotlintest.shouldBe
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import kotlinx.serialization.ExperimentalSerializationApi
import io.kotlintest.specs.ShouldSpec
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.mockk
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.protobuf.ProtoBuf
import proto.store.service.*
import store.service.gateway.GetStoreByTypeRequest
import store.service.gateway.Store
import store.service.gateway.StoreGateway
import store.service.service.StoreServiceImpl
import store.service.gateway.CreateStoreRequest
import store.service.gateway.GetStoreByNameRequest
import store.service.gateway.GetAllStoresRequest
import store.service.gateway.DeleteStoreByIdRequest
import store.service.gateway.UpdateStoreRequest
import store.service.service.StoreMapper.Companion.mapToProtoStore
import utility.request.Request

@ObsoleteCoroutinesApi
@ExperimentalSerializationApi
@MicronautTest
class StoreServiceImplTest : ShouldSpec({

    val store = DummyData.createStore("IKEA")

    val storeGateway = mockk<StoreGateway> {

        coEvery {
            getAllStores(GetAllStoresRequest(Request.Type.GET))
        } returns listOf(store, store)

        coEvery {
            getStoreByName(GetStoreByNameRequest(store.name))
        } returns store

        coEvery {
            getStoreByType(GetStoreByTypeRequest(store.type))
        } returns listOf(store)

        coJustRun { deleteStore(DeleteStoreByIdRequest(store.id)) }
        coJustRun { updateStore(UpdateStoreRequest(store.id, store)) }
        coJustRun { createStore(CreateStoreRequest(store)) }
    }

    val storeService = StoreServiceImpl(gateway = storeGateway, coroutineContext = newSingleThreadContext("gprc-test"))

    should("GET all stores") {
        val getAllStoresRequest = proto.store.service.GetAllStoresRequest
                .newBuilder()
                .setType(RequestType.GET)
                .build()
        val getAllStoresResponse = storeService.getAllStores(getAllStoresRequest)
        getAllStoresResponse.also {
            it.stores.storesCount shouldBe 2
            it.stores.storesList.map { ProtoBuf.decodeFromByteArray<Store>(it.toByteArray()) } shouldBe listOf(store, store)
        }
    }

    should("GET a store by name") {
        val getStoreByNameRequest = proto.store.service.GetStoreByNameRequest.newBuilder()
                .setName(store.name)
                .setType(RequestType.GET)
                .build()
        val getStoreByNameResponse = storeService.getStoreByName(getStoreByNameRequest)
        ProtoBuf.decodeFromByteArray<Store>(getStoreByNameResponse.store.toByteArray()) shouldBe store
    }

    should("GET all stores by type") {
        val getStoreByTypeRequest = proto.store.service.GetStoreByTypeRequest.newBuilder()
                .setStoreType(proto.store.service.Store.Type.valueOf(store.type?.name ?: Store.Type.UNKNOWN.name))
                .setType(RequestType.GET)
                .build()
        val getAllStoresResponse = storeService.getStoreByType(getStoreByTypeRequest)
        getAllStoresResponse.also {
            it.stores.storesCount shouldBe 1
            it.stores.storesList.map { ProtoBuf.decodeFromByteArray<Store>(it.toByteArray()) } shouldBe listOf(store)
        }
    }

    should("CREATE a store") {
        val createRequest = proto.store.service.CreateStoreRequest
                .newBuilder()
                .setStore(store.mapToProtoStore())
                .setType(RequestType.POST)
                .build()
        val createResponse = storeService.createStore(createRequest)
        createResponse.response.status shouldBe 201
    }

    should("DELETE a store by id") {
        val deleteStoreByIdRequest = proto.store.service.DeleteStoreByIdRequest.newBuilder()
                .setId(store.id)
                .setType(RequestType.DELETE)
                .build()
        val getStoreByNameResponse = storeService.deleteStore(deleteStoreByIdRequest)
        getStoreByNameResponse.response.status shouldBe 204
    }

    should("UPDATE a store by id") {
        val updateStoreByIdRequest = proto.store.service.UpdateStoreRequest
                .newBuilder()
                .setUpdate(store.mapToProtoStore())
                .setId(store.id)
                .setType(RequestType.PUT)
                .build()
        val getStoreByIdResponse = storeService.updateStore(updateStoreByIdRequest)
        getStoreByIdResponse.response.status shouldBe 204
    }
})
