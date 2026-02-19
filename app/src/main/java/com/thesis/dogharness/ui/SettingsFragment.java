package com.thesis.dogharness.ui;

import android.content.Intent;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.github.MakMoinee.library.preference.CustomPref;
import com.thesis.dogharness.R;
import com.thesis.dogharness.adapters.SettingsAdapter;
import com.thesis.dogharness.databinding.DialogSetHeartRateBinding;
import com.thesis.dogharness.databinding.DialogSetNotifBinding;
import com.thesis.dogharness.databinding.DialogSetTemperatureBinding;
import com.thesis.dogharness.databinding.FragmentSettingsBinding;
import com.thesis.dogharness.interfaces.IBantayEventListener;
import com.thesis.dogharness.interfaces.SettingsEventListener;
import com.thesis.dogharness.models.LocalSettings;
import com.thesis.dogharness.service.HeartRateMonitorService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingsFragment extends Fragment {

    FragmentSettingsBinding binding;
    IBantayEventListener listener;
    List<LocalSettings> settingsList = new ArrayList<>();
    SettingsAdapter adapter;
    DialogSetHeartRateBinding dialogSetHeartRateBinding;
    AlertDialog alertDialogHeartRate, alertDialogTemp, alertDialogNotif;

    DialogSetTemperatureBinding dialogSetTemperatureBinding;
    DialogSetNotifBinding dialogSetNotifBinding;


    boolean isTurnOn = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(LayoutInflater.from(requireContext()), container, false);
        createSettings();
        return binding.getRoot();
    }

    private void createSettings() {
        settingsList = new ArrayList<>();
        LocalSettings settings = new LocalSettings.LocalSettingsBuilder()
                .setSettingName("Heart Rate")
                .setImageResource(R.drawable.ic_heart)
                .build();
        settingsList.add(settings);

        settings = new LocalSettings.LocalSettingsBuilder()
                .setSettingName("Temperature")
                .setImageResource(R.drawable.ic_temp)
                .build();
        settingsList.add(settings);

        settings = new LocalSettings.LocalSettingsBuilder()
                .setSettingName("Notifications")
                .setImageResource(R.drawable.ic_notifications)
                .build();
        settingsList.add(settings);


        adapter = new SettingsAdapter(requireContext(), settingsList, new SettingsEventListener() {
            @Override
            public void clickOption(String option) {
                switch (option) {
                    case "Heart Rate":
                        showDialogHeartRate();
                        break;
                    case "Temperature":
                        showDialogTemp();
                        break;

                    case "Notifications":
                        showDialogNotif();
                        break;
                }
            }
        });
        binding.recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recycler.setAdapter(adapter);
    }

    private void showDialogNotif() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(requireContext());
        dialogSetNotifBinding = DialogSetNotifBinding.inflate(LayoutInflater.from(requireContext()), null, false);
        int isTurnOnNotif = new CustomPref(requireContext()).getIntItem("isTurnOn");
        isTurnOn = (isTurnOnNotif > 0);
        if (isTurnOnNotif > 0) {
            dialogSetNotifBinding.btnTurnOn.setChecked(true);
        } else {
            dialogSetNotifBinding.btnTurnOff.setChecked(true);
        }
        dialogSetNotifBinding.radio.setOnCheckedChangeListener((radioGroup, i) -> {

            if (i == R.id.btnTurnOn) {
                isTurnOn = true;
            } else {
                isTurnOn = false;
            }

        });

        dialogSetNotifBinding.btnSave.setOnClickListener(view -> {
            Map<String, Object> mMap = new HashMap<>();
            mMap.put("isTurnOn", isTurnOn ? 1 : 0);
            new CustomPref(requireContext()).storeData(mMap);
            if (isTurnOn) {
                Intent serviceIntent = new Intent(requireContext(), HeartRateMonitorService.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    requireContext().startForegroundService(serviceIntent);
                } else {
                    requireContext().startService(serviceIntent);
                }
            } else {
                requireContext().stopService(new Intent(requireContext(), HeartRateMonitorService.class));
            }
            Toast.makeText(requireContext(), "Successfully Set Notification", Toast.LENGTH_SHORT).show();
            alertDialogNotif.dismiss();
        });

        dialogSetNotifBinding.btnCancel.setOnClickListener(view -> alertDialogNotif.dismiss());

        mBuilder.setView(dialogSetNotifBinding.getRoot());
        mBuilder.setCancelable(false);
        alertDialogNotif = mBuilder.create();
        alertDialogNotif.show();
    }

    private void showDialogTemp() {
        Float temperature;
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(requireContext());
        dialogSetTemperatureBinding = DialogSetTemperatureBinding.inflate(LayoutInflater.from(requireContext()), null, false);

        if (new CustomPref(requireContext()).getFloatItem("temperature") != null) {
            temperature = new CustomPref(requireContext()).getFloatItem("temperature");
            dialogSetTemperatureBinding.editTemperature.setText(String.format("%.2f", temperature));
        }
        dialogSetTemperatureBinding.btnSave.setOnClickListener(view -> {
            String tempStr = dialogSetTemperatureBinding.editTemperature.getText().toString().trim();
            if (tempStr.isEmpty()) {
                Toast.makeText(requireContext(), "Please Don't Leave Temperature Field Empty", Toast.LENGTH_SHORT).show();
            } else {
                Map<String, Object> mMap = new HashMap<>();
                mMap.put("temperature", Float.parseFloat(tempStr));
                new CustomPref(requireContext()).storeData(mMap);
                Toast.makeText(requireContext(), "Successfully Set Temperature", Toast.LENGTH_SHORT).show();
                alertDialogTemp.dismiss();
            }
        });
        dialogSetTemperatureBinding.btnCancel.setOnClickListener(view -> {
            dialogSetTemperatureBinding.editTemperature.setText("");
            alertDialogTemp.dismiss();
        });
        mBuilder.setView(dialogSetTemperatureBinding.getRoot());
        mBuilder.setCancelable(false);
        alertDialogTemp = mBuilder.create();
        alertDialogTemp.show();
    }

    private void showDialogHeartRate() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(requireContext());
        dialogSetHeartRateBinding = DialogSetHeartRateBinding.inflate(LayoutInflater.from(requireContext()), null, false);
        int heart_rate = new CustomPref(requireContext()).getIntItem("heart_rate");
        if (heart_rate != 0) {
            dialogSetHeartRateBinding.editHearRate.setText(Integer.toString(heart_rate));
        }
        dialogSetHeartRateBinding.btnSave.setOnClickListener(view -> {
            String heartRateStr = dialogSetHeartRateBinding.editHearRate.getText().toString().trim();
            if (heartRateStr.isEmpty()) {
                Toast.makeText(requireContext(), "Please Don't Leave Heart Rate Field Empty", Toast.LENGTH_SHORT).show();
            } else {
                Map<String, Object> mMap = new HashMap<>();
                mMap.put("heart_rate", Integer.parseInt(heartRateStr));
                new CustomPref(requireContext()).storeData(mMap);
                Toast.makeText(requireContext(), "Successfully Set Heart Rate", Toast.LENGTH_SHORT).show();
                alertDialogHeartRate.dismiss();
            }
        });

        dialogSetHeartRateBinding.btnCancel.setOnClickListener(view -> {
            dialogSetHeartRateBinding.editHearRate.setText("");
            alertDialogHeartRate.dismiss();
        });

        mBuilder.setView(dialogSetHeartRateBinding.getRoot());
        mBuilder.setCancelable(false);
        alertDialogHeartRate = mBuilder.create();
        alertDialogHeartRate.show();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof IBantayEventListener) {
            listener = (IBantayEventListener) context;
        }
    }
}
