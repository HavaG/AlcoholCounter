package com.example.alcoholcounter;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.alcoholcounter.Adapter.DrinkAdapter;
import com.example.alcoholcounter.Adapter.DrinkItemClickListener;
import com.example.alcoholcounter.database.Drink;
import com.example.alcoholcounter.database.DrinkCounterDB;

import java.util.List;

public class DrinksActivity extends AppCompatActivity implements DrinkItemClickListener {

    List<Drink> drinks;
    DrinkCounterDB DB;
    RecyclerView drinkRV;
    DrinkAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DB = DrinkCounterDB.getInstance(getBaseContext());
        loadBackground();
        setContentView(R.layout.drink_list);
        initRecyclerView();
    }

    private void initRecyclerView() {
        drinkRV = findViewById(R.id.drinkRecyclerView);
        adapter = new DrinkAdapter(this);
        loadItemsInBackground();
        drinkRV.setAdapter(adapter);
        drinkRV.setLayoutManager(new LinearLayoutManager(this));
    }

    @SuppressLint("StaticFieldLeak")
    private void loadBackground() {
        new AsyncTask<Void, Void, List<Drink>>() {

            @Override
            protected List<Drink> doInBackground(Void... voids) {
                drinks = DB.DrinksDao().getAll();
                return DB.DrinksDao().getAll();
            }

            @Override
            protected void onPostExecute(List<Drink> drinkItems) {
                drinks = drinkItems;
            }
        }.execute();
    }


    @SuppressLint("StaticFieldLeak")
    private void loadItemsInBackground() {
        new AsyncTask<Void, Void, List<Drink>>() {

            @Override
            protected List<Drink> doInBackground(Void... voids) {
                return DB.DrinksDao().getAll();
            }

            @Override
            protected void onPostExecute(List<Drink> drinkItems) {
                adapter.update(drinkItems);
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onDrinkCreated(final Drink newDrink) {
        new AsyncTask<Void, Void, Drink>() {

            @Override
            protected Drink doInBackground(Void... voids) {
                newDrink.id = DB.DrinksDao().insert(newDrink);
                return newDrink;
            }

            @Override
            protected void onPostExecute(Drink newDrink) {
                adapter.addItem(newDrink);
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onDrinkDeleted(final Drink delDrink) {
        new AsyncTask<Void, Void, Drink>() {

            @Override
            protected Drink doInBackground(Void... voids) {
                DB.DrinksDao().deleteItem(delDrink);
                return null;
            }

            @Override
            protected void onPostExecute(Drink newDrink) {
                adapter.deleteItem(newDrink);
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onDrinkChanged(final Drink changedDrink) {
        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... voids) {
                DB.DrinksDao().update(changedDrink);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean isSuccessful) {
                Log.d("MainActivity", "ShoppingListItem update was successful");
            }
        }.execute();
    }
}
