package org.weight2fit.application.ui.gui;

import org.weight2fit.application.ui.UiFitParamsSupplier;
import org.weight2fit.domain.FitParamsConsumer;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author Andriy Kryvtsun
 */
public interface GuiFactory {

    UiFitParamsSupplier createSupplier();

    FitParamsConsumer createConsumer(File outFile) throws FileNotFoundException;

    void showMessage(int type, String text, String message);
}
