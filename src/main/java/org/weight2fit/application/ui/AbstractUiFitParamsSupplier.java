package org.weight2fit.application.ui;

import org.weight2fit.domain.FitFields;
import org.weight2fit.domain.FitParams;

import java.util.Date;

/**
 * Common logic for all <code>UiFitParamsSupplier</code> impls.
 *
 * @author Andriy Kryvtsun
 */
public abstract class AbstractUiFitParamsSupplier {

    protected FitParams params;

    // TODO move to domain validation
    protected void completeParams() {
        // if timestamp isn't set use current date
        if (!params.hasValue(FitFields.TIMESTAMP)) {
            params.setValue(FitFields.TIMESTAMP, new Date());
        }
    }
}
