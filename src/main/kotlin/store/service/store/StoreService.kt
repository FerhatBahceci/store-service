@file:Suppress("UNCHECKED_CAST")

package store.service.store

import com.google.protobuf.StringValue
import com.google.protobuf.Timestamp
import kotlinx.coroutines.*
import store.viewer.*
import java.time.Instant

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi

class StoreService(private val dao: StoreGateway) :
        StoreServiceImplBase(coroutineContext = newFixedThreadPoolContext(4, "grpc-server")), CoroutineScope {

    override suspend fun geAllStores(request: GetAllStoresRequest): Stores =
            withContext(Dispatchers.IO) {
                dao.getStores().mapToStores()
            }

    override suspend fun getStore(request: GetStoreRequest): store.viewer.Store =
            withContext(Dispatchers.IO) {
                dao.getStore(request.id.mapToString())?.mapToStore() ?: store.viewer.Store.newBuilder().build()
            }

    companion object {
        private fun List<Store>.mapToStores(): Stores =
                Stores.newBuilder().addAllStores(this.map { store -> store.mapToStore() }).build()

        private fun Store.mapToStore(): store.viewer.Store = store.viewer.Store.newBuilder()
                .setId(id.mapToStringValue())
                .setDescription(description.mapToStringValue())
                .setPhoneNo(phoneNo.mapToStringValue())
                .setStoreType(type.mapToType())
                .setCloses(closes.mapToTimestamp())
                .setOpens(opens.mapToTimestamp())
                .build()
    }
}

fun String.mapToStringValue() = StringValue.newBuilder().setValue(this).build()

fun StringValue.mapToString() = StringBuilder(this.value).toString()

fun Instant.mapToTimestamp(): Timestamp = Timestamp.newBuilder().setNanos(this.nano).setSeconds(this.epochSecond).build()

fun Store.Type.mapToType() = Type.valueOf(this.name)


