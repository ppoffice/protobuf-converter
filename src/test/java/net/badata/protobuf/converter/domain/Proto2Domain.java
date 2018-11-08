package net.badata.protobuf.converter.domain;

import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;
import net.badata.protobuf.converter.mapping.Proto2MapperImpl;
import net.badata.protobuf.converter.proto.Proto2Proto;

import java.util.List;

/**
 * @author ppoffice
 */
public class Proto2Domain {

    @ProtoClass(value = Proto2Proto.Proto2MappingTest.class, mapper = Proto2MapperImpl.class)
    public static class Test {

        @ProtoField
        private Long longValue;
        @ProtoField
        private Integer intValue;
        @ProtoField
        private Float floatValue;
        @ProtoField
        private Double doubleValue;
        @ProtoField
        private Boolean booleanValue;
        @ProtoField
        private String stringValue;
        @ProtoField
        private Proto2Domain.NestedTest nestedValue;
        @ProtoField
        private List<String> stringListValue;
        @ProtoField
        private List<Proto2Domain.NestedTest> nestedListValue;

        public Long getLongValue() {
            return longValue;
        }

        public void setLongValue(final Long longValue) {
            this.longValue = longValue;
        }

        public Integer getIntValue() {
            return intValue;
        }

        public void setIntValue(final Integer intValue) {
            this.intValue = intValue;
        }

        public Float getFloatValue() {
            return floatValue;
        }

        public void setFloatValue(final Float floatValue) {
            this.floatValue = floatValue;
        }

        public Double getDoubleValue() {
            return doubleValue;
        }

        public void setDoubleValue(final Double doubleValue) {
            this.doubleValue = doubleValue;
        }

        public Boolean getBooleanValue() {
            return booleanValue;
        }

        public void setBooleanValue(final Boolean booleanValue) {
            this.booleanValue = booleanValue;
        }

        public String getStringValue() {
            return stringValue;
        }

        public void setStringValue(final String stringValue) {
            this.stringValue = stringValue;
        }

        public Proto2Domain.NestedTest getNestedValue() {
            return nestedValue;
        }

        public void setNestedValue(final Proto2Domain.NestedTest nestedValue) {
            this.nestedValue = nestedValue;
        }

        public List<String> getStringListValue() {
            return stringListValue;
        }

        public void setStringListValue(final List<String> stringListValue) {
            this.stringListValue = stringListValue;
        }

        public List<Proto2Domain.NestedTest> getNestedListValue() {
            return nestedListValue;
        }

        public void setNestedListValue(final List<Proto2Domain.NestedTest> nestedListValue) {
            this.nestedListValue = nestedListValue;
        }
    }

    @ProtoClass(value = Proto2Proto.Proto2NestedTest.class)
    public static class NestedTest {

        @ProtoField
        private String stringValue;

        public String getStringValue() {
            return stringValue;
        }

        public void setStringValue(final String stringValue) {
            this.stringValue = stringValue;
        }
    }
}
