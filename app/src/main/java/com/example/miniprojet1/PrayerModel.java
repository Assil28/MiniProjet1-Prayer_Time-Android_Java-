package com.example.miniprojet1;

public class PrayerModel {
    private String prayer;
    private String time;


    public PrayerModel(String prayer, String time) {
        this.prayer = prayer;
        this.time = time;
    }

    public String getPrayer() {
        return prayer;
    }

    public void setPrayer(String prayer) {
        this.prayer = prayer;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
