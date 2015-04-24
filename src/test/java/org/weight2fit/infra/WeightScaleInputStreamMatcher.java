package org.weight2fit.infra;

import com.garmin.fit.*;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.weight2fit.domain.FitFields;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Andriy Kryvtsun
 */
public class WeightScaleInputStreamMatcher extends TypeSafeMatcher<InputStream> {

    private static final Map<Integer, FitFields> mapping = new HashMap();

    static {
        mapping.put(Fit.FIELD_NUM_TIMESTAMP, FitFields.TIMESTAMP);
        mapping.put(0, FitFields.WEIGHT);
    }

    private final FitFields[] fields;

    public WeightScaleInputStreamMatcher(FitFields... fields) {
        this.fields = fields;
    }

    @Override
    protected boolean matchesSafely(InputStream item) {

        WeightScaleStreamValidator validator = new WeightScaleStreamValidator();

        Decode decode = new Decode();
        decode.addListener(validator);

        while (!decode.read(item));

        return validator.isValid();
    }

    private boolean isFieldTypeAcceptable(int type) {
        FitFields field = mapping.get(type);
        return field != null;
    }

    @Override
    public void describeTo(Description description) {
        description
                .appendText("valid Weight File format with")
                .appendValueList(" ", ",", " ", fields)
                .appendText("FIT message fields");
    }

    /**
     * @see 'D00001309 FIT File Types Description Rev 1.6.pdf', chapter '7 Weight File'
     */
    private class WeightScaleStreamValidator implements MesgListener {

        private int fieldIdCount = 0;
        private boolean weightScaleMesgsPresent = false;
        private boolean areOnlyAllowedMesgNums = true;

        private boolean weightFieldsPresent = false;

        @Override
        public void onMesg(Mesg mesg) {
            switch (mesg.getNum()) {
                case MesgNum.FILE_ID:
                    fieldIdCount++;
                    break;
                case MesgNum.USER_PROFILE: break;
                case MesgNum.WEIGHT_SCALE:
                    checkWeightFields(mesg);
                    weightScaleMesgsPresent = true;
                    break;
                case MesgNum.DEVICE_INFO: break;
                default:
                    areOnlyAllowedMesgNums = false;
            }
        }

        private void checkWeightFields(Mesg mesg) {
            for (Field field: mesg.getFields()) {
                if (!isFieldTypeAcceptable(field.getType()))
                    return;
            }
            weightFieldsPresent = true;
        }

        boolean isValid() {
            return isStreamValid() && weightFieldsPresent;
        }

        private boolean isStreamValid() {
            return fieldIdCount == 1 && weightScaleMesgsPresent &&
                    areOnlyAllowedMesgNums;
        }
    }
}
