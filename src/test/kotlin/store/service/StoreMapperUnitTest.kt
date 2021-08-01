package store.service

import io.kotlintest.shouldBe
import io.kotlintest.specs.ShouldSpec
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.protobuf.ProtoBuf
import store.service.DummyData.Companion.createStore
import store.service.service.mapToProtoStore

@ExperimentalSerializationApi
class StoreMapperUnitTest : ShouldSpec({

    should("map store.service.model.Store to proto.store.service.Store") {

        val store = createStore("Ica")

        val protoStore = store.mapToProtoStore()

        val serializedStore = ProtoBuf.decodeFromByteArray<Store>(protoStore.toByteArray())

        serializedStore shouldBe store
    }
})
