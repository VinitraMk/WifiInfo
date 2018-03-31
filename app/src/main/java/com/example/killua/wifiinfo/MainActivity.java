package com.example.killua.wifiinfo;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    String[] PERMISSIONS = new String[]{Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};
    int PERMISSION_ALL = 1;
    private WifiManager mainWifi;
    private WifiReceiver receiverWifi;
    WifiAdapter adapter;
    ArrayList<Wifi> wifiList ;
    List<ScanResult> resList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        wifiList = new ArrayList();
        mainWifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        receiverWifi = new WifiReceiver();
        registerReceiver(receiverWifi, new IntentFilter(
                WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.scan, menu);
        return true;
    }



    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.scan_button) {
            //Toast.makeText(this, "Scan", Toast.LENGTH_SHORT);
            Log.v("button","scan");

            if(!hasPermissions(this, PERMISSIONS)){
                ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
            }
            if(hasPermissions(this,PERMISSIONS))
                proceedAfterPermission();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==PERMISSION_ALL) {
            int c=0;
            for(int i=0;i<grantResults.length;i++) {
                if(grantResults[i]==PackageManager.PERMISSION_GRANTED)
                    c++;
            }
            Log.v("grant",""+c);
            if(c==5)
                proceedAfterPermission();
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                Log.v("permis",permission);
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    private void setAdapter() {
        if(wifiList.size()>0)
            wifiList.clear();
        for(ScanResult obj:resList) {
            Log.v("obj", ""+obj.level);
            wifiList.add(new Wifi(obj.SSID, obj.BSSID,obj.level));
        }

        adapter = new WifiAdapter(this,wifiList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void scanWifiList() {
        Log.v("scanstart","yes");
        mainWifi.startScan();
        resList = mainWifi.getScanResults();
        Log.v("wifilist",""+resList.size());
        setAdapter();
    }



    private void proceedAfterPermission() {
        //Toast.makeText(getBaseContext(), "We got All Permissions", Toast.LENGTH_LONG).show();
        final LocationManager manager = (LocationManager)getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        Log.v("enab",""+manager.isProviderEnabled(LocationManager.GPS_PROVIDER));
        if(!manager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            Toast.makeText(this,"GPS is disabled. Please turn it on",Toast.LENGTH_SHORT).show();
        else
            scanWifiList();
    }

    class WifiReceiver extends BroadcastReceiver {
        public void onReceive(Context c, Intent intent) {
        }
    }
}
