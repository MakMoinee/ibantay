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
        homeList.add("Heart Rate");
        homeList.add("Temperature");

        adapter = new HomeAdapter(requireContext(), homeList, new HomeEventListener() {
            @Override
            public void onClickListener() {
            }

            @Override
            public void onClickMenu(String option) {
                openMenuIntent(option);
            }
        });
        binding.myRecycler.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.myRecycler.setAdapter(adapter);
    }

    private void openMenuIntent(String option) {

    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof IBantayEventListener) {
            listener = (IBantayEventListener) context;
        }
    }
}
