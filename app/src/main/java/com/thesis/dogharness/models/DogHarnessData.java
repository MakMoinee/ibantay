package com.thesis.dogharness.models;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DogHarnessData {
    private SensorReadings latest_reading;
    private Map<String, SensorReadings> sensor_data;



}
