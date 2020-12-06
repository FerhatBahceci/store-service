package store.service.gateway

import kotlinx.serialization.*
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.util.*

@Serializable(with = LocalDateTimeSerializer::class)
@ExperimentalSerializationApi
data class Store(val description: String,
                 val id: String,
                 val name: String,
                 val openingHours: EnumMap<DayOfWeek, Hours>,
                 val phoneNo: String,
                 val type: Type) {

    data class Hours(val opening: LocalDateTime, val closing: LocalDateTime)

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

@ExperimentalSerializationApi
@Serializer(forClass = LocalDateTime::class)
object LocalDateTimeSerializer : KSerializer<LocalDateTime> {
    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): LocalDateTime {
        return LocalDateTime.parse(decoder.decodeString())
    }
}
