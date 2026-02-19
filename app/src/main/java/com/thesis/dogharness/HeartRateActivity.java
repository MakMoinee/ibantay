package com.thesis.dogharness;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.github.MakMoinee.library.interfaces.DefaultBaseListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.thesis.dogharness.adapters.HeartRateAdapter;
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
    HeartRateAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHeartRateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = new LocalRealDB();
        loadData();
        setListeners();
    }

    private void setListeners() {
        binding.btnClearAll.setOnClickListener(view -> {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(HeartRateActivity.this);
            DialogInterface.OnClickListener dListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (i == DialogInterface.BUTTON_NEGATIVE) {
                        db.deleteSensor(new DefaultBaseListener() {
                            @Override
                            public <T> void onSuccess(T any) {
                                Toast.makeText(HeartRateActivity.this, "Successfully Deleted Data", Toast.LENGTH_SHORT).show();
                                loadData();
                            }

                            @Override
                            public void onError(Error error) {
                                Toast.makeText(HeartRateActivity.this, "Failed To Delete Data, Please Try Again Later", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        dialogInterface.dismiss();
                    }
                }
            };
            mBuilder.setMessage("Are You Sure You Want To Clear All Data?")
                    .setNegativeButton("Yes", dListener)
                    .setPositiveButton("No", dListener)
                    .setCancelable(false)
                    .show();
        });

        binding.refresh.setOnRefreshListener(this::loadData);
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
            binding.refresh.setRefreshing(true);
            GenericTypeIndicator<Map<String, SensorReadings>> type =
                    new GenericTypeIndicator<>() {
                    };
            Map<String, SensorReadings> sensorData = snapshot.getValue(type);
            sensorReadingsList = new ArrayList<>(sensorData != null ? sensorData.values() : Collections.emptyList());
            sensorReadingsList.sort((a, b) -> Integer.compare(
                    b.getTimestamp() != null ? b.getTimestamp() : 0,
                    a.getTimestamp() != null ? a.getTimestamp() : 0
            ));

            if (sensorReadingsList.size() > 0) {
                adapter = new HeartRateAdapter(HeartRateActivity.this, sensorReadingsList);
                binding.recycler.setLayoutManager(new LinearLayoutManager(HeartRateActivity.this));
                binding.recycler.setAdapter(adapter);
            }
        } else {
            sensorReadingsList = new ArrayList<>();
            binding.recycler.setAdapter(null);
        }

        binding.refresh.setRefreshing(false);
    }

}
