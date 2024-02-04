package com.example.miniprojet1;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import android.Manifest;

import com.example.miniprojet1.Models.FetchData;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

public class MainActivity extends AppCompatActivity {

    Fragment fragment;
    private LocationManager locationManager;
    private  int PERMISSION_CODE=1;
    private String ville;
    Button prayerTimeB,duaB,qiblaB,mousqueB;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WindowManager windowManager = (WindowManager) getBaseContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();

        prayerTimeB=findViewById(R.id.button);
        duaB=findViewById(R.id.button2);
        qiblaB=findViewById(R.id.button3);
        mousqueB=findViewById(R.id.button4);

        this.btnd(null);




    }
    @Override
    protected void onResume() {
        super.onResume();
        if(Configuration.ORIENTATION_LANDSCAPE == getResources().getConfiguration().orientation)
        {
            Log.d("SCR_ROT", "ORIENTATION_LANDSCAPE");
        }
        else if (Configuration.ORIENTATION_PORTRAIT == getResources().getConfiguration().orientation)
        {
            Log.d("SCR_ROT", "ORIENTATION_PORTRAIT");
        }



        // Get Location Info

        locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},PERMISSION_CODE);
        }
        Location location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        // Pour mettre par defaut le  nom de la region a travers Longitude et Latitude recuperer de LocationManager
        this.ville= getCityName(location.getLongitude(),location.getLatitude());




        SharedPreferences preferences = getSharedPreferences("WidgetPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("cityName", this.ville);
        editor.putString("longitude", String.valueOf((Double) location.getLongitude()));
        editor.putString("latitude", String.valueOf((Double) location.getLatitude()));
        editor.apply();
    }






    // Methode Pour recuperer le nom de la ville selon longitude et latitude
    private String getCityName(double longitude,double latitude){
        String cityName="Not Found";

        Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
        try {
            List<Address> addresses=gcd.getFromLocation(latitude,longitude,10);
            for (Address adr : addresses){
                if(adr != null){
                    String city= adr.getLocality();
                    if(city != null && !city.equals("")){
                        cityName=city;
                    }
                    else{
                        Log.d("TAG","City not found");
                        //Toast.makeText(this, "City not Found", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        this.ville=cityName;
        return cityName;

    }


    public void btnp(View view) {
        //fragment = prayer_time.newInstance(this.ville);
        Log.d("WS COMMIT","City name: "+this.ville);
        fragment = prayer_time.newInstance(this.ville);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();

        ft.replace(R.id.fragment,fragment);

        ft.commit();
        prayerTimeB.setBackgroundResource(R.drawable.rounded_actif_button);
        duaB.setBackgroundResource(R.drawable.rounded_button);
        qiblaB.setBackgroundResource(R.drawable.rounded_button);




    }

    public void btnd(View view) {
      // poour naviger vers une fragment
        fragment=new dua();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.fragment,fragment);
        ft.commit(); //poour activer lopération
        //*******

        duaB.setBackgroundResource(R.drawable.rounded_actif_button);
        prayerTimeB.setBackgroundResource(R.drawable.rounded_button);
        qiblaB.setBackgroundResource(R.drawable.rounded_button);

    }

    public void btndQuibla(View view) {

        fragment = new Qibla();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();

        ft.replace(R.id.fragment,fragment);

        ft.commit();
        qiblaB.setBackgroundResource(R.drawable.rounded_actif_button);
        duaB.setBackgroundResource(R.drawable.rounded_button);
        prayerTimeB.setBackgroundResource(R.drawable.rounded_button);

    }


    public void btndMousque(View view) {


        fragment = new MapFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();

        ft.replace(R.id.fragment,fragment);

        ft.commit();
        mousqueB.setBackgroundResource(R.drawable.rounded_actif_button);
        qiblaB.setBackgroundResource(R.drawable.rounded_button);
        duaB.setBackgroundResource(R.drawable.rounded_button);
        prayerTimeB.setBackgroundResource(R.drawable.rounded_button);
    }
}