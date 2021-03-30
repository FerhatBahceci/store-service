import io.kotlintest.shouldBe
import io.kotlintest.specs.ShouldSpec
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.protobuf.ProtoBuf
import store.service.DummyData.Companion.createStore
import store.service.gateway.Store
import store.service.service.StoreMapper.Companion.mapToProtoStore

@ExperimentalSerializationApi
class StoreMapperUnitTest : ShouldSpec({

    should("map store.service.gateway.Store to proto.store.service.Store") {

        val store = createStore("Ica")

        val protoStore = store.mapToProtoStore()

        val serializedStore = ProtoBuf.decodeFromByteArray<Store>(protoStore.toByteArray())

        serializedStore shouldBe store
    }
})
