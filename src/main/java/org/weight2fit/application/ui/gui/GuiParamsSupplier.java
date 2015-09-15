package org.weight2fit.application.ui.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.weight2fit.application.ui.AbstractUiFitParamsSupplier;
import org.weight2fit.application.ui.shared.Constants;
import org.weight2fit.application.ui.shared.UiUtils;
import org.weight2fit.domain.FitException;
import org.weight2fit.domain.FitFields;
import org.weight2fit.domain.FitParams;
import org.weight2fit.domain.FitParamsSupplier;

import java.util.Calendar;
import java.util.Date;

/**
 * Main application window.
 *
 * @author Andriy Kryvtsun
 */
class GuiParamsSupplier extends AbstractUiFitParamsSupplier implements FitParamsSupplier {

    private final Display display;
    final Shell shell;

    DateTime timestamp;
    Text weight;
    private Text bodyFat;
    private Text bodyWater;
    private Text visceralFat;
    private Text muscleMass;
    private Text physiqueRating;
    private Text boneMass;
    Text metabolicAge;
    private Text dci;

    boolean doGeneration = false;

    public GuiParamsSupplier() {
        this.display = Display.getDefault();

        shell = createShell(display);
        Group group = createMeasures(shell);
        createButton(shell, group);

        shell.pack();
        centerWidget(display, shell);
    }

    Shell createShell(Display display) {
        Shell shell = new Shell(display, SWT.CLOSE | SWT.TITLE);
        shell.setText(Constants.APP_NAME + " " + UiUtils.getVersion());

        Image logo = loadImage("logo.png");
        shell.setImage(logo);

        GridLayout layout = new GridLayout();
        layout.marginLeft = 5;
        layout.marginTop = 5;
        layout.marginRight = 5;
        layout.marginBottom = 5;
        layout.verticalSpacing = 10;
        shell.setLayout(layout);

        return shell;
    }

    private Image loadImage(String imageName) {
        return new Image(display, UiUtils.getResource(imageName));
    }

    Group createMeasures(Composite parent) {
        Group group = new Group(parent, SWT.NULL);
        group.setText("Measures");

        GridLayout layout = new GridLayout();
        layout.numColumns = 6;
        layout.marginLeft = 5;
        layout.marginTop = 5;
        layout.marginRight = 5;
        layout.marginBottom = 5;
        group.setLayout(layout);

        timestamp = createDateField(group, "&Timestamp:");

        weight = createTextField(group, "&Weight:", "kg", FieldVerifiers.DOUBLE);
        weight.setFocus();

        bodyFat = createTextField(group, "Body &Fat:", "%", FieldVerifiers.DOUBLE);
        bodyWater = createTextField(group, "&Body Water:", "%", FieldVerifiers.DOUBLE);
        visceralFat = createTextField(group, "&Visceral Fat:", null, FieldVerifiers.INTEGER);
        muscleMass = createTextField(group, "&Muscle Mass:", "kg", FieldVerifiers.DOUBLE);
        physiqueRating = createTextField(group, "Physique &Rating:", null, FieldVerifiers.INTEGER);
        boneMass = createTextField(group, "B&one Mass:", "kg", FieldVerifiers.DOUBLE);
        metabolicAge = createTextField(group, "Metabolic &Age:", "years", FieldVerifiers.INTEGER);
        dci = createTextField(group, "&DCI:", "C", FieldVerifiers.DOUBLE);

        return group;
    }

    private DateTime createDateField(Group group, String labelStr) {
        addFieldLabel(group, labelStr);

        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH);

        DateTime field = new DateTime(group, SWT.DATE | SWT.DROP_DOWN);
        field.setDate(year, month, day);

