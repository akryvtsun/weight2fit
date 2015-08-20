package org.weight2fit.application.ui.cli;

import org.weight2fit.application.Weight2FitApplication;
import org.weight2fit.application.ui.UiNotifier;
import org.weight2fit.domain.FitParams;
import org.weight2fit.domain.FitParamsConsumer;
import org.weight2fit.domain.FitParamsSupplier;
import org.weight2fit.infrastructure.FileParamsConsumer;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Command Line application
 *
 * @author Andriy Kryvtsun
 */
public class CmdLineApplication implements Weight2FitApplication {
    static final Logger LOG = Logger.getLogger(CmdLineApplication.class.getName());

    private final FitParamsSupplier supplier;
    private final FitParamsConsumer consumer;
    private final UiNotifier notifier;

    public static Weight2FitApplication create(String... args) {
        UiNotifier notifier = new CmdLineNotifier();

        CmdLineParamsSupplier supplier = new CmdLineParamsSupplier(notifier, args);
        FitParamsConsumer consumer = new FileParamsConsumer(supplier, notifier);

        return new CmdLineApplication(supplier, consumer, notifier);
    }

    CmdLineApplication(FitParamsSupplier supplier, FitParamsConsumer consumer, UiNotifier notifier) {
        this.supplier = supplier;
        this.consumer = consumer;
        this.notifier = notifier;
    }

    @Override
    public int execute() {
        int result = 0;

        try {
            FitParams params = supplier.get();

            if (params != null)
                consumer.accept(params);
            else
                result = 1;
        }
        catch (Exception e) {
            LOG.log(Level.SEVERE, "an exception was thrown", e);

            String errorMessage = e.getCause() != null
                    ? e.getCause().getLocalizedMessage()
                    : e.getLocalizedMessage();

            notifier.showErrorMessage("Error: " + errorMessage);

            result = 2;
        }

        return result;
    }
}
