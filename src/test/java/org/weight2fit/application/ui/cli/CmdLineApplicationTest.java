package org.weight2fit.application.ui.cli;

import org.junit.Test;
import org.weight2fit.application.Weight2FitApplication;
import org.weight2fit.application.ui.FileParamsConsumerCreator;
import org.weight2fit.application.ui.UiFitParamsSupplier;
import org.weight2fit.application.ui.UiNotifier;
import org.weight2fit.domain.FitException;
import org.weight2fit.domain.FitFields;
import org.weight2fit.domain.FitParams;
import org.weight2fit.domain.FitParamsConsumer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.*;

/**
 * @author Andriy Kryvtsun
 */
public class CmdLineApplicationTest {

    @Test
    public void execute_getFitParams_ok() throws FitException, FileNotFoundException {
        final FitParams params = new FitParams();
        params.setValue(FitFields.TIMESTAMP, new Date());
        params.setValue(FitFields.WEIGHT, 85d);

        final String fileName = "mock_file";
        final File file = new File(fileName);

        UiFitParamsSupplier supplier = mock(UiFitParamsSupplier.class);
        when(supplier.get()).thenReturn(params);
        when(supplier.getFile()).thenReturn(file);

        FitParamsConsumer consumer = mock(FitParamsConsumer.class);
        FileParamsConsumerCreator consumerCreator = mock(FileParamsConsumerCreator.class);
        when(consumerCreator.create(file)).thenReturn(consumer);

        UiNotifier notifier = mock(UiNotifier.class);

        Weight2FitApplication application = new CmdLineApplication(supplier, consumerCreator, notifier);
        int result = application.execute();

        assertEquals(0, result);

        verify(consumer, times(1)).accept(params);

        verify(notifier, times(1)).showInfoMessage(contains(fileName));
        verify(notifier, never()).showErrorMessage(anyString());
    }

    @Test
    public void execute_getFitParams_withError() throws FitException, FileNotFoundException {
        UiFitParamsSupplier supplier = mock(UiFitParamsSupplier.class);
        when(supplier.get()).thenThrow(FitException.class);

        FileParamsConsumerCreator consumerCreator = mock(FileParamsConsumerCreator.class);
        UiNotifier notifier = mock(UiNotifier.class);

        Weight2FitApplication application = new CmdLineApplication(supplier, consumerCreator, notifier);
        int result = application.execute();

        assertEquals(2, result);

        verify(notifier, times(1)).showErrorMessage(anyString());
        verify(notifier, never()).showInfoMessage(anyString());
    }
}