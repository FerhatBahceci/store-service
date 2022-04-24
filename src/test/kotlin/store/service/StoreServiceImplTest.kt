/*
package store.service

import io.kotlintest.shouldBe
import io.kotlintest.specs.ShouldSpec
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import kotlinx.serialization.ExperimentalSerializationApi
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.mockk
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.protobuf.ProtoBuf
import proto.store.service.*
import store.service.gateway.Store
import store.service.gateway.StoreGateway
import store.service.service.StoreServiceImpl
import store.service.service.mapToProtoStore

@ObsoleteCoroutinesApi
@ExperimentalSerializationApi
@MicronautTest
class StoreServiceImplTest : ShouldSpec({

    val STORE = DummyData.createStore("IKEA")
    val NEW_NAME = "WILLYS"
    val UPDATE_STORE = STORE.copy(name = NEW_NAME)

    val storeGateway = mockk<StoreGateway> {

        coEvery {
            getAllStores()
        } returns listOf(STORE)

        coEvery {
            getStoreByName(STORE.name!!)
        } returns STORE

        coEvery {
            getStoreByType(STORE.type!!)
        } returns listOf(STORE)

        coEvery {
            updateStore(UPDATE_STORE, STORE.id!!)
        } returns UPDATE_STORE

        coJustRun { deleteStore(STORE.id!!) }
        coJustRun { createStore(STORE) }
    }

    val service = StoreServiceImpl(gateway = storeGateway, coroutineContext = newSingleThreadContext("gprc-test"), kafkaClient = )

    should("CREATE a store") {
        val CREATE_RESPONSE = service.createStore(STORE)
        CREATE_RESPONSE.response.status shouldBe 201
    }

    should("UPDATE a store") {
        val UPDATE_STORE_RESPONSE = service.updateStore(UPDATE_STORE)
        UPDATE_STORE shouldBe ProtoBuf.decodeFromByteArray<Store>(UPDATE_STORE_RESPONSE.update.toByteArray())
        UPDATE_STORE_RESPONSE.response.status shouldBe 204
    }

    should("GET a store") {
        val GET_STORE_BY_NAME_RESPONSE = service.getStoreByName(STORE.name)
        ProtoBuf.decodeFromByteArray<Store>(GET_STORE_BY_NAME_RESPONSE.store.toByteArray()) shouldBe STORE
        GET_STORE_BY_NAME_RESPONSE.response.status shouldBe 200

        val GET_STORES_BY_TYPE_RESPONSE = service.getStoreByType(STORE.type)
        STORE.assertStoresResponse(GET_STORES_BY_TYPE_RESPONSE, 1)
        GET_STORES_BY_TYPE_RESPONSE.response.status shouldBe 200

        val GET_ALL_STORES_RESPONSE = service.getAllStores()
        STORE.assertStoresResponse(GET_ALL_STORES_RESPONSE, 1)
        GET_ALL_STORES_RESPONSE.response.status shouldBe 200
    }

    should("DELETE a store") {
        val DELETE_RESPONSE = service.deleteStore(STORE.id)
        DELETE_RESPONSE.response.status shouldBe 204
    }
})

@ExperimentalSerializationApi
private suspend fun StoreServiceGrpcKt.StoreServiceCoroutineImplBase.deleteStore(id: String?): DeleteStoreResponse {
    val deleteStoreByIdRequest = DeleteStoreByIdRequest.newBuilder()
        .setId(id)
        .build()
    return deleteStore(deleteStoreByIdRequest)
}

@ExperimentalSerializationApi
private suspend fun StoreServiceGrpcKt.StoreServiceCoroutineImplBase.createStore(store: Store): CreatedStoreResponse {
    val createRequest = CreateStoreRequest
        .newBuilder()
        .setStore(store.mapToProtoStore())
        .build()
    return createStore(createRequest)
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
        .setStore(store.mapToProtoStore())
        .setId(store.id)
        .build()
    return updateStore(updateStoreByIdRequest)
}

@ExperimentalSerializationApi
private fun Store.assertStoresResponse(storesResponse: GetStoresResponse, expectedAmount: Int) {
    storesResponse.stores.storesCount shouldBe expectedAmount
    storesResponse.stores.storesList.map { ProtoBuf.decodeFromByteArray<Store>(it.toByteArray()) } shouldBe listOf(this)
}
*/
