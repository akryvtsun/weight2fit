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
        fileIdMesg.setProduct(123);
        fileIdMesg.setSerialNumber(4567L);

        weightScaleMesg = new WeightScaleMesg();
        // not mandatory fields
        weightScaleMesg.setPercentFat(40f);                 // Body Fat
        weightScaleMesg.setPercentHydration(57f);           // Body Water
        weightScaleMesg.setVisceralFatMass(50f);
        weightScaleMesg.setVisceralFatRating((short) 1);    // Visceral Fat
        weightScaleMesg.setBoneMass(40f);                   // Bone Mass
        weightScaleMesg.setMuscleMass(30f);                 // Muscle Mass
        weightScaleMesg.setBasalMet(3000f);
        weightScaleMesg.setActiveMet(4000f);                // Daily Caloric Intake
        weightScaleMesg.setPhysiqueRating((short) 2);       // Physique Rating
        weightScaleMesg.setMetabolicAge((short) 35);        // Metabolic Age
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
