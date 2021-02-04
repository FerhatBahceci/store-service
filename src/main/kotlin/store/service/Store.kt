package store.service

import kotlinx.serialization.*
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.protobuf.ProtoBuf
import utility.Response
import utility.proto.Timestamp

@ExperimentalSerializationApi
@Serializable
data class Store(
    override val coordinates: Coordinates? = null,
    override val description: String? = null,
    override val id: String? = null,
    override val name: String? = null,
    val hours: Hours? = null,
    override val phoneNo: String? = null,
    val type: Type? = null,
) : AbstractStore, Response<Store> {

    @Serializable(with = Hours.HoursSerializer::class)
    data class Hours(val hours: Map<DayOfWeek, OpeningHours>) {  //TODO preferably use EnumMap

        @Serializer(forClass = Hours::class)
        object HoursSerializer : DeserializationStrategy<Hours> {

            override fun serialize(encoder: Encoder, value: Hours) {
                ProtoBuf.encodeToByteArray(value)
            }

            override fun deserialize(decoder: Decoder): Hours =
                Hours(MapSerializer(DayOfWeek.serializer(), OpeningHours.serializer()).deserialize(decoder))
        }

        @Serializable
        data class OpeningHours(val opening: Timestamp?, val closing: Timestamp?)

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
