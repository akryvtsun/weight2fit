package org.weight2fit.application.ci;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.DoubleOptionHandler;
import org.kohsuke.args4j.spi.FileOptionHandler;
import org.kohsuke.args4j.spi.IntOptionHandler;
import org.kohsuke.args4j.spi.OptionHandler;
import org.weight2fit.domain.FitFields;
import org.weight2fit.domain.FitParams;
import org.weight2fit.domain.FitParamsSupplier;

import java.io.File;

/**
 * FIT parameters reader from command line.
 *
 * @author Andiry Kryvtsun
 */
public class CmdLineParamsSupplier2 implements FitParamsSupplier {

    private final String[] args;
    private final CmdLineParser parser;

    private final FitParams params = new FitParams();
    @Option(name = "-o", usage = "Output file", required = true, handler = FileOptionHandler.class)
    private File out;

    public CmdLineParamsSupplier2(String... args) {
        this.args = args;

        parser = new CmdLineParser(this);

        addOption(FitFields.TIMESTAMP, "t", "timestamp", "Time Stamp", DateOptionHandler.class);
        addOption(FitFields.WEIGHT, "w", "weight", "Weight of the body", DoubleOptionHandler.class);
        addOption(FitFields.BODY_FAT, "bf", "bodyFat", "Fat of the body", DoubleOptionHandler.class);
        addOption(FitFields.BODY_WATER, "bw", "bodyWater", "Water of the body", DoubleOptionHandler.class);
        addOption(FitFields.VISCERAL_FAT, "vf", "visceralFat", "Visceral Fat of the body", DoubleOptionHandler.class);
        addOption(FitFields.MUSCLE_MASS, "mm", "muscleMass", "Muscle mass of the body", DoubleOptionHandler.class);
        addOption(FitFields.PHYSIQUE_RATING, "pr", "physiqueRating", "Physique Rating", IntOptionHandler.class);
        addOption(FitFields.BONE_MASS, "bm", "boneMass", "Bone Mass of the body", DoubleOptionHandler.class);
        addOption(FitFields.DCI, "dci", "dailyCalorieIntake", "Daily Calorie Intake", IntOptionHandler.class);
        addOption(FitFields.DCI, "ma", "metabolicAge", "Metabolic Age", IntOptionHandler.class);
    }

    private void addOption(FitFields field,
                           String name, String longName, String description,
                           Class<? extends OptionHandler> handler) {
        FitFieldSetter setter = new FitFieldSetter(params, field);
        CmdLineOption option = new CmdLineOption("-" + name, "--" + longName, description, handler);
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
