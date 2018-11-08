package net.badata.protobuf.converter.inspection;

/**
 * Implementation of {@link net.badata.protobuf.converter.inspection.NullValueInspector NullValueInspector} that
 * does not regard the zero values as nulls.
 *
 * @author ppoffice
 */
public class Proto2NullValueInspectorImpl implements NullValueInspector {
    @Override
    public boolean isNull(Object value) {
        return value == null;
    }
}
