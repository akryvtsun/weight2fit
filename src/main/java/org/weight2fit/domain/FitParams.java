package org.weight2fit.domain;

import java.util.Date;

/**
 * Created by englishman on 4/15/15.
 */
public class FitParams {

    private Date timestamp;
    private double weight;

    public Date getTimestamp() {
        return timestamp;
    }

    public double getWeight() {
        return weight;
    }

    public static class Builder {

        private Date timestamp;
        private double weight;

        public static final Builder create() {
            return new Builder();
        }

        public Builder withTimestamp(Date timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder withWeight(double weight) {
            this.weight = weight;
            return this;
        }

        public FitParams build() {
            FitParams params = new FitParams();
            params.timestamp = timestamp;
            params.weight = weight;
            return params;
        }
    }
}
