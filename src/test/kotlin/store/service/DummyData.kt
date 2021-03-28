package store.service

import com.google.protobuf.Timestamp
import proto.store.service.*
import proto.store.service.Coordinates
import proto.store.service.CreateStoreRequest
import proto.store.service.Store

class DummyData {

    companion object {

        val PROTO_STORE = Store.newBuilder()
            .putHours(
                "FRIDAY",
                OpeningHours.newBuilder()
                    .setOpening(Timestamp.newBuilder().setNanos(0).setSeconds(37800L))  //10.30
                    .setClosing(Timestamp.newBuilder().setNanos(0).setSeconds(79200L))    // 22.00
                    .build()
            )
            .setCoordinates(Coordinates.newBuilder().setLatitude(420L).setLongitude(420L).build())
            .setDescription("DESCRIPTION")
            .setId("420".plus(Math.random()))
            .setName("Wallmart")
            .setPhoneNo("+46 111 111 11")
            .setType(Type.GROCERIES)
            .build()

        val PROTO_REQUEST = CreateStoreRequest.newBuilder()
            .setStore(PROTO_STORE)
            .build()

    }
}

