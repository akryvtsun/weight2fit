package org.weight2fit.application.ui.gui;

import org.weight2fit.application.Weight2FitApplication;
import org.weight2fit.application.ui.UiNotifier;
import org.weight2fit.domain.FitParams;
import org.weight2fit.domain.FitParamsConsumer;
import org.weight2fit.domain.FitParamsSupplier;
import org.weight2fit.infrastructure.EmptyFileSupplier;
import org.weight2fit.infrastructure.FileParamsConsumer;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * GUI application
 *
 * @author Andriy Kryvtsun
 */
public class GuiApplication implements Weight2FitApplication {
    static final Logger LOG = Logger.getLogger(GuiApplication.class.getName());

    private final FitParamsSupplier supplier;
    private final FitParamsConsumer consumer;
    private final UiNotifier notifier;

    public static Weight2FitApplication create() {
        UiNotifier notifier = new GuiNotifier();

        FitParamsSupplier supplier = new GuiParamsSupplier();
        FitParamsConsumer consumer = new FileParamsConsumer(new EmptyFileSupplier(), notifier);

        return new GuiApplication(supplier, consumer, notifier);
    }

    GuiApplication(FitParamsSupplier supplier, FitParamsConsumer consumer, UiNotifier notifier) {
        this.supplier = supplier;
        this.consumer = consumer;
        this.notifier = notifier;
    }

    @Override
    public int execute() {
        FitParams params = null;

        do {
            try {
                params = supplier.get();

                if (params != null)
                    consumer.accept(params);

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
