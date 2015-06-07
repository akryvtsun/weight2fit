package org.weight2fit.application.shared;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Common UI functions.
 *
 * @author Andriy Kryvtsun
 */
public final class UiUtils {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat(Constants.DATE_PATTERN);

    public static File createDefaultFile(Date timestamp) {
        String dateStr = DATE_FORMAT.format(timestamp);
        String fileName = String.format(Constants.FILE_PATTERN, dateStr);
        return new File(fileName);
    }

    public static Date parse(String source) throws ParseException {
        return DATE_FORMAT.parse(source);
    }

    private UiUtils() {}
}
