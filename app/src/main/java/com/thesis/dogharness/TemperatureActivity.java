package com.thesis.dogharness;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.thesis.dogharness.adapters.TemperatureAdapter;
import com.thesis.dogharness.databinding.ActivityTemperatureBinding;
import com.thesis.dogharness.repository.LocalRealDB;

public class TemperatureActivity extends AppCompatActivity {

    ActivityTemperatureBinding binding;
    TemperatureAdapter adapter;
    LocalRealDB db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTemperatureBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
