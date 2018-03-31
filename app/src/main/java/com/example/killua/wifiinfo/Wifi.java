package com.example.killua.wifiinfo;

/**
 * Created by killua on 31/3/18.
 */

public class Wifi {
    private String SSID,BSSID;
    private int level;

    public Wifi(String SSID, String BSSID, int level) {
        this.SSID = SSID;
        this.BSSID = BSSID;
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public String getSSID() {
        return SSID;
    }

    public void setSSID(String SSID) {
        this.SSID = SSID;
    }

    public String getBSSID() {
        return BSSID;
    }

    public void setBSSID(String BSSID) {
        this.BSSID = BSSID;
    }
}
