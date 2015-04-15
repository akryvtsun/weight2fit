package org.weight2fit.infra;

import com.garmin.fit.Manufacturer;
import org.apache.commons.cli.*;
import org.weight2fit.domain.Parameters;
import org.weight2fit.domain.ParametersHolder;
import org.weight2fit.domain.ParametersService;

import java.text.SimpleDateFormat;

/**
 * @author Andriy Kryvtsun
 */
public class CmdLineParametersService implements ParametersService {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private final String[] args;
    private final Options options;

    public CmdLineParametersService(String... args) {
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
    public ParametersHolder getParameters() throws Exception {
        CommandLineParser parser = new BasicParser();
        CommandLine line = parser.parse(options, args);

        ParametersHolder holder = new ParametersHolder();

        if (line.hasOption("timestamp")) {
            String value = line.getOptionValue("timestamp");
            holder.put(Parameters.TIMESTEMP, DATE_FORMAT.parse(value));
        }

        if (line.hasOption("weight")) {
            String value = line.getOptionValue("weight");
            holder.put(Parameters.WEIGHT, Double.valueOf(value));
        }

        if (line.hasOption("out")) {
            String value = line.getOptionValue("out");
            holder.put(Parameters.OUT, value);
        }

        return holder;
    }
}
