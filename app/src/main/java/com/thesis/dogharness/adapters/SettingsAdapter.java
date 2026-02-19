package com.thesis.dogharness.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thesis.dogharness.R;
import com.thesis.dogharness.interfaces.SettingsEventListener;
import com.thesis.dogharness.models.LocalSettings;

import java.util.List;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.ViewHolder> {
    Context mContext;
    List<LocalSettings> settingsList;
    SettingsEventListener listener;

    public SettingsAdapter(Context mContext, List<LocalSettings> settingsList, SettingsEventListener listener) {
        this.mContext = mContext;
        this.settingsList = settingsList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SettingsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.item_settings, parent, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull SettingsAdapter.ViewHolder holder, int position) {
        LocalSettings settings = settingsList.get(position);
        holder.lblSettingName.setText(settings.getSettingName());
        holder.imgLogo.setImageResource(settings.getImageResource());
        holder.txtData.setText("");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.clickOption(settings.getSettingName());
            }
        });
    }



    @Override
    public int getItemCount() {
        return settingsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtData;
        TextView lblSettingName;
        ImageView imgLogo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtData = itemView.findViewById(R.id.txtData);
            lblSettingName = itemView.findViewById(R.id.lblSettingName);
            imgLogo = itemView.findViewById(R.id.imgLogo);
        }
    }
}
