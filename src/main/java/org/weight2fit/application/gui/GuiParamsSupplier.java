package org.weight2fit.application.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.weight2fit.application.UiFitParamsSupplier;
import org.weight2fit.application.shared.Constants;
import org.weight2fit.application.shared.UiUtils;
import org.weight2fit.domain.FitException;
import org.weight2fit.domain.FitFields;
import org.weight2fit.domain.FitParams;

import java.io.File;
import java.text.ParseException;
import java.util.Date;

/**
 * Main application window.
 *
 * @author Andriy Kryvtsun
 */
// TODO use Spinner for integer fields
// TODO use date/time picker for Timestamp
public class GuiParamsSupplier implements UiFitParamsSupplier {

    private final Display display;
    private final Shell shell;

    private Text timestamp;
    private Text weight;
    private Text bodyFat;
    private Text bodyWater;
    private Text visceralFat;
    private Text muscleMass;
    private Text physiqueRating;
    private Text boneMass;
    private Text metabolicAge;
    private Text dci;

    private FitParams params;

    public GuiParamsSupplier() {
        display = Display.getDefault();

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
                prepareParams();
                shell.close();
            }
        });

        shell.pack();
        centerShell(display);
    }

    private void prepareParams() {
        try {
            params = new FitParams();

            obtainDateParam(timestamp, FitFields.TIMESTAMP);
            obtainDoubleParam(weight, FitFields.WEIGHT);
            obtainDoubleParam(bodyFat, FitFields.BODY_FAT);
            obtainDoubleParam(bodyWater, FitFields.BODY_WATER);
            obtainIntParam(visceralFat, FitFields.VISCERAL_FAT);
            obtainDoubleParam(muscleMass, FitFields.MUSCLE_MASS);
            obtainIntParam(physiqueRating, FitFields.PHYSIQUE_RATING);
            obtainDoubleParam(boneMass, FitFields.BONE_MASS);
            obtainIntParam(metabolicAge, FitFields.METABOLIC_AGE);
            obtainIntParam(dci, FitFields.DCI);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void obtainDateParam(Text txtField, FitFields fitField) throws ParseException {
        String value = txtField.getText();
        if (value.length() > 0) {
            params.setValue(fitField, UiUtils.parse(value));
        }
    }

    private void obtainDoubleParam(Text txtField, FitFields fitField) {
        String value = txtField.getText();
        if (value.length() > 0) {
            params.setValue(fitField, Double.parseDouble(value));
        }
    }

    private void obtainIntParam(Text txtField, FitFields fitField) {
        String value = txtField.getText();
        if (value.length() > 0) {
            params.setValue(fitField, Integer.parseInt(value));
        }
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

        timestamp = createField(group, "Timestamp:", null, FieldVerifiers.DATE);
        timestamp.setText(UiUtils.toString(new Date()));

        weight = createField(group, "Weight:", "kg", FieldVerifiers.DOUBLE);
        weight.setFocus();

        bodyFat = createField(group, "Body Fat:", "%", FieldVerifiers.DOUBLE);
        bodyWater = createField(group, "Body Water:", "%", FieldVerifiers.DOUBLE);
        visceralFat = createField(group, "Visceral Fat:", null, FieldVerifiers.INTEGER);
        muscleMass = createField(group, "Muscle Mass:", "kg", FieldVerifiers.DOUBLE);
        physiqueRating = createField(group, "Physique Rating:", null, FieldVerifiers.INTEGER);
        boneMass = createField(group, "Bone Mass:", "kg", FieldVerifiers.DOUBLE);
        metabolicAge = createField(group, "Metabolic Age:", "years", FieldVerifiers.INTEGER);
        dci = createField(group, "DCI:", "C", FieldVerifiers.INTEGER);

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

    @Override
    public FitParams get() throws FitException {
        shell.open();

        while (!shell.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();
        }
        display.dispose();

        return params;
    }

    @Override
    public File getFile() {
        return new File("out.fit");
    }
}
