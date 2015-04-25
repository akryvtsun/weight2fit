package org.weight2fit.infrastructure;

import com.garmin.fit.Manufacturer;
import org.weight2fit.domain.FitFields;
import org.weight2fit.domain.FitParams;
import org.weight2fit.domain.FitParamsConsumer;

import java.io.OutputStream;
import java.util.Date;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Andriy Kryvtsun
 */
public class OutputStreamParamsConsumer implements FitParamsConsumer {

    private final OutputStream os;

    public OutputStreamParamsConsumer(OutputStream os) {
        this.os = checkNotNull(os, "OutputStream must be not null");
    }

    @Override
    public void accept(FitParams params) throws Exception {

        WeightScaleBuilder builder = new WeightScaleBuilder()
                .manufacturer(Manufacturer.TANITA);

        if (params.hasValue(FitFields.TIMESTAMP))
            builder.timestamp((Date)params.getValue(FitFields.TIMESTAMP));

        if (params.hasValue(FitFields.WEIGHT))
            builder.weight(params.getDoubleValue(FitFields.WEIGHT));

        if (params.hasValue(FitFields.BODY_FAT))
            builder.percentFat(params.getDoubleValue(FitFields.BODY_FAT));

        if (params.hasValue(FitFields.BODY_WATER))
            builder.percentHydration(params.getDoubleValue(FitFields.BODY_WATER));

        if (params.hasValue(FitFields.VISCERAL_FAT))
            builder.visceralFatRating(params.getIntValue(FitFields.VISCERAL_FAT));

        if (params.hasValue(FitFields.MUSCLE_MASS))
            builder.muscleMass(params.getDoubleValue(FitFields.MUSCLE_MASS));

        if (params.hasValue(FitFields.PHYSIQUE_RATING))
            builder.physiqueRating(params.getIntValue(FitFields.PHYSIQUE_RATING));

        if (params.hasValue(FitFields.BONE_MASS))
            builder.boneMass(params.getDoubleValue(FitFields.BONE_MASS));

        if (params.hasValue(FitFields.DCI))
            builder.activeMet(params.getDoubleValue(FitFields.DCI));

        if (params.hasValue(FitFields.METABOLIC_AGE))
            builder.metabolicAge(params.getIntValue(FitFields.METABOLIC_AGE));

        byte[] buffer = builder.build();

        try {
            os.write(buffer);
        }
        finally {
            os.close();
        }
    }
}