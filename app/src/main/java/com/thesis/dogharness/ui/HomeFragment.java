package com.thesis.dogharness.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.thesis.dogharness.HeartRateActivity;
import com.thesis.dogharness.LocateActivity;
import com.thesis.dogharness.TemperatureActivity;
import com.thesis.dogharness.adapters.HomeAdapter;
import com.thesis.dogharness.databinding.FragmentHomeBinding;
import com.thesis.dogharness.interfaces.HomeEventListener;
import com.thesis.dogharness.interfaces.IBantayEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    IBantayEventListener listener;
    HomeAdapter adapter;
    List<String> homeList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(LayoutInflater.from(requireContext()), container, false);
        createHomeList();
        return binding.getRoot();
    }

    private void createHomeList() {
        homeList = new ArrayList<>();
        homeList.add("Heart Rate");
        homeList.add("Temperature");
        homeList.add("Locate");

        adapter = new HomeAdapter(requireContext(), homeList, new HomeEventListener() {
            @Override
            public void onClickListener() {
            }

            @Override
            public void onClickMenu(String option) {
                openMenuIntent(option);
            }
        });
        binding.myRecycler.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        binding.myRecycler.setAdapter(adapter);
    }

    private void openMenuIntent(String option) {
        Intent intent = null;
        switch (option) {
            case "Heart Rate":
                intent = new Intent(requireContext(), HeartRateActivity.class);
                break;
            case "Temperature":
                intent = new Intent(requireContext(), TemperatureActivity.class);
                break;
            case "Locate":
                intent = new Intent(requireContext(), LocateActivity.class);
                break;
        }

        if (intent != null) {
            requireContext().startActivity(intent);
        }

    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof IBantayEventListener) {
            listener = (IBantayEventListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
