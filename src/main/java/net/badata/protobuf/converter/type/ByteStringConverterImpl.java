package net.badata.protobuf.converter.type;

import com.google.protobuf.ByteString;

/**
 * Converts domain byte[] field value to protobuf {@link com.google.protobuf.ByteString ByteString} field value.
 *
 * @author ppoffice
 */
public class ByteStringConverterImpl implements TypeConverter<byte[], ByteString> {

    /**
     * {@inheritDoc}
     */
    @Override
    public byte[] toDomainValue(Object instance) {
        return ((ByteString) instance).toByteArray();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ByteString toProtobufValue(Object instance) {
        return ByteString.copyFrom((byte[]) instance);
    }
}
