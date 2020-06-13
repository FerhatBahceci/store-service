package store.service.store

import com.google.protobuf.StringValue
import com.google.protobuf.Timestamp
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import store.service.store.ValueMapper.Companion.mapToString
import store.service.store.ValueMapper.Companion.mapToStringValue
import store.service.store.ValueMapper.Companion.mapToTimestamp
import store.viewer.*
import java.time.Instant

@ObsoleteCoroutinesApi
class StoreServiceImpl(private val gateway: StoreGateway) :
        StoreServiceImplBase(coroutineContext = newFixedThreadPoolContext(4, "grpc-server")), CoroutineScope {

    override suspend fun geAllStores(request: GetAllStoresRequest): Stores = gateway.getStores().mapToStores()

    override suspend fun getStore(request: GetStoreRequest) =
            gateway.getStore(request.id.mapToString()).mapToStore()

    private suspend fun Flow<Store>.mapToStores() =
            Stores.newBuilder().addAllStores(this.map { store -> store.mapToStore() }.toList()).build()

    private fun Store.mapToStore() = store.viewer.Store.newBuilder()
            .setId(id.mapToStringValue())
            .setDescription(description.mapToStringValue())
            .setPhoneNo(phoneNo.mapToStringValue())
            .setStoreType(type.mapToType())
            .setCloses(closes.mapToTimestamp())
            .setOpens(opens.mapToTimestamp())
            .build()

    private fun Store.Type.mapToType() = Type.valueOf(this.name)
}

class ValueMapper {
    companion object {
        fun String.mapToStringValue() = StringValue.newBuilder().setValue(this).build()

        fun StringValue.mapToString() = StringBuilder(this.value).toString()

        fun Instant.mapToTimestamp(): Timestamp = Timestamp.newBuilder().setNanos(this.nano).setSeconds(this.epochSecond).build()
    }
}

