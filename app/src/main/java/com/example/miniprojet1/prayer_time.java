package com.example.miniprojet1;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;

import android.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.miniprojet1.Adapter.PrayerRVAdapter;
import com.example.miniprojet1.Models.ApiResponseModel;
import com.example.miniprojet1.Models.PrayerModel;
import com.google.gson.Gson;


import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class prayer_time extends Fragment {

    private List<PrayerModel> prayerModels;
    private PrayerRVAdapter prayerRVAdapter;

    private LocationManager locationManager;
    private int PERMISSION_CODE = 1;
    private String cityName;


    private TextView location;
    RecyclerView prayerRv;

    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;
    TextView date;

    public prayer_time() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static prayer_time newInstance(String cityName) {
        prayer_time fragment = new prayer_time();
        Bundle args = new Bundle();
        args.putString("CITY_NAME", cityName);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cityName = getArguments().getString("CITY_NAME");
        }


        // Initialize prayerModels
        prayerModels = new ArrayList<>();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prayer_time, container, false);
        prayerRv = view.findViewById(R.id.prayerRV);
        date = view.findViewById(R.id.date);

        location=view.findViewById(R.id.location);
        // Récupérer la valeur de la ville depuis les arguments
        if (getArguments() != null) {
            cityName = getArguments().getString("CITY_NAME");
            location.setText(cityName); // Mettre à jour le TextView avec la valeur de la ville
        }
        else
        {
            location.setText("cityName not found");
            location.setTextSize(20);
        }

/*
// Start the service to get prayer info
        Intent serviceIntent = new Intent(getActivity(), PrayerService.class);
        serviceIntent.putExtra("cityName", cityName);
        getActivity().startService(serviceIntent);
 */

        getprayerInfo(cityName);


        return view;
    }


    // Methode recupperer prayer d'une ville a travers son nom
    public void getprayerInfo(String cityName) {
        // Obtenez la date locale du système
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY", Locale.getDefault());
        String localDate = dateFormat.format(calendar.getTime());
        Log.d("WS_COMM", "LOCAL DATE : "+localDate);
        date.setText(localDate);


        SharedPreferences preferences = getActivity().getSharedPreferences("WidgetPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();


        String url = "https://api.aladhan.com/v1/calendarByCity?city=" + cityName + "&country=Tunisia&method=2&month="+localDate.substring(3,5)+"&year="+localDate.substring(6,10);
        Log.d("WS_COMM", "URL IS : " + url);
        // nous avons travailler avec le volley
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("WS_COMM", "GOT response : " + response.toString());
                //Vider La liste a chaque fois on change la ville
                prayerModels.clear();
                try {


                    ApiResponseModel root = new Gson().fromJson(response.toString(), ApiResponseModel.class);
                    Log.d("WS_COMM", "DATA COUNT : "+root.data.size());
                    for (ApiResponseModel.Datum d : root.data) {
                        if (d.date.gregorian.date.equals(localDate)) {
                            Gson JParser = new Gson();
                            Map<String, String> map = JParser.fromJson(JParser.toJson(d.timings), Map.class);
                            LinkedHashMap<String, String> orderedTimings = new LinkedHashMap<>();

                            // Organiser les prières dans l'ordre spécifié
                            orderedTimings.put("Imsak", map.get("Imsak"));
                            orderedTimings.put("Fajr", map.get("Fajr"));
                            orderedTimings.put("Sunrise", map.get("Sunrise"));
                            orderedTimings.put("Dhuhr", map.get("Dhuhr"));
                            orderedTimings.put("Asr", map.get("Asr"));
                            orderedTimings.put("Sunset", map.get("Sunset"));
                            orderedTimings.put("Maghrib", map.get("Maghrib"));
                            orderedTimings.put("Isha", map.get("Isha"));

                            for (Map.Entry<String, String> entry : orderedTimings.entrySet()) {
                                prayerModels.add(new PrayerModel(entry.getKey(), entry.getValue()));
                            }

                            // Convertir la liste en JSON
                            String prayersJson = new Gson().toJson(prayerModels);
                            // Mettre les prières dans SharedPreferences
                            editor.putString("prayers", prayersJson);
                            editor.apply();
                            break;
                        }
                    }
                    Log.d("WS_COMM", "DONE AFFECTING, GOT COUNT "+prayerModels.size());

                    //pour que la liste soit triéé
                    //Collections.sort(prayerModels);

                    prayerRVAdapter = new PrayerRVAdapter(prayerModels, getActivity().getApplicationContext()); // You need to create this adapter
                    Log.d("WS_COMM", "DONE RVAdapter");
                    prayerRv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                    prayerRv.setAdapter(prayerRVAdapter);

                    Log.d("WS_COMM", "DONE SET ADAPTER");
                } catch (Exception e) {
                    Log.d("WS_COMM", "ERROR");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("WS_COMM", "Got error :(");
                Toast.makeText(getActivity().getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }
        );
        requestQueue.add(jsonObjectRequest);
    }
}