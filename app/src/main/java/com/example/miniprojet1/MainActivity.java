package com.example.miniprojet1;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.app.Fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import android.Manifest;
import android.widget.Spinner;

import com.example.miniprojet1.Models.FetchData;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

public class MainActivity extends AppCompatActivity {

    Fragment fragment;
    private LocationManager locationManager;
    private  int PERMISSION_CODE=1;
    private String ville;
    Button prayerTimeB,duaB,qiblaB,mousqueB,tasbihB,abulationB;

    private GoogleMap mMap;

    Spinner spinner;

    public static  final String[] languagesEn={"Select Language","English","Arabic"};
    public static  final String[] languagesAr={"اختار اللغة","إنجليزية","عربية"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WindowManager windowManager = (WindowManager) getBaseContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();

        spinner=findViewById(R.id.spinner);

        prayerTimeB=findViewById(R.id.button);
        duaB=findViewById(R.id.button2);
        qiblaB=findViewById(R.id.button3);
        mousqueB=findViewById(R.id.button4);
        tasbihB=findViewById(R.id.button5);
        abulationB=findViewById(R.id.button6);
        this.btnd(null);

    }



    public void setLocal(Activity activity,String langCode){
        Locale locale=new Locale(langCode);
        locale.setDefault(locale);
        Resources resources=activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config,resources.getDisplayMetrics());
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

        // Adapter pour le Spinner
        ArrayAdapter<String> adapter;
        if (getResources().getConfiguration().locale.getLanguage().equals("fr")) {
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, languagesEn);
        } else {
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, languagesAr);
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLang = parent.getItemAtPosition(position).toString();

                if (selectedLang.equals("English") || selectedLang.equals("إنجليزية")) {
                    setLocal(MainActivity.this, "fr");
                    finish();
                    startActivity(getIntent());

                } else if (selectedLang.equals("Arabic") || selectedLang.equals("عربية")) {
                    setLocal(MainActivity.this, "ar");
                    finish();
                    startActivity(getIntent());

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
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

        mousqueB.setBackgroundResource(R.drawable.rounded_button);
        abulationB.setBackgroundResource(R.drawable.rounded_button);




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
        mousqueB.setBackgroundResource(R.drawable.rounded_button);
        tasbihB.setBackgroundResource(R.drawable.rounded_button);
        abulationB.setBackgroundResource(R.drawable.rounded_button);


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
        mousqueB.setBackgroundResource(R.drawable.rounded_button);
        tasbihB.setBackgroundResource(R.drawable.rounded_button);
        abulationB.setBackgroundResource(R.drawable.rounded_button);



    }


    public void btndMousque(View view) {

        if (isPackageInstalled("com.google.android.apps.maps", getPackageManager())) {
            fragment = new GoToMaps();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            ft.replace(R.id.fragment, fragment);

            ft.commit();
            mousqueB.setBackgroundResource(R.drawable.rounded_actif_button);
            qiblaB.setBackgroundResource(R.drawable.rounded_button);
            duaB.setBackgroundResource(R.drawable.rounded_button);
            prayerTimeB.setBackgroundResource(R.drawable.rounded_button);
            tasbihB.setBackgroundResource(R.drawable.rounded_button);
            abulationB.setBackgroundResource(R.drawable.rounded_button);

        }
        else {
            androidx.fragment.app.Fragment fragment = new MapsFragment();
            androidx.fragment.app.FragmentManager fm = getSupportFragmentManager(); // Use getSupportFragmentManager() instead of getFragmentManager()
            androidx.fragment.app. FragmentTransaction ft = fm.beginTransaction();

            ft.replace(R.id.fragment,fragment);

            ft.commit();
            mousqueB.setBackgroundResource(R.drawable.rounded_actif_button);
            qiblaB.setBackgroundResource(R.drawable.rounded_button);
            duaB.setBackgroundResource(R.drawable.rounded_button);
            prayerTimeB.setBackgroundResource(R.drawable.rounded_button);
            tasbihB.setBackgroundResource(R.drawable.rounded_button);
            abulationB.setBackgroundResource(R.drawable.rounded_button);

        }


    }
    private boolean isPackageInstalled(String packageName, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public void btndTasbih(View view) {

        fragment = new tasbih_fragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();

        ft.replace(R.id.fragment,fragment);

        ft.commit();


        tasbihB.setBackgroundResource(R.drawable.rounded_actif_button);
        qiblaB.setBackgroundResource(R.drawable.rounded_button);
        duaB.setBackgroundResource(R.drawable.rounded_button);
        prayerTimeB.setBackgroundResource(R.drawable.rounded_button);
        mousqueB.setBackgroundResource(R.drawable.rounded_button);
        abulationB.setBackgroundResource(R.drawable.rounded_button);

    }

    public void btnabulation(View view) {
        fragment = new blution_for_prayer();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();

        ft.replace(R.id.fragment,fragment);

        ft.commit();

        abulationB.setBackgroundResource(R.drawable.rounded_actif_button);

        tasbihB.setBackgroundResource(R.drawable.rounded_button);
        qiblaB.setBackgroundResource(R.drawable.rounded_button);
        duaB.setBackgroundResource(R.drawable.rounded_button);
        prayerTimeB.setBackgroundResource(R.drawable.rounded_button);
        mousqueB.setBackgroundResource(R.drawable.rounded_button);
    }
}