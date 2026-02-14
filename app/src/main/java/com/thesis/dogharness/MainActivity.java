package com.thesis.dogharness;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.MakMoinee.library.dialogs.MyDialog;
import com.github.MakMoinee.library.preference.CustomPref;
import com.thesis.dogharness.databinding.ActivityMainBinding;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    MyDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        pDialog = new MyDialog(MainActivity.this);
        int isStart = new CustomPref(MainActivity.this).getIntItem("isStart");
        if (isStart == 1) {
            Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        }
        setListeners();
    }

    private void setListeners() {
        binding.btnStart.setOnClickListener(v -> {
            Map<String, Object> myMap = new HashMap<>();
            myMap.put("isStart", 1);
            new CustomPref(MainActivity.this).storeData(myMap);
            Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        });
    }
}