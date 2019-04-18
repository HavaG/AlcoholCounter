package com.example.alcoholcounter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.alcoholcounter.database.Drink;
import com.example.alcoholcounter.database.DrinkCounterDB;
import com.example.alcoholcounter.fragments.BeerDialogFragment;

public class MainActivity extends AppCompatActivity implements BeerDialogFragment.NewDrinkDialogListener {

    DrinkCounterDB DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DB = DrinkCounterDB.getInstance(getBaseContext());

        //beer
        FloatingActionButton addBeer = findViewById(R.id.beer);
        addBeer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BeerDialogFragment().show(getSupportFragmentManager(), BeerDialogFragment.TAG);
            }
        });

        //wine
        FloatingActionButton addWine = findViewById(R.id.wine);
        addWine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Add wine", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //other
        FloatingActionButton addOther = findViewById(R.id.other);
        addOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Add other", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showDrinks(View v) {
        Intent i = new Intent(this, DrinksActivity.class);
        startActivity(i);
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
        }.execute();
    }
}
