package store.service

import kotlinx.serialization.*
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.protobuf.ProtoBuf
import utility.ProtoTimestampInstantSerializer
import utility.Response
import java.time.Instant

@ExperimentalSerializationApi
@Serializable
data class Store(
    val description: String? = null,
    val id: String? = null,
    val name: String? = null,
    @Serializable(with = Hours.HoursSerializer::class) val hours: Hours,
    val phoneNo: String? = null,
    val type: Type? = null
) : Response<Store> {

    @Serializable
    data class Hours(val hours: Map<DayOfWeek, OpeningHours>) {  //TODO preferably use EnumMap

        @Serializable(with = ProtoTimestampInstantSerializer::class)
        data class OpeningHours(val opening: Instant, val closing: Instant)

        @ExperimentalSerializationApi
        @Serializer(forClass = Hours::class)
        object HoursSerializer : DeserializationStrategy<Hours> {

            override fun serialize(encoder: Encoder, value: Hours) {
                ProtoBuf.encodeToByteArray(value)
            }

            override fun deserialize(decoder: Decoder): Hours {
                val hoursSerializer: KSerializer<Map<DayOfWeek, OpeningHours>> =
                    MapSerializer(DayOfWeek.serializer(), OpeningHours.serializer())
                return Hours(hoursSerializer.deserialize(decoder))
            }
        }
    }

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
