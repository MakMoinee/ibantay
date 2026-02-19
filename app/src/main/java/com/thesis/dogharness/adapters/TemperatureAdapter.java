package com.thesis.dogharness.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thesis.dogharness.R;
import com.thesis.dogharness.models.SensorReadings;

import java.util.List;

public class TemperatureAdapter extends RecyclerView.Adapter<TemperatureAdapter.ViewHolder> {

    Context mContext;
    List<SensorReadings> sensorReadingsList;

    public TemperatureAdapter(Context mContext, List<SensorReadings> sensorReadingsList) {
        this.mContext = mContext;
        this.sensorReadingsList = sensorReadingsList;
    }

    @NonNull
    @Override
    public TemperatureAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.item_temperature, parent, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull TemperatureAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return sensorReadingsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
