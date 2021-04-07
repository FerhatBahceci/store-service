package store.service.service

import com.google.protobuf.Timestamp
import kotlinx.serialization.ExperimentalSerializationApi
import proto.store.service.Store

@ExperimentalSerializationApi
class StoreMapper {

    companion object {

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

        private fun store.service.gateway.Store.Coordinates.mapToProtoCoordinates() =
                Store.Coordinates.newBuilder()
                        .setLatitude(latitude ?: 0)
                        .setLongitude(longitude ?: 0)
                        .build()

        private fun store.service.gateway.Store.Type.mapToProtoStoreType() =
                Store.Type.forNumber(this.ordinal)

        private fun Map<String, store.service.gateway.Store.OpeningHours>.mapToProtoHours() =
                map { it.key to it.value.mapToProtoOpeningHours() }.toMap()

        private fun store.service.gateway.Store.OpeningHours.mapToProtoOpeningHours() =
                Store.OpeningHours.newBuilder()
                        .setOpening(this.opening?.mapToTimestamp())
                        .setClosing(this.closing?.mapToTimestamp())
                        .build()

        private fun utility.proto.Timestamp.mapToTimestamp() =
                Timestamp.newBuilder().setSeconds(this.seconds ?: 0).setNanos(this.nanos ?: 0).build()

    }
}
