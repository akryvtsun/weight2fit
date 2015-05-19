package org.weight2fit.application.cli;

import org.junit.Test;
import org.weight2fit.domain.FitException;
import org.weight2fit.domain.FitFields;
import org.weight2fit.domain.FitParams;
import org.weight2fit.domain.shared.Constants;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Andriy Kryvtsun
 */
public class CmdLineParamsSupplierTest {

    public static final Date DATE = new Date(2015 - 1900, 04 - 1, 17);
    public static final String FILE_NAME = "res.fit";
    public static final File FILE = new File(FILE_NAME);
    public static final double DELTA = 0.001;

    @Test(expected = NullPointerException.class)
    public void CmdLineParamsSupplier_emptyArgsSet_NullPointerException() throws Exception {
        new CmdLineParamsSupplier(null);
    }

    @Test(expected = FitException.class)
    public void get_absentsReqArgument_ok() throws Exception {
        CmdLineParamsSupplier supplier = new CmdLineParamsSupplier(
            new CmdLine()
                .sp("t", "2015-04-17")
                .sp("o", FILE_NAME)
                .build()
        );

        supplier.get();
    }

    @Test(expected = FitException.class)
    public void get_incorrectTimestamp_CmdLineException() throws Exception {
        CmdLineParamsSupplier supplier = new CmdLineParamsSupplier(
            new CmdLine()
                .lp("timestamp", "2015x04-17")
                .lp("weight", "85.5")
                .lp("out", FILE_NAME)
                .build()
        );

        supplier.get();
    }

    @Test(expected = FitException.class)
    public void get_incorrectWeight_CmdLineException() throws Exception {
        CmdLineParamsSupplier supplier = new CmdLineParamsSupplier(
                new CmdLine()
                        .lp("timestamp", "2015-04-17")
                        .lp("weight", "85x5")
                        .lp("out", FILE_NAME)
                        .build()
        );

        supplier.get();
    }

    @Test
    public void get_minimumArgsSet_ok() throws Exception {
        final Date today = new Date();
        today.setHours(0);
        today.setMinutes(0);
        today.setSeconds(0);

        // create default file name
        DateFormat formatter = new SimpleDateFormat(Constants.DATE_PATTERN);
        String dateStr = formatter.format(today);
        String fileName = String.format(Constants.FILE_PATTERN, dateStr);
        final File file = new File(fileName);

        CmdLineParamsSupplier supplier = new CmdLineParamsSupplier(
            new CmdLine()
                .lp("weight", "85.5")
                .build()
        );

        FitParams params = supplier.get();

        assertEquals(today, params.getValue(FitFields.TIMESTAMP));
        assertEquals(85.5, (Double)params.getValue(FitFields.WEIGHT), DELTA);
        assertEquals(file, supplier.getFile());
    }

    @Test
    public void get_allParamsSet_ok() throws Exception {
        CmdLineParamsSupplier supplier = new CmdLineParamsSupplier(
            new CmdLine()
                .lp("timestamp", "2015-04-17")
                .lp("weight", "85.5")
                .lp("bodyFat", "40")
                .lp("bodyWater", "55")
                .sp("vf", "7")
                .lp("muscleMass", "20")
                .sp("pr", "7")
                .lp("boneMass", "30")
                .sp("dci", "3030")
                .lp("metabolicAge", "40")
                .lp("out", FILE_NAME)
                .build()
        );

        FitParams params = supplier.get();

        assertEquals(DATE, params.getValue(FitFields.TIMESTAMP));
        assertEquals(85.5, (Double)params.getValue(FitFields.WEIGHT), DELTA);
        assertEquals(40, (Double)params.getValue(FitFields.BODY_FAT), DELTA);
        assertEquals(55, (Double)params.getValue(FitFields.BODY_WATER), DELTA);
        assertEquals(7, params.getValue(FitFields.VISCERAL_FAT));
        assertEquals(20, (Double)params.getValue(FitFields.MUSCLE_MASS), DELTA);
        assertEquals(7, params.getValue(FitFields.PHYSIQUE_RATING));
        assertEquals(30, (Double)params.getValue(FitFields.BONE_MASS), DELTA);
        assertEquals(3030, (Double)params.getValue(FitFields.DCI), DELTA);
        assertEquals(40, params.getValue(FitFields.METABOLIC_AGE));
        assertEquals(FILE, supplier.getFile());
    }

    private static class CmdLine {

        private List<String> arguments = new ArrayList();

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