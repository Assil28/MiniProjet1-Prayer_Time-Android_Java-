package com.example.miniprojet1.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
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
import java.util.Calendar;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PrayerService extends Service {
    private List<PrayerModel> prayerModels;
    private PrayerRVAdapter prayerRVAdapter;
    private RecyclerView prayerRv;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String cityName = intent.getStringExtra("cityName");
        if (cityName != null) {
            getprayerInfo(cityName);
        }
        return START_NOT_STICKY;
    }



    public void getprayerInfo(String cityName) {
        String url = "https://api.aladhan.com/v1/calendarByCity?city=" + cityName + "&country=Tunisia&method=2&month=01&year=2024";
        Log.d("WS_COMM", "URL IS : " + url);
        // nous avons travailler avec le volley
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext().getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("WS_COMM", "GOT response : " + response.toString());
                //Vider La liste a chaque fois on change la ville
                //prayerModels.clear();
                try {
                    // Obtenez la date locale du système
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("DD-MM-YYYY", Locale.getDefault());
                    String localDate = dateFormat.format(calendar.getTime());

                    Toast.makeText(getApplicationContext().getApplicationContext(),
                            localDate,
                            Toast.LENGTH_LONG).show();

                    ApiResponseModel root = new Gson().fromJson(response.toString(), ApiResponseModel.class);
                    Log.d("WS_COMM", "DATA COUNT : "+root.data.size());
                    for (ApiResponseModel.Datum d : root.data) {
                        if (d.date.gregorian.date.equals(localDate)) {
                            Gson JParser = new Gson();
                            Map<String, String> map = JParser.fromJson(JParser.toJson(d.timings), Map.class);
                            for (Map.Entry<String, String> entry : map.entrySet()) {
                                prayerModels.add(new PrayerModel(entry.getKey(), entry.getValue()));
                            }
                            break;
                        }
                    }
                    Log.d("WS_COMM", "DONE AFFECTING, GOT COUNT "+prayerModels.size());

                    //pour que la liste soit triéé
                    //Collections.sort(prayerModels);

                    prayerRVAdapter = new PrayerRVAdapter(prayerModels,getApplicationContext(),null); // You need to create this adapter
                    Log.d("WS_COMM", "DONE RVAdapter");
                    prayerRv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
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
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }
        );
        requestQueue.add(jsonObjectRequest);
    }
}