        addFieldUnit(group, null);
        return field;
    }

    private Text createTextField(Group group, String labelStr, String unitStr, VerifyListener verifier) {
        addFieldLabel(group, labelStr);
        Text field = addTextField(group, verifier);
        addFieldUnit(group, unitStr);
        return field;
    }

    private void addFieldLabel(Group group, String labelStr) {
        Label title = new Label(group, SWT.RIGHT);
        title.setText(labelStr);
        title.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    }

    private Text addTextField(Group group, VerifyListener verifier) {
        final Text field = new Text(group, SWT.BORDER | SWT.SINGLE | SWT.RIGHT);
        field.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        if (verifier != null)
            field.addVerifyListener(verifier);
        return field;
    }

    private void addFieldUnit(Group group, String unitStr) {
        Label unit = new Label(group, SWT.RIGHT);
        if (unitStr != null)
            unit.setText(unitStr);
    }

    private void createButton(Shell shell, Group group) {
        GridData data = new GridData();
        data.horizontalAlignment = GridData.FILL;
        data.verticalAlignment = GridData.FILL;
        data.grabExcessHorizontalSpace = true;
        data.grabExcessVerticalSpace = true;
        group.setLayoutData(data);

        Button button = new Button(shell, SWT.PUSH);
        button.setText("&Create FIT file");

        Image generate = loadImage("generate.png");
        button.setImage(generate);

        GridData data2 = new GridData();
        data2.horizontalAlignment = GridData.END;
        data2.grabExcessHorizontalSpace = true;
        button.setLayoutData(data2);

        button.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                doGeneration = true;
            }
        });
    }

    private static void centerWidget(Display display, Control widget) {
        Monitor primary = display.getPrimaryMonitor();
        Rectangle bounds = primary.getBounds();
        Rectangle rect = widget.getBounds();

        int x = bounds.x + (bounds.width - rect.width) / 2;
        int y = bounds.y + (bounds.height - rect.height) / 2;

        widget.setLocation(x, y);
    }

    @Override
    public FitParams get() throws FitException {
        // clean up
        if (doGeneration)
            doGeneration = false;
         else
            shell.open();

        // SWT main loop
        while (!shell.isDisposed() && !doGeneration) {
            if (!display.readAndDispatch())
                display.sleep();
        }

        // result preparation
        if (doGeneration) {
            prepareParams();
            completeParams();
        }
        else {
            display.dispose();
            params = null;
        }

        return params;
    }

    private void prepareParams() throws FitException {
        try {
            params = new FitParams();

            obtainTimestampParam(timestamp, FitFields.TIMESTAMP);
            obtainDoubleParam(weight, FitFields.WEIGHT);
            obtainDoubleParam(bodyFat, FitFields.BODY_FAT);
            obtainDoubleParam(bodyWater, FitFields.BODY_WATER);
            obtainIntParam(visceralFat, FitFields.VISCERAL_FAT);
            obtainDoubleParam(muscleMass, FitFields.MUSCLE_MASS);
            obtainIntParam(physiqueRating, FitFields.PHYSIQUE_RATING);
            obtainDoubleParam(boneMass, FitFields.BONE_MASS);
            obtainIntParam(metabolicAge, FitFields.METABOLIC_AGE);
            obtainDoubleParam(dci, FitFields.DCI);
        }
        catch (Exception e) {
            throw new FitException("Error during FitParams creation", e);
        }
    }

    private void obtainTimestampParam(DateTime field, FitFields fitField) {
        Date value = getDate(field);
        params.setValue(fitField, value);
    }

    private Date getDate(DateTime field) {
        Calendar cal = Calendar.getInstance();
        cal.set(field.getYear(), field.getMonth(), field.getDay());
        return cal.getTime();
    }

    private void obtainDoubleParam(Text field, FitFields fitField) {
        String value = field.getText();
        if (value.length() > 0) {
            params.setValue(fitField, Double.parseDouble(value));
        }
    }

    private void obtainIntParam(Text field, FitFields fitField) {
        String value = field.getText();
        if (value.length() > 0) {
            params.setValue(fitField, Integer.parseInt(value));
        }
    }
}
