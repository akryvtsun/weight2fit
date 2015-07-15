package org.weight2fit.application;

import org.weight2fit.application.ui.UiFitParamsSupplier;
import org.weight2fit.application.ui.UiNotifier;
import org.weight2fit.application.ui.cli.CmdLineApplication;
import org.weight2fit.application.ui.cli.CmdLineNotifier;
import org.weight2fit.application.ui.cli.CmdLineParamsSupplier;
import org.weight2fit.application.ui.gui.GuiApplication;
import org.weight2fit.application.ui.gui.GuiNotifier;
import org.weight2fit.application.ui.gui.GuiParamsSupplier;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;

/**
 * The main executable class.
 * Initializes logging and starts application.
 *
 * @author Andriy Kryvtsun
 */
public class Launcher {

    public static void main(String... args) {
        try {
            initializeLogging();
        } catch (IOException e) {
            // avoid any error messages during init logging
        }

        Weight2FitApplication application = args.length != 0
                ? createCmdLineApplication(args)
                : createGuiApplication();
        int result = application.execute();

        System.exit(result);
    }

    // avoids logging output by default
    private static void initializeLogging() throws IOException {
        String logFile = System.getProperty("java.util.logging.config.file");

        if(logFile == null) {
            InputStream propertiesStream = Launcher.class.getClassLoader().getResourceAsStream("logging.properties");
            LogManager.getLogManager().readConfiguration(propertiesStream);
        }
    }

    private static Weight2FitApplication createCmdLineApplication(String[] args) {
        UiFitParamsSupplier supplier = new CmdLineParamsSupplier(args);
        CmdLineNotifier notifier = new CmdLineNotifier();

        return new CmdLineApplication(supplier, notifier);
    }

    private static Weight2FitApplication createGuiApplication() {
        UiFitParamsSupplier supplier = new GuiParamsSupplier();
        UiNotifier notifier = new GuiNotifier();

        return new GuiApplication(supplier, notifier);
    }
}
