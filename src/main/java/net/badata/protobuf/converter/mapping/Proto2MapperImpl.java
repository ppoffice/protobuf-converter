package net.badata.protobuf.converter.mapping;

import com.google.protobuf.MessageLite;
import net.badata.protobuf.converter.exception.InvocationException;
import net.badata.protobuf.converter.exception.MappingException;
import net.badata.protobuf.converter.resolver.FieldResolver;
import net.badata.protobuf.converter.utils.FieldUtils;

/**
 * Implementation of {@link net.badata.protobuf.converter.mapping.Mapper Mapper} that is applied to protobuf 2.x messages.
 * This implementation checks if a field is set during protobuf to domain object conversion. If field is not set,
 * the corresponding domain field will be set to default value using {@link net.badata.protobuf.converter.inspection.DefaultValue}.
 * Otherwise, this class behaves the same as {@link DefaultMapperImpl}.
 *
 * @author ppoffice
 */
public class Proto2MapperImpl extends DefaultMapperImpl {
    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends MessageLite> MappingResult mapToDomainField(final FieldResolver fieldResolver, final T protobuf,
            final Object domain) throws MappingException {
        try {
            if (!FieldUtils.isProtobufHasFieldValue(fieldResolver, protobuf)) {
                return new MappingResult(MappingResult.Result.UNSET, null, domain);
            }
        } catch (InvocationException ignored) {} // no `has` method, continue
        Object protobufFieldValue;
        try {
            protobufFieldValue = FieldUtils.getProtobufFieldValue(fieldResolver, protobuf);
        } catch (InvocationException e) {
            throw new MappingException(e);
        }
        if (FieldUtils.isComplexType(fieldResolver.getField())) {
            return new MappingResult(MappingResult.Result.NESTED_MAPPING, protobufFieldValue, domain);
        }
        if (FieldUtils.isCollectionType(fieldResolver.getField())) {
            return new MappingResult(MappingResult.Result.COLLECTION_MAPPING, protobufFieldValue, domain);
        }
        return new MappingResult(MappingResult.Result.MAPPED, protobufFieldValue, domain);
    }
}
