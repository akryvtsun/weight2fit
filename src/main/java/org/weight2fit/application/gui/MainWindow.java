package org.weight2fit.application.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.weight2fit.application.shared.Constants;

/**
 * Main application window.
 *
 * @author Andriy Kryvtsun
 */
// TODO use Spinner for integer fields
// TODO use date/time picker for Timestamp
public class MainWindow {

    private Shell shell;

    public MainWindow(Display display) {

        shell = new Shell(display, SWT.CLOSE | SWT.TITLE);
        shell.setText(Constants.APP_NAME);

        GridLayout layout = new GridLayout();
        layout.marginLeft = 5;
        layout.marginTop = 5;
        layout.marginRight = 5;
        layout.marginBottom = 5;
        layout.verticalSpacing = 10;
        shell.setLayout(layout);

        Group group = createMeasures(shell);

        GridData data = new GridData();
        data.horizontalAlignment = GridData.FILL;
        data.verticalAlignment = GridData.FILL;
        data.grabExcessHorizontalSpace = true;
        data.grabExcessVerticalSpace = true;
        group.setLayoutData(data);

        Button button = new Button(shell, SWT.PUSH);
        button.setText("Generate File");
        GridData data2 = new GridData();
        data2.horizontalAlignment = GridData.END;
        data2.grabExcessHorizontalSpace = true;
        button.setLayoutData(data2);
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

    private Group createMeasures(Composite parent) {
        Group group = new Group(parent, SWT.NULL);
        group.setText("Measures");

        GridLayout layout = new GridLayout();
        layout.numColumns = 6;
        layout.marginLeft = 5;
        layout.marginTop = 5;
        layout.marginRight = 5;
        layout.marginBottom = 5;
        group.setLayout(layout);

        Text timestamp = createField(group, "Timestamp:", null, FieldVerifiers.DATE);
        Text weight = createField(group, "Weight:", "kg", FieldVerifiers.DOUBLE);
        Text bodyFat = createField(group, "Body Fat:", "%", FieldVerifiers.DOUBLE);
        Text bodyWater = createField(group, "Body Water:", "%", FieldVerifiers.DOUBLE);
        Text visceralFat = createField(group, "Visceral Fat:", null, FieldVerifiers.INTEGER);
        Text muscleMass = createField(group, "Muscle Mass:", "kg", FieldVerifiers.DOUBLE);
        Text physiqueRating = createField(group, "Physique Rating:", null, FieldVerifiers.INTEGER);
        Text boneMass = createField(group, "Bone Mass:", "kg", FieldVerifiers.DOUBLE);
        Text metabolicAge = createField(group, "Metabolic Age:", "years", FieldVerifiers.INTEGER);
        Text dci = createField(group, "DCI:", "C", FieldVerifiers.INTEGER);

        return group;
    }

    private Text createField(Group group, String titleStr, String unitStr, VerifyListener verifier) {
        Label title = new Label(group, SWT.RIGHT);
        title.setText(titleStr);
        title.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        final Text field = new Text(group, SWT.BORDER | SWT.SINGLE | SWT.RIGHT);
        if (verifier != null)
            field.addVerifyListener(verifier);

        Label unit = new Label(group, SWT.RIGHT);
        if (unitStr != null)
            unit.setText(unitStr);

        return field;
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
