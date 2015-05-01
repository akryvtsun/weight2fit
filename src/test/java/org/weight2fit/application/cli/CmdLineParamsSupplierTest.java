package org.weight2fit.application.cli;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.kohsuke.args4j.CmdLineException;
import org.weight2fit.domain.FitFields;
import org.weight2fit.domain.FitParams;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Andriy Kryvtsun
 */
public class CmdLineParamsSupplierTest {

    public static final Date DATE = new Date(2015 - 1900, 04 - 1, 17);
    public static final double DELTA = 0.001;

    @Test(expected = NullPointerException.class)
    public void CmdLineParamsSupplier_emptyArgsSet_NullPointerException() throws Exception {
        new CmdLineParamsSupplier(null);
    }

    @Test
    public void get_minimumArgsSet_ok() throws Exception {
        CmdLineParamsSupplier supplier = new CmdLineParamsSupplier(
            new CmdLine()
                .lp("timestamp", "2015-04-17")
                .lp("out", "res.fit")
                .build()
        );

        FitParams params = supplier.get();

        assertEquals(DATE, params.getValue(FitFields.TIMESTAMP));
        assertEquals("res.fit", supplier.getFileName());
    }

    @Test(expected = CmdLineException.class)
    public void get_incorrectTimestamp_ParseException() throws Exception {
        CmdLineParamsSupplier supplier = new CmdLineParamsSupplier(
            new CmdLine()
                .lp("timestamp", "2015x04-17")
                .lp("out", "res.fit")
                .build()
        );

        supplier.get();
    }

    @Test
    public void get_onlyWeight_ok() throws Exception {
        CmdLineParamsSupplier supplier = new CmdLineParamsSupplier(
            new CmdLine()
                .lp("timestamp", "2015-04-17")
                .lp("weight", "85.5")
                .lp("out", "res.fit")
                .build()
        );

        FitParams params = supplier.get();

        assertEquals(DATE, params.getValue(FitFields.TIMESTAMP));
        assertEquals(85.5, params.getDoubleValue(FitFields.WEIGHT), DELTA);
        assertEquals("res.fit", supplier.getFileName());
    }

    @Test
    public void get_allParamsSet_ok() throws Exception {
        CmdLineParamsSupplier supplier = new CmdLineParamsSupplier(
            new CmdLine()
                .lp("timestamp", "2015-04-17")
                .lp("weight", "85.5")
                .lp("bodyFat", "40")
                .lp("bodyWater", "55")
                .lp("visceralFat", "7")
                .lp("muscleMass", "20")
                .sp("pr", "7")
                .lp("boneMass", "30")
                .sp("dci", "3030")
                .lp("metabolicAge", "40")
                .lp("out", "res.fit")
                .build()
        );

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

    private static class CmdLine {

        private List<String> arguments = Lists.newArrayList();

        // short parameter
        CmdLine sp(String param, String value) {
            arguments.add("-" + param);
            arguments.add(value);
            return this;
        }

        // long parameter
        CmdLine lp(String param, String value) {
            arguments.add("--" + param);
            arguments.add(value);
            return this;
        }

        String[] build() {
            return arguments.toArray(new String[arguments.size()]);
        }
    }
}