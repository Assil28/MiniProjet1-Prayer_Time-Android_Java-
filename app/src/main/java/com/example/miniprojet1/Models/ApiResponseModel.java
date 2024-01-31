package com.example.miniprojet1.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ApiResponseModel {
    public int code;
    public String status;
    public ArrayList<Datum> data;

public class Date{
    public String readable;
    public String timestamp;
    public Gregorian gregorian;
    public Hijri hijri;
}

public class Datum{
    public Timings timings;
    public Date date;
    public Meta meta;
}

public class Designation{
    public String abbreviated;
    public String expanded;
}

public class Gregorian{
    public String date;
    public String format;
    public String day;
    public Weekday weekday;
    public Month month;
    public String year;
    public Designation designation;
}

public class Hijri{
    public String date;
    public String format;
    public String day;
    public Weekday weekday;
    public Month month;
    public String year;
    public Designation designation;
    public ArrayList<Object> holidays;
}

public class Location{
    public double latitude;
    public double longitude;
}

public class Meta{
    public double latitude;
    public double longitude;
    public String timezone;
    public Method method;
    public String latitudeAdjustmentMethod;
    public String midnightMode;
    public String school;
    public Offset offset;
}

public class Method{
    public int id;
    public String name;
    public Params params;
    public Location location;
}

public class Month{
    public int number;
    public String en;
    public String ar;
}

public class Offset{
    @SerializedName("Imsak")
    public int imsak;
    @SerializedName("Fajr")
    public int fajr;
    @SerializedName("Sunrise")
    public int sunrise;
    @SerializedName("Dhuhr")
    public int dhuhr;
    @SerializedName("Asr")
    public int asr;
    @SerializedName("Maghrib")
    public int maghrib;
    @SerializedName("Sunset")
    public int sunset;
    @SerializedName("Isha")
    public int isha;
    @SerializedName("Midnight")
    public int midnight;
}

public class Params{
    @SerializedName("Fajr")
    public int fajr;
    @SerializedName("Isha")
    public int isha;
}



public class Timings{
    @SerializedName("Fajr")
    public String fajr;
    @SerializedName("Sunrise")
    public String sunrise;
    @SerializedName("Dhuhr")
    public String dhuhr;
    @SerializedName("Asr")
    public String asr;
    @SerializedName("Sunset")
    public String sunset;
    @SerializedName("Maghrib")
    public String maghrib;
    @SerializedName("Isha")
    public String isha;
    @SerializedName("Imsak")
    public String imsak;
    @SerializedName("Midnight")
    public String midnight;
    @SerializedName("Firstthird")
    public String firstthird;
    @SerializedName("Lastthird")
    public String lastthird;
}

public class Weekday{
    public String en;
    public String ar;
}

}
