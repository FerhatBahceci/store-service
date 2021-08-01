package utility.bson

import kotlinx.serialization.*
import org.bson.BsonReader
import org.bson.BsonWriter
import org.bson.codecs.Codec
import org.bson.codecs.DecoderContext
import org.bson.codecs.EncoderContext
import utility.proto.Timestamp
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.LocalTime

@ExperimentalSerializationApi
class ProtoTimestampCodec : Codec<Timestamp> {

    private val formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withZone(ZoneId.systemDefault())

    override fun encode(writer: BsonWriter, value: Timestamp, encoderContext: EncoderContext) {
        writer.writeString(
            formatter.format(
                LocalTime.ofSecondOfDay(value.seconds ?: 0)
            )
        )
    }

    override fun decode(reader: BsonReader, decoderContext: DecoderContext): Timestamp =
        reader.readString().let {
            LocalTime.from(formatter.parse(it)).toSecondOfDay().let {
                Timestamp(it.toLong())
            }
        }

    override fun getEncoderClass(): Class<Timestamp> = Timestamp::class.java
}
