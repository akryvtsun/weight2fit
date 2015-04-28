package org.weight2fit.application.ci;

import org.junit.Ignore;
import org.junit.Test;
import org.kohsuke.args4j.CmdLineException;
import org.weight2fit.domain.FitFields;
import org.weight2fit.domain.FitParams;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by englishman on 4/24/15.
 */
public class CmdLineParamsSupplier2Test {

    public static final Date DATE = new Date(2015 - 1900, 04 - 1, 17);
    public static final double DELTA = 0.001;

    @Test(expected = CmdLineException.class)
    @Ignore
    public void CmdLineParamsSupplier2_emptyArgsSet_ParseException() throws Exception {
        new CmdLineParamsSupplier2(null);
    }

    @Test(expected = CmdLineException.class)
    @Ignore
    public void CmdLineParamsSupplier2_incompleteArgsSet_ParseException() throws Exception {
        new CmdLineParamsSupplier2(a(p("timestamp", "2015-04-17")));
    }

    @Test
    public void get_minimumArgsSet_ok() throws Exception {
        CmdLineParamsSupplier2 supplier = new CmdLineParamsSupplier2(
                a(p("timestamp", "2015-04-17") +
                  p("out", "res.fit")));

        FitParams params = supplier.get();

        assertEquals(DATE, params.getValue(FitFields.TIMESTAMP));
        assertEquals("res.fit", supplier.getFileName());
    }

    @Test(expected = CmdLineException.class)
    public void get_incorrectTimestamp_ParseException() throws Exception {
        CmdLineParamsSupplier2 supplier = new CmdLineParamsSupplier2(
                a(p("timestamp", "2015x04-17") +
                  p("out", "res.fit")));

        supplier.get();
    }

    @Test
    public void get_onlyWeight_ok() throws Exception {
        CmdLineParamsSupplier2 supplier = new CmdLineParamsSupplier2(
                a(p("timestamp", "2015-04-17") +
                  p("weight", "85.5") +
                  p("out", "res.fit")));

        FitParams params = supplier.get();

        assertEquals(DATE, params.getValue(FitFields.TIMESTAMP));
        assertEquals(85.5, params.getDoubleValue(FitFields.WEIGHT), DELTA);
        assertEquals("res.fit", supplier.getFileName());
    }

    @Test
    public void get_allParamsSet_ok() throws Exception {
        CmdLineParamsSupplier2 supplier = new CmdLineParamsSupplier2(
                a(p("timestamp", "2015-04-17") +
                  p("weight", "85.5") +
                  p("bodyFat", "40") +
                  p("bodyWater", "55") +
                  p("visceralFat", "7") +
                  p("muscleMass", "20") +
                  p("physiqueRating", "7") +
                  p("boneMass", "30") +
                  p("dailyCalorieIntake", "3030") +
                  p("metabolicAge", "40") +
                  p("out", "res.fit")));

        FitParams params = supplier.get();

        assertEquals(DATE, params.getValue(FitFields.TIMESTAMP));
        assertEquals(85.5, params.getDoubleValue(FitFields.WEIGHT), DELTA);
        assertEquals(40, params.getDoubleValue(FitFields.BODY_FAT), DELTA);
        assertEquals(55, params.getDoubleValue(FitFields.BODY_WATER), DELTA);
        assertEquals(7, params.getIntValue(FitFields.VISCERAL_FAT));
        assertEquals(20, params.getDoubleValue(FitFields.MUSCLE_MASS), DELTA);
        assertEquals(7, params.getIntValue(FitFields.PHYSIQUE_RATING));
        assertEquals(30, params.getDoubleValue(FitFields.BONE_MASS), DELTA);
        assertEquals(3030, params.getIntValue(FitFields.DCI));
        assertEquals(40, params.getIntValue(FitFields.METABOLIC_AGE));
        assertEquals("res.fit", supplier.getFileName());
    }

    private static String p(String name, String value) {
        return String.format("--%s %s ", name, value);
    }

    private static String[] a(String args) {
        return args.trim().split("\\s+");
    }
}