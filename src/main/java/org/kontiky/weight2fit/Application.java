package org.kontiky.weight2fit;

import com.garmin.fit.*;

import java.util.Date;

/**
 * @see <a href="https://forums.garmin.com/showthread.php?24518-Import-of-Withings-wifi-scale-data-now-possible"/>Import of Withings wifi scale data now possible...</a>
 * @see <a href="http://jmfloreszazo.com/scale2fi">scale2fit</a>
 * @see <a href="https://github.com/marchibbins/fit-weight-scale">fit-weight-scale</a>
 * @see <a href="http://connect.garmin.com/health">garmin.com/health</a>
 */
public class Application {
    public static void main( String[] args ) {
        FileEncoder fitFile = new FileEncoder();
        fitFile.open(new java.io.File("test.fit"));

        {
            FileIdMesg msg = new FileIdMesg();
            // mandatory fields
            msg.setType(File.WEIGHT);
            msg.setManufacturer(Manufacturer.TANITA);
            msg.setProduct(123);
            msg.setSerialNumber(4567L);

            fitFile.write(msg);
        }

        {
            WeightScaleMesg msg = new WeightScaleMesg();
            // mandatory fields
            msg.setTimestamp(new DateTime(new Date()));
            msg.setWeight(82.7f);               // Weight
            // other fields
            msg.setPercentFat(40f);             // Body Fat
            msg.setPercentHydration(57f);       // Body Water
            msg.setVisceralFatMass(50f);
            msg.setVisceralFatRating((short)1); // Visceral Fat
            msg.setBoneMass(40f);               // Bone Mass
            msg.setMuscleMass(30f);             // Muscle Mass
            msg.setBasalMet(3000f);
            msg.setActiveMet(4000f);            // Daily Caloric Intake
            msg.setPhysiqueRating((short)2);    // Physique Rating
            msg.setMetabolicAge((short)35);     // Metabolic Age

            fitFile.write(msg);
        }

        fitFile.close();

        System.out.println("FIT file was written");
    }
}
