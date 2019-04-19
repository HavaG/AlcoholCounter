package com.example.alcoholcounter.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.alcoholcounter.R;
import com.example.alcoholcounter.database.Drink;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.DrinkViewHolder> {

    private DrinkItemClickListener listener;
    private List<Drink> drinks;

    public DrinkAdapter(DrinkItemClickListener listener) {
        this.listener = listener;
        drinks = new ArrayList<>();
    }

    @NonNull
    @Override
    public DrinkViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View drinkView = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.drink_item, viewGroup, false);
        return new DrinkViewHolder(drinkView);
    }

    @Override
    public void onBindViewHolder(@NonNull DrinkViewHolder drinkViewHolder, int position) {
        Drink drinkItem = drinks.get(position);

        drinkViewHolder.nameTV.setText(drinkItem.name);

        Double d1 = drinkItem.unit * drinkItem.quantity;
        String unit;
        if(d1 >= 10){
            d1 = Math.round(d1 * 100.0) / 1000.0;
            unit = " l";
        } else if(d1 >= 1){
            d1 = Math.round(d1 * 100.0) / 100.0;
            unit = " dl";
        } else {
            d1 = Math.round(d1 * 100.0) / 10.0;
            unit = " cl";
        }

        Long roundedAmount = d1.longValue();
        String s1;
        if(d1%1 == 0){
            s1 = roundedAmount.toString();
        } else {
            s1 = Double.toString(d1);
        }
        s1 += unit;
        drinkViewHolder.unitTV.setText(s1);

        Double d2 = drinkItem.degrees;
        String s2;
        roundedAmount = d2.longValue();
        if(d2%1 == 0) {
            s2 = roundedAmount.toString() + "%";
        } else {
            s2 = d2.toString() + "%";
        }
        drinkViewHolder.degreeTV.setText(s2);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(drinkItem.date.getTime());
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);

        String year4digits = String.format(Locale.getDefault(),"%04d", mYear);
        String month2digits = String.format(Locale.getDefault(),"%02d", mMonth);
        String day2digits = String.format(Locale.getDefault(),"%02d", mDay);


        String s3 = year4digits + ". " + month2digits + ". " + day2digits + ".";

        drinkViewHolder.dateTV.setText(s3);

        drinkViewHolder.drinkItem = drinkItem;
    }

    @Override
    public int getItemCount() {
        return drinks.size();
    }

    class DrinkViewHolder extends RecyclerView.ViewHolder {

        TextView nameTV;
        TextView unitTV;
        TextView quantityTV;
        TextView degreeTV;
        ImageButton deleteBtn;
        TextView dateTV;

        Drink drinkItem;

        DrinkViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTV =itemView.findViewById(R.id.drinkName);
            unitTV = itemView.findViewById(R.id.drinkUnit);
            quantityTV = itemView.findViewById(R.id.drinkQuantity);
            degreeTV = itemView.findViewById(R.id.drinkDegree);
            deleteBtn = itemView.findViewById(R.id.drinkRemoveBtn);
            dateTV = itemView.findViewById(R.id.drinkDate);


            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteItem(drinkItem);
                    listener.onDrinkDeleted(drinkItem);
                }
            });

        }
    }

    public void addItem(Drink drinkItem){
        drinks.add(drinkItem);
        notifyItemInserted(drinks.size()-1);
    }

    public void deleteItem(Drink drinkItem){
        drinks.remove(drinkItem);
        notifyDataSetChanged();
    }

    public void update(List<Drink> drinks) {
        this.drinks.clear();
        this.drinks.addAll(drinks);
        notifyDataSetChanged();
    }
}
