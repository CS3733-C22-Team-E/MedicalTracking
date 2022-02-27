package edu.wpi.teame.model.mongoCodecs;

import edu.wpi.teame.model.Credential;
import edu.wpi.teame.model.enums.AccessLevel;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import java.lang.ref.WeakReference;
import java.nio.charset.StandardCharsets;

public class CredentialCodec implements Codec<Credential> {
    @Override
    public Credential decode(BsonReader reader, DecoderContext decoderContext) {
        Credential credential = new Credential();
        reader.readStartDocument();

        while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            String fieldName = reader.readName();
            if (fieldName.equals("_id")) {
                credential.setId(reader.readInt32());
            } else if(fieldName.equals("salt")) {
                //todo ask amitai ab how to get string to bytes
                credential.setSalt(reader.readString().getBytes(StandardCharsets.UTF_8));
            }else if(fieldName.equals("username")) {
                credential.setUsername(reader.readString());
            }else if (fieldName.equals("password")) {
                credential.setPassword(reader.readString());
            }else if(fieldName.equals("accessLevel")) {
                credential.setAccessLevel(AccessLevel.values()[reader.readInt32()]);
            }else if(fieldName.equals("imageURL")) {
                credential.setImageURL(reader.readString());
            }else if(fieldName.equals("isDeleted")) {
                credential.setDeleted(reader.readInt32() == 1);
            }
        }
        reader.readEndDocument();

        return credential;
    }

    @Override
    public void encode(BsonWriter writer, Credential value, EncoderContext encoderContext) {
        writer.writeStartDocument();
        writer.writeInt32("_id", value.getId());
        writer.writeString("salt", value.getSalt().toString());
        writer.writeString("username", value.getUsername());
        writer.writeString("password", value.getPassword());
        writer.writeInt32("accessLevel", value.getAccessLevel().ordinal());
        writer.writeString("imageURL", value.getImageURL());
        writer.writeInt32("isDeleted", value.getIsDeleted() ? 1 : 0);
        writer.writeEndDocument();

    }

    @Override
    public Class<Credential> getEncoderClass() {
        return Credential.class;
    }
}
