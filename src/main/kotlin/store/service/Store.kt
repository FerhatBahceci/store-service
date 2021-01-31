package store.service

import kotlinx.serialization.*
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.protobuf.ProtoBuf
import utility.ProtoTimestampInstantSerializer
import utility.Response
import java.lang.Exception
import java.time.Instant

@ExperimentalSerializationApi
@Serializable
data class Store(
    val description: String? = null,
    val id: String? = null,
    val name: String? = null,
    @Serializable(with = Hours.HoursSerializer::class) val hours: Hours? = null,
    val phoneNo: String? = null,
    val type: Type? = null
) : Response<Store> {

    @Serializable
    data class Hours(val hours: Map<DayOfWeek, OpeningHours>) {  //TODO preferably use EnumMap

        @ExperimentalSerializationApi
        @Serializer(forClass = Hours::class)
        object HoursSerializer : DeserializationStrategy<Hours> {

            override fun serialize(encoder: Encoder, value: Hours) {
                ProtoBuf.encodeToByteArray(value)
            }

            override fun deserialize(decoder: Decoder): Hours =
                Hours(MapSerializer(DayOfWeek.serializer(), OpeningHours.serializer()).deserialize(decoder))
        }

        @Serializable(with = OpeningHours.OpeningHoursSerializer::class)
        data class OpeningHours(val opening: Instant?, val closing: Instant?) {

            @ExperimentalSerializationApi
            @Serializer(forClass = OpeningHours::class)
            object OpeningHoursSerializer : DeserializationStrategy<OpeningHours> {

                override fun serialize(encoder: Encoder, value: OpeningHours) {
                    ProtoBuf.encodeToByteArray(value)
                }

                override fun deserialize(decoder: Decoder): OpeningHours {
                    var opening: Instant? = null
                    var closing: Instant? = null

                    try {
                        decoder.decodeStructure(descriptor) {
                            while (true) {
                                when (val index = decodeElementIndex(descriptor)) {
                                    0 -> opening =
                                        decodeSerializableElement(descriptor, 0, ProtoTimestampInstantSerializer)
                                    1 -> closing =
                                        decodeSerializableElement(descriptor, 1, ProtoTimestampInstantSerializer)
                                    CompositeDecoder.DECODE_DONE -> break
                                    else -> error("Unexpected index: $index")
                                }
                            }
                        }
                    } catch (e: Exception) {

                    }
                    return OpeningHours(opening, closing)
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
