package org.weight2fit.infrastructure;

import com.garmin.fit.Manufacturer;
import org.weight2fit.domain.FitException;
import org.weight2fit.domain.FitFields;
import org.weight2fit.domain.FitParams;
import org.weight2fit.domain.FitParamsConsumer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import static org.weight2fit.domain.shared.Utils.checkNotNull;

/**
 * @author Andriy Kryvtsun
 */
public class OutputStreamParamsConsumer implements FitParamsConsumer {

    private static final int DEFAULT_MANUFACTURER = Manufacturer.TANITA;
    private static final int DEFAULT_PRODUCT = 1;
    private static final long DEFAULT_SERIAL_NUMBER = 1L;

    private final OutputStream os;

    public OutputStreamParamsConsumer(OutputStream os) {
        this.os = checkNotNull(os, "OutputStream must be not null");
    }

    @Override
    public void accept(FitParams params) throws FitException {
        checkNotNull(params);

        try {
            byte[] buffer = generateParamBytes(params);
            storeParamBytes(buffer);
        } catch (IOException e) {

            throw new FitException("Error during FitParams storing", e);
        }
    }

    private byte[] generateParamBytes(FitParams params) {

        WeightScaleArrayBuilder builder = new WeightScaleArrayBuilder()
                .manufacturer(DEFAULT_MANUFACTURER)
                .product(DEFAULT_PRODUCT)
                .serialNumber(DEFAULT_SERIAL_NUMBER);

        if (params.hasValue(FitFields.TIMESTAMP)) {
            Date value = params.getValue(FitFields.TIMESTAMP);
            builder.timestamp(value);
        }

        if (params.hasValue(FitFields.WEIGHT)) {
            double value = params.getValue(FitFields.WEIGHT);
            builder.weight(value);
        }

        if (params.hasValue(FitFields.BODY_FAT)) {
            double value = params.getValue(FitFields.BODY_FAT);
            builder.percentFat(value);
        }

        if (params.hasValue(FitFields.BODY_WATER)) {
            double value = params.getValue(FitFields.BODY_WATER);
            builder.percentHydration(value);
        }

        if (params.hasValue(FitFields.VISCERAL_FAT)) {
            int value = params.getValue(FitFields.VISCERAL_FAT);
            builder.visceralFatRating(value);
        }

        if (params.hasValue(FitFields.MUSCLE_MASS)) {
            double value = params.getValue(FitFields.MUSCLE_MASS);
            builder.muscleMass(value);
        }

        if (params.hasValue(FitFields.PHYSIQUE_RATING)) {
            int value = params.getValue(FitFields.PHYSIQUE_RATING);
            builder.physiqueRating(value);
        }

        if (params.hasValue(FitFields.BONE_MASS)) {
            double value = params.getValue(FitFields.BONE_MASS);
            builder.boneMass(value);
        }

        if (params.hasValue(FitFields.DCI)) {
            double value = params.getValue(FitFields.DCI);
            builder.activeMet(value);
        }

        if (params.hasValue(FitFields.METABOLIC_AGE)) {
            int value = params.getValue(FitFields.METABOLIC_AGE);
            builder.metabolicAge(value);
        }

        return builder.build();
    }

    private void storeParamBytes(byte[] buffer) throws IOException {
        try {
            os.write(buffer);
        } finally {
            os.close();
        }
    }
}
