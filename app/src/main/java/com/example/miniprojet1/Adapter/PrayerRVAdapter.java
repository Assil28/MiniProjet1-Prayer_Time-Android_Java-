package com.example.miniprojet1.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniprojet1.Models.PrayerModel;
import com.example.miniprojet1.R;

import java.util.List;

public class PrayerRVAdapter extends RecyclerView.Adapter<PrayerRVAdapter.ViewHolder> {

    private Context context;
    private List<PrayerModel> prayerModels;

    public PrayerRVAdapter(List<PrayerModel> prayerModels, Context context) {
        this.prayerModels = prayerModels;
        this.context = context;

    }

    @NonNull
    @Override
    public PrayerRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.prayer_item,parent,false);
        return new PrayerRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PrayerRVAdapter.ViewHolder holder, int position) {
        // Récupérer le modèle de prière à la position donnée
        PrayerModel model = prayerModels.get(position);

        // Vérifier si la liste de modèles de prière est vide
        if (prayerModels.isEmpty()) {
            // Si la liste est vide, vous pouvez afficher un message ou effectuer une action appropriée
            holder.prayer.setText("Aucune donnée disponible");
            holder.time.setText("");
        } else {
            // Si la liste n'est pas vide, affichez normalement les données
            holder.prayer.setText(model.getPrayer());
            holder.time.setText(model.getTime());
        }

        Log.d("ADAPTER","SETTING DATA FOR " + model);




    }

    @Override
    public int getItemCount() {
        return this.prayerModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView prayer,time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            prayer =itemView.findViewById(R.id.prayer);
            time=itemView.findViewById(R.id.time);


        }
    }
}
