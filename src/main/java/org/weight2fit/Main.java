package org.weight2fit;

import com.garmin.fit.Manufacturer;
import org.apache.commons.cli.*;
import org.weight2fit.infra.WeightScaleBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

/**
 * https://forums.garmin.com/showthread.php?24518-Import-of-Withings-wifi-scale-data-now-possible
 * http://jmfloreszazo.com/scale2fit
 * https://github.com/marchibbins/fit-weight-scale
 * http://connect.garmin.com/health
 * http://www.thisisant.com/resources/fit
 */
public class Main {
    private static final Logger LOG = Logger.getLogger(Main.class.getName());

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String... args) throws Exception {
        LOG.info("Starting coding...");

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

        Options options = new Options();
        options.addOption(timestamp);
        options.addOption(weight);
        options.addOption(out);

        CommandLineParser parser = new BasicParser();
        try {
            // parse the command line arguments
            CommandLine line = parser.parse(options, args);

            WeightScaleBuilder builder = new WeightScaleBuilder()
                    .manufacturer(Manufacturer.TANITA);

            if (line.hasOption("timestamp")) {
                String value = line.getOptionValue("timestamp");
                builder.timestamp(DATE_FORMAT.parse(value));
            }

            if (line.hasOption("weight")) {
                String value = line.getOptionValue("weight");
                builder.weight(Double.valueOf(value));
            }

            byte[] buffer = builder.build();

            String outFile = line.getOptionValue("out");
            File file = new File(outFile);
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(buffer);
            }

            LOG.info("FIT file " + outFile + " was written");
        }
        catch(ParseException exp) {
            // oops, something went wrong
            System.err.println( "Parsing failed.  Reason: " + exp.getMessage() );
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp( "weight2fit", options);
        }
    }
}
