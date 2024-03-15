package com.example.miniprojet1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniprojet1.Models.DuaModel;
import com.example.miniprojet1.R;

import java.util.List;


public class DuaRVAdapter extends RecyclerView.Adapter<DuaRVAdapter.ViewHolder> {


    private Context context;
    private List<DuaModel> duaModels;

    public DuaRVAdapter(Context context, List<DuaModel> duaModels) {
        this.context = context;
        this.duaModels = duaModels;
    }

    @NonNull
    @Override
    public DuaRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.dua_rv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DuaRVAdapter.ViewHolder holder, int position) {
        DuaModel model= duaModels.get(position);

        holder.duaTV.setText(model.getDuatext());

    }

    @Override
    public int getItemCount() {
        return this.duaModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView duaTV;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            duaTV =itemView.findViewById(R.id.dua);

        }
    }
}
