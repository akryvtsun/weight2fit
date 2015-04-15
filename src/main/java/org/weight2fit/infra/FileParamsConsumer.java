package org.weight2fit.infra;

import com.garmin.fit.Manufacturer;
import org.weight2fit.domain.FitParams;
import org.weight2fit.domain.FitParamsConsumer;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by englishman on 4/15/15.
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

        byte[] buffer = builder.build();

        File file = new File(fineName);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(buffer);
        }
    }
}
