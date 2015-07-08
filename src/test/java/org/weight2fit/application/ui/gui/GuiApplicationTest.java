package org.weight2fit.application.ui.gui;

import org.eclipse.swt.SWT;
import org.junit.Test;
import org.weight2fit.application.Weight2FitApplication;
import org.weight2fit.application.ui.UiFitParamsSupplier;
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
        final FitParams params = new FitParams();
        final File file = new File("mock_file");

        UiFitParamsSupplier supplier = mock(UiFitParamsSupplier.class);
        when(supplier.get()).thenReturn(params).thenReturn(null);
        when(supplier.getFile()).thenReturn(file);

        FitParamsConsumer consumer = mock(FitParamsConsumer.class);

        GuiFactory factory = mock(GuiFactory.class);
        when(factory.createSupplier()).thenReturn(supplier);
        when(factory.createConsumer(file)).thenReturn(consumer);

        Weight2FitApplication application = new GuiApplication(factory);
        int result = application.execute();

        assertEquals(1, result);

        verify(consumer, times(1)).accept(params);
        verify(factory, times(1)).showMessage(eq(SWT.ICON_INFORMATION), eq("Info"), anyString());
        verify(factory, never()).showMessage(eq(SWT.ICON_ERROR), anyString(), anyString());
    }
}