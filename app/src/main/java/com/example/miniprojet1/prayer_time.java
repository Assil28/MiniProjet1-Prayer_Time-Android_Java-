package com.example.miniprojet1;

import android.location.LocationManager;
import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class prayer_time extends Fragment {

    private List<PrayerModel> prayerModels;
    private PrayerRVAdapter prayerRVAdapter;

    private LocationManager locationManager;
    private  int PERMISSION_CODE=1;
    private String cityName;



    private TextView location;
    RecyclerView  prayerRv;

    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;
    TextView date;

    public prayer_time() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static prayer_time newInstance(String cityName) {
        System.out.println(cityName);
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

        /* location=view.findViewById(R.id.location);
        location.setText(cityName);
*/


        getprayerInfo(cityName);





        return view;
    }




    // Methode recupperer prayer d'une ville a travers son nom
    public void getprayerInfo(String cityName){
        String url="https://api.aladhan.com/v1/calendarByCity?city="+cityName+"&country=Tunisia&method=2&month=01&year=2024";

        // nous avons travailler avec le volley
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //Vider La liste a chaque fois on change la ville
                prayerModels.clear();


                try {
                    // Obtenez la date locale du système
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                    String localDate = dateFormat.format(calendar.getTime());

                    date.setText(localDate);


                    // Parcourir les données dans la réponse JSON
                    JSONArray dataArray = response.getJSONArray("data");
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject dataObject = dataArray.getJSONObject(i);
                        JSONObject dateObject = dataObject.getJSONObject("date");

                        // Récupérer la date lisible de chaque entrée
                        String readableDate = dateObject.getString("readable");

                        // Si la date lisible correspond à la date locale du système
                        if (readableDate.equals(localDate)) {
                            // Récupérer les timings des prières
                            JSONObject timingsObject = dataObject.getJSONObject("timings");
                            String imsak = timingsObject.getString("Imsak");
                            prayerModels.add(new PrayerModel("Imsak",imsak));

                            String fajrTime = timingsObject.getString("Fajr");
                            prayerModels.add(new PrayerModel("Fajr",fajrTime));

                            String sunriseTime = timingsObject.getString("Sunrise");
                            prayerModels.add(new PrayerModel("Sunrise",sunriseTime));

                            String dhuhr = timingsObject.getString("Dhuhr");
                            prayerModels.add(new PrayerModel("Dhuhr",dhuhr));

                            String asr = timingsObject.getString("Asr");
                            prayerModels.add(new PrayerModel("Asr",asr));

                            String maghrib = timingsObject.getString("Maghrib");
                            prayerModels.add(new PrayerModel("Maghrib",maghrib));

                            String isha = timingsObject.getString("Isha");
                            prayerModels.add(new PrayerModel("Isha",isha));

                            prayerRVAdapter = new PrayerRVAdapter(prayerModels,getActivity()); // You need to create this adapter

                            prayerRv.setAdapter(prayerRVAdapter);

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Please enter valide city name", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue.add(jsonObjectRequest);
    }
}