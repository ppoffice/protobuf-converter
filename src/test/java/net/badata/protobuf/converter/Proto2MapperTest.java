package net.badata.protobuf.converter;

import net.badata.protobuf.converter.domain.Proto2Domain;
import net.badata.protobuf.converter.exception.MappingException;
import net.badata.protobuf.converter.mapping.DefaultMapperImpl;
import net.badata.protobuf.converter.mapping.MappingResult;
import net.badata.protobuf.converter.mapping.Proto2MapperImpl;
import net.badata.protobuf.converter.proto.MappingProto;
import net.badata.protobuf.converter.proto.Proto2Proto;
import net.badata.protobuf.converter.resolver.AnnotatedFieldResolverFactoryImpl;
import net.badata.protobuf.converter.resolver.FieldResolver;
import net.badata.protobuf.converter.resolver.FieldResolverFactory;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.Arrays;

import static net.badata.protobuf.converter.mapping.MappingResult.Result;

/**
 * @author jsjem
 * @author Roman Gushel
 */
public class Proto2MapperTest {

	private DefaultMapperImpl mapper;
	private FieldResolverFactory fieldResolverFactory;
	private Proto2Domain.Test testDomain;
	private Proto2Proto.Proto2MappingTest testProtobuf;
	private Proto2Domain.Test unsetTestDomain;
	private Proto2Proto.Proto2MappingTest unsetTestProtobuf;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setUp() throws Exception {
		mapper = new Proto2MapperImpl();
		fieldResolverFactory = new AnnotatedFieldResolverFactoryImpl();
		createTestDomain();
		createTestProtobuf();
		createUnsetTestDomain();
		createUnsetTestProtobuf();
	}

	@After
	public void tearDown() throws Exception {
		mapper = null;
		testProtobuf = null;
		testDomain = null;
		unsetTestProtobuf = null;
		unsetTestDomain = null;
	}

	private void createTestProtobuf() {
		testProtobuf =  Proto2Proto.Proto2MappingTest.newBuilder()
				.setBooleanValue(true)
				.setFloatValue(0.1f)
				.setDoubleValue(0.5)
				.setIntValue(1)
				.setLongValue(2L)
				.setStringValue("3")
				.setNestedValue(Proto2Proto.Proto2NestedTest.newBuilder().setStringValue("4"))
				.addStringListValue("10")
				.addNestedListValue(Proto2Proto.Proto2NestedTest.newBuilder().setStringValue("20"))
				.build();
	}

	private void createTestDomain() {
		testDomain = new Proto2Domain.Test();
		testDomain.setBooleanValue(false);
		testDomain.setFloatValue(0.2f);
		testDomain.setDoubleValue(0.6);
		testDomain.setIntValue(101);
		testDomain.setLongValue(102L);
		testDomain.setStringValue("103");
		Proto2Domain.NestedTest nested = new Proto2Domain.NestedTest();
		nested.setStringValue("104");
		testDomain.setNestedValue(nested);
		testDomain.setStringListValue(Arrays.asList("110"));
		Proto2Domain.NestedTest nestedList = new Proto2Domain.NestedTest();
		nested.setStringValue("120");
		testDomain.setNestedListValue(Arrays.asList(nestedList));
	}

	private void createUnsetTestDomain() {
		unsetTestDomain = new Proto2Domain.Test();
	}

	private void createUnsetTestProtobuf() {
		unsetTestProtobuf =  Proto2Proto.Proto2MappingTest.newBuilder().build();
	}

	@Test
	public void testMapObjectToDomain() throws MappingException {
		exception = ExpectedException.none();

		MappingResult result = mapper.mapToDomainField(findDomainField("floatValue"), testProtobuf, testDomain);
		testMappingResult(result, Result.MAPPED, testProtobuf.getFloatValue(), testDomain);

		result = mapper.mapToDomainField(findDomainField("doubleValue"), testProtobuf, testDomain);
		testMappingResult(result, Result.MAPPED, testProtobuf.getDoubleValue(), testDomain);

		result = mapper.mapToDomainField(findDomainField("intValue"), testProtobuf, testDomain);
		testMappingResult(result, Result.MAPPED, testProtobuf.getIntValue(), testDomain);

		result = mapper.mapToDomainField(findDomainField("longValue"), testProtobuf, testDomain);
		testMappingResult(result, Result.MAPPED, testProtobuf.getLongValue(), testDomain);

		result = mapper.mapToDomainField(findDomainField("stringValue"), testProtobuf, testDomain);
		testMappingResult(result, Result.MAPPED, testProtobuf.getStringValue(), testDomain);

	}

