package org.weight2fit.application.ui.cli;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;
import org.kohsuke.args4j.spi.OneArgumentOptionHandler;
import org.kohsuke.args4j.spi.Setter;
import org.weight2fit.application.ui.shared.UiUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * <code>OptionHandler</code> implementation for parsing <code>java.util.Date</code> arguments.
 * Must have <b>public</b> access level cause of reflection introspection in <b>args4j</b>.
 *
 * @author Andriy Kryvtsun
 */
public class DateOptionHandler extends OneArgumentOptionHandler<Date> {

    public DateOptionHandler(CmdLineParser parser, OptionDef option, Setter<? super Date> setter) {
        super(parser, option, setter);
    }

    @Override
    protected Date parse(String argument) throws NumberFormatException, CmdLineException {

        try {
            return UiUtils.parse(argument);
        } catch (ParseException e) {
            throw new CmdLineException(owner, e);
        }
    }
}
