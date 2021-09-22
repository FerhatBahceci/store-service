package store.service.service

import kotlinx.serialization.ExperimentalSerializationApi
import proto.store.service.Store
import com.google.protobuf.Timestamp

@ExperimentalSerializationApi
fun store.service.gateway.Store.mapToProtoStore() =
        Store.newBuilder()
                .setCoordinates(coordinates?.mapToProtoCoordinates())
                .setDescription(description)
                .setId(id)
                .setName(name)
                .setPhoneNo(phoneNo)
                .putAllHours(hours?.mapToProtoHours())
                .setType(type?.mapToProtoStoreType())
                .build()

@ExperimentalSerializationApi
private fun store.service.gateway.Store.Coordinates.mapToProtoCoordinates() =
        Store.Coordinates.newBuilder()
                .setLatitude(latitude ?: 0)
                .setLongitude(longitude ?: 0)
                .build()

@ExperimentalSerializationApi
private fun store.service.gateway.Store.Type.mapToProtoStoreType() =
        Store.Type.forNumber(this.ordinal)

@ExperimentalSerializationApi
private fun Map<String, store.service.gateway.Store.OpeningHours>.mapToProtoHours() =
        map { it.key to it.value.mapToProtoOpeningHours() }.toMap()

@ExperimentalSerializationApi
private fun store.service.gateway.Store.OpeningHours.mapToProtoOpeningHours() =
        Store.OpeningHours.newBuilder()
                .setOpening(this.opening?.mapToTimestamp())
                .setClosing(this.closing?.mapToTimestamp())
                .build()

@ExperimentalSerializationApi
private fun utility.proto.Timestamp.mapToTimestamp() =
        Timestamp.newBuilder()
                .setSeconds(this.seconds ?: 0)
                .setNanos(this.nanos ?: 0)
                .build()
