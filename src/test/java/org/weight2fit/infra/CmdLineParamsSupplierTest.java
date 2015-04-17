package org.weight2fit.infra;

import org.junit.Test;
import org.weight2fit.domain.FitParams;

import static org.junit.Assert.*;

/**
 * @author Andriy Kryvtsun
 */
public class CmdLineParamsSupplierTest {
    @Test
    public void get() throws Exception {
        CmdLineParamsSupplier supplier = new CmdLineParamsSupplier("-timestamp 2015-04-17 -weight 85.5 -out res.fit".split(" "));

        FitParams params = supplier.get();

        assertEquals(85.5, params.getWeight(), 0.01);
        assertEquals("res.fit", supplier.getFileName());
    }
}