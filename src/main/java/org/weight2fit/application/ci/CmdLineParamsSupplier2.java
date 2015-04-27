package org.weight2fit.application.ci;

import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.OptionHandlerFilter;
import org.kohsuke.args4j.spi.DoubleOptionHandler;
import org.kohsuke.args4j.spi.FileOptionHandler;
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
    @Option(name = "-o", usage = "Output file", required = false, handler = FileOptionHandler.class)
    private File out;

    public CmdLineParamsSupplier2(String... args) {
        this.args = args;

        parser = new CmdLineParser(this);
        parser.addOption(new FitFieldSetter(params, FitFields.WEIGHT),
                new CmdLineOption("-w", "--weight", "Weight of the body", DoubleOptionHandler.class));
        parser.addOption(new FitFieldSetter(params, FitFields.BODY_FAT),
                new CmdLineOption("-f", "--bodyFat", "Fat of the body", DoubleOptionHandler.class));

        parser.printUsage(System.out);
        System.out.println();
        System.out.println(parser.printExample(OptionHandlerFilter.ALL));
        System.out.println();
        parser.printSingleLineUsage(System.out);
    }

    @Override
    public FitParams get() throws Exception {
        parser.parseArgument(args);
        return params;
    }
}
