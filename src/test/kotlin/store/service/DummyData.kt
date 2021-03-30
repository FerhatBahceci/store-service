package store.service

import com.google.protobuf.Timestamp
import kotlinx.serialization.ExperimentalSerializationApi
import proto.store.service.*
import proto.store.service.Coordinates
import proto.store.service.Store

class DummyData {

    companion object {

        const val DESCRIPTION = "DESCRIPTION"
        const val DAY_OF_WEEK = "FRIDAY"
        const val NANOS = 0
        const val OPENING_SECONDS = 37800L
        const val CLOSING_SECONDS = 79200L
        const val LATITUDE = 420L
        const val LONGITUDE = 420L
        const val PHONE_NO = "+46 111 111 11"

        @ExperimentalSerializationApi
        fun createStore(
            name: String,
            id: String = createId(),
            type: store.service.gateway.Store.Type = store.service.gateway.Store.Type.GROCERIES
        ) =
            store.service.gateway.Store(
                description = DESCRIPTION,
                id = id,
                type = type,
                name = name,
                phoneNo = PHONE_NO,
                coordinates = store.service.gateway.Store.Coordinates(latitude = LATITUDE, longitude = LONGITUDE),
                hours = mapOf(
                    DAY_OF_WEEK to store.service.gateway.Store.OpeningHours(
                        opening = utility.proto.Timestamp(nanos = NANOS, seconds = OPENING_SECONDS),
                        closing = utility.proto.Timestamp(nanos = NANOS, seconds = CLOSING_SECONDS)
                    )
                )
            )

        fun createProtoStore(name: String, id: String = createId(), type: Type = Type.GROCERIES) =
            Store.newBuilder()
                .putHours(
                    DAY_OF_WEEK,
                    OpeningHours.newBuilder()
                        .setOpening(Timestamp.newBuilder().setNanos(NANOS).setSeconds(OPENING_SECONDS))  // 10.30
                        .setClosing(Timestamp.newBuilder().setNanos(NANOS).setSeconds(CLOSING_SECONDS))  // 22.00
                        .build()
                )
                .setCoordinates(Coordinates.newBuilder().setLatitude(LATITUDE).setLongitude(LONGITUDE).build())
                .setDescription(DESCRIPTION)
                .setId(id)
                .setName(name)
                .setPhoneNo(PHONE_NO)
                .setType(type)
                .build()

        fun createId() = (Math.random() * 100).toString()
    }
}


