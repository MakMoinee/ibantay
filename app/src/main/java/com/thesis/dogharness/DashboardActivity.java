package com.thesis.dogharness;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.github.MakMoinee.library.preference.CustomPref;
import com.thesis.dogharness.databinding.ActivityDashboardBinding;
import com.thesis.dogharness.interfaces.IBantayEventListener;
import com.thesis.dogharness.service.HeartRateMonitorService;

public class DashboardActivity extends AppCompatActivity implements IBantayEventListener {

    ActivityDashboardBinding binding;
    NavController navController;
    private static final int REQUEST_POST_NOTIFICATIONS = 200;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        navController = Navigation.findNavController(DashboardActivity.this, R.id.fragment);
        NavigationUI.setupWithNavController(binding.bottomNav, navController);

        requestNotificationPermissionIfNeeded();
        startHeartRateMonitorIfEnabled();
    }

    private void requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                        REQUEST_POST_NOTIFICATIONS);
            }
        }
    }

    private void startHeartRateMonitorIfEnabled() {
        int isTurnOn = new CustomPref(this).getIntItem("isTurnOn");
        if (isTurnOn > 0) {
            Intent serviceIntent = new Intent(this, HeartRateMonitorService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(serviceIntent);
            } else {
                startService(serviceIntent);
            }
        }
    }

    @Override
    public void onClickHome() {

    }

    @Override
    public void onClickSettings() {

    }
}
