package org.weight2fit.application.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.weight2fit.application.shared.Constants;

/**
 * @author Andriy Kryvtsun
 */
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
        shell.setLayout(layout);

        createMeasures(shell);

        Button button = new Button(shell, SWT.PUSH);
        button.setText("Generate file");
        GridData data = new GridData();
        data.horizontalAlignment = GridData.END;
        data.grabExcessHorizontalSpace = true;
        button.setLayoutData(data);
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

        GridData data = new GridData();
        data.horizontalAlignment = GridData.FILL;
        data.verticalAlignment = GridData.FILL;
        data.grabExcessHorizontalSpace = true;
        data.grabExcessVerticalSpace = true;
        group.setLayoutData(data);

        GridLayout layout = new GridLayout();
        layout.numColumns = 6;
        layout.marginLeft = 5;
        layout.marginTop = 5;
        layout.marginRight = 5;
        layout.marginBottom = 5;
        group.setLayout(layout);

        new Label(group, SWT.RIGHT).setText("Timestamp:");;
        final Text timestampText = new Text(group, SWT.BORDER | SWT.SINGLE);
        timestampText.addVerifyListener(new VerifyListener() {
            @Override
            public void verifyText(VerifyEvent e) {
                System.out.println(e.text);
                e.doit = false;
            }
        });
        new Label(group, SWT.RIGHT);

        new Label(group, SWT.RIGHT).setText("Weight:");
        final Text weightText = new Text(group, SWT.BORDER | SWT.SINGLE);
        new Label(group, SWT.RIGHT).setText("kg");

        new Label(group, SWT.RIGHT).setText("Body Fat:");
        final Text bodyFatText = new Text(group, SWT.BORDER | SWT.SINGLE);
        new Label(group, SWT.RIGHT).setText("%");

        new Label(group, SWT.RIGHT).setText("Body Water:");
        final Text bodyWaterText = new Text(group, SWT.BORDER | SWT.SINGLE);
        new Label(group, SWT.RIGHT).setText("%");

        new Label(group, SWT.RIGHT).setText("Visceral Fat:");
        final Text visceralFatText = new Text(group, SWT.BORDER | SWT.SINGLE);
        new Label(group, SWT.RIGHT);

        new Label(group, SWT.RIGHT).setText("Muscle Mass:");
        final Text muscleMassText = new Text(group, SWT.BORDER | SWT.SINGLE);
        new Label(group, SWT.RIGHT).setText("kg");

        new Label(group, SWT.RIGHT).setText("Physique Rating:");
        final Text physiqueRatingText = new Text(group, SWT.BORDER | SWT.SINGLE);
        new Label(group, SWT.RIGHT);

        new Label(group, SWT.RIGHT).setText("Bone Mass:");
        final Text boneMassText = new Text(group, SWT.BORDER | SWT.SINGLE);
        new Label(group, SWT.RIGHT).setText("kg");

        new Label(group, SWT.RIGHT).setText("DCI:");;
        final Text dciText = new Text(group, SWT.BORDER | SWT.SINGLE);
        new Label(group, SWT.RIGHT).setText("C");

        new Label(group, SWT.RIGHT).setText("Metabolic Age:");;
        final Text metabolicAgeText = new Text(group, SWT.BORDER | SWT.SINGLE);
        new Label(group, SWT.RIGHT).setText("years");
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
