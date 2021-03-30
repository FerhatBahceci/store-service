package store.service.service

import com.google.protobuf.Timestamp
import kotlinx.serialization.ExperimentalSerializationApi
import proto.store.service.Coordinates
import proto.store.service.OpeningHours
import proto.store.service.Store
import proto.store.service.Type

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
            Coordinates.newBuilder()
                .setLatitude(latitude ?: 0)
                .setLongitude(longitude ?: 0)
                .build()

        private fun store.service.gateway.Store.Type.mapToProtoStoreType() =
            Type.forNumber(this.ordinal)

        private fun Map<String, store.service.gateway.Store.OpeningHours>.mapToProtoHours() =
            map { it.key to it.value.mapToProtoOpeningHours() }.toMap()

        private fun store.service.gateway.Store.OpeningHours.mapToProtoOpeningHours() =
            OpeningHours.newBuilder()
                .setOpening(
                    Timestamp.newBuilder().setSeconds(this.opening?.seconds ?: 0).setNanos(this.opening?.nanos ?: 0)
                ).setClosing(
                    Timestamp.newBuilder().setSeconds(this.closing?.seconds ?: 0).setNanos(this.closing?.nanos ?: 0)
                ).build()
    }
}
