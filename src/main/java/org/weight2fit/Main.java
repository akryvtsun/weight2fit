package org.weight2fit;

import org.weight2fit.domain.FitParams;
import org.weight2fit.domain.FitParamsConsumer;
import org.weight2fit.infra.CmdLineParamsSupplier;
import org.weight2fit.infra.FileParamsConsumer;

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

    public static void main(String... args) throws Exception {

        CmdLineParamsSupplier supplier = new CmdLineParamsSupplier(args);

        FitParams params = supplier.get();
        if (params != null) {
            String fileName = supplier.getFileName();

            FitParamsConsumer consumer = new FileParamsConsumer(fileName);
            consumer.accept(params);

            LOG.info("FIT file " + fileName + " was created");
        }
        else {
            LOG.info("FIT file wasn't created");
        }
    }
}
