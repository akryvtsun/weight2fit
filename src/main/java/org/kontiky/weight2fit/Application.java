package org.kontiky.weight2fit;

import com.garmin.fit.*;

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

        BufferEncoder encoder = new BufferEncoder();

        {
            FileIdMesg msg = new FileIdMesg();
            // mandatory fields
            msg.setType(File.WEIGHT);
            msg.setManufacturer(Manufacturer.TANITA);
            msg.setProduct(123);
            msg.setSerialNumber(4567L);

            encoder.write(msg);
        }

        {
            WeightScaleMesg msg = new WeightScaleMesg();
            // mandatory fields
            msg.setTimestamp(new DateTime(new Date()));
            msg.setWeight(82.7f);                   // Weight
            // other fields
            msg.setPercentFat(40f);                 // Body Fat
            msg.setPercentHydration(57f);           // Body Water
            msg.setVisceralFatMass(50f);
            msg.setVisceralFatRating((short) 1);    // Visceral Fat
            msg.setBoneMass(40f);                   // Bone Mass
            msg.setMuscleMass(30f);                 // Muscle Mass
            msg.setBasalMet(3000f);
            msg.setActiveMet(4000f);                // Daily Caloric Intake
            msg.setPhysiqueRating((short) 2);       // Physique Rating
            msg.setMetabolicAge((short) 35);        // Metabolic Age

            encoder.write(msg);
        }

        byte[] buffer = encoder.close();

        try (FileOutputStream fos = new FileOutputStream(new java.io.File("test.fit"))) {
            fos.write(buffer);
        }

        LOG.info("FIT file was written");
    }
}
