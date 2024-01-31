package com.example.miniprojet1.Models;

public class PrayerModel implements Comparable<PrayerModel> {
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

    @Override
    public String toString() {
        return "PrayerModel{" +
                "prayer='" + prayer + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    // Implementing compareTo method for sorting based on prayer time
    @Override
    public int compareTo(PrayerModel other) {
        // Assuming prayer time is in HH:MM format
        String[] thisTimeParts = this.time.split(":");
        String[] otherTimeParts = other.time.split(":");

        int thisHour = Integer.parseInt(thisTimeParts[0]);
        int thisMinute = Integer.parseInt(thisTimeParts[1]);

        int otherHour = Integer.parseInt(otherTimeParts[0]);
        int otherMinute = Integer.parseInt(otherTimeParts[1]);

        // Compare hours first
        if (thisHour != otherHour) {
            return thisHour - otherHour;
        } else {
            // If hours are same, compare minutes
            return thisMinute - otherMinute;
        }
    }
}
