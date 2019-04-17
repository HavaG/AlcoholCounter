package com.example.alcoholcounter.Adapter;

import com.example.alcoholcounter.database.Drink;

public interface DrinkItemClickListener {
    void onDrinkChanged(Drink drinkItem);
    void onDrinkDeleted(Drink drinkItem);
    void onDrinkClick(Drink drinkItem);
}
