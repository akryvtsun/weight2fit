package org.weight2fit.application.cli;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.OptionHandlerFilter;
import org.kohsuke.args4j.spi.FileOptionHandler;
import org.kohsuke.args4j.spi.IntOptionHandler;
import org.kohsuke.args4j.spi.OptionHandler;
import org.weight2fit.application.UiFitParamsSupplier;
import org.weight2fit.application.shared.Constants;
import org.weight2fit.application.shared.UiUtils;
import org.weight2fit.domain.FitException;
import org.weight2fit.domain.FitFields;
import org.weight2fit.domain.FitParams;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static org.weight2fit.domain.shared.Utils.checkNotNull;

/**
 * FIT parameters reader from command line.
 *
 * @author Andiry Kryvtsun
 */
public class CmdLineParamsSupplier implements UiFitParamsSupplier {

    private final String[] args;
    private final CmdLineParser parser;

    @Option(name = "-o", aliases = { "--out" }, usage = "Output FIT file name", handler = FileOptionHandler.class)
    private File out;

    @Option(name = "-h", aliases = { "--help" }, help = true, usage = "Shows help info")
    private boolean help;

    private final FitParams params = new FitParams();

    public CmdLineParamsSupplier(String... args) {
        this.args = checkNotNull(args);

        parser = new CmdLineParser(this);
        addOptions();
        sortOptions();
    }

    private void addOptions() {
        addOption(FitFields.TIMESTAMP, createTimestampOption());
        addOption(FitFields.WEIGHT, createWeightOption());
        addOption(FitFields.BODY_FAT, createBodyFatOption());
        addOption(FitFields.BODY_WATER, createBodyWaterOption());
        addOption(FitFields.VISCERAL_FAT, createVisceralFatOption());
        addOption(FitFields.MUSCLE_MASS, createMuscleMassOption());
        addOption(FitFields.PHYSIQUE_RATING, createPhysiqueRatingOption());
        addOption(FitFields.BONE_MASS, createBoneMassOption());
        addOption(FitFields.DCI, createDailyCalorieIntakeOption());
        addOption(FitFields.METABOLIC_AGE, createMetabolicAgeOption());
    }

    // beautify usage example view
    private void sortOptions() {
        List<OptionHandler> options = parser.getOptions();
        // put "help" at the end of options list
        Collections.sort(options, new Comparator<OptionHandler>() {
            @Override
            public int compare(OptionHandler o1, OptionHandler o2) {
                return o1.option.help()
                        ? 1
                        : o2.option.help() ? -1 : 0;
            }
        });
        // put "out" just before the last "help" option
        List<OptionHandler> subList = options.subList(0, options.size() - 1);
        Collections.sort(subList, new Comparator<OptionHandler>() {
            @Override
            public int compare(OptionHandler o1, OptionHandler o2) {
                return o1 instanceof FileOptionHandler
                        ? 1
                        : o2 instanceof FileOptionHandler ? -1 : 0;
            }
        });
    }

    private Option createTimestampOption() {
        return CmdLineOption.Builder.create()
                .name("t").longName("timestamp")
                .description("Timestamp of measurement")
                .metaVar(Constants.DATE_PATTERN)
                .handler(DateOptionHandler.class)
                .build();
    }

    private Option createWeightOption() {
        return CmdLineOption.Builder.create()
                .name("w").longName("weight")
                .required()
                .description("Weight of the body in kg")
                .build();
    }

    private Option createBodyFatOption() {
        return CmdLineOption.Builder.create()
                .name("bf").longName("bodyFat")
                .description("Fat of the body in %")
                .build();
    }

    private Option createBodyWaterOption() {
        return CmdLineOption.Builder.create()
                .name("bw").longName("bodyWater")
                .description("Water of the body in %")
                .build();
    }

    private Option createVisceralFatOption() {
        return CmdLineOption.Builder.create()
                .name("vf").longName("visceralFat")
                .description("Visceral fat")
                .metaVar("index")
                .handler(IntOptionHandler.class)
                .build();
    }

    private Option createMuscleMassOption() {
        return CmdLineOption.Builder.create()
                .name("mm").longName("muscleMass")
                .description("Muscle mass of the body in kg")
                .build();
    }

    private Option createPhysiqueRatingOption() {
        return CmdLineOption.Builder.create()
                .name("pr").longName("physiqueRating")
                .description("Physique rating")
                .metaVar("index")
                .handler(IntOptionHandler.class)
                .build();
    }

    private Option createBoneMassOption() {
        return CmdLineOption.Builder.create()
                .name("bm").longName("boneMass")
                .description("Bone mass of the body in kg")
                .build();
    }

    private Option createDailyCalorieIntakeOption() {
        return CmdLineOption.Builder.create()
                .name("dci").longName("dailyCalorieIntake")
                .description("Daily calorie intake in kcal/day")
                .build();
    }

    private Option createMetabolicAgeOption() {
        return CmdLineOption.Builder.create()
                .name("ma").longName("metabolicAge")
                .description("Metabolic age in years")
                .handler(IntOptionHandler.class)
                .build();
    }

    private void addOption(FitFields field, Option option) {
        FitFieldSetter setter = new FitFieldSetter(params, field);
        parser.addOption(setter, option);
    }

    @Override
    public FitParams get() throws FitException {
        try {
            parser.parseArgument(args);

            if (help) {
                showHelpInfo();
                return null;
            }
            else {
                completeArguments();
                return params;
            }
        }
        catch (CmdLineException e) {
            System.out.println("Error: " + e.getLocalizedMessage());
            showHelpInfo();

            throw new FitException("Error during FitParams creation", e);
        }
    }

    private void showHelpInfo() {
        System.out.println("Usage: " + Constants.APP_NAME + parser.printExample(OptionHandlerFilter.REQUIRED));
        parser.printUsage(System.out);
    }

    private void completeArguments() {
        // if timestamp isn't set use current date
        if (!params.hasValue(FitFields.TIMESTAMP)) {
            params.setValue(FitFields.TIMESTAMP, new Date());
        }
        // if file name isn't set use timestamp for it
        if (out == null) {
            Date timestamp = params.getValue(FitFields.TIMESTAMP);
            out = UiUtils.createDefaultFile(timestamp);
        }
    }

    @Override
    public File getFile() {
        return out;
    }
}
