package com.thesis.dogharness.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorReadings {
    private Integer heart_rate;
    private String location_source;
    private Boolean location_valid;
    private Integer spo2;
    private Double temperature;
    private Integer timestamp;

    private Double latitude;
    private Double longitude;
    private Double altitude;
    private Integer satellites;

    public SensorReadings(SensorReadingBuilder builder) {
        this.heart_rate = builder.heart_rate;
        this.location_source = builder.location_source;
        this.location_valid = builder.location_valid;
        this.spo2 = builder.spo2;
        this.timestamp = builder.timestamp;
        this.temperature = builder.temperature;
        this.latitude = builder.latitude;
        this.longitude = builder.longitude;
        this.altitude = builder.altitude;
        this.satellites = builder.satellites;
    }

    public static class SensorReadingBuilder {
        private Integer heart_rate;
        private String location_source;
        private Boolean location_valid;
        private Integer spo2;
        private Double temperature;
        private Integer timestamp;

        private Double latitude;
        private Double longitude;
        private Double altitude;
        private Integer satellites;

        public SensorReadingBuilder setHeart_rate(Integer heart_rate) {
            this.heart_rate = heart_rate;
            return this;
        }

        public SensorReadingBuilder setLocation_source(String location_source) {
            this.location_source = location_source;
            return this;
        }

        public SensorReadingBuilder setLocation_valid(Boolean location_valid) {
            this.location_valid = location_valid;
            return this;
        }

        public SensorReadingBuilder setSpo2(Integer spo2) {
            this.spo2 = spo2;
            return this;
        }

        public SensorReadingBuilder setTemperature(Double temperature) {
            this.temperature = temperature;
            return this;
        }

        public SensorReadingBuilder setTimestamp(Integer timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public SensorReadingBuilder setLatitude(Double latitude) {
            this.latitude = latitude;
            return this;
        }

        public SensorReadingBuilder setLongitude(Double longitude) {
            this.longitude = longitude;
            return this;
        }

        public SensorReadingBuilder setAltitude(Double altitude) {
            this.altitude = altitude;
            return this;
        }

        public SensorReadingBuilder setSatellites(Integer satellites) {
            this.satellites = satellites;
            return this;
        }

        public SensorReadings build() {
            return new SensorReadings(this);
        }
    }
}
