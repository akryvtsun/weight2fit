package org.weight2fit.application.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;
import org.weight2fit.application.shared.Constants;

/**
 * @author Andriy Kryvtsun
 */
public class MainWindow {
    private Shell shell;

    public MainWindow(Display display) {
        shell = new Shell(display/*, SWT.CLOSE | SWT.TITLE*/);
        //shell.setSize(250, 250);

        shell.setText(Constants.APP_NAME);
        RowLayout layout = new RowLayout(SWT.VERTICAL);
        layout.marginLeft = 5;
        layout.marginTop = 5;
        layout.marginRight = 5;
        layout.marginBottom = 5;
        shell.setLayout(layout);

        createMeasures(shell);

        Button button = new Button(shell, SWT.PUSH);
        button.setText("Generate");
        button.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                shell.close();
            }
        });

        shell.pack();

        centerShell(display);

        shell.open();
    }

    private void createMeasures(Composite parent) {
        Group group = new Group(parent, SWT.NULL /*SHADOW_ETCHED_IN*/);
        group.setText("Measures");
        group.setLayout(new FormLayout());

        final Label l1 = new Label(group, SWT.RIGHT);
        l1.setText("First Name");
        FormData fd = new FormData();
        fd.top = new FormAttachment(10, 10);
        fd.left = new FormAttachment(0, 10);
        fd.bottom = new FormAttachment(30, 0);
        fd.right = new FormAttachment(40, 0);
        l1.setLayoutData(fd);

        final Label l2 = new Label(group, SWT.RIGHT);
        l2.setText("Last Name");
        fd = new FormData();
        fd.top = new FormAttachment(l1, 5);
        fd.left = new FormAttachment(0, 10);
        fd.bottom = new FormAttachment(40, 0);
        fd.right = new FormAttachment(40, 0);
        l2.setLayoutData(fd);

        final Text t1 = new Text(group, SWT.BORDER | SWT.SINGLE);
        fd = new FormData();
        fd.top = new FormAttachment(l1, 0, SWT.TOP);
        fd.left = new FormAttachment(l1, 10);
        t1.setLayoutData(fd);

        final Text t2 = new Text(group, SWT.BORDER | SWT.SINGLE);
        fd = new FormData();
        fd.top = new FormAttachment(l2, 0, SWT.TOP);
        fd.left = new FormAttachment(l2, 10);
        t2.setLayoutData(fd);
    }

    private void centerShell(Display display) {
        Monitor primary = display.getPrimaryMonitor();
        Rectangle bounds = primary.getBounds();
        Rectangle rect = shell.getBounds();

        int x = bounds.x + (bounds.width - rect.width) / 2;
        int y = bounds.y + (bounds.height - rect.height) / 2;

        shell.setLocation(x, y);
    }

    public boolean isDisposed() {
        return shell.isDisposed();
    }
}
