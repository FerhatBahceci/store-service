package utility

import kotlinx.serialization.*
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.protobuf.ProtoBuf
import java.lang.Exception
import java.time.Instant

@ExperimentalSerializationApi
@Serializer(forClass = Instant::class)
object ProtoTimestampInstantSerializer : DeserializationStrategy<Instant> {

    override fun serialize(encoder: Encoder, value: Instant) {
        ProtoBuf.encodeToByteArray(value)
    }

    override fun deserialize(decoder: Decoder): Instant {
        var seconds = 0L
        var nanos = 0
        try {
            decoder.decodeStructure(descriptor) {
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> seconds = decodeLongElement(descriptor, index)
                        1 -> nanos = decodeIntElement(descriptor, index)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
            }
        } catch (e: Exception) {
            print(e)
        }

        return Instant.ofEpochSecond(seconds, nanos.toLong())
    }
}
