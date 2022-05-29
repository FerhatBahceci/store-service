package store.service

import io.grpc.StatusRuntimeException
import io.kotlintest.shouldThrow
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import kotlinx.serialization.ExperimentalSerializationApi
import org.junit.ClassRule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.testcontainers.containers.DockerComposeContainer
import org.testcontainers.containers.wait.strategy.Wait
import proto.store.service.*
import store.service.DummyData.Companion.createId
import store.service.DummyData.Companion.createStore
import store.service.gateway.Store
import store.service.service.mapToProtoStore
import java.io.File
import javax.inject.Inject

@ExperimentalSerializationApi
@MicronautTest
class StoreServiceGrpcClientTest(@Inject val storeServiceBlockingStub: StoreServiceGrpc.StoreServiceBlockingStub) {

    val STORE_NAME = "ICA"
    val STORE = createStore(name = STORE_NAME)

    val DB_PORT = 27017
    val SCHEMA_REGISTRY_PORT = 8081
    val KAFKA_PORT = 29092

    @ClassRule
    val environment =
        DockerComposeContainer(
            File("/Users/ferhatbahceci/projects/store-service/docker-compose.yml")
        )
            .withExposedService(
                "mongodb_1", DB_PORT,
                Wait.forListeningPort()
            )
            .withExposedService(
                "schema-registry_1", SCHEMA_REGISTRY_PORT,
                Wait.forListeningPort()
            )
            .withExposedService(
                "kafka_1", KAFKA_PORT,
                Wait.forListeningPort()
            )

    @BeforeEach
    fun setUp() {
        environment.start()
    }

    @Test
    fun crudTest() {
        createStoreTest()
        getStoreByTypeTest()
        getStoreByNameTest()
        getAllStoresTest()
        updateStoreByIdTest()
        deleteStoreByIdTest()
    }

    private fun createStoreTest() {
        val response = storeServiceBlockingStub.create(STORE)
        assert(response?.response?.status == 201)
    }

    private fun getStoreByTypeTest() {
        val response = storeServiceBlockingStub.getByType(STORE.type)
        assert(response.stores.storesCount >= 1)
    }

    private fun getStoreByNameTest() {
        storeServiceBlockingStub.getStoreByName(STORE.name).apply {
            assert(this.store.name == STORE.name)
            assert(this.response.status == 200)
        }
    }

    private fun getAllStoresTest() {
        storeServiceBlockingStub.getAllStores().apply {
            assert(this.stores.storesCount >= 1)
            assert(this.response.status == 200)
        }
    }

    private fun updateStoreByIdTest() {
        val NEW_NAME = "Hemköp"
        val UPDATE_STORE = STORE.copy(name = NEW_NAME)
        storeServiceBlockingStub.updateStore(UPDATE_STORE).apply {
            assert(this.update.name == NEW_NAME)
            assert(this.response.status == 204)
        }
    }

    private fun deleteStoreByIdTest() {

        val ID = createId()
        val NAME = "DELETE_ME"
        val CREATE_FOR_DELETE_STORE = STORE.copy(id = ID, name = NAME)

        storeServiceBlockingStub.create(CREATE_FOR_DELETE_STORE)
        storeServiceBlockingStub.deleteStore(ID).apply {
            assert(this?.response?.status == 204)
        }
        shouldThrow<StatusRuntimeException> {
            storeServiceBlockingStub.getStoreByName(NAME)
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

