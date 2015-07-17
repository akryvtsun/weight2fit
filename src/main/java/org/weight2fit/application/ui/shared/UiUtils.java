package org.weight2fit.application.ui.shared;

import java.io.File;
import java.security.CodeSource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Common UI functions.
 *
 * @author Andriy Kryvtsun
 */
public final class UiUtils {

    private static final Logger LOG = Logger.getLogger(UiUtils.class.getName());

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat(Constants.DATE_PATTERN);

    public static File createDefaultFile(Date timestamp) {
        String dateStr = toString(timestamp);
        String fileName = String.format(Constants.FILE_PATTERN, dateStr);
        return new File(fileName);
    }

    public static Date parse(String source) throws ParseException {
        return DATE_FORMAT.parse(source);
    }

    public static String toString(Date timestamp) {
        return DATE_FORMAT.format(timestamp);
    }

    public static String getVersion() {
        String version = "<last version>";

        try {
            CodeSource codeSource = UiUtils.class.getProtectionDomain().getCodeSource();
            String path = codeSource.getLocation().toURI().getPath();

            if (path.endsWith(".jar") || path.endsWith(".exe")) {
                JarFile jarFile = new JarFile(path);
                Attributes attrs = jarFile.getManifest().getMainAttributes();
                version = attrs.getValue("Version");
            }
        } catch (Exception e) {
            LOG.log(Level.FINE, "an exception was thrown", e);
        }

        return version;
    }

    private UiUtils() {}
}
