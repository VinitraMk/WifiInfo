package com.example.killua.wifiinfo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.net.wifi.ScanResult;


import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by killua on 30/3/18.
 */

public class WifiAdapter extends RecyclerView.Adapter<WifiAdapter.MyViewHolder> {

    private Context mContext;
    private List<Wifi> wifiList = new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView ssid,bssid;
        public ImageView level;
        public MyViewHolder(View view) {
            super(view);
            ssid=(TextView)view.findViewById(R.id.ssid);
            bssid=(TextView) view.findViewById(R.id.bssid);
            level=(ImageView)view.findViewById(R.id.level);
        }
    }

    public WifiAdapter(Context context, ArrayList<Wifi> list) {
        this.mContext = context;
        this.wifiList = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.text_element, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Wifi wifi = wifiList.get(position);
        holder.ssid.setText(wifi.getSSID());
        holder.bssid.setText(wifi.getBSSID());
        int temp=wifi.getLevel();
        if(temp<=-90)
            holder.level.setImageResource(R.mipmap.wifi_null);
        else if(temp>-90 && temp<=-70)
            holder.level.setImageResource(R.mipmap.wifi_weak);
        else if(temp>-70 && temp<=-60)
            holder.level.setImageResource(R.mipmap.wifi_fair);
        else if(temp>-60)
            holder.level.setImageResource(R.mipmap.wifi_good);
    }

    @Override
    public int getItemCount() {
        return wifiList.size();
    }

}

