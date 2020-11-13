package store.service.store

import com.google.protobuf.StringValue
import com.google.protobuf.Timestamp
import kotlinx.coroutines.*
import store.service.store.ValueMapper.Companion.mapToString
import store.service.store.ValueMapper.Companion.mapToStringValue
import store.service.store.ValueMapper.Companion.mapToTimestamp
import store.viewer.*
import java.time.DayOfWeek
import java.time.Instant
import java.util.*

@ObsoleteCoroutinesApi
class StoreServiceImpl(private val gateway: StoreGateway) :
        StoreServiceImplBase(coroutineContext = newFixedThreadPoolContext(4, "grpc-server")), CoroutineScope {

    override suspend fun geAllStores(request: GetAllStoresRequest): Stores = gateway.getStores().mapToStores()

    override suspend fun getStore(request: GetStoreRequest) =
            gateway.getStore(request.id.mapToString()).mapToStore()

    private fun List<Store>.mapToStores() = Stores.newBuilder().addAllStores(this.map { store -> store.mapToStore() }.toList()).build()

    private fun Store.mapToStore() = store.viewer.Store.newBuilder()
            .setId(id.mapToStringValue())
            .setDescription(description.mapToStringValue())
            .setPhoneNo(phoneNo.mapToStringValue())
            .setStoreType(Type.valueOf(this.name))
/*
            .putAllOpeningHours(openingHours.mapToOpeningHours())
*/
            .build()

/*    private fun EnumMap<DayOfWeek, Store.Hours>.mapToOpeningHours(): Map<String, Hours> {

        mutableMapOf<String, Hours>().let {
            this.map { entry ->
                it.put(entry.key.name, entry.value.mapToHours())
            }

        }
    }*/

    private fun Store.Hours.mapToHours() =
            Hours.newBuilder()
                    .setOpening(opening.mapToTimestamp())
                    .setClosing(closing.mapToTimestamp())
                    .build()
}

class ValueMapper {

    companion object {

        fun String.mapToStringValue() = StringValue.newBuilder().setValue(this).build()

        fun StringValue.mapToString() = StringBuilder(this.value).toString()

        fun Instant.mapToTimestamp() =
                Timestamp.newBuilder()
                        .setNanos(this.nano)
                        .setSeconds(this.epochSecond)
                        .build()
    }
}