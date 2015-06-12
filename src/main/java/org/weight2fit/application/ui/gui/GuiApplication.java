package org.weight2fit.application.ui.gui;

import org.weight2fit.application.Weight2FitApplication;
import org.weight2fit.application.ui.UiFitParamsSupplier;
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

    public GuiApplication() {
    }

    @Override
    public int execute() {
        int result = 0;

        try {
            UiFitParamsSupplier supplier = new GuiParamsSupplier();
            FitParams params = supplier.get();

            if (params == null)
                return 1;

            File outFile = supplier.getFile();

            FitParamsConsumer consumer = new FileParamsConsumer(outFile);
            consumer.accept(params);

            System.out.println("FIT file '" + outFile + "' was created");
        }
        catch (Exception e) {
            LOG.log(Level.SEVERE, "an exception was thrown", e);

            result = 2;
        }

        return result;
    }
}
