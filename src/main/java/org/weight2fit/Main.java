package org.weight2fit;

import com.garmin.fit.Manufacturer;
import org.apache.commons.cli.*;
import org.weight2fit.domain.Parameters;
import org.weight2fit.domain.ParametersHolder;
import org.weight2fit.domain.ParametersService;
import org.weight2fit.infra.CmdLineParametersService;
import org.weight2fit.infra.WeightScaleBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
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

        ParametersService service = new CmdLineParametersService(args);
        ParametersHolder holder = service.getParameters();

        WeightScaleBuilder builder = new WeightScaleBuilder()
                .manufacturer(Manufacturer.TANITA);

        if (holder.get(Parameters.TIMESTEMP) != null) {
            builder.timestamp((Date) holder.get(Parameters.TIMESTEMP));
        }

        if (holder.get(Parameters.WEIGHT) != null) {
            builder.weight((Double) holder.get(Parameters.WEIGHT));
        }

        byte[] buffer = builder.build();

        String outFile = (String)holder.get(Parameters.OUT);
        File file = new File(outFile);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(buffer);
        }

        LOG.info("FIT file " + outFile + " was written");
    }
}
