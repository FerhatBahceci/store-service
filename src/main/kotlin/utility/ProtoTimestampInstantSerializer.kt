package utility

import kotlinx.serialization.*
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.CompositeDecoder.Companion.DECODE_DONE
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import java.lang.Exception
import java.time.Instant

@ExperimentalSerializationApi
@Serializer(forClass = Instant::class)
object ProtoTimestampInstantSerializer : DeserializationStrategy<Instant> {

    override fun serialize(encoder: Encoder, value: Instant) {
        encoder.encodeLong(value.epochSecond)
        encoder.encodeInt(value.nano)
    }

    override fun deserialize(decoder: Decoder): Instant {
        var seconds = 0L
        var nanos = 0
        try {
            val composite = decoder.beginStructure(descriptor)
            decoder.decodeStructure(descriptor) {
                while (true) {
                    when (val index = composite.decodeElementIndex(descriptor)) {
                        0 -> seconds = composite.decodeLongElement(descriptor, 0)
                        1 -> nanos = composite.decodeIntElement(descriptor, 1)
                        DECODE_DONE -> break // Input is over
                        else -> error("Unexpected index: $index")
                    }
                }
                composite.endStructure(descriptor)
            }
        } catch (e: Exception) {
            print(e)
        }
        return Instant.ofEpochSecond(seconds, nanos.toLong())
    }
}
