package org.kontiky.weight2fit;

import com.garmin.fit.*;

import java.util.Date;

public class WeightScaleBuilder {
    private final FileIdMesg fileIdMesg;
    private final WeightScaleMesg weightScaleMesg;

    public WeightScaleBuilder() {
        fileIdMesg = new FileIdMesg();
        // mandatory fields
        fileIdMesg.setType(File.WEIGHT);
        fileIdMesg.setProduct(0);
        fileIdMesg.setSerialNumber(0L);

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

    public WeightScaleBuilder weight(float value) {
        weightScaleMesg.setWeight(value);
        return this;
    }

    // Body Fat
    public WeightScaleBuilder percentFat(float value) {
        weightScaleMesg.setPercentFat(value);
        return this;
    }

    // Body Water
    public WeightScaleBuilder percentHydration(float value) {
        weightScaleMesg.setPercentHydration(value);
        return this;
    }

    public WeightScaleBuilder visceralFatMass(float value) {
        weightScaleMesg.setVisceralFatMass(value);
        return this;
    }

    // Visceral Fat
    public WeightScaleBuilder visceralFatRating(int value) {
        weightScaleMesg.setVisceralFatRating((short) value);
        return this;
    }

    // Bone Mass
    public WeightScaleBuilder boneMass(float value) {
        weightScaleMesg.setBoneMass(value);
        return this;
    }

    // Muscle Mass
    public WeightScaleBuilder muscleMass(float value) {
        weightScaleMesg.setMuscleMass(value);
        return this;
    }

    public WeightScaleBuilder basalMet(float value) {
        weightScaleMesg.setBasalMet(value);
        return this;
    }

    // Daily Caloric Intake
    public WeightScaleBuilder activeMet(float value) {
        weightScaleMesg.setActiveMet(value);
        return this;
    }

    // Physique Rating
    public WeightScaleBuilder physiqueRating(int value) {
        weightScaleMesg.setPhysiqueRating((short) value);
        return this;
    }

    // Metabolic Age
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
