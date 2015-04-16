package org.weight2fit.infra;

import com.garmin.fit.*;

import java.util.Date;

/**
 * Weight FIT parameters assembler.
 *
 * @author Andriy Kryvtsun
 */
public class WeightScaleBuilder {
    private final FileIdMesg fileIdMesg;
    private final WeightScaleMesg weightScaleMesg;

    public WeightScaleBuilder() {
        fileIdMesg = new FileIdMesg();
        // mandatory fields
        fileIdMesg.setType(File.WEIGHT);
        fileIdMesg.setProduct(111);
        fileIdMesg.setSerialNumber(222L);

        weightScaleMesg = new WeightScaleMesg();
    }

    public WeightScaleBuilder manufacturer(int value) {
        fileIdMesg.setManufacturer(value);
        return this;
    }

    public WeightScaleBuilder timestamp(Date value) {
        weightScaleMesg.setTimestamp(new DateTime(value));
        return this;
    }

    /**
     * Sets Weight in kg
     */
    public WeightScaleBuilder weight(double value) {
        weightScaleMesg.setWeight((float) value);
        return this;
    }

    /**
     * Sets Body Fat in %
     */
    public WeightScaleBuilder percentFat(double value) {
        weightScaleMesg.setPercentFat((float) value);
        return this;
    }

    /**
     * Sets Body Water in %
     */
    public WeightScaleBuilder percentHydration(double value) {
        weightScaleMesg.setPercentHydration((float) value);
        return this;
    }

    /**
     * Sets Visceral Fat Mass in kg
     */
    public WeightScaleBuilder visceralFatMass(double value) {
        weightScaleMesg.setVisceralFatMass((float) value);
        return this;
    }

    /**
     * Sets Visceral Fat
     */
    public WeightScaleBuilder visceralFatRating(int value) {
        weightScaleMesg.setVisceralFatRating((short) value);
        return this;
    }

    /**
     * Sets Bone Mass in kg
     */
    public WeightScaleBuilder boneMass(double value) {
        weightScaleMesg.setBoneMass((float) value);
        return this;
    }

    /**
     * Sets Muscle Mass in kg
     */
    public WeightScaleBuilder muscleMass(double value) {
        weightScaleMesg.setMuscleMass((float) value);
        return this;
    }

    /**
     * Sets Basal Met in kcal/day
     */
    public WeightScaleBuilder basalMet(double value) {
        weightScaleMesg.setBasalMet((float) value);
        return this;
    }

    /**
     * Sets Daily Caloric Intake in kcal/day
     * Comment: ~4kJ per kcal, 0.25 allows max 16384 kcal
     */
    public WeightScaleBuilder activeMet(double value) {
        weightScaleMesg.setActiveMet((float) value);
        return this;
    }

    /**
     * Sets Physique Rating
     */
    public WeightScaleBuilder physiqueRating(int value) {
        weightScaleMesg.setPhysiqueRating((short) value);
        return this;
    }

    /**
     * Sets Metabolic Age in years
     */
    public WeightScaleBuilder metabolicAge(int value) {
        weightScaleMesg.setMetabolicAge((short) value);
        return this;
    }

    public byte[] build() {
        checkMandatoryFields();

        BufferEncoder encoder = new BufferEncoder();
        encoder.write(fileIdMesg);
        encoder.write(weightScaleMesg);
        return encoder.close();
    }

    private void checkMandatoryFields() {
        if (fileIdMesg.getType() == null)
            throw new IllegalStateException("Field Type is absent");
        if (fileIdMesg.getManufacturer() == null)
            throw new IllegalStateException("Field Manufacturer is absent");
        if (fileIdMesg.getProduct() == null)
            throw new IllegalStateException("Field Product is absent");
        if (fileIdMesg.getSerialNumber() == null)
            throw new IllegalStateException("Field SerialNumber is absent");

        if (weightScaleMesg.getTimestamp() == null)
            throw new IllegalStateException("Field Timestamp is absent");
        if (weightScaleMesg.getWeight() == null)
            throw new IllegalStateException("Field Weight is absent");
    }
}
