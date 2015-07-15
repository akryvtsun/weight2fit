package org.weight2fit.application.ui.gui;

import org.eclipse.swt.widgets.Display;
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
}
