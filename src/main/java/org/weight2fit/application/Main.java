package org.weight2fit.application;

import org.weight2fit.application.cli.CmdLineParamsSupplier;
import org.weight2fit.domain.FitParams;
import org.weight2fit.domain.FitParamsConsumer;
import org.weight2fit.infrastructure.FileParamsConsumer;

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

    private static final Logger LOG = Logger.getLogger(Main.class.getName());

    public static void main(String... args) {

        try {
            CmdLineParamsSupplier supplier = new CmdLineParamsSupplier(args);

            FitParams params = supplier.get();
            String fileName = supplier.getFileName();

            FitParamsConsumer consumer = new FileParamsConsumer(fileName);
            consumer.accept(params);

            LOG.info("FIT file " + fileName + " was created");
        }
        catch(Exception e) {
            LOG.severe(e.getMessage());
        }
    }
}
