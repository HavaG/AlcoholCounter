package com.example.alcoholcounter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
        addBeer.setImageBitmap(textAsBitmap("Beer", 40, Color.BLACK));

        //wine
        FloatingActionButton addWine = findViewById(R.id.wine);
        addWine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Add wine", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        addWine.setImageBitmap(textAsBitmap("Wine", 40, Color.BLACK));

        //other
        FloatingActionButton addOther = findViewById(R.id.other);
        addOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Add other", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        addOther.setImageBitmap(textAsBitmap("Other", 40, Color.BLACK));
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


    //method to convert your text to image
    public static Bitmap textAsBitmap(String text, float textSize, int textColor) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.0f); // round
        int height = (int) (baseline + paint.descent() + 0.0f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0, baseline, paint);
        return image;
    }
}
