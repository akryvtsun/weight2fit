package org.weight2fit.application.ui.cli;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.weight2fit.application.ui.UiNotifier;
import org.weight2fit.domain.FitException;
import org.weight2fit.domain.FitFields;
import org.weight2fit.domain.FitParams;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

/**
 * @author Andriy Kryvtsun
 */
@RunWith(MockitoJUnitRunner.class)
public class CmdLineParamsSupplierTest {

    @SuppressWarnings("deprecation")
    private static final Date DATE = new Date(2015 - 1900, 4 - 1, 17);
    private static final String FILE_NAME = "res.fit";
    private static final File FILE = new File(FILE_NAME);
    private static final double DELTA = 0.001;

    @Mock
    UiNotifier notifier;

    @Test(expected = NullPointerException.class)
    public void CmdLineParamsSupplier_emptyArgsSet_NullPointerException() {
        new CmdLineParamsSupplier(notifier, null);
    }

    @Test(expected = FitException.class)
    public void get_absentsReqArgument_FitException() throws Exception {
        CmdLineParamsSupplier supplier = new CmdLineParamsSupplier(notifier,
            new CmdLine()
                .sp("t", "2015-04-17")
                .sp("o", FILE_NAME)
                .build()
        );

        supplier.get();
    }

    @Test(expected = FitException.class)
    public void get_incorrectTimestamp_FitException() throws Exception {
        CmdLineParamsSupplier supplier = new CmdLineParamsSupplier(notifier,
            new CmdLine()
                .lp("timestamp", "2015x04-17")
                .lp("weight", "85.5")
                .lp("out", FILE_NAME)
                .build()
        );

        supplier.get();
    }

    @Test(expected = FitException.class)
    public void get_incorrectWeight_FitException() throws Exception {
        CmdLineParamsSupplier supplier = new CmdLineParamsSupplier(notifier,
                new CmdLine()
                        .lp("timestamp", "2015-04-17")
                        .lp("weight", "85x5")
                        .lp("out", FILE_NAME)
                        .build()
        );

        supplier.get();
    }

    @Test
    public void get_showHelp_ok() throws Exception {
        CmdLineParamsSupplier supplier = new CmdLineParamsSupplier(notifier,
                new CmdLine()
                        .lp("help", null)
                        .build()
        );

        FitParams params = supplier.get();

        assertNull(params);
        verify(notifier, times(3)).showInfoMessage(anyString());
    }

    @Test
    public void get_minimumArgsSet_ok() throws Exception {
        final Date today = new Date();

        CmdLineParamsSupplier supplier = new CmdLineParamsSupplier(notifier,
            new CmdLine()
                .lp("weight", "85.5")
                .build()
        );

        FitParams params = supplier.get();

        Date timestamp = params.getValue(FitFields.TIMESTAMP);
        assertEquals(truncateMillis(today), truncateMillis(timestamp));
        assertEquals(85.5, (Double)params.getValue(FitFields.WEIGHT), DELTA);

        verify(notifier, never()).showInfoMessage(anyString());
    }

    private Date truncateMillis(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    @Test
    public void get_allParamsSet_ok() throws Exception {
        CmdLineParamsSupplier supplier = new CmdLineParamsSupplier(notifier,
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
        assertEquals(7, ((Integer)params.getValue(FitFields.VISCERAL_FAT)).intValue());
        assertEquals(20, (Double)params.getValue(FitFields.MUSCLE_MASS), DELTA);
        assertEquals(7, ((Integer)params.getValue(FitFields.PHYSIQUE_RATING)).intValue());
        assertEquals(30, (Double)params.getValue(FitFields.BONE_MASS), DELTA);
        assertEquals(3030, (Double)params.getValue(FitFields.DCI), DELTA);
        assertEquals(40, ((Integer)params.getValue(FitFields.METABOLIC_AGE)).intValue());
        assertEquals(FILE, supplier.getFile());

        verify(notifier, never()).showInfoMessage(anyString());
    }

    private static class CmdLine {

        private List<String> arguments = new ArrayList<String>();

        // short parameter
        CmdLine sp(String param, String value) {
            arguments.add("-" + param);
            if (value != null)
                arguments.add(value);
            return this;
        }

        // long parameter
        CmdLine lp(String param, String value) {
            arguments.add("--" + param);
            if (value != null)
                arguments.add(value);
            return this;
        }

        String[] build() {
            return arguments.toArray(new String[arguments.size()]);
        }
    }
}