package net.badata.protobuf.converter.writer;

import com.google.protobuf.MessageLite;
import net.badata.protobuf.converter.exception.InvocationException;
import net.badata.protobuf.converter.exception.WriteException;
import net.badata.protobuf.converter.resolver.FieldResolver;
import net.badata.protobuf.converter.type.TypeConverter;
import net.badata.protobuf.converter.utils.FieldUtils;

/**
 * Writes data to the protobuf dto.
 *
 * @author jsjem
 * @author Roman Gushel
 */
public class ProtobufWriter extends AbstractWriter {

	/**
	 * Constructor.
	 *
	 * @param destination Protobuf dto builder instance.
	 */
	public ProtobufWriter(final MessageLite.Builder destination) {
		super(destination);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void write(final Object destination, final FieldResolver fieldResolver, final Object value) throws
			WriteException {
		if (value != null) {
			TypeConverter<?, ?> typeConverter = fieldResolver.getTypeConverter();
			try {
				FieldUtils.setProtobufFieldValue(fieldResolver, destination, typeConverter.toProtobufValue(value));
			} catch (InvocationException e) {
				throw new WriteException(e);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * Note: if the field value of a protobuf object is to be unset, the `clear[FieldName]` method will be called.
	 */
	@Override
	protected void unset(Object destination, FieldResolver fieldResolver) throws WriteException {
		try {
			FieldUtils.clearProtobufFieldValue(fieldResolver, destination);
		} catch (InvocationException e) {
			throw new WriteException(e);
		}
	}
}
