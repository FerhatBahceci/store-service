package store.service.store

import java.time.DayOfWeek
import java.time.Instant
import java.util.*

data class Store(val description: String,
                 val id: String,
                 val name: String,
                 val openingHours: EnumMap<DayOfWeek, Hours>,
                 val phoneNo: String,
                 val type: Type) {

    data class Hours(val opening: Instant, val closing: Instant)

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