package org.weight2fit.application.ui.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.weight2fit.application.ui.UiFitParamsSupplier;
import org.weight2fit.domain.FitParamsConsumer;
import org.weight2fit.infrastructure.FileParamsConsumer;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author Andriy Kryvtsun
 */
public class SwtFactory implements GuiFactory {

    private final Display display;

    public SwtFactory() {
        display = Display.getDefault();
    }

    @Override
    public UiFitParamsSupplier createSupplier() {
        return new GuiParamsSupplier(display);
    }

    @Override
    public FitParamsConsumer createConsumer(File outFile) throws FileNotFoundException {
        return new FileParamsConsumer(outFile);
    }

    @Override
    public void showMessage(int type, String text, String message) {
        Shell parent = display.getActiveShell();

        MessageBox messageBox = new MessageBox(parent, SWT.SHEET | type);
        messageBox.setText(text);
        messageBox.setMessage(message);

        messageBox.open();
    }
}
