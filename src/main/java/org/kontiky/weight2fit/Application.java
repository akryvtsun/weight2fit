package org.kontiky.weight2fit;

import com.garmin.fit.Manufacturer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

/**
 * https://forums.garmin.com/showthread.php?24518-Import-of-Withings-wifi-scale-data-now-possible
 * http://jmfloreszazo.com/scale2fi
 * https://github.com/marchibbins/fit-weight-scale
 * http://connect.garmin.com/health
 */
public class Application {
    private static final Logger LOG = Logger.getLogger(Application.class.getName());

    public static void main( String[] args ) throws IOException {
        LOG.info("Starting coding...");

        byte[] buffer = new WeightScaleBuilder()
            .manufacturer(Manufacturer.TANITA)
            .timestamp(new Date())
            .weight(83f)
        .build();

        try (FileOutputStream fos = new FileOutputStream(new java.io.File("test.fit"))) {
            fos.write(buffer);
        }

        LOG.info("FIT file was written");
    }
}
