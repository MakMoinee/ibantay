package com.thesis.dogharness;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.thesis.dogharness.databinding.ActivityHeartRateBinding;
import com.thesis.dogharness.models.SensorReadings;
import com.thesis.dogharness.repository.LocalRealDB;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class HeartRateActivity extends AppCompatActivity {

    ActivityHeartRateBinding binding;
    LocalRealDB db;
    List<SensorReadings> sensorReadingsList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHeartRateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = new LocalRealDB();
        loadData();
    }

    private void loadData() {
        db.getAllData(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                updateData(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updateData(DataSnapshot snapshot) {
        if (snapshot.exists()) {
            GenericTypeIndicator<Map<String, SensorReadings>> type =
                    new GenericTypeIndicator<>() {
                    };
            Map<String, SensorReadings> sensorData = snapshot.getValue(type);
            sensorReadingsList = new ArrayList<>(sensorData != null ? sensorData.values() : Collections.emptyList());
            sensorReadingsList.sort((a, b) -> Integer.compare(
                    b.getTimestamp() != null ? b.getTimestamp() : 0,
                    a.getTimestamp() != null ? a.getTimestamp() : 0
            ));
        }
    }

}
