package org.weight2fit.infrastructure;

import com.garmin.fit.Decode;
import com.garmin.fit.Manufacturer;
import org.junit.Test;
import org.weight2fit.domain.FitFields;

import java.io.ByteArrayInputStream;
import java.util.Date;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.weight2fit.infrastructure.WeightScaleInputStreamMatcher.hasFields;

/**
 * TODO check reusability
 * TODO check mandatory fields absents more
 *
 * @author Andriy Kryvtsun
 */
public class WeightScaleArrayBuilderTest {

    @Test(expected = NullPointerException.class)
    public void build_absentMandatoryFields_NullPointerException() {
        new WeightScaleArrayBuilder().build();
    }

    @Test
    public void build_checkIntegrity_ok() {
        byte[] buffer = new WeightScaleArrayBuilder()
            .manufacturer(Manufacturer.TANITA)
            .product(1)
            .serialNumber(1L)
            .timestamp(new Date())
            .weight(83f)
            .percentFat(55)
            .build();

        assertTrue(new Decode().isFileFit(new ByteArrayInputStream(buffer)));
        assertTrue(new Decode().checkFileIntegrity(new ByteArrayInputStream(buffer)));
        assertThat(new ByteArrayInputStream(buffer), hasFields(FitFields.TIMESTAMP, FitFields.WEIGHT, FitFields.BODY_FAT));
    }
}