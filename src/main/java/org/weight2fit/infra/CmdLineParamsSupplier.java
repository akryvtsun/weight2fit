package org.weight2fit.infra;

import org.apache.commons.cli.*;
import org.weight2fit.domain.FitParams;
import org.weight2fit.domain.FitParamsSupplier;

import java.text.SimpleDateFormat;

/**
 * Created by englishman on 4/15/15.
 */
public class CmdLineParamsSupplier implements FitParamsSupplier {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private final String[] args;
    private final Options options;

    private CommandLine line;

    public CmdLineParamsSupplier(String... args) {
        this.args = args;

        Option timestamp = OptionBuilder.withArgName("date")
                .isRequired()
                .hasArg()
                .create("timestamp");

        Option weight = OptionBuilder.withArgName("weight")
                .hasArg()
                .create("weight");

        Option out = OptionBuilder.withArgName("file")
                .isRequired()
                .hasArg()
                .create("out");

        options = new Options();
        options.addOption(timestamp);
        options.addOption(weight);
        options.addOption(out);
    }

    @Override
    public FitParams get() throws Exception {
        checkCommandLine();

        FitParams.Builder builder = new FitParams.Builder();

        if (line.hasOption("timestamp")) {
            String value = line.getOptionValue("timestamp");
            builder.withTimestamp(DATE_FORMAT.parse(value));
        }

        if (line.hasOption("weight")) {
            String value = line.getOptionValue("weight");
            builder.withWeight(Double.valueOf(value));
        }

        return builder.build();
    }

    public String getFileName() throws Exception {
        checkCommandLine();

        String fineName = null;

        if (line.hasOption("out")) {
            String value = line.getOptionValue("out");
            fineName = value;
        }

        return fineName;
    }

    private void checkCommandLine() throws ParseException {
        if (line == null) {
            CommandLineParser parser = new BasicParser();
            line = parser.parse(options, args);
        }
    }
}
