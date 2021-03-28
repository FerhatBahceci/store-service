package store.service

import com.google.protobuf.Timestamp
import proto.store.service.*
import proto.store.service.Coordinates
import proto.store.service.Store

class DummyData {

    companion object {

        fun createProtoStore(name: String, id: String, type: Type = Type.GROCERIES) =
            Store.newBuilder()
                .putHours(
                    "FRIDAY",
                    OpeningHours.newBuilder()
                        .setOpening(Timestamp.newBuilder().setNanos(0).setSeconds(37800L))  // 10.30
                        .setClosing(Timestamp.newBuilder().setNanos(0).setSeconds(79200L))  // 22.00
                        .build()
                )
                .setCoordinates(Coordinates.newBuilder().setLatitude(420L).setLongitude(420L).build())
                .setDescription("DESCRIPTION")
                .setId(id)
                .setName(name)
                .setPhoneNo("+46 111 111 11")
                .setType(type)
                .build()

        fun createId() = (Math.random() * 100).toString()
    }
}


