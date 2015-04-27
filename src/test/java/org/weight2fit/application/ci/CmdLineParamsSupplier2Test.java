package org.weight2fit.application.ci;

import org.junit.Ignore;
import org.junit.Test;
import org.weight2fit.domain.FitFields;
import org.weight2fit.domain.FitParams;

import static org.junit.Assert.assertTrue;

/**
 * Created by englishman on 4/24/15.
 */
public class CmdLineParamsSupplier2Test {

    @Test
    public void testGet() throws Exception {
        CmdLineParamsSupplier2 supplier = new CmdLineParamsSupplier2(a(p("w", "85.4")));
        FitParams params = supplier.get();
        assertTrue(params.hasValue(FitFields.WEIGHT));
    }

    @Test
    @Ignore
    public void testGet2() throws Exception {
        CmdLineParamsSupplier2 supplier = new CmdLineParamsSupplier2(a(p("weight", "85.4")));
        FitParams params = supplier.get();
        assertTrue(params.hasValue(FitFields.WEIGHT));
    }

    private static String p(String name, String value) {
        return String.format("-%s %s ", name, value);
    }

    private static String[] a(String args) {
        return args.trim().split("\\s+");
    }
}