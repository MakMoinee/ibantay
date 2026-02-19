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
import com.thesis.dogharness.interfaces.HomeEventListener;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    Context mContext;
    List<String> menuList;

    HomeEventListener listener;

    public HomeAdapter(Context mContext, List<String> menuList, HomeEventListener listener) {
        this.mContext = mContext;
        this.menuList = menuList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.item_home, parent, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {
        String menuRawText = menuList.get(position);
        switch (menuRawText) {
            case "Monitoring":
                holder.imgMenu.setImageResource(R.drawable.ic_monitor);
                break;
            case "Notifications":
                holder.imgMenu.setImageResource(R.drawable.ic_notif_dark);
                break;

            case "Locate":
                holder.imgMenu.setImageResource(R.drawable.ic_location);
                break;
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickMenu(menuRawText);
            }
        });
        holder.menuText.setText(menuRawText);
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView menuText;
        ImageView imgMenu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            menuText = itemView.findViewById(R.id.txtMenu);
            imgMenu = itemView.findViewById(R.id.imgMenu);
        }
    }
}
