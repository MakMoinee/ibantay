package com.thesis.dogharness;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.thesis.dogharness.databinding.ActivityDashboardBinding;
import com.thesis.dogharness.interfaces.IBantayEventListener;

public class DashboardActivity extends AppCompatActivity implements IBantayEventListener {

    ActivityDashboardBinding binding;
    NavController navController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        navController = Navigation.findNavController(DashboardActivity.this, R.id.fragment);
        NavigationUI.setupWithNavController(binding.bottomNav, navController);
    }

    @Override
    public void onClickHome() {

    }

    @Override
    public void onClickSettings() {

    }
}
