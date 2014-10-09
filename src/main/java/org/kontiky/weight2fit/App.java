package org.kontiky.weight2fit;

import com.garmin.fit.*;

import java.util.Date;

/**
 * @see https://forums.garmin.com/showthread.php?24518-Import-of-Withings-wifi-scale-data-now-possible
 * @see http://jmfloreszazo.com/scale2fi/
 * @see https://github.com/marchibbins/fit-weight-scale
 */
public class App {
    public static void main( String[] args ) {
        FileEncoder fitFile = new FileEncoder();
        fitFile.open(new java.io.File("test.fit"));

        {
            FileIdMesg msg = new FileIdMesg();
            msg.setType(File.WEIGHT);
            msg.setManufacturer(Manufacturer.TANITA);
            msg.setProduct(123);
            msg.setSerialNumber(4567L);
            fitFile.write(msg);
        }

        {
            WeightScaleMesg msg = new WeightScaleMesg();
            msg.setTimestamp(new DateTime(new Date()));
            msg.setWeight(82.7f);
            msg.setPercentHydration(56f);
            fitFile.write(msg);
        }

        fitFile.close();

        System.out.println("FIT file was written");
    }
}