	@Test
	public void testUnsetMapObjectToDomain() throws MappingException {
		exception = ExpectedException.none();

		MappingResult result = mapper.mapToDomainField(findDomainField("floatValue"), unsetTestProtobuf, unsetTestDomain);
		testMappingResult(result, Result.UNSET, null, unsetTestDomain);

		result = mapper.mapToDomainField(findDomainField("doubleValue"), unsetTestProtobuf, unsetTestDomain);
		testMappingResult(result, Result.UNSET, null, unsetTestDomain);

		result = mapper.mapToDomainField(findDomainField("intValue"), unsetTestProtobuf, unsetTestDomain);
		testMappingResult(result, Result.UNSET, null, unsetTestDomain);

		result = mapper.mapToDomainField(findDomainField("longValue"), unsetTestProtobuf, unsetTestDomain);
		testMappingResult(result, Result.UNSET, null, unsetTestDomain);

		result = mapper.mapToDomainField(findDomainField("stringValue"), unsetTestProtobuf, unsetTestDomain);
		testMappingResult(result, Result.UNSET, null, unsetTestDomain);
	}

	private FieldResolver findDomainField(final String fieldName) {
		try {
			return fieldResolverFactory.createResolver(Proto2Domain.Test.class.getDeclaredField(fieldName));
		} catch (NoSuchFieldException e) {
			throw new IllegalArgumentException("No such field: " + fieldName, e);
		}
	}

	private void testMappingResult(final MappingResult result, final Result code, final Object value,
			final Object destination) {
		Assert.assertEquals(code, result.getCode());
		Assert.assertEquals(value, result.getValue());
		Assert.assertEquals(destination, result.getDestination());
	}

	@Test
	public void testMapCollectionToDomain() throws MappingException {
		exception = ExpectedException.none();
		MappingResult result = mapper.mapToDomainField(findDomainField("stringListValue"), testProtobuf, testDomain);
		testMappingResult(result, Result.COLLECTION_MAPPING, testProtobuf.getStringListValueList(), testDomain);

		result = mapper.mapToDomainField(findDomainField("nestedListValue"), testProtobuf, testDomain);
		testMappingResult(result, Result.COLLECTION_MAPPING, testProtobuf.getNestedListValueList(), testDomain);
	}

	@Test
	public void testUnsetMapCollectionToDomain() throws MappingException {
		exception = ExpectedException.none();
		MappingResult result = mapper.mapToDomainField(findDomainField("stringListValue"), unsetTestProtobuf, unsetTestDomain);
		testMappingResult(result, Result.COLLECTION_MAPPING, unsetTestProtobuf.getStringListValueList(), unsetTestDomain);

		result = mapper.mapToDomainField(findDomainField("nestedListValue"), unsetTestProtobuf, unsetTestDomain);
		testMappingResult(result, Result.COLLECTION_MAPPING, unsetTestProtobuf.getNestedListValueList(), unsetTestDomain);
	}

	@Test
	public void testMapNestedToDomain() throws MappingException {
		exception = ExpectedException.none();
		MappingResult result = mapper.mapToDomainField(findDomainField("nestedValue"), testProtobuf, testDomain);
		testMappingResult(result, Result.NESTED_MAPPING, testProtobuf.getNestedValue(), testDomain);
	}

	@Test
	public void testUnsetMapNestedToDomain() throws MappingException {
		exception = ExpectedException.none();
		MappingResult result = mapper.mapToDomainField(findDomainField("nestedValue"), unsetTestProtobuf, unsetTestDomain);
		testMappingResult(result, Result.UNSET, null, unsetTestDomain);
	}

