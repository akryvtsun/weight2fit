package org.weight2fit.application.ui;

import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.FileOptionHandler;
import org.weight2fit.application.UiFitParamsSupplier;
import org.weight2fit.application.ui.shared.UiUtils;
import org.weight2fit.domain.FitFields;
import org.weight2fit.domain.FitParams;

import java.io.File;
import java.util.Date;

/**
 * @author Andriy Kryvtsun
 */
// TODO remove CLI arg4j @Option from this common class
public abstract class AbstractUiFitParamsSupplier implements UiFitParamsSupplier {

    protected FitParams params;

    @Option(name = "-o", aliases = { "--out" }, usage = "Output FIT file name", handler = FileOptionHandler.class)
    protected File out;

    @Override
    public File getFile() {
        return out;
    }

    protected void completeParams() {
        // if timestamp isn't set use current date
        if (!params.hasValue(FitFields.TIMESTAMP)) {
            params.setValue(FitFields.TIMESTAMP, new Date());
        }
        // if file name isn't set use timestamp for it
        if (out == null) {
            Date timestamp = params.getValue(FitFields.TIMESTAMP);
            out = UiUtils.createDefaultFile(timestamp);
        }
    }
}
