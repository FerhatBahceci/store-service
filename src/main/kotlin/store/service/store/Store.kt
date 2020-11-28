package store.service.store

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.util.*

//TODO fix serialization! Remove contextual
@Serializable
data class Store(val description: String,
                 val id: String,
                 val name: String,
                 val openingHours: @Contextual EnumMap<DayOfWeek, Hours>,
                 val phoneNo: String,
                 val type: Type) {

    @Serializable
    data class Hours(val opening: @Contextual LocalDateTime,
                     val closing: @Contextual LocalDateTime)

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
