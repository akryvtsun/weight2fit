package org.weight2fit.infrastructure;

import com.garmin.fit.*;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.weight2fit.domain.FitFields;

import java.io.InputStream;
import java.util.*;

/**
 * @author Andriy Kryvtsun
 */
public class WeightScaleInputStreamMatcher extends TypeSafeMatcher<InputStream> {

    private static final Map<Integer, FitFields> mapping = new HashMap();

    // see static initializer in com.garmin.fit.WeightScaleMesg class
    static {
        mapping.put(Fit.FIELD_NUM_TIMESTAMP, FitFields.TIMESTAMP);
        mapping.put(0, FitFields.WEIGHT);
        mapping.put(1, FitFields.BODY_FAT);
        mapping.put(10, FitFields.METABOLIC_AGE);
    }

    private final Set<FitFields> fields;

    public static Matcher hasFields(FitFields... fields) {
        return new WeightScaleInputStreamMatcher(fields);
    }

    private WeightScaleInputStreamMatcher(FitFields... fields) {
        this.fields = new HashSet(Arrays.asList(fields));
    }

    @Override
    protected boolean matchesSafely(InputStream item) {

        WeightScaleStreamValidator validator = new WeightScaleStreamValidator();

        Decode decode = new Decode();
        decode.addListener(validator);

        while (!decode.read(item));

        return validator.isValid() && fields.isEmpty();
    }

    // all specific fields must be present and
    // there must not be another fields
    private boolean isFieldAcceptable(int num) {
        FitFields field = mapping.get(num);
        return field != null && fields.remove(field);
    }

    @Override
    public void describeTo(Description description) {
        description
                .appendText("valid Weight Message format with ")
                .appendValueList(" ", ", ", " ", fields)
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
                if (!isFieldAcceptable(field.getNum()))
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
