package store.service.gateway

import kotlinx.serialization.*
import utility.proto.Timestamp

@ExperimentalSerializationApi
@Serializable
data class  Store(
    var coordinates: Coordinates? = null,
    var description: String? = null,
    var id: String? = null,
    var name: String? = null,
    var hours: Map<String, OpeningHours>? = null,
    var phoneNo: String? = null,
    var type: Type? = null,
) {

    @ExperimentalSerializationApi
    @Serializable
    data class Coordinates(var longitude: Long? = null, var latitude: Long? = null)

    @Serializable
    data class OpeningHours(var opening: Timestamp? = null, var closing: Timestamp? = null)

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
        TOYS_HOBBY,
        UNKNOWN
    }
}
