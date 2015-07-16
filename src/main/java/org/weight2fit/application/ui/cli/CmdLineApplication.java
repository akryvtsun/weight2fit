package org.weight2fit.application.ui.cli;

import org.weight2fit.application.Weight2FitApplication;
import org.weight2fit.application.ui.FileParamsConsumerCreator;
import org.weight2fit.application.ui.UiFitParamsSupplier;
import org.weight2fit.application.ui.UiNotifier;
import org.weight2fit.domain.FitParams;
import org.weight2fit.domain.FitParamsConsumer;
import org.weight2fit.infrastructure.FileParamsConsumer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Command Line application
 *
 * @author Andriy Kryvtsun
 */
public class CmdLineApplication implements Weight2FitApplication {
    private static final Logger LOG = Logger.getLogger(CmdLineApplication.class.getName());

    private final UiFitParamsSupplier supplier;
    private final FileParamsConsumerCreator consumeCreator;
    private final UiNotifier notifier;

    public static Weight2FitApplication create(String... args) {
        UiFitParamsSupplier supplier = new CmdLineParamsSupplier(args);
        FileParamsConsumerCreator creator = new FileParamsConsumerCreator() {
            @Override
            public FitParamsConsumer create(File file) throws FileNotFoundException {
                return new FileParamsConsumer(file);
            }
        };
        UiNotifier notifier = new CmdLineNotifier();

        return new CmdLineApplication(supplier, creator, notifier);
    }

    CmdLineApplication(UiFitParamsSupplier supplier, FileParamsConsumerCreator consumeCreator, UiNotifier notifier) {
        this.supplier = supplier;
        this.consumeCreator = consumeCreator;
        this.notifier = notifier;
    }

    @Override
    public int execute() {
        int result = 0;

        try {
            FitParams params = supplier.get();

            if (params != null) {
                File outFile = supplier.getFile();

                FitParamsConsumer consumer = consumeCreator.create(outFile);
                consumer.accept(params);

                notifier.showInfoMessage("FIT file '" + outFile + "' was created");
            }
            else
                result = 1;
        }
        catch (Exception e) {
            LOG.log(Level.SEVERE, "an exception was thrown", e);

            String errorMessage = e.getCause() != null
                    ? e.getCause().getLocalizedMessage()
                    : e.getLocalizedMessage();

            notifier.showErrorMessage(errorMessage);

            result = 2;
        }

        return result;
    }
}
