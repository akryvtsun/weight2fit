package org.weight2fit.application.ui.gui;

import org.junit.Test;
import org.weight2fit.application.Weight2FitApplication;
import org.weight2fit.application.ui.UiFitParamsSupplier;
import org.weight2fit.application.ui.UiNotifier;
import org.weight2fit.domain.FitException;
import org.weight2fit.domain.FitFields;
import org.weight2fit.domain.FitParams;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Andriy Kryvtsun
 */
public class GuiApplicationTest {

    @Test
    // TODO avoid 'mock_file' creation in project file system
    public void execute_getFitParams_ok() throws FitException, FileNotFoundException {
        final FitParams params = new FitParams();
        params.setValue(FitFields.TIMESTAMP, new Date());
        params.setValue(FitFields.WEIGHT, 85d);

        final String fileName = "mock_file";
        final File file = new File(fileName);

        UiFitParamsSupplier supplier = mock(UiFitParamsSupplier.class);
        when(supplier.get()).thenReturn(params).thenReturn(null);
        when(supplier.getFile()).thenReturn(file);

        UiNotifier notifier = mock(UiNotifier.class);

        Weight2FitApplication application = new GuiApplication(supplier, notifier);
        int result = application.execute();

        assertEquals(1, result);

        verify(notifier, times(1)).showInfoMessage(contains(fileName));
        verify(notifier, never()).showErrorMessage(anyString());
    }
}