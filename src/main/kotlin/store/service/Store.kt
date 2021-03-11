package store.service

import kotlinx.serialization.*
import utility.proto.Timestamp

@ExperimentalSerializationApi
@Serializable
data class Store(
        val coordinates: Coordinates,
        val description: String,
        val id: String? = null,
        val name: String,
        val hours: Map<String, OpeningHours>,
        val phoneNo: String,
        val type: Type,
) {

    @ExperimentalSerializationApi
    @Serializable
    data class Coordinates(val longitude: Long, val latitude: Long)

    //  TODO Invalid Map type. Maps MUST have string keys, does not work by simply providing the below custom codec for the Enum. Could possibly be wrapped into its own class Hours and then provide a custom Hours codec
/*    @Serializable
    enum class DayOfWeek {
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY,
        SUNDAY;

    private class DayOfWeekCodec : Codec<Store.DayOfWeek> {

        override fun decode(reader: BsonReader, decoderContext: DecoderContext): Store.DayOfWeek =
                Store.DayOfWeek.valueOf(reader.readString())

        override fun encode(writer: BsonWriter?, value: Store.DayOfWeek?, encoderContext: EncoderContext?) {
            writer?.writeString(value?.name)
        }

        override fun getEncoderClass(): Class<Store.DayOfWeek> =
                Store.DayOfWeek::class.java
    }
    }*/

    @Serializable
    data class OpeningHours(val opening: Timestamp, val closing: Timestamp)

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
