package com.example.tiposdementes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaapterEstadistica extends RecyclerView.Adapter<AdaapterEstadistica.ViewHolderEstadistica> {

    private ArrayList<TestEstadistica> listHome;
    private Context context;

    public AdaapterEstadistica(ArrayList<TestEstadistica> listHome, Context context) {
        this.listHome = listHome;
        this.context = context;
    }

    @NonNull
    @Override
    public AdaapterEstadistica.ViewHolderEstadistica onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_estadistica, parent, false);
        return new ViewHolderEstadistica(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolderEstadistica holder, int position) {
        holder.txtnombre.setText(listHome.get(position).getNombre());
        holder.txtcarrera.setText(listHome.get(position).getCarrera());
        holder.txtmente.setText(context.getString(R.string.targetmente) + listHome.get(position).getMente());
    }

    @Override
    public int getItemCount() {
        return listHome.size();
    }

    public class ViewHolderEstadistica extends RecyclerView.ViewHolder {
        private TextView txtnombre, txtcarrera, txtmente;

        public ViewHolderEstadistica(@NonNull View itemView) {
            super(itemView);
            txtnombre = itemView.findViewById(R.id.nombreUsuario);
            txtcarrera = itemView.findViewById(R.id.nombreCarrera);
            txtmente = itemView.findViewById(R.id.txtMente);
        }
    }
}
