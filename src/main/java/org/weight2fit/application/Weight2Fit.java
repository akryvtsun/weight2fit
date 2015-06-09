package org.weight2fit.application;

import org.weight2fit.application.cli.CmdLineParamsSupplier;
import org.weight2fit.application.gui.MainWindow;
import org.weight2fit.domain.FitParams;
import org.weight2fit.domain.FitParamsConsumer;
import org.weight2fit.infrastructure.FileParamsConsumer;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Weight2Fit application.
 * Orchestrates all internal components.
 *
 * @author Andriy Kryvtsun
 */
// TODO add informative logging everywhere
// TODO add internalization (in GUI)
public class Weight2Fit {

    private static final Logger LOG = Logger.getLogger(Weight2Fit.class.getName());

    private final String[] args;

    public Weight2Fit(String... args) {
        this.args = args;
    }

    public int execute() {
        int result = 0;

        try {
            FitParams params;
            File file;

            if (args.length == 0) {
                MainWindow window = new MainWindow();

                params = window.get();
                file = window.getFile();
            }
            else {
                CmdLineParamsSupplier supplier = new CmdLineParamsSupplier(args);

                params = supplier.get();
                file = supplier.getFile();
            }

            FitParamsConsumer consumer = new FileParamsConsumer(file);
            consumer.accept(params);

            System.out.println("FIT file '" + file + "' was created");
        }
        catch (Exception e) {
            LOG.log(Level.SEVERE, "an exception was thrown", e);

            result = 1;
        }

        return result;
    }
}
