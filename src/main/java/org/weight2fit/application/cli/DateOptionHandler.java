package org.weight2fit.application.cli;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;
import org.kohsuke.args4j.spi.OneArgumentOptionHandler;
import org.kohsuke.args4j.spi.Setter;
import org.weight2fit.domain.shared.Constants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Andriy Kryvtsun
 */
public class DateOptionHandler extends OneArgumentOptionHandler<Date> {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat(Constants.DATE_PATTERN);

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
