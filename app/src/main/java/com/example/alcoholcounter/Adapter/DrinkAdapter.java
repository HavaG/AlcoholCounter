package com.example.alcoholcounter.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.alcoholcounter.database.Drink;

import java.util.ArrayList;
import java.util.List;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.DrinkViewHolder> {

    DrinkItemClickListener listener;
    private List<Drink> drinks;

    public DrinkAdapter(DrinkItemClickListener listener) {
        this.listener = listener;
        drinks = new ArrayList<>();
    }

    @NonNull
    @Override
    public DrinkViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull DrinkViewHolder drinkViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class DrinkViewHolder extends RecyclerView.ViewHolder {
        public DrinkViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