	@Test
	public void testMapObjectToProtobuf() throws MappingException {
		exception = ExpectedException.none();
		MappingProto.MappingTest.Builder protobufBuilder = MappingProto.MappingTest.newBuilder();
		MappingResult result = mapper.mapToProtobufField(findDomainField("booleanValue"), testDomain, protobufBuilder);
		testMappingResult(result, Result.MAPPED, testDomain.getBooleanValue(), protobufBuilder);

		result = mapper.mapToProtobufField(findDomainField("floatValue"), testDomain, protobufBuilder);
		testMappingResult(result, Result.MAPPED, testDomain.getFloatValue(), protobufBuilder);

		result = mapper.mapToProtobufField(findDomainField("doubleValue"), testDomain, protobufBuilder);
		testMappingResult(result, Result.MAPPED, testDomain.getDoubleValue(), protobufBuilder);

		result = mapper.mapToProtobufField(findDomainField("intValue"), testDomain, protobufBuilder);
		testMappingResult(result, Result.MAPPED, testDomain.getIntValue(), protobufBuilder);

		result = mapper.mapToProtobufField(findDomainField("longValue"), testDomain, protobufBuilder);
		testMappingResult(result, Result.MAPPED, testDomain.getLongValue(), protobufBuilder);

		result = mapper.mapToProtobufField(findDomainField("stringValue"), testDomain, protobufBuilder);
		testMappingResult(result, Result.MAPPED, testDomain.getStringValue(), protobufBuilder);
	}

	@Test
	public void testUnsetMapObjectToProtobuf() throws MappingException {
		exception = ExpectedException.none();
		MappingProto.MappingTest.Builder protobufBuilder = MappingProto.MappingTest.newBuilder();
		MappingResult result = mapper.mapToProtobufField(findDomainField("booleanValue"), unsetTestDomain, protobufBuilder);
		testMappingResult(result, Result.UNSET, null, protobufBuilder);

		result = mapper.mapToProtobufField(findDomainField("floatValue"), unsetTestDomain, protobufBuilder);
		testMappingResult(result, Result.UNSET, null, protobufBuilder);

		result = mapper.mapToProtobufField(findDomainField("doubleValue"), unsetTestDomain, protobufBuilder);
		testMappingResult(result, Result.UNSET, null, protobufBuilder);

		result = mapper.mapToProtobufField(findDomainField("intValue"), unsetTestDomain, protobufBuilder);
		testMappingResult(result, Result.UNSET, null, protobufBuilder);

		result = mapper.mapToProtobufField(findDomainField("longValue"), unsetTestDomain, protobufBuilder);
		testMappingResult(result, Result.UNSET, null, protobufBuilder);

		result = mapper.mapToProtobufField(findDomainField("stringValue"), unsetTestDomain, protobufBuilder);
		testMappingResult(result, Result.UNSET, null, protobufBuilder);
	}

	@Test
	public void testMapCollectionToProtobuf() throws MappingException {
		exception = ExpectedException.none();
		MappingProto.MappingTest.Builder protobufBuilder = MappingProto.MappingTest.newBuilder();
		MappingResult result = mapper
				.mapToProtobufField(findDomainField("stringListValue"), testDomain, protobufBuilder);
		testMappingResult(result, Result.COLLECTION_MAPPING, testDomain.getStringListValue(), protobufBuilder);

		result = mapper.mapToProtobufField(findDomainField("nestedListValue"), testDomain, protobufBuilder);
		testMappingResult(result, Result.COLLECTION_MAPPING, testDomain.getNestedListValue(), protobufBuilder);
	}

	@Test
	public void testUnsetMapCollectionToProtobuf() throws MappingException {
		exception = ExpectedException.none();
		MappingProto.MappingTest.Builder protobufBuilder = MappingProto.MappingTest.newBuilder();
		MappingResult result = mapper
				.mapToProtobufField(findDomainField("stringListValue"), unsetTestDomain, protobufBuilder);
		testMappingResult(result, Result.UNSET, null, protobufBuilder);

		result = mapper.mapToProtobufField(findDomainField("nestedListValue"), unsetTestDomain, protobufBuilder);
		testMappingResult(result, Result.UNSET, null, protobufBuilder);
	}

	@Test
	public void testMapNestedToProtobuf() throws MappingException {
		exception = ExpectedException.none();
		MappingProto.MappingTest.Builder protobufBuilder = MappingProto.MappingTest.newBuilder();
		MappingResult result = mapper.mapToProtobufField(findDomainField("nestedValue"), testDomain, protobufBuilder);
		testMappingResult(result, Result.NESTED_MAPPING, testDomain.getNestedValue(), protobufBuilder);
	}

	@Test
	public void testUnsetMapNestedToProtobuf() throws MappingException {
		exception = ExpectedException.none();
		MappingProto.MappingTest.Builder protobufBuilder = MappingProto.MappingTest.newBuilder();
		MappingResult result = mapper.mapToProtobufField(findDomainField("nestedValue"), unsetTestDomain, protobufBuilder);
		testMappingResult(result, Result.UNSET, null, protobufBuilder);
	}

}
