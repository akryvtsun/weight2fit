package org.weight2fit.application.ui.gui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.weight2fit.domain.FitFields;
import org.weight2fit.domain.FitParams;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

/**
 * @author Andriy Kryvtsun
 */
public class GuiParamsSupplierTest {

    @Test
    public void get_imitateCloseWindow_ok() throws Exception {

        GuiParamsSupplier supplier = new GuiParamsSupplier() {
            @Override
            protected Shell createShell(Display display) {
                return spy(super.createShell(display));
            }
        };

        doNothing().when(supplier.shell).open();
        when(supplier.shell.isDisposed()).thenReturn(true);

        FitParams params = supplier.get();

        assertNull(params);
    }

    @Test
    public void get_imitatePressButton_ok() throws Exception {

        final GuiParamsSupplier supplier = new GuiParamsSupplier() {
            @Override
            protected Shell createShell(Display display) {
                return spy(super.createShell(display));
            }

            @Override
            protected Group createMeasures(Composite parent) {
                Group group = super.createMeasures(parent);
                timestamp.setText("2015-08-20");
                weight.setText("88.3");
                metabolicAge.setText("43");
                return group;
            }
        };

        doAnswer(new Answer() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                supplier.doGeneration = true;
                return null;
            }
        }).when(supplier.shell).open();

        FitParams params = supplier.get();

        assertNotNull(params);
        assertNotNull(params.getValue(FitFields.TIMESTAMP));
        assertNotNull(params.getValue(FitFields.WEIGHT));
        assertNotNull(params.getValue(FitFields.METABOLIC_AGE));

        // initiates disposing of internal display
        when(supplier.shell.isDisposed()).thenReturn(true);
        supplier.get();
    }
}