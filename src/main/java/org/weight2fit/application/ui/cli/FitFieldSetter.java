package org.weight2fit.application.ui.cli;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.spi.FieldSetter;
import org.kohsuke.args4j.spi.Setter;
import org.weight2fit.domain.FitFields;
import org.weight2fit.domain.FitParams;

import java.lang.reflect.AnnotatedElement;

/**
 * <code>Setter</code> implementation for <code>FitParams</code> instance.
 *
 * @author Andriy Kryvtsun
 */
public class FitFieldSetter implements Setter {

    private final FitParams params;
    private final FitFields field;

    FitFieldSetter(FitParams params, FitFields field) {
        this.params = params;
        this.field = field;
    }

    @Override
    public void addValue(Object value) throws CmdLineException {
        params.setValue(field, value);
    }

    @Override
    public Class getType() {
        return null;
    }

    @Override
    public boolean isMultiValued() {
        return false;
    }

    @Override
    public FieldSetter asFieldSetter() {
        return null;
    }

    @Override
    public AnnotatedElement asAnnotatedElement() {
        return null;
    }
}
