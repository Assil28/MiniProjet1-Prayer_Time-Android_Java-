package com.example.miniprojet1;

import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.miniprojet1.Models.PrayerDetails;

import java.util.ArrayList;
import java.util.List;


public class prayer_details extends Fragment {

    TextView prayer,voix,nbrofrakka;

    PrayerDetails prayerDetails;




    private static final String ARG_PARAM1 = "param1";


    private String mParam1;


    public prayer_details() {
        // Required empty public constructor
    }


    public static prayer_details newInstance(String param1) {
        prayer_details fragment = new prayer_details();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
        if(mParam1.equals("Fajr")){
            prayerDetails=new PrayerDetails("Fajr", 2, "high voice");
        }
        else if(mParam1.equals("Dhuhr")){
            prayerDetails=(new PrayerDetails("Dhuhr", 4, "Low voice"));


        }  else if(mParam1.equals("Asr")){
            prayerDetails=(new PrayerDetails("Asr", 4, "Low voice"));

        }
        else if(mParam1.equals("Maghrib")){
            prayerDetails=(new PrayerDetails("Maghrib", 3, "First Two Rakaas with high voice and the Last one with Low voice"));

        }
        else if(mParam1.equals("Isha")){
            prayerDetails=(new PrayerDetails("Isha", 4, "First Two Rakaas high voice and the other two with Low voice"));

        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_prayer_details, container, false);

        prayer = view.findViewById(R.id.pray);
        nbrofrakka = view.findViewById(R.id.nb_rakaa);
        voix = view.findViewById(R.id.voix);

        prayer.setText(prayerDetails.getPrayerName());
        nbrofrakka.setText(Integer.toString(prayerDetails.getNumberOfRakaa()).toString());
        voix.setText(prayerDetails.getVoice().toString());

        return  view;
    }
}