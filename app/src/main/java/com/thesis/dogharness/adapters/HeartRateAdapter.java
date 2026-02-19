package com.thesis.dogharness.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thesis.dogharness.R;
import com.thesis.dogharness.models.SensorReadings;

import java.util.List;

public class HeartRateAdapter extends RecyclerView.Adapter<HeartRateAdapter.ViewHolder> {

    Context mContext;
    List<SensorReadings> sensorReadingsList;

    public HeartRateAdapter(Context mContext, List<SensorReadings> sensorReadingsList) {
        this.mContext = mContext;
        this.sensorReadingsList = sensorReadingsList;
    }

    @NonNull
    @Override
    public HeartRateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.item_heart_rate, parent, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull HeartRateAdapter.ViewHolder holder, int position) {
        SensorReadings readings = sensorReadingsList.get(position);
        holder.txtBPM.setText(String.format("%d", readings.getHeart_rate()));
        holder.txtPulse.setText(String.format("%d", readings.getSpo2()));
    }

    @Override
    public int getItemCount() {
        return sensorReadingsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtBPM;
        TextView txtPulse;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtBPM = itemView.findViewById(R.id.txtBPM);
            txtPulse = itemView.findViewById(R.id.txtPulse);
        }
    }
}
