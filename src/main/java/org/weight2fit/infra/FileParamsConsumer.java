package org.weight2fit.infra;

import com.garmin.fit.Manufacturer;
import org.weight2fit.domain.FitParams;
import org.weight2fit.domain.FitParamsConsumer;

import java.io.File;
import java.io.FileOutputStream;

/**
 * FIT parameters writer to file.
 *
 * @author Andiry Kryvtsun
 */
public class FileParamsConsumer implements FitParamsConsumer {

    private final String fineName;

    public FileParamsConsumer(String fineName) {
        this.fineName = fineName;
    }

    @Override
    public void accept(FitParams params) throws Exception {

        WeightScaleBuilder builder = new WeightScaleBuilder()
                .manufacturer(Manufacturer.TANITA);

        builder.timestamp(params.getTimestamp());
        builder.weight(params.getWeight());
        builder.percentFat(params.getBodyFat());
        builder.percentHydration(params.getBodyWater());
        builder.visceralFatRating(params.getVisceralFat());
        builder.muscleMass(params.getMuscleMass());
        builder.physiqueRating(params.getPhysiqueRating());
        builder.boneMass(params.getBoneMass());
        builder.activeMet(params.getDCI());
        builder.metabolicAge(params.getMetabolicAge());

        byte[] buffer = builder.build();

        File file = new File(fineName);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(buffer);
        }
    }
}
