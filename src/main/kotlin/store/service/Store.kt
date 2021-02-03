package store.service

import kotlinx.serialization.*
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.protobuf.ProtoBuf
import utility.Response
import utility.proto.Timestamp

@ExperimentalSerializationApi
interface AbstractStore {
    val coordinates: Coordinates?
    val description: String?
    val id: String?
    val name: String?
    val phoneNo: String?
}

@ExperimentalSerializationApi
@Serializable(with = Coordinates.CoordinatesSerializer::class)
data class Coordinates(val longitude: Long?, val latitude: Long?) {

    @Serializer(forClass = Coordinates::class)
    object CoordinatesSerializer : DeserializationStrategy<Coordinates> {

        override fun serialize(encoder: Encoder, value: Coordinates) {
            ProtoBuf.encodeToByteArray(value)
        }

        override fun deserialize(decoder: Decoder): Coordinates {
            var longitude: Long? = null
            var latitude: Long? = null

            decoder.decodeStructure(descriptor) {
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> longitude =
                            decodeLongElement(descriptor, 0)
                        1 -> latitude =
                            decodeLongElement(descriptor, 1)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
            }
            return Coordinates(longitude, latitude)
        }

    }
}

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
        data class OpeningHours(val opening: Timestamp, val closing: Timestamp)

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
