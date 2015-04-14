package org.weight2fit.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by englishman on 4/13/15.
 */
public class ParametersHolder {
    private final Map<Parameters, Object> map = new HashMap();

    public void put(Parameters param, Object value) {
        map.put(param, value);
    }

    public Object get(Parameters param) {
        return map.get(param);
    }
}
