package store.service

import kotlinx.serialization.*
import utility.proto.Timestamp

@ExperimentalSerializationApi
@Serializable
data class Store(
        val coordinates: Coordinates? = null,
        val description: String? = null,
        val id: String? = null,
        val name: String? = null,
        val hours: Map<String, OpeningHours>? = null,
        val phoneNo: String? = null,
        val type: Type? = null,
) {

    @ExperimentalSerializationApi
    @Serializable
    data class Coordinates(val longitude: Long? = null, val latitude: Long? = null)

    @Serializable
    data class OpeningHours(val opening: Timestamp? = null, val closing: Timestamp? = null)

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
