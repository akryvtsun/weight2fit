package org.weight2fit.infra;

import org.apache.commons.cli.*;
import org.weight2fit.domain.FitFields;
import org.weight2fit.domain.FitParams;
import org.weight2fit.domain.FitParamsSupplier;

import java.text.SimpleDateFormat;

/**
 * FIT parameters reader from command line.
 *
 * @author Andiry Kryvtsun
 */
// TODO use more useful CLI library
// TODO use short command line acronyms
public class CmdLineParamsSupplier implements FitParamsSupplier {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private CommandLine line;

    public CmdLineParamsSupplier(String... args) throws ParseException {
        CommandLineParser parser = new BasicParser();
        Options options = createOptions();

        try {
            line = parser.parse(options, args);
        } catch (ParseException e) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.setWidth(Integer.MAX_VALUE);
            formatter.printHelp("weight2fit", options);

            throw e;
        }
    }

    private Options createOptions() {
        Options options = new Options();
        options.addOption(createTimeStampOption());
        options.addOption(createValueOption("weight"));
        options.addOption(createPercentageOption("bodyFat"));
        options.addOption(createPercentageOption("bodyWater"));
        options.addOption(createValueOption("visceralFat"));
        options.addOption(createValueOption("muscleMass"));
        options.addOption(createValueOption("physiqueRating"));
        options.addOption(createValueOption("boneMass"));
        options.addOption(createValueOption("dailyCalorieIntake"));
        options.addOption(createValueOption("metabolicAge"));
        options.addOption(createOutOption());
        return options;
    }

    @Override
    public FitParams get() throws Exception {

        final FitParams params = new FitParams();

        if (line.hasOption("timestamp")) {
            String value = line.getOptionValue("timestamp");
            params.setValue(FitFields.TIMESTAMP, DATE_FORMAT.parse(value));
        }

        if (line.hasOption("weight")) {
            String value = line.getOptionValue("weight");
            params.setValue(FitFields.WEIGHT, Double.valueOf(value));
        }

        if (line.hasOption("bodyFat")) {
            String value = line.getOptionValue("bodyFat");
            params.setValue(FitFields.BODY_FAT, Double.valueOf(value));
        }

        if (line.hasOption("bodyWater")) {
            String value = line.getOptionValue("bodyWater");
            params.setValue(FitFields.BODY_WATER, Double.valueOf(value));
        }

        if (line.hasOption("visceralFat")) {
            String value = line.getOptionValue("visceralFat");
            params.setValue(FitFields.VISCERAL_FAT, Integer.valueOf(value));
        }

        if (line.hasOption("muscleMass")) {
            String value = line.getOptionValue("muscleMass");
            params.setValue(FitFields.MUSCLE_MASS, Double.valueOf(value));
        }

        if (line.hasOption("physiqueRating")) {
            String value = line.getOptionValue("physiqueRating");
            params.setValue(FitFields.PHYSIQUE_RATING, Integer.valueOf(value));
        }

        if (line.hasOption("boneMass")) {
            String value = line.getOptionValue("boneMass");
            params.setValue(FitFields.BONE_MASS, Double.valueOf(value));
        }

        if (line.hasOption("dailyCalorieIntake")) {
            String value = line.getOptionValue("dailyCalorieIntake");
            params.setValue(FitFields.DCI, Integer.valueOf(value));
        }

        if (line.hasOption("metabolicAge")) {
            String value = line.getOptionValue("metabolicAge");
            params.setValue(FitFields.METABOLIC_AGE, Integer.valueOf(value));
        }

        return params;
    }

    public String getFileName() {
        String fineName = null;

        if (line.hasOption("out")) {
            String value = line.getOptionValue("out");
            fineName = value;
        }

        return fineName;
    }

    private Option createTimeStampOption() {
        return OptionBuilder
                .isRequired()
                .hasArg()
                .withArgName(DATE_FORMAT.toPattern())
                .create("timestamp");
    }

    private Option createValueOption(String name) {
        return OptionBuilder
                .hasArg()
                .withArgName("value")
                .withDescription("long description on the value")
                .withLongOpt("thisISLongOPiton")
                .create(name);
    }

    private Option createPercentageOption(String name) {
        return OptionBuilder
                .hasArg()
                .withArgName("percentage")
                .create(name);
    }

    private Option createOutOption() {
        return OptionBuilder
                .isRequired()
                .hasArg()
                .withArgName("file")
                .create("out");
    }
}
