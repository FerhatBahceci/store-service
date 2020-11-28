package store.service.store

import com.google.protobuf.Int32Value
import com.google.protobuf.StringValue
import com.google.protobuf.Timestamp
import io.grpc.Status
import io.grpc.StatusException
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.protobuf.ProtoBuf
import store.service.*
import java.time.Instant
import kotlin.coroutines.CoroutineContext

class StoreServiceImpl(private val gateway: StoreGateway,
                       override val coroutineContext: CoroutineContext) :
        StoreServiceGrpcKt.StoreServiceCoroutineImplBase(), CoroutineScope {

    override suspend fun getStoreByType(request: GetStoreByTypeRequest): GetStoresResponse { throw
        StatusException(Status.UNIMPLEMENTED.withDescription("Method store.service.StoreService.GeAllStores is unimplemented"))
    }

    override suspend fun geAllStores(request: GetStoresRequest): GetStoresResponse = throw
    StatusException(Status.UNIMPLEMENTED.withDescription("Method store.service.StoreService.GeAllStores is unimplemented"))

    override suspend fun createStore(request: CreateStoreRequest): CreatedStoreResponse = throw
    StatusException(Status.UNIMPLEMENTED.withDescription("Method store.service.StoreService.CreateStore is unimplemented"))

    override suspend fun getStoreById(request: GetStoreByIdRequest): GetStoreResponse = throw
    StatusException(Status.UNIMPLEMENTED.withDescription("Method store.service.StoreService.GetStoreById is unimplemented"))

    override suspend fun updateStore(request: store.service.Store): UpdateStoreResponse = throw
    StatusException(Status.UNIMPLEMENTED.withDescription("Method proto.store.service.StoreService.UpdateStore is unimplemented"))

    override suspend fun deleteStore(request: DeleteStoreByIdRequest): DeleteStoreResponse { throw
        StatusException(Status.UNIMPLEMENTED.withDescription("Method proto.store.service.StoreService.DeleteStore is unimplemented"))
    }

/*    private fun List<Store>.mapToStores() = Stores.newBuilder().addAllStores(this.map { store -> store.mapToStore() }.toList()).build()


    private fun Store.mapToStore() = store.viewer.Store.newBuilder()
            .setId(id.mapToStringValue())
            .setDescription(description.mapToStringValue())
            .setPhoneNo(phoneNo.mapToStringValue())
            .setStoreType(Type.valueOf(this.name))
            .putAllOpeningHours(openingHours.mapToOpeningHours())

            .build()


    private fun EnumMap<DayOfWeek, Store.Hours>.mapToOpeningHours(): Map<String, Hours> {

        mutableMapOf<String, Hours>().let {
            this.map { entry ->
                it.put(entry.key.name, entry.value.mapToHours())
            }

        }
    }

    private fun Store.Hours.mapToHours() =
            Hours.newBuilder()
                    .setOpening(opening.mapToTimestamp())
                    .setClosing(closing.mapToTimestamp())
                    .build()*/

}

class ValueMapper {

    companion object {

        fun String.mapToStringValue() = StringValue.newBuilder().setValue(this).build()

        fun Int.mapToIntegerValue() = Int32Value.newBuilder().setValue(this).build()

        fun StringValue.mapToString() = StringBuilder(this.value).toString()

        fun Instant.mapToTimestamp() =
                Timestamp.newBuilder()
                        .setNanos(this.nano)
                        .setSeconds(this.epochSecond)
                        .build()
    }
}
