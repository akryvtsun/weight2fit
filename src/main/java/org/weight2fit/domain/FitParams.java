package org.weight2fit.domain;

import java.util.EnumMap;
import java.util.Map;

/**
 * FIT parameters holder.
 *
 * @author Andriy Kryvtsun
 */
public final class FitParams {

    private final Map<FitFields, Object> holder = new EnumMap<FitFields, Object>(FitFields.class);

    @SuppressWarnings("unchecked")
    public <T> T getValue(FitFields field) {
        return (T) holder.get(field);
    }

    public void setValue(FitFields field, Object value) {
        holder.put(field, value);
    }

    public boolean hasValue(FitFields field) {
        return holder.containsKey(field);
    }
}
