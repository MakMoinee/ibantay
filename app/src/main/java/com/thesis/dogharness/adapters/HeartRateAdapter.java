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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
        holder.txtTemp.setText(String.format("%.2f", readings.getTemperature()));
        Integer raw = readings.getTimestamp();
        if (raw == null || raw == 0) {
            holder.txtDate.setText("—");
        } else {
            // Backend timestamp is in seconds; Date expects milliseconds
            long ts = raw.longValue() * 1000;
            holder.txtDate.setText(getDateFromTimestamp(ts, ""));
        }
    }

    @Override
    public int getItemCount() {
        return sensorReadingsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtBPM;
        TextView txtPulse;
        TextView txtTemp;
        TextView txtDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtBPM = itemView.findViewById(R.id.txtBPM);
            txtPulse = itemView.findViewById(R.id.txtPulse);
            txtTemp = itemView.findViewById(R.id.txtTemp);
            txtDate = itemView.findViewById(R.id.txtDate);
        }
    }

    private static String getDateFromTimestamp(long timestamp, String pattern) {

        if (pattern == null || pattern.isEmpty()) {
            pattern = "yyyy-MM-dd hh:mm a";
        }

        Date date = new Date(timestamp);

        SimpleDateFormat dateTimeFormat = new SimpleDateFormat(pattern, Locale.getDefault());

        return dateTimeFormat.format(date);
    }

}
