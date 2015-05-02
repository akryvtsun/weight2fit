package org.weight2fit.infrastructure;

import com.garmin.fit.*;

import java.util.Date;

import static org.weight2fit.domain.shared.Utils.checkNotNull;

/**
 * Weight FIT parameters assembler.
 *
 * @author Andriy Kryvtsun
 */
public class WeightScaleArrayBuilder {
    private final FileIdMesg fileIdMesg;
    private final WeightScaleMesg weightScaleMesg;

    public WeightScaleArrayBuilder() {
        fileIdMesg = new FileIdMesg();
        // one of mandatory fields
        fileIdMesg.setType(File.WEIGHT);

        weightScaleMesg = new WeightScaleMesg();
    }

    ///// Device definition /////

    public WeightScaleArrayBuilder manufacturer(int value) {
        fileIdMesg.setManufacturer(value);
        return this;
    }

    public WeightScaleArrayBuilder product(int value) {
        fileIdMesg.setProduct(value);
        return this;
    }

    public WeightScaleArrayBuilder serialNumber(long value) {
        fileIdMesg.setSerialNumber(value);
        return this;
    }

    ///// Weight definition /////

    public WeightScaleArrayBuilder timestamp(Date value) {
        weightScaleMesg.setTimestamp(new DateTime(value));
        return this;
    }

    /**
     * Sets Weight in kg
     */
    public WeightScaleArrayBuilder weight(double value) {
        weightScaleMesg.setWeight((float) value);
        return this;
    }

    /**
     * Sets Body Fat in %
     */
    public WeightScaleArrayBuilder percentFat(double value) {
        weightScaleMesg.setPercentFat((float) value);
        return this;
    }

    /**
     * Sets Body Water in %
     */
    public WeightScaleArrayBuilder percentHydration(double value) {
        weightScaleMesg.setPercentHydration((float) value);
        return this;
    }

    /**
     * Sets Visceral Fat Mass in kg
     */
    public WeightScaleArrayBuilder visceralFatMass(double value) {
        weightScaleMesg.setVisceralFatMass((float) value);
        return this;
    }

    /**
     * Sets Visceral Fat
     */
    public WeightScaleArrayBuilder visceralFatRating(int value) {
        weightScaleMesg.setVisceralFatRating((short) value);
        return this;
    }

    /**
     * Sets Bone Mass in kg
     */
    public WeightScaleArrayBuilder boneMass(double value) {
        weightScaleMesg.setBoneMass((float) value);
        return this;
    }

    /**
     * Sets Muscle Mass in kg
     */
    public WeightScaleArrayBuilder muscleMass(double value) {
        weightScaleMesg.setMuscleMass((float) value);
        return this;
    }

    /**
     * Sets Basal Met in kcal/day
     */
    public WeightScaleArrayBuilder basalMet(double value) {
        weightScaleMesg.setBasalMet((float) value);
        return this;
    }

    /**
     * Sets Daily Caloric Intake in kcal/day
     * Comment: ~4kJ per kcal, 0.25 allows max 16384 kcal
     */
    public WeightScaleArrayBuilder activeMet(double value) {
        weightScaleMesg.setActiveMet((float) value);
        return this;
    }

    /**
     * Sets Physique Rating
     */
    public WeightScaleArrayBuilder physiqueRating(int value) {
        weightScaleMesg.setPhysiqueRating((short) value);
        return this;
    }

    /**
     * Sets Metabolic Age in years
     */
    public WeightScaleArrayBuilder metabolicAge(int value) {
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
        checkNotNull(fileIdMesg.getType(), "Field Type is absent");
        checkNotNull(fileIdMesg.getManufacturer(), "Field Manufacturer is absent");
        checkNotNull(fileIdMesg.getProduct(), "Field Product is absent");
        checkNotNull(fileIdMesg.getSerialNumber(), "Field SerialNumber is absent");

        checkNotNull(weightScaleMesg.getTimestamp(), "Field Timestamp is absent");
        checkNotNull(weightScaleMesg.getWeight(), "Field Weight is absent");
    }
}
