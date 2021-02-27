package utility.bson

import kotlinx.serialization.*
import java.util.*
import org.bson.BsonReader
import org.bson.BsonTimestamp
import org.bson.BsonWriter
import org.bson.codecs.Codec
import org.bson.codecs.DecoderContext
import org.bson.codecs.EncoderContext
import utility.proto.Timestamp

@ExperimentalSerializationApi
class ProtoTimestampCodec : Codec<Timestamp> {

    override fun encode(writer: BsonWriter, value: Timestamp, encoderContext: EncoderContext) {
        writer.writeTimestamp(BsonTimestamp(value.seconds.toInt(), value.nanos))
    }

    override fun decode(reader: BsonReader, decoderContext: DecoderContext): Timestamp =
            reader.readTimestamp().let {
                Timestamp(seconds = it.time.toLong(), it.inc)
            }

    override fun getEncoderClass(): Class<Timestamp> =
            Timestamp::class.java
}
