package com.example.miniprojet1;

import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.miniprojet1.Adapter.DuaRVAdapter;
import com.example.miniprojet1.Models.DuaModel;

import java.util.ArrayList;
import java.util.List;


public class dua extends Fragment {

    private RecyclerView duaRV;
    private List<DuaModel> duaModels;
    private DuaRVAdapter duaRVAdapter;


    public dua() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        duaModels = new ArrayList<>();

        DuaModel duaModel = new DuaModel(getResources().getString(R.string.duaModel));
        duaModels.add(duaModel);

        DuaModel duaModel1 = new DuaModel(getResources().getString(R.string.duaModel1));

        duaModels.add(duaModel1);

        DuaModel duaModel2 = new DuaModel(getResources().getString(R.string.duaModel2));
        duaModels.add(duaModel2);

        DuaModel duaModel3 = new DuaModel(getResources().getString(R.string.duaModel3));
        duaModels.add(duaModel3);

        DuaModel duaModel4 = new DuaModel(getResources().getString(R.string.duaModel4));
        duaModels.add(duaModel4);

        DuaModel duaModel5 = new DuaModel(getResources().getString(R.string.duaModel5));
        duaModels.add(duaModel5);

        DuaModel duaModel6 = new DuaModel(getResources().getString(R.string.duaModel6));
        duaModels.add(duaModel6);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dua, container, false);
        duaRV = view.findViewById(R.id.duaRV);



        // Vous devez créer et définir l'adaptateur après avoir ajouté des éléments à la liste
        duaRVAdapter = new DuaRVAdapter(getActivity(), duaModels);
        duaRV.setAdapter(duaRVAdapter);

        return view;
    }

}