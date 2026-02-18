package com.thesis.dogharness.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.thesis.dogharness.databinding.FragmentSettingsBinding;
import com.thesis.dogharness.interfaces.IBantayEventListener;

public class SettingsFragment extends Fragment {

    FragmentSettingsBinding binding;
    IBantayEventListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(LayoutInflater.from(requireContext()), container, false);
        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof IBantayEventListener){
            listener = (IBantayEventListener) context;
        }
    }
}
