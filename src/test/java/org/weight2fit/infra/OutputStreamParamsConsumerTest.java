package org.weight2fit.infra;

import com.garmin.fit.Decode;
import org.junit.Test;
import org.weight2fit.domain.FitParams;
import org.weight2fit.domain.FitParamsConsumer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Date;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author Andriy Kryvtsun
 */
public class OutputStreamParamsConsumerTest {

    @Test(expected = NullPointerException.class)
    public void OutputStreamParamsConsumer_nullArgument_NullPointerException() {
        new OutputStreamParamsConsumer(null);
    }

    @Test
    public void accept_minimumFields_ok() throws Exception {
        FitParams params = new FitParams.Builder()
                .withTimestamp(new Date())
                .build();

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        FitParamsConsumer consumer = new OutputStreamParamsConsumer(os);
        consumer.accept(params);
        byte[] buffer = os.toByteArray();

        assertTrue(Decode.isFit(new ByteArrayInputStream(buffer)));
        assertTrue(Decode.checkIntegrity(new ByteArrayInputStream(buffer)));
        assertThat(new ByteArrayInputStream(buffer), new WeightScaleInputStreamMatcher());
    }
}