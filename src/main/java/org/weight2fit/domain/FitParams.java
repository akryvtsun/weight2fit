package org.weight2fit.domain;

import java.util.Date;

/**
 * Created by englishman on 4/15/15.
 */
public class FitParams {

    private Date timestamp;
    private double weight;
    private double bodyFat;
    private double bodyWater;
    private int visceralFat;
    private double muscleMass;
    private int physiqueRating;
    private double boneMass;
    private int dailyCalorieIntake;
    private int metabolicAge;

    public Date getTimestamp() {
        return timestamp;
    }

    public double getWeight() {
        return weight;
    }

    public double getBodyFat() {
        return bodyFat;
    }

    public double getBodyWater() {
        return bodyWater;
    }

    public int getVisceralFat() {
        return visceralFat;
    }

    public double getMuscleMass() {
        return muscleMass;
    }

    public int getPhysiqueRating() {
        return physiqueRating;
    }

    public double getBoneMass() {
        return boneMass;
    }

    public int getDCI() {
        return dailyCalorieIntake;
    }

    public int getMetabolicAge() {
        return metabolicAge;
    }

    public static class Builder {

        private Date timestamp;
        private double weight;
        private double bodyFat;
        private double bodyWater;
        private int visceralFat;
        private double muscleMass;
        private int physiqueRating;
        private double boneMass;
        private int dailyCalorieIntake;
        private int metabolicAge;

        public Builder withTimestamp(Date timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder withWeight(double weight) {
            this.weight = weight;
            return this;
        }

        public Builder withBodyFat(double bodyFat) {
            this.bodyFat = bodyFat;
            return this;
        }

        public Builder withBodyWater(double bodyWater) {
            this.bodyWater = bodyWater;
            return this;
        }

        public Builder withVisceralFat(int visceralFat) {
            this.visceralFat = visceralFat;
            return this;
        }

        public Builder withMuscleMass(double muscleMass) {
            this.muscleMass = muscleMass;
            return this;
        }

        public Builder withPhysiqueRating(int physiqueRating) {
            this.physiqueRating = physiqueRating;
            return this;
        }

        public Builder withBoneMass(double boneMass) {
            this.boneMass = boneMass;
            return this;
        }

        public Builder withDCI(int dailyCalorieIntake) {
            this.dailyCalorieIntake = dailyCalorieIntake;
            return this;
        }

        public Builder withMetabolicAge(int metabolicAge) {
            this.metabolicAge = metabolicAge;
            return this;
        }

        public FitParams build() {
            FitParams params = new FitParams();
            params.timestamp = timestamp;
            params.weight = weight;
            params.bodyFat = bodyFat;
            params.bodyWater = bodyWater;
            params.visceralFat = visceralFat;
            params.muscleMass = muscleMass;
            params.physiqueRating = physiqueRating;
            params.boneMass = boneMass;
            params.dailyCalorieIntake = dailyCalorieIntake;
            params.metabolicAge = metabolicAge;
            return params;
        }
    }
}
