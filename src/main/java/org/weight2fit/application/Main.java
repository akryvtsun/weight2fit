package org.weight2fit.application;

import org.weight2fit.application.cli.CmdLineParamsSupplier;
import org.weight2fit.domain.FitParams;
import org.weight2fit.domain.FitParamsConsumer;
import org.weight2fit.infrastructure.FileParamsConsumer;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The main executable class.
 * Orchestrate all internal components.
 *
 * @author Andriy Kryvtsun
 */
// TODO add informative logging everywhere
// TODO add internalization (in GUI)
public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String... args) {

        try {
            CmdLineParamsSupplier supplier = new CmdLineParamsSupplier(args);

            FitParams params = supplier.get();
            File file = supplier.getFile();

            FitParamsConsumer consumer = new FileParamsConsumer(file);
            consumer.accept(params);

            LOGGER.info("FIT file " + file + " was created");
        }
        catch(Exception e) {
            LOGGER.log(Level.SEVERE, "an exception was thrown", e);
        }
    }
}
