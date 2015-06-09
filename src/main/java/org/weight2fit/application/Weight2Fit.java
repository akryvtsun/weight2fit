package org.weight2fit.application;

import org.weight2fit.application.cli.CmdLineParamsSupplier;
import org.weight2fit.application.gui.GuiParamsSupplier;
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
            FitFileParamsSupplier supplier = args.length == 0
                ? new GuiParamsSupplier()
                : new CmdLineParamsSupplier(args);

            FitParams params = supplier.get();
            File outFile = supplier.getFile();

            FitParamsConsumer consumer = new FileParamsConsumer(outFile);
            consumer.accept(params);

            System.out.println("FIT outFile '" + outFile + "' was created");
        }
        catch (Exception e) {
            LOG.log(Level.SEVERE, "an exception was thrown", e);

            result = 1;
        }

        return result;
    }
}
