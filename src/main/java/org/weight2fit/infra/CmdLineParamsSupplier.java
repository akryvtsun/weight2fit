package org.weight2fit.infra;

import org.apache.commons.cli.*;
import org.weight2fit.domain.FitParams;
import org.weight2fit.domain.FitParamsSupplier;

import java.text.SimpleDateFormat;
import java.util.logging.Logger;

/**
 * FIT parameters reader from command line.
 *
 * @author Andiry Kryvtsun
 */
// TODO use more useful CLI library
public class CmdLineParamsSupplier implements FitParamsSupplier {

    private static final Logger LOG = Logger.getLogger(CmdLineParamsSupplier.class.getName());

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private final String[] args;
    private final Options options;

    private CommandLine line;

    public CmdLineParamsSupplier(String... args) {
        this.args = args;

        options = new Options();
        options.addOption(createTimeStampOption());
        options.addOption(createWeightOption());
        options.addOption(createBodyFatOption());
        options.addOption(createBodyWaterOption());
        options.addOption(createVisceralFatOption());
        options.addOption(createMuscleMassOption());
        options.addOption(createPhysiqueRatingOption());
        options.addOption(createBoneMassOption());
        options.addOption(createDailyCalorieIntakeOption());
        options.addOption(createMetabolicAgeOption());
        options.addOption(createOutOption());
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

        if (line.hasOption("bodyFat")) {
            String value = line.getOptionValue("bodyFat");
            builder.withBodyFat(Double.valueOf(value));
        }

        if (line.hasOption("bodyWater")) {
            String value = line.getOptionValue("bodyWater");
            builder.withBodyWater(Double.valueOf(value));
        }

        if (line.hasOption("visceralFat")) {
            String value = line.getOptionValue("visceralFat");
            builder.withVisceralFat(Integer.valueOf(value));
        }

        if (line.hasOption("muscleMass")) {
            String value = line.getOptionValue("muscleMass");
            builder.withMuscleMass(Double.valueOf(value));
        }

        if (line.hasOption("physiqueRating")) {
            String value = line.getOptionValue("physiqueRating");
            builder.withPhysiqueRating(Integer.valueOf(value));
        }

        if (line.hasOption("boneMass")) {
            String value = line.getOptionValue("boneMass");
            builder.withBoneMass(Double.valueOf(value));
        }

        if (line.hasOption("dailyCalorieIntake")) {
            String value = line.getOptionValue("dailyCalorieIntake");
            builder.withDCI(Integer.valueOf(value));
        }

        if (line.hasOption("metabolicAge")) {
            String value = line.getOptionValue("metabolicAge");
            builder.withMetabolicAge(Integer.valueOf(value));
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

    private Option createOutOption() {
        return OptionBuilder
                .isRequired()
                .hasArg()
                .withArgName("file")
                .create("out");
    }

    private Option createMetabolicAgeOption() {
        return OptionBuilder
                .hasArg()
                .withArgName("value")
                .create("metabolicAge");
    }

    private Option createDailyCalorieIntakeOption() {
        return OptionBuilder
                .hasArg()
                .withArgName("value")
                .create("dailyCalorieIntake");
    }

    private Option createBoneMassOption() {
        return OptionBuilder
                .hasArg()
                .withArgName("value")
                .create("boneMass");
    }

    private Option createPhysiqueRatingOption() {
        return OptionBuilder
                .hasArg()
                .withArgName("value")
                .create("physiqueRating");
    }

    private Option createMuscleMassOption() {
        return OptionBuilder
                .hasArg()
                .withArgName("value")
                .create("muscleMass");
    }

    private Option createVisceralFatOption() {
        return OptionBuilder
                .hasArg()
                .withArgName("value")
                .create("visceralFat");
    }

    private Option createBodyWaterOption() {
        return OptionBuilder
                .hasArg()
                .withArgName("percentage")
                .create("bodyWater");
    }

    private Option createBodyFatOption() {
        return OptionBuilder
                .hasArg()
                .withArgName("percentage")
                .create("bodyFat");
    }

    private Option createWeightOption() {
        return OptionBuilder
                .hasArg()
                .withArgName("value")
                .create("weight");
    }

    private Option createTimeStampOption() {
        return OptionBuilder
                .isRequired()
                .hasArg()
                .withArgName("date")
                .create("timestamp");
    }

    private void checkCommandLine() {
        if (line == null) {
            CommandLineParser parser = new BasicParser();
            try {
                line = parser.parse(options, args);
            } catch (ParseException e) {
                LOG.warning(e.getLocalizedMessage());

                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("weight2fit", options);
            }
        }
    }
}
