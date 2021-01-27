package store.service

import kotlinx.serialization.*
import utility.Response

@ExperimentalSerializationApi
@Serializable
data class Store(val description: String? = null,
                 val id: String? = null,
                 val name: String? = null,
                 val dayOfWeek: DayOfWeek? = null,  //TODO Fix EnumMap<DayOfWeek,OpeningHours>
                 val phoneNo: String? = null,
                 val type: Type? = null) : Response<Store> {

    @Serializable
    enum class DayOfWeek {
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY,
        SUNDAY;
    }

    @Serializable
    enum class Type {
        ACCESSORIES,
        BOOKS_MEDIA_ELECTRONICS,
        FASHION,
        GROCERIES,
        HEALTH_BEAUTY,
        HOME_DECORATION,
        JEWELLERY,
        LEISURE,
        OPTICIAN,
        RESTAURANT_CAFE,
        SERVICE,
        SPORTS,
        STAND,
        TOYS_HOBBY;
    }
}
