package org.weight2fit.infrastructure;

import com.garmin.fit.Decode;
import org.junit.Test;
import org.weight2fit.domain.FitFields;
import org.weight2fit.domain.FitParams;
import org.weight2fit.domain.FitParamsConsumer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Date;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.weight2fit.infrastructure.WeightScaleInputStreamMatcher.hasFields;

/**
 * @author Andriy Kryvtsun
 */
public class OutputStreamParamsConsumerTest {

    @Test(expected = NullPointerException.class)
    public void OutputStreamParamsConsumer_nullArgument_NullPointerException() {
        new OutputStreamParamsConsumer(null);
    }

    @Test(expected = NullPointerException.class)
    public void accept_nullArgument_NullPointerException() throws Exception {
        OutputStream os = new ByteArrayOutputStream();
        FitParamsConsumer consumer = new OutputStreamParamsConsumer(os);
        consumer.accept(null);
    }

    @Test
    public void accept_minimumFields_ok() throws Exception {
        FitParams params = new FitParams();
        params.setValue(FitFields.TIMESTAMP, new Date());
        params.setValue(FitFields.WEIGHT, 80.5);
        params.setValue(FitFields.METABOLIC_AGE, 43);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        FitParamsConsumer consumer = new OutputStreamParamsConsumer(os);
        consumer.accept(params);
        byte[] buffer = os.toByteArray();

        assertTrue(new Decode().isFileFit(new ByteArrayInputStream(buffer)));
        assertTrue(new Decode().checkFileIntegrity(new ByteArrayInputStream(buffer)));
        assertThat(new ByteArrayInputStream(buffer), hasFields(FitFields.TIMESTAMP, FitFields.WEIGHT, FitFields.METABOLIC_AGE));
    }
}