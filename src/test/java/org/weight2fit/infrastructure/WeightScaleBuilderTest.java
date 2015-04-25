package org.weight2fit.infrastructure;

import com.garmin.fit.Decode;
import com.garmin.fit.Manufacturer;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.Date;

import static org.junit.Assert.assertTrue;

/**
 * TODO check reusability
 * TODO check mandatory fields absents more
 */
public class WeightScaleBuilderTest {

    @Test(expected = NullPointerException.class)
    public void absentsMandatoryFields() {
        new WeightScaleBuilder().build();
    }

    @Test
    public void checkIntegrity() {
        byte[] buffer = new WeightScaleBuilder()
            .manufacturer(Manufacturer.TANITA)
            .timestamp(new Date())
            .weight(83f)
        .build();

        assertTrue(Decode.isFit(new ByteArrayInputStream(buffer)));
        assertTrue(Decode.checkIntegrity(new ByteArrayInputStream(buffer)));
    }
}