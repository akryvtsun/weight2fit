package org.weight2fit.infra;

import org.apache.commons.cli.ParseException;
import org.junit.Test;
import org.weight2fit.domain.FitParams;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * @author Andriy Kryvtsun
 */
public class CmdLineParamsSupplierTest {

    public static final double DELTA = 0.01;

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

        assertEquals(new Date(2015-1900, 04-1, 17), params.getTimestamp());
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

        assertEquals(new Date(2015-1900, 04-1, 17), params.getTimestamp());
        assertEquals(85.5, params.getWeight(), DELTA);
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

        assertEquals(new Date(2015-1900, 04-1, 17), params.getTimestamp());
        assertEquals(85.5, params.getWeight(), DELTA);
        assertEquals(40, params.getBodyFat(), DELTA);
        assertEquals(55, params.getBodyWater(), DELTA);
        assertEquals(7, params.getVisceralFat());
        assertEquals(20, params.getMuscleMass(), DELTA);
        assertEquals(7, params.getPhysiqueRating());
        assertEquals(30, params.getBoneMass(), DELTA);
        assertEquals(3030, params.getDCI());
        assertEquals(40, params.getMetabolicAge());
        assertEquals("res.fit", supplier.getFileName());
    }

    private static String[] a(String args) {
        return args.split("\\s+");
    }
}