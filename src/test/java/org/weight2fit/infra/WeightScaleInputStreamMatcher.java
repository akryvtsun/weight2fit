package org.weight2fit.infra;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.io.InputStream;

/**
 * @author Andriy Kryvtsun
 */
public class WeightScaleInputStreamMatcher extends TypeSafeMatcher<InputStream> {
    @Override
    protected boolean matchesSafely(InputStream item) {
        return false;
    }

    @Override
    public void describeTo(Description description) {
        // TODO add string description for the matcher
    }
}
