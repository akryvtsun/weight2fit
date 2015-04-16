package org.weight2fit;

import org.weight2fit.domain.FitParams;
import org.weight2fit.domain.FitParamsConsumer;
import org.weight2fit.infra.CmdLineParamsSupplier;
import org.weight2fit.infra.FileParamsConsumer;

import java.util.logging.Logger;

/**
 * https://forums.garmin.com/showthread.php?24518-Import-of-Withings-wifi-scale-data-now-possible
 * http://jmfloreszazo.com/scale2fit
 * https://github.com/marchibbins/fit-weight-scale
 * http://connect.garmin.com/health
 * http://www.thisisant.com/resources/fit
 *
 * @author Andriy Kryvtsun
 */
public class Main {
    private static final Logger LOG = Logger.getLogger(Main.class.getName());

    public static void main(String... args) throws Exception {
        LOG.info("Starting coding...");

        CmdLineParamsSupplier supplier = new CmdLineParamsSupplier(args);

        FitParams params = supplier.get();
        String fileName = supplier.getFileName();

        FitParamsConsumer consumer = new FileParamsConsumer(fileName);
        consumer.accept(params);

        LOG.info("FIT file was written");
    }
}
