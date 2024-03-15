package com.example.miniprojet1.Models;

public class PrayerDetails {
    private String prayerName;
    private int numberOfRakaa;
    private String voice;

    public PrayerDetails(String prayerName, int numberOfRakaa, String voice) {
        this.prayerName = prayerName;
        this.numberOfRakaa = numberOfRakaa;
        this.voice = voice;
    }

    // Getters et setters (optionnels)
    public String getPrayerName() {
        return prayerName;
    }

    public void setPrayerName(String prayerName) {
        this.prayerName = prayerName;
    }

    public int getNumberOfRakaa() {
        return numberOfRakaa;
    }

    public void setNumberOfRakaa(int numberOfRakaa) {
        this.numberOfRakaa = numberOfRakaa;
    }

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }
}