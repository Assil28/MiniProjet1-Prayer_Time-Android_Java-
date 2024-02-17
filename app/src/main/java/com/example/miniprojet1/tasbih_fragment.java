package com.example.miniprojet1;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class tasbih_fragment extends Fragment {

    private TextView counter;
    private Button tasbih,reset;

    private int i = 0;
    private SharedPreferences sharedPreferences;

    public tasbih_fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize SharedPreferences
        sharedPreferences = getActivity().getPreferences(this.getActivity().MODE_PRIVATE);
        // Retrieve the value of i from SharedPreferences
        i = sharedPreferences.getInt("counterValue", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tasbih_fragment, container, false);
        counter = view.findViewById(R.id.counter);
        tasbih = view.findViewById(R.id.ta);
        reset=view.findViewById(R.id.reset);
        // Update the counter text
        counter.setText(String.valueOf(i));

        // Set OnClickListener for the tasbih button
        tasbih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Increment the counter when the button is clicked
                i++;
                // Update the counter text
                counter.setText(String.valueOf(i));
                // Store the updated value of i in SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("counterValue", i);
                editor.apply();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Increment the counter when the button is clicked
                i=0;
                // Update the counter text
                counter.setText(String.valueOf(i));
                // Store the updated value of i in SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("counterValue", i);
                editor.apply();
            }
        });

        return view;
    }
}