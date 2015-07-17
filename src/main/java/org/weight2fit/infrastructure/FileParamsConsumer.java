package org.weight2fit.infrastructure;

import org.weight2fit.application.ui.FileSupplier;
import org.weight2fit.application.ui.UiNotifier;
import org.weight2fit.application.ui.shared.UiUtils;
import org.weight2fit.domain.FitException;
import org.weight2fit.domain.FitFields;
import org.weight2fit.domain.FitParams;
import org.weight2fit.domain.FitParamsConsumer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;

/**
 * FIT parameters writer to a file.
 *
 * @author Andiry Kryvtsun
 */
public class FileParamsConsumer implements FitParamsConsumer {

    private final FileSupplier supplier;
    private final UiNotifier notifier;

    public FileParamsConsumer(FileSupplier supplier, UiNotifier notifier) {
        this.supplier = supplier;
        this.notifier = notifier;
    }

    @Override
    public void accept(FitParams params) throws FitException {

        File outFile = supplier.getFile();
        // if file name isn't set use timestamp for it
        if (outFile == null) {
            Date timestamp = params.getValue(FitFields.TIMESTAMP);
            outFile = UiUtils.createDefaultFile(timestamp);
        }

        try {
            OutputStream os = new FileOutputStream(outFile);
            FitParamsConsumer consumer = new OutputStreamParamsConsumer(os);

            consumer.accept(params);

            notifier.showInfoMessage("FIT file '" + outFile + "' was created");

        } catch (FileNotFoundException e) {

            throw new FitException("Can not create output file", e);
        }
    }
}
