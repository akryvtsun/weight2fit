package org.weight2fit.application;

import org.eclipse.swt.widgets.Display;
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
        if (args.length == 0) {
            Display display = Display.getDefault();

            MainWindow window = new MainWindow(display);

            while (!window.isDisposed()) {
                if (!display.readAndDispatch())
                    display.sleep();
            }
            display.dispose();

            return 0;
        }

        int result = 0;

        try {
            CmdLineParamsSupplier supplier = new CmdLineParamsSupplier(args);

            FitParams params = supplier.get();
            File file = supplier.getFile();

            FitParamsConsumer consumer = new FileParamsConsumer(file);
            consumer.accept(params);

            System.out.println("FIT file '" + file + "' was created");
        }
        catch(Exception e) {
            LOG.log(Level.SEVERE, "an exception was thrown", e);

            result = 1;
        }

        return result;
    }
}
