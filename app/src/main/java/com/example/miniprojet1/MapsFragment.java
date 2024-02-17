package com.example.miniprojet1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.miniprojet1.Models.FetchData;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment {

    private GoogleMap mMap;
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {

               /* StringBuilder stringBuilder = new StringBuilder
                    ("http://maps.googleapis.com/maps/api/place/nearbysearch/json?");
            stringBuilder.append("location=" + latitude + "," + longitude);
            stringBuilder.append("&radius=1000");
            stringBuilder.append("&type=mosque");
            stringBuilder.append("&sensor=true");
            stringBuilder.append("&key=" + "AIzaSyA9CamnVenbDKPKO050TiGh3Q8yt3h1YfQ");

            String url=stringBuilder.toString();
            Object dataFetch[]=new Object[2];
            dataFetch[0]=mMap;
            dataFetch[1]=url;

            FetchData fetchData=new FetchData();
            fetchData.execute(fetchData);*/

            SharedPreferences preferences = getActivity().getSharedPreferences("WidgetPrefs", Context.MODE_PRIVATE);
            String latitudeString = preferences.getString("latitude", "");
            String longitudeString = preferences.getString("longitude", "");

            Log.d("WS COMMIT","longitudePref: "+longitudeString);
            Log.d("WS COMMIT","latitudePref: "+latitudeString);

            if (!latitudeString.isEmpty() && !longitudeString.isEmpty()) {
                double latitude = Double.parseDouble(latitudeString);
                double longitude = Double.parseDouble(longitudeString);

                LatLng location = new LatLng(latitude, longitude);
                googleMap.addMarker(new MarkerOptions().position(location).title("MyLocation"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15)); // Zoom level can be adjusted


            StringBuilder stringBuilder=new StringBuilder
                    ("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
            stringBuilder.append("location="+latitude+","+longitude);
            stringBuilder.append("&radius=1000");
                stringBuilder.append("&type=mosque");
                stringBuilder.append("&sensor=true");
                stringBuilder.append("&key=AIzaSyA9CamnVenbDKPKO050TiGh3Q8yt3h1YfQ");

                String url=stringBuilder.toString();
                Object dataFetch[]=new Object[2];
                dataFetch[0]=mMap;
                dataFetch[1]=url;

                FetchData fetchData=new FetchData();
                fetchData.execute(dataFetch);

            }
            else {
                LatLng sydney = new LatLng(-34, 151);
                googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            }


        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}