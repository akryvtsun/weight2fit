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
        layout.verticalSpacing = 10;
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

        Label label2 = new Label(group, SWT.RIGHT);
        label2.setText("Timestamp:");
        label2.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        final Text timestampText = new Text(group, SWT.BORDER | SWT.SINGLE);
        timestampText.addVerifyListener(new VerifyListener() {
            @Override
            public void verifyText(VerifyEvent e) {
                System.out.println(e.text);
                e.doit = false;
            }
        });
        new Label(group, SWT.RIGHT);

        Label label3 = new Label(group, SWT.RIGHT);
        label3.setText("Weight:");
        label3.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        final Text weightText = new Text(group, SWT.BORDER | SWT.SINGLE);
        new Label(group, SWT.RIGHT).setText("kg");

        Label label4 = new Label(group, SWT.RIGHT);
        label4.setText("Body Fat:");
        label4.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        final Text bodyFatText = new Text(group, SWT.BORDER | SWT.SINGLE);
        Label label = new Label(group, SWT.RIGHT);
        label.setText("%");

        Label label1 = new Label(group, SWT.RIGHT);
        label1.setText("Body Water:");
        label1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        final Text bodyWaterText = new Text(group, SWT.BORDER | SWT.SINGLE);
        new Label(group, SWT.RIGHT).setText("%");

        Label label5 = new Label(group, SWT.RIGHT);
        label5.setText("Visceral Fat:");
        label5.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        final Text visceralFatText = new Text(group, SWT.BORDER | SWT.SINGLE);
        new Label(group, SWT.RIGHT);

        Label label6 = new Label(group, SWT.RIGHT);
        label6.setText("Muscle Mass:");
        label6.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        final Text muscleMassText = new Text(group, SWT.BORDER | SWT.SINGLE);
        new Label(group, SWT.RIGHT).setText("kg");

        Label label7 = new Label(group, SWT.RIGHT);
        label7.setText("Physique Rating:");
        label7.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        final Text physiqueRatingText = new Text(group, SWT.BORDER | SWT.SINGLE);
        new Label(group, SWT.RIGHT);

        Label label8 = new Label(group, SWT.RIGHT);
        label8.setText("Bone Mass:");
        label8.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        final Text boneMassText = new Text(group, SWT.BORDER | SWT.SINGLE);
        new Label(group, SWT.RIGHT).setText("kg");

        Label label9 = new Label(group, SWT.RIGHT);
        label9.setText("Metabolic Age:");
        label9.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        final Text metabolicAgeText = new Text(group, SWT.BORDER | SWT.SINGLE);
        new Label(group, SWT.RIGHT).setText("years");

        Label label10 = new Label(group, SWT.RIGHT);
        label10.setText("DCI:");
        label10.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        final Text dciText = new Text(group, SWT.BORDER | SWT.SINGLE);
        new Label(group, SWT.RIGHT).setText("C");
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
