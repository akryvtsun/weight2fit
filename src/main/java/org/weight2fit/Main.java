package org.weight2fit;

import com.garmin.fit.Manufacturer;
import org.weight2fit.infra.CommandLineProcessor;
import org.weight2fit.infra.WeightScaleBuilder;

import java.io.File;
import java.io.FileOutputStream;
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

    public static void main(String... args) throws Exception {
        LOG.info("Starting coding...");

        CommandLineProcessor processor = new CommandLineProcessor();
        processor.parse(args);

        byte[] buffer = new WeightScaleBuilder()
            .manufacturer(Manufacturer.TANITA)
            .timestamp(processor.getTimestamp())
            .weight(processor.getWeight())
        .build();

        File file = new File(processor.getOutFileName());
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(buffer);
        }

        LOG.info("FIT file was written");
    }
}
