package org.weight2fit.infra;

import com.garmin.fit.Decode;
import com.garmin.fit.Mesg;
import com.garmin.fit.MesgListener;
import com.garmin.fit.MesgNum;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.io.InputStream;

/**
 * @author Andriy Kryvtsun
 */
public class WeightScaleInputStreamMatcher extends TypeSafeMatcher<InputStream> {
    @Override
    protected boolean matchesSafely(InputStream item) {

        WeightScaleStreamValidator validator = new WeightScaleStreamValidator();

        Decode decode = new Decode();
        decode.addListener(validator);

        while (!decode.read(item));

        return validator.isValid();
    }

    @Override
    public void describeTo(Description description) {
        // TODO add string description for the matcher
    }

    /**
     * @see 'D00001309 FIT File Types Description Rev 1.6.pdf', chapter '7 Weight File'
     */
    private static class WeightScaleStreamValidator implements MesgListener {
        private int fieldIdCount = 0;
        private int weightScaleCount = 0;
        private boolean areOnlyAllowedMesgNums = true;

        @Override
        public void onMesg(Mesg mesg) {
            switch (mesg.getNum()) {
                case MesgNum.FILE_ID:
                    fieldIdCount++;
                    break;
                case MesgNum.USER_PROFILE: break;
                case MesgNum.WEIGHT_SCALE:
                    weightScaleCount++;
                    break;
                case MesgNum.DEVICE_INFO: break;
                default:
                    areOnlyAllowedMesgNums = false;
            }
        }

        boolean isValid() {
            return fieldIdCount == 1 &&
                   weightScaleCount >= 1 &&
                   areOnlyAllowedMesgNums;
        }
    }
}
