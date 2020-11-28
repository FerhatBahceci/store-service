package store.service.store

import kotlinx.serialization.Contextual
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber
import store.service.DayOfWeek
import java.time.LocalDateTime
import java.util.*

//TODO fix serialization! Remove contextual
@ExperimentalSerializationApi
@Serializable
data class Store(@ProtoNumber(1) val description: String,
                 @ProtoNumber(2) val id: String,
                 @ProtoNumber(3) val name: String,
                 @ProtoNumber(4) val openingHours: @Contextual EnumMap<DayOfWeek, Hours>,
                 @ProtoNumber(5) val phoneNo: String,
                 @ProtoNumber(6) val type: Type) {

    @Serializable
    data class Hours(@ProtoNumber(1) val opening: @Contextual LocalDateTime,
                     @ProtoNumber(2) val closing: @Contextual LocalDateTime)

    @Serializable
    enum class Type {
        ACCESSORIES,
        LEISURE,
        BOOKS_MEDIA_ELECTRONICS,
        HEALTH_BEAUTY,
        OPTICIAN,
        HOME_DECORATION,
        TOYS_HOBBY,
        GROCERIES,
        RESTAURANT_CAFE,
        JEWELLERY,
        STAND,
        FASHION,
        SERVICE,
        SPORTS,
    }
}
