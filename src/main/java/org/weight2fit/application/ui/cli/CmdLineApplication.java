package org.weight2fit.application.ui.cli;

import org.weight2fit.application.Weight2FitApplication;
import org.weight2fit.application.ui.UiFitParamsSupplier;
import org.weight2fit.domain.FitParams;
import org.weight2fit.domain.FitParamsConsumer;
import org.weight2fit.infrastructure.FileParamsConsumer;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Command Line application
 *
 * @author Andriy Kryvtsun
 */
public class CmdLineApplication implements Weight2FitApplication {
    private static final Logger LOG = Logger.getLogger(CmdLineApplication.class.getName());

    private final String[] args;

    public CmdLineApplication(String... args) {
        this.args = args;
    }

    @Override
    public int execute() {
        int result = 0;

        try {
            UiFitParamsSupplier supplier = new CmdLineParamsSupplier(args);
            FitParams params = supplier.get();

            if (params != null) {
                File outFile = supplier.getFile();

                FitParamsConsumer consumer = new FileParamsConsumer(outFile);
                consumer.accept(params);

                System.out.println("FIT file '" + outFile + "' was created");
            }
            else
                result = 1;
        }
        catch (Exception e) {
            LOG.log(Level.SEVERE, "an exception was thrown", e);

            result = 2;
        }

        return result;
    }
}
