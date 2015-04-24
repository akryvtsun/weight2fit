package org.weight2fit.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * FIT parameters holder.
 *
 * @author Andriy Kryvtsun
 */
public class FitParams {

    private Map<FitFields, Object> holder = new HashMap();

    public void setValue(FitFields field, Object value) {
        holder.put(field, value);
    }

    public Object getValue(FitFields field) {
        return holder.get(field);
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
}
