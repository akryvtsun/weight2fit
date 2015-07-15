package org.weight2fit.application.ui.gui;

import org.junit.Test;
import org.weight2fit.application.Weight2FitApplication;
import org.weight2fit.application.ui.UiFitParamsSupplier;
import org.weight2fit.application.ui.UiNotifier;
import org.weight2fit.domain.FitException;
import org.weight2fit.domain.FitParams;
import org.weight2fit.domain.FitParamsConsumer;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Andriy Kryvtsun
 */
public class GuiApplicationTest {

    @Test
    public void execute_getFitParams_ok() throws FitException, FileNotFoundException {
        final String fileName = "mock_file";
        final FitParams params = new FitParams();
        final File file = new File(fileName);

        UiFitParamsSupplier supplier = mock(UiFitParamsSupplier.class);
        when(supplier.get()).thenReturn(params).thenReturn(null);
        when(supplier.getFile()).thenReturn(file);

        FitParamsConsumer consumer = mock(FitParamsConsumer.class);

        GuiFactory factory = mock(GuiFactory.class);
        when(factory.createSupplier()).thenReturn(supplier);
        when(factory.createConsumer(file)).thenReturn(consumer);

        UiNotifier notifier = mock(UiNotifier.class);

        Weight2FitApplication application = new GuiApplication(factory, notifier);
        int result = application.execute();

        assertEquals(1, result);

        verify(consumer, times(1)).accept(params);
        verify(notifier, times(1)).showInfoMessage(contains(fileName));
        verify(notifier, never()).showErrorMessage(anyString());
    }
}