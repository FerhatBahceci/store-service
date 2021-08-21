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
import store.service.gateway.StoreGateway
import store.service.service.StoreServiceImpl
import store.service.service.mapToProtoStore

@ObsoleteCoroutinesApi
@ExperimentalSerializationApi
@MicronautTest
class StoreServiceImplTest : ShouldSpec({

    val store = DummyData.createStore("IKEA")

    val storeGateway = mockk<StoreGateway> {

        coEvery {
            getAllStores()
        } returns listOf(store, store)

        coEvery {
            getStoreByName(store.name!!)
        } returns store

        coEvery {
            getStoreByType(store.type!!)
        } returns listOf(store)

        coEvery {
            updateStore(store, store.id!!)
        } returns store

        coJustRun { deleteStore(store.id!!) }
        coJustRun { createStore(store) }
    }

    val storeService = StoreServiceImpl(gateway = storeGateway, coroutineContext = newSingleThreadContext("gprc-test"))

    should("GET all stores") {
        val getAllStoresRequest = GetAllStoresRequest
                .newBuilder()
                .build()
        val getAllStoresResponse = storeService.getAllStores(getAllStoresRequest)
        getAllStoresResponse.also {
            it.stores.storesCount shouldBe 2
            it.stores.storesList.map { ProtoBuf.decodeFromByteArray<Store>(it.toByteArray()) } shouldBe listOf(store, store)
        }
    }

    should("GET a store by name") {
        val getStoreByNameRequest = GetStoreByNameRequest.newBuilder()
                .setName(store.name)
                .build()
        val getStoreByNameResponse = storeService.getStoreByName(getStoreByNameRequest)
        ProtoBuf.decodeFromByteArray<Store>(getStoreByNameResponse.store.toByteArray()) shouldBe store
    }

    should("GET all stores by type") {
        val getStoreByTypeRequest = GetStoreByTypeRequest.newBuilder()
                .setStoreType(proto.store.service.Store.Type.valueOf(store.type?.name ?: Store.Type.UNKNOWN.name))
                .build()
        val getAllStoresResponse = storeService.getStoreByType(getStoreByTypeRequest)
        getAllStoresResponse.also {
            it.stores.storesCount shouldBe 1
            it.stores.storesList.map { ProtoBuf.decodeFromByteArray<Store>(it.toByteArray()) } shouldBe listOf(store)
        }
    }

    should("CREATE a store") {
        val createRequest = CreateStoreRequest
                .newBuilder()
                .setStore(store.mapToProtoStore())
                .build()
        storeService.createStore(createRequest)
    }

    should("DELETE a store by id") {
        val deleteStoreByIdRequest = DeleteStoreByIdRequest.newBuilder()
                .setId(store.id)
                .build()
         storeService.deleteStore(deleteStoreByIdRequest)
    }

    should("UPDATE a store by id") {
        val updateStoreByIdRequest = UpdateStoreRequest
                .newBuilder()
                .setUpdate(store.mapToProtoStore())
                .setId(store.id)
                .build()
        storeService.updateStore(updateStoreByIdRequest)
    }
})
