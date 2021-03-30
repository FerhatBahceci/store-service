package store.service

import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import kotlinx.serialization.ExperimentalSerializationApi
import proto.store.service.CreateStoreRequest
import io.kotlintest.AbstractProjectConfig
import io.micronaut.runtime.context.env.ConfigurationAdvice
import io.micronaut.test.extensions.kotlintest.MicronautKotlinTestExtension
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import store.service.gateway.StoreGateway
import store.service.service.StoreMapper.Companion.mapToProtoStore
import store.service.service.StoreServiceImpl

@ObsoleteCoroutinesApi
@ExperimentalSerializationApi
@MicronautTest
class StoreServiceImplTest : BehaviorSpec({

    val store = DummyData.createStore("IKEA")

    val storeGateway = mockk<StoreGateway> {
        coEvery {
            createStore(CreateStoreRequest(store))
        } returns Unit
    }

    val storeService = createStoreService(storeGateway)

    given("the store service") {

        `when`("a store is created with the service") {
            val createRequest = CreateStoreRequest.newBuilder().setStore(store.mapToProtoStore()).build()
            val createResponse = storeService.createStore(createRequest)
            then("the response is 201") {
                assert(createResponse.response.status == 201)
            }
        }

        /* `when`("the service is called with 3") {
             val result = mathService.compute(3)
             then("the result is 12") {
                 result shouldBe 12
             }
         }*/
    }
})

@ObsoleteCoroutinesApi
@ExperimentalSerializationApi
fun createStoreService(storeGateway: StoreGateway) =
    StoreServiceImpl(gateway = storeGateway, coroutineContext = newSingleThreadContext("gprc-test"))
