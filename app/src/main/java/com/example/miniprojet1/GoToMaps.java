package com.example.miniprojet1;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import android.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class GoToMaps extends Fragment {


    public GoToMaps() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_go_to_maps, container, false);

getDirection();
        return view;
    }

    public void getDirection(){
        try {
            SharedPreferences preferences = getActivity().getSharedPreferences("WidgetPrefs", Context.MODE_PRIVATE);
            String latitudeString = preferences.getString("latitude", "");
            String longitudeString = preferences.getString("longitude", "");
            String from=latitudeString+","+longitudeString;
            String to="Mosquée";
            Uri uri=Uri.parse("https://www.google.com/maps/dir/"+from+"/"+to);
            Intent intent=new Intent(Intent.ACTION_VIEW,uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }catch (ActivityNotFoundException e){
            Uri uri=Uri.parse("https://play.google.com/store/apps/details?id=com.google.andriod.apps.masp");
            Intent intent=new Intent(Intent.ACTION_VIEW,uri);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

    }
}