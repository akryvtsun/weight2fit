package org.weight2fit.application;

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

        Weight2Fit application = new Weight2Fit(args);
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
}
