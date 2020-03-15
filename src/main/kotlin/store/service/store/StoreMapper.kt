package store.viewer.service

import com.google.protobuf.StringValue
import com.google.protobuf.Timestamp
import store.viewer.Store
import store.viewer.Stores
import store.viewer.Type
import java.time.Instant

fun mapToStores(stores: Set<store.viewer.repositories.Store>): Stores =
        Stores.newBuilder().addAllStores(stores.map { store -> store.mapToStore() }).build()

fun store.viewer.repositories.Store.mapToStore(): Store = Store.newBuilder()
        .setId(id.mapToStringValue())
        .setDescription(description?.mapToStringValue())
        .setPhoneNo(phoneNo?.mapToStringValue())
        .setStoreType(type?.mapToType())
        .setCloses(closes?.mapToTimestamp())
        .setOpens(opens?.mapToTimestamp())
        .build()

fun String.mapToStringValue() = StringValue.newBuilder().setValue(this).build()

fun Instant.mapToTimestamp(): Timestamp = Timestamp.newBuilder().setNanos(this.nano).setSeconds(this.epochSecond).build()

fun store.viewer.repositories.Store.Type.mapToType() = Type.valueOf(this.name)
