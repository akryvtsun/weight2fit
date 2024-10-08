package org.weight2fit.application.ui.cli;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.weight2fit.application.Weight2FitApplication;
import org.weight2fit.application.ui.UiNotifier;
import org.weight2fit.domain.*;

import java.util.Date;
import java.util.logging.Level;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Andriy Kryvtsun
 */
@RunWith(MockitoJUnitRunner.class)
public class CmdLineApplicationTest {

    @Mock
    private FitParamsSupplier supplier;

    @Mock
    private FitParamsConsumer consumer;

    @Mock
    private UiNotifier notifier;

    @Test
    public void execute_getFitParams_ok() throws FitException {
        final FitParams params = new FitParams();
        params.setValue(FitFields.TIMESTAMP, new Date());
        params.setValue(FitFields.WEIGHT, 85d);

        when(supplier.get()).thenReturn(params);

        Weight2FitApplication application = new CmdLineApplication(supplier, consumer, notifier);
        int result = application.execute();

        assertEquals(0, result);

        verify(consumer, times(1)).accept(params);
        verify(notifier, never()).showErrorMessage(anyString());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void execute_getFitParams_withError() throws FitException {
        when(supplier.get()).thenThrow(FitException.class);

        CmdLineApplication.LOG.setLevel(Level.OFF); // avoid console exception showing
        Weight2FitApplication application = new CmdLineApplication(supplier, consumer, notifier);
        int result = application.execute();

        assertEquals(2, result);

        verify(notifier, times(1)).showErrorMessage(anyString());
    }
}