package net.badata.protobuf.converter.mapping;

import com.google.protobuf.MessageLite;
import net.badata.protobuf.converter.exception.InvocationException;
import net.badata.protobuf.converter.exception.MappingException;
import net.badata.protobuf.converter.inspection.NullValueInspector;
import net.badata.protobuf.converter.resolver.FieldResolver;
import net.badata.protobuf.converter.utils.FieldUtils;


/**
 * Implementation of {@link net.badata.protobuf.converter.mapping.Mapper Mapper} that is applied by default.
 * This implementation maps fields values directly from domain instance to related protobuf instance and vice versa.
 *
 * @author jsjem
 * @author Roman Gushel
 */
public class DefaultMapperImpl implements Mapper {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T extends MessageLite> MappingResult mapToDomainField(final FieldResolver fieldResolver, final T protobuf,
			final Object domain) throws MappingException {
		// Only complex message fields have "has" method in protobuf 3.
		if (FieldUtils.isComplexType(fieldResolver.getField())) {
			try {
				if (!FieldUtils.isProtobufHasFieldValue(fieldResolver, protobuf)) {
					return new MappingResult(MappingResult.Result.UNSET, null, domain);
				}
			} catch (InvocationException ignored) {} // no `has` method, continue
		}

		Object protobufFieldValue;
		try {
			protobufFieldValue = FieldUtils.getProtobufFieldValue(fieldResolver, protobuf);
		} catch (InvocationException e) {
			throw new MappingException(e);
		}
		NullValueInspector nullInspector = fieldResolver.getNullValueInspector();
		if (nullInspector.isNull(protobufFieldValue)) {
			return new MappingResult(MappingResult.Result.UNSET, null, domain);
		}
		if (FieldUtils.isComplexType(fieldResolver.getField())) {
			return new MappingResult(MappingResult.Result.NESTED_MAPPING, protobufFieldValue, domain);
		}
		if (FieldUtils.isCollectionType(fieldResolver.getField())) {
			return new MappingResult(MappingResult.Result.COLLECTION_MAPPING, protobufFieldValue, domain);
		}
		return new MappingResult(MappingResult.Result.MAPPED, protobufFieldValue, domain);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T extends MessageLite.Builder> MappingResult mapToProtobufField(final FieldResolver fieldResolver,
			final Object domain, final T protobufBuilder) throws MappingException {
		Object domainFieldValue;
		try {
			domainFieldValue = FieldUtils.getDomainFieldValue(fieldResolver, domain);
		} catch (InvocationException e) {
			throw new MappingException(e);
		}
		NullValueInspector nullInspector = fieldResolver.getNullValueInspector();
		if (nullInspector.isNull(domainFieldValue)) {
			return new MappingResult(MappingResult.Result.UNSET, null, protobufBuilder);
		}
		if (FieldUtils.isComplexType(fieldResolver.getField())) {
			return new MappingResult(MappingResult.Result.NESTED_MAPPING, domainFieldValue, protobufBuilder);
		}
		if (FieldUtils.isCollectionType(fieldResolver.getField())) {
			return new MappingResult(MappingResult.Result.COLLECTION_MAPPING, domainFieldValue, protobufBuilder);
		}
		return new MappingResult(MappingResult.Result.MAPPED, domainFieldValue, protobufBuilder);
	}

}
