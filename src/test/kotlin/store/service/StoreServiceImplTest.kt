package store.service

import io.kotlintest.shouldBe
import io.kotlintest.specs.ShouldSpec
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import kotlinx.serialization.ExperimentalSerializationApi
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.protobuf.ProtoBuf
import proto.store.service.*
import store.service.DummyData.Companion.RECORD_META_DATA
import store.service.gateway.Store
import store.service.gateway.StoreGateway
import store.service.service.KafkaClient
import store.service.service.StoreServiceImpl
import store.service.service.mapToProtoStore

@ObsoleteCoroutinesApi
@ExperimentalSerializationApi
@MicronautTest
class StoreServiceImplTest : ShouldSpec({

    val store = DummyData.createStore("IKEA")
    val newName = "WILLYS"
    val updateStore = store.copy(name = newName)

    val storeGateway = mockk<StoreGateway> {

        coEvery {
            getAllStores()
        } returns listOf(store)

        coEvery {
            getStoreByName(store.name!!)
        } returns store

        coEvery {
            getStoreByType(store.type!!)
        } returns listOf(store)

        coEvery {
            updateStore(updateStore, store.id!!)
        } returns updateStore

        coJustRun { deleteStore(store.id!!) }
        coJustRun { createStore(store) }
    }

    val mockKafkaClient = mockk<KafkaClient<StoreSearchEvent>> {

        every {
            publish(any(), any(), any())
        } returns RECORD_META_DATA
    }

    val service =
        StoreServiceImpl(
            gateway = storeGateway,
            coroutineContext = newSingleThreadContext("gprc-test"),
            kafkaClient = mockKafkaClient
        )

    should("CREATE a store") {
        val response = service.createStore(store)
        response.response.status shouldBe 201
    }

    should("UPDATE a store") {
        val response = service.updateStore(updateStore)
        updateStore shouldBe ProtoBuf.decodeFromByteArray<Store>(response.update.toByteArray())
        response.response.status shouldBe 204
    }

    should("GET store by name") {
        val response = service.getStoreByName(store.name)
        ProtoBuf.decodeFromByteArray<Store>(response.store.toByteArray()) shouldBe store
        response.response.status shouldBe 200
    }

    should("GET store by type"){
        val response = service.getStoreByType(store.type)
        store.assertStoresResponse(response, 1)
        response.response.status shouldBe 200
    }

    should("GET all stores"){
        val response = service.getAllStores()
        store.assertStoresResponse(response, 1)
        response.response.status shouldBe 200
    }

    should("DELETE a store") {
        val response = service.deleteStore(store.id)
        response.response.status shouldBe 204
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
