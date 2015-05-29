package org.weight2fit.application.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * @author Andriy Kryvtsun
 */
public class MainWindow {
    private Display display;
    private Shell shell;

    public MainWindow(Display display) {
        this.display = display;

        shell = new Shell(display);

        Text helloWorldTest = new Text(shell, SWT.NONE);
        helloWorldTest.setText("Hello World SWT");
        helloWorldTest.pack();

        shell.pack();
        shell.open();
    }

    public boolean isDisposed() {
        return shell.isDisposed();
    }
}
