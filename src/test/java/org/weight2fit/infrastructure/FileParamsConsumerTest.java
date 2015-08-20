package org.weight2fit.infrastructure;

import org.junit.Test;
import org.weight2fit.application.ui.FileSupplier;
import org.weight2fit.application.ui.UiNotifier;
import org.weight2fit.domain.FitException;
import org.weight2fit.domain.FitFields;
import org.weight2fit.domain.FitParams;
import org.weight2fit.domain.FitParamsConsumer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Andriy Kryvtsun
 */
public class FileParamsConsumerTest {

    @Test
    public void accept_getFileNameFromSupplier_ok() throws Exception {
        final String fileName = "output.fit";

        FileSupplier supplier = mock(FileSupplier.class);
        when(supplier.getFile()).thenReturn(new File(fileName));

        UiNotifier notifier = mock(UiNotifier.class);

        final OutputStream outputStream = mock(OutputStream.class);

        FitParamsConsumer consumer = new FileParamsConsumer(supplier, notifier) {
            @Override
            protected OutputStream createOutputStream(File outFile) throws FileNotFoundException {
                assertEquals(fileName, outFile.getName());
                return outputStream;
            }
        };

        FitParams params = new FitParams();
        params.setValue(FitFields.TIMESTAMP, new Date());
        params.setValue(FitFields.WEIGHT, 85.5);

        consumer.accept(params);

        verify(notifier).showInfoMessage(anyString());
        verify(outputStream).write(any(byte[].class));
    }

    @Test
    public void accept_getFileNameFromFitParams_ok() throws Exception {
        final Date DATE = new Date(2015 - 1900, 8 - 1, 20);

        FileSupplier supplier = new EmptyFileSupplier();
        UiNotifier notifier = mock(UiNotifier.class);

        final OutputStream outputStream = mock(OutputStream.class);

        FitParamsConsumer consumer = new FileParamsConsumer(supplier, notifier) {
            @Override
            protected OutputStream createOutputStream(File outFile) throws FileNotFoundException {
                assertEquals("2015-08-20.fit", outFile.getName());
                return outputStream;
            }
        };

        FitParams params = new FitParams();
        params.setValue(FitFields.TIMESTAMP, DATE);
        params.setValue(FitFields.WEIGHT, 85.5);

        consumer.accept(params);

        verify(notifier).showInfoMessage(anyString());
        verify(outputStream).write(any(byte[].class));
    }

    @Test(expected = FitException.class)
    public void accept_problemWhileOutput() throws Exception {
        FileSupplier supplier = new EmptyFileSupplier();
        UiNotifier notifier = mock(UiNotifier.class);

        FitParamsConsumer consumer = new FileParamsConsumer(supplier, notifier) {
            @Override
            protected OutputStream createOutputStream(File outFile) throws FileNotFoundException {
                throw new FileNotFoundException();
            }
        };

        FitParams params = new FitParams();
        params.setValue(FitFields.TIMESTAMP, new Date());
        params.setValue(FitFields.WEIGHT, 85.5);

        consumer.accept(params);

        verify(notifier, never()).showInfoMessage(anyString());
    }
}