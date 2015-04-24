package org.weight2fit.infra;

import org.apache.commons.cli.ParseException;
import org.junit.Test;
import org.weight2fit.domain.FitFields;
import org.weight2fit.domain.FitParams;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * @author Andriy Kryvtsun
 */
public class CmdLineParamsSupplierTest {

    public static final Date DATE = new Date(2015 - 1900, 04 - 1, 17);
    public static final double DELTA = 0.001;

    @Test(expected = ParseException.class)
    public void CmdLineParamsSupplier_emptyArgsSet_ParseException() throws Exception {
        new CmdLineParamsSupplier(null);
    }

    @Test(expected = ParseException.class)
    public void CmdLineParamsSupplier_incompleteArgsSet_ParseException() throws Exception {
        new CmdLineParamsSupplier(a("-timestamp 2015-04-17"));
    }

    @Test
    public void get_minimumArgsSet_ok() throws Exception {
        CmdLineParamsSupplier supplier = new CmdLineParamsSupplier(a("-timestamp 2015-04-17 -out res.fit"));

        FitParams params = supplier.get();

        assertEquals(DATE, params.getValue(FitFields.TIMESTAMP));
        assertEquals("res.fit", supplier.getFileName());
    }

    @Test(expected = java.text.ParseException.class)
    public void get_incorrectTimestamp_ParseException() throws Exception {
        CmdLineParamsSupplier supplier = new CmdLineParamsSupplier(a("-timestamp 2015x04-17 -out res.fit"));

        supplier.get();
    }

    @Test
    public void get_onlyWeight_ok() throws Exception {
        CmdLineParamsSupplier supplier = new CmdLineParamsSupplier(a("-timestamp 2015-04-17 -weight 85.5 -out res.fit"));

        FitParams params = supplier.get();

        assertEquals(DATE, params.getValue(FitFields.TIMESTAMP));
        assertEquals(85.5, params.getDoubleValue(FitFields.WEIGHT), DELTA);
        assertEquals("res.fit", supplier.getFileName());
    }

    @Test
    public void get_allParamsSet_ok() throws Exception {
        CmdLineParamsSupplier supplier = new CmdLineParamsSupplier(
                a("-timestamp 2015-04-17 " +
                  "-weight 85.5 " +
                  "-bodyFat 40 " +
                  "-bodyWater 55 " +
                  "-visceralFat 7 " +
                  "-muscleMass 20 " +
                  "-physiqueRating 7 " +
                  "-boneMass 30 " +
                  "-dailyCalorieIntake 3030 " +
                  "-metabolicAge 40 " +
                  "-out res.fit"));

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

    private static String[] a(String args) {
        return args.split("\\s+");
    }
}