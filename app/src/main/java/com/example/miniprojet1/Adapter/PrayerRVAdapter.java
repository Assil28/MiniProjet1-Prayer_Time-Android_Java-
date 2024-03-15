package com.example.miniprojet1.Adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniprojet1.Models.PrayerModel;
import com.example.miniprojet1.R;
import com.example.miniprojet1.prayer_details;
import com.example.miniprojet1.prayer_time;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

public class PrayerRVAdapter extends RecyclerView.Adapter<PrayerRVAdapter.ViewHolder> {

    CardView cardView;
    private Context context;
    private List<PrayerModel> prayerModels;
    private FragmentManager fragmentManager;


    Fragment fragment;

    public PrayerRVAdapter(List<PrayerModel> prayerModels, Context context,FragmentManager fragmentManager) {
        this.prayerModels = prayerModels;
        this.context = context;
        this.fragmentManager = fragmentManager;
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
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    PrayerModel model = prayerModels.get(position);
                    prayer_details fragment = prayer_details.newInstance(model.getPrayer());
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.replace(R.id.fragment, fragment); // Assurez-vous de remplacer R.id.fragment_container par l'ID de votre conteneur de fragment dans MainActivity
                    ft.addToBackStack(null);
                    ft.commit();
                }
            });
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

            cardView=itemView.findViewById(R.id.card);

            PushDownAnim.setPushDownAnimTo(cardView).setScale(PushDownAnim.MODE_SCALE,0.80f);

        }
    }
}
