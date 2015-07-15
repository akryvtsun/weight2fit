package org.weight2fit.application.ui.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.weight2fit.application.ui.UiNotifier;

/**
 * @author Andriy Kryvtsun
 */
class GuiNotifier implements UiNotifier {

    private final Display display;

    public GuiNotifier() {
        display = Display.getDefault();
    }

    @Override
    public void showInfoMessage(String message) {
        showMessage(SWT.ICON_INFORMATION, "Info", message);
    }

    @Override
    public void showErrorMessage(String message) {
        showMessage(SWT.ICON_ERROR, "Error", message);
    }

    private void showMessage(int type, String text, String message) {
        Shell parent = display.getActiveShell();

        MessageBox messageBox = new MessageBox(parent, SWT.SHEET | type);
        messageBox.setText(text);
        messageBox.setMessage(message);

        messageBox.open();
    }
}
