package org.weight2fit.application.ui.gui;

import org.weight2fit.application.Weight2FitApplication;
import org.weight2fit.application.ui.UiFitParamsSupplier;
import org.weight2fit.application.ui.UiNotifier;
import org.weight2fit.domain.FitParams;
import org.weight2fit.domain.FitParamsConsumer;
import org.weight2fit.infrastructure.FileParamsConsumer;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * GUI application
 *
 * @author Andriy Kryvtsun
 */
public class GuiApplication implements Weight2FitApplication {
    private static final Logger LOG = Logger.getLogger(GuiApplication.class.getName());

    private final UiFitParamsSupplier supplier;
    private final UiNotifier notifier;

    public static Weight2FitApplication create() {
        UiFitParamsSupplier supplier = new GuiParamsSupplier();
        UiNotifier notifier = new GuiNotifier();

        return new GuiApplication(supplier, notifier);
    }

    GuiApplication(UiFitParamsSupplier supplier, UiNotifier notifier) {
        this.supplier = supplier;
        this.notifier = notifier;
    }

    @Override
    public int execute() {
        FitParams params = null;

        do {
            try {
                params = supplier.get();

                if (params != null) {
                    File outFile = supplier.getFile();

                    FitParamsConsumer consumer = new FileParamsConsumer(outFile);
                    consumer.accept(params);

                    notifier.showInfoMessage("FIT file '" + outFile + "' was created");
                }
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "an exception was thrown", e);

                String errorMessage = e.getCause() != null
                        ? e.getCause().getLocalizedMessage()
                        : e.getLocalizedMessage();

                notifier.showErrorMessage(errorMessage);
            }
        }
        while (params != null);

        return 1;
    }
}
