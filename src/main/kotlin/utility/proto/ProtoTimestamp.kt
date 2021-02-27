package utility.proto

import kotlinx.serialization.*
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.protobuf.ProtoBuf

@ExperimentalSerializationApi
@Serializable(with = Timestamp.TimestampSerializer::class)
data class Timestamp(val seconds: Long, val nanos: Int) {

    @ExperimentalSerializationApi
    @Serializer(forClass = Timestamp::class)
    object TimestampSerializer : DeserializationStrategy<Timestamp> {

        override fun serialize(encoder: Encoder, value: Timestamp) {
            ProtoBuf.encodeToByteArray(value)
        }

        override fun deserialize(decoder: Decoder): Timestamp {
            var seconds: Long = 0
            var nanos = 0

            decoder.decodeStructure(descriptor) {
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> seconds = decodeLongElement(descriptor, 0)
                        1 -> nanos = decodeIntElement(descriptor, 1)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
            }
            return Timestamp(seconds, nanos)
        }
    }
}
