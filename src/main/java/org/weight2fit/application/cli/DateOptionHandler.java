package org.weight2fit.application.cli;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;
import org.kohsuke.args4j.spi.OneArgumentOptionHandler;
import org.kohsuke.args4j.spi.Setter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Andriy Kryvtsun
 */
public class DateOptionHandler extends OneArgumentOptionHandler<Date> {

    static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_PATTERN);

    public DateOptionHandler(CmdLineParser parser, OptionDef option, Setter<? super Date> setter) {
        super(parser, option, setter);
    }

    @Override
    protected Date parse(String argument) throws NumberFormatException, CmdLineException {

        try {
            return DATE_FORMAT.parse(argument);
        } catch (ParseException e) {
            throw new CmdLineException(owner, e);
        }
    }
}
