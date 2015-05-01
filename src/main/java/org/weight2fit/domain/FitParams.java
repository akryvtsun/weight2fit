package org.weight2fit.domain;

import java.util.Date;
import java.util.EnumMap;
import java.util.Map;

/**
 * FIT parameters holder.
 *
 * @author Andriy Kryvtsun
 */
public final class FitParams {

    private final Map<FitFields, Object> holder = new EnumMap<>(FitFields.class);

    public void setValue(FitFields field, Object value) {
        holder.put(field, value);
    }

    public Date getDateValue(FitFields field) {
        return (Date)getValue(field);
    }

    public int getIntValue(FitFields field) {
        return (Integer)getValue(field);
    }

    public double getDoubleValue(FitFields field) {
        return (Double)getValue(field);
    }

    public boolean hasValue(FitFields field) {
        return holder.containsKey(field);
    }

    private Object getValue(FitFields field) {
        return holder.get(field);
    }
}
