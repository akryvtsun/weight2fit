package org.weight2fit.application.ci;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.FileOptionHandler;
import org.kohsuke.args4j.spi.IntOptionHandler;
import org.weight2fit.domain.FitFields;
import org.weight2fit.domain.FitParams;
import org.weight2fit.domain.FitParamsSupplier;

import java.io.File;

/**
 * FIT parameters reader from command line.
 *
 * @author Andiry Kryvtsun
 */
public class CmdLineParamsSupplier implements FitParamsSupplier {

    private final String[] args;
    private final CmdLineParser parser;

    private final FitParams params = new FitParams();
    @Option(name = "-o", aliases = { "--out" }, usage = "Output file", required = true, handler = FileOptionHandler.class)
    private File out;

    public CmdLineParamsSupplier(String... args) {
        this.args = args;
        parser = new CmdLineParser(this);
        addOptions();
    }

    private void addOptions() {
        addOption(FitFields.TIMESTAMP, CmdLineOption.Builder.create()
                .required()
                .name("t").longName("timestamp")
                .description("Time Stamp").handler(DateOptionHandler.class)
                .build());
        addOption(FitFields.WEIGHT, CmdLineOption.Builder.create()
                .name("w").longName("weight")
                .description("Weight of the body")
                .build());
        addOption(FitFields.BODY_FAT, CmdLineOption.Builder.create()
                .name("bf").longName("bodyFat")
                .description("Fat of the body")
                .build());
        addOption(FitFields.BODY_WATER, CmdLineOption.Builder.create()
                .name("bw").longName("bodyWater")
                .description("Water of the body")
                .build());
        addOption(FitFields.VISCERAL_FAT, CmdLineOption.Builder.create()
                .name("vf").longName("visceralFat")
                .description("Visceral Fat of the body").handler(IntOptionHandler.class)
                .build());
        addOption(FitFields.MUSCLE_MASS, CmdLineOption.Builder.create()
                .name("mm").longName("muscleMass")
                .description("Muscle mass of the body")
                .build());
        addOption(FitFields.PHYSIQUE_RATING, CmdLineOption.Builder.create()
                .name("pr").longName("physiqueRating")
                .description("Physique Rating").handler(IntOptionHandler.class)
                .build());
        addOption(FitFields.BONE_MASS, CmdLineOption.Builder.create()
                .name("bm").longName("boneMass")
                .description("Bone Mass of the body")
                .build());
        addOption(FitFields.DCI, CmdLineOption.Builder.create()
                .name("dci").longName("dailyCalorieIntake")
                .description("Daily Calorie Intake")
                .handler(IntOptionHandler.class)
                .build());
        addOption(FitFields.METABOLIC_AGE, CmdLineOption.Builder.create()
                .name("ma").longName("metabolicAge")
                .description("Metabolic Age").handler(IntOptionHandler.class)
                .build());
    }

    private void addOption(FitFields field, Option option) {
        FitFieldSetter setter = new FitFieldSetter(params, field);
        parser.addOption(setter, option);
    }

    @Override
    public FitParams get() throws Exception {
        try {
            parser.parseArgument(args);
            return params;
        }
        catch (CmdLineException e) {
            parser.printUsage(System.out);
            throw e;
        }
    }

    public String getFileName() {
        return out.getName();
    }
}
