package com.thesis.dogharness.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.thesis.dogharness.R;
import com.thesis.dogharness.adapters.SettingsAdapter;
import com.thesis.dogharness.databinding.FragmentSettingsBinding;
import com.thesis.dogharness.interfaces.IBantayEventListener;
import com.thesis.dogharness.models.LocalSettings;

import java.util.ArrayList;
import java.util.List;

public class SettingsFragment extends Fragment {

    FragmentSettingsBinding binding;
    IBantayEventListener listener;
    List<LocalSettings> settingsList = new ArrayList<>();
    SettingsAdapter adapter;

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


        adapter = new SettingsAdapter(requireContext(), settingsList);
        binding.recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recycler.setAdapter(adapter);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof IBantayEventListener) {
            listener = (IBantayEventListener) context;
        }
    }
}
