package com.example.alcoholcounter.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.alcoholcounter.R;
import com.example.alcoholcounter.database.Drink;
import com.example.alcoholcounter.database.DrinkCounterDB;
import com.example.alcoholcounter.database.Type;

import java.util.ArrayList;
import java.util.List;

public class BeerDialogFragment extends DialogFragment {

    public static final String TAG = "NewCategoryDialogFragment";

    private DrinkCounterDB DB;
    private List<Type> types;
    private List<String> typeNames = new ArrayList<>();
    private List<String> unitNames = new ArrayList<>();

    public interface NewDrinkDialogListener {
        void onDrinkCreated(Drink newDrink);
    }

    private NewDrinkDialogListener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentActivity activity = getActivity();
        if (activity instanceof NewDrinkDialogListener) {
            listener = (NewDrinkDialogListener) activity;
        } else {
            throw new RuntimeException("Activity must implement the NewCategoryDialogListener interface!");
        }

        DB = DrinkCounterDB.getInstance(getContext());
        loadBackground();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new AlertDialog.Builder(requireContext())
                .setTitle("Add Beers")
                .setView(getContentView())
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (isValid()) {
                            listener.onDrinkCreated(getDrink());
                        } else {
                            Toast.makeText(getContext(), "All textfield required", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
    }

    private EditText beerWineName;
    private EditText beerWineQuantity;
    private Spinner beerWineTypeSpinner;
    private Spinner beerWineUnitSpinner;

    //átveszi az értékeket az xml-ből. Feltölti a Spinner-t adatokkal
    private View getContentView() {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_new_beer_wine, null);
        beerWineName =contentView.findViewById(R.id.beerWineName);
        beerWineQuantity =contentView.findViewById(R.id.beerWineQuantity);
        beerWineTypeSpinner =contentView.findViewById(R.id.beerWineTypeSpinner);

        for (int i = 0; i < types.size(); i++){
            typeNames.add(types.get(i).name);
        }

        beerWineUnitSpinner =contentView.findViewById(R.id.beerWineUnitSpinner);
            //TODO: unnit spinner
            // if ml --> *100
            // if cl --> *10
            // if dl --> nothing
            // if liter --> /10

        return contentView;
    }

    //ellenőrzi az összes adatot. Ha helyesek, akkor true
    private boolean isValid() {
        /*
        if(nameedittext.gettext().length() > 0 && date != 0) {
            return true;
        }
        return false;
        */
        return false;
    }

    //Háttérben feltölni a types listát az összes típussal
    @SuppressLint("StaticFieldLeak")
    private void loadBackground() {
        new AsyncTask<Void, Void, List<Type>>() {

            @Override
            protected List<Type> doInBackground(Void... voids) {
                types = DB.TypeDao().getAll();
                return DB.TypeDao().getAll();
            }

            @Override
            protected void onPostExecute(List<Type> typeItems) {
                types = typeItems;
            }
        }.execute();
    }

    //hozzáadja az új italt
    private Drink getDrink() {
        Drink drink = new Drink();
        //TODO: drink adataival feltölteni
        if(beerWineName.getText().toString() != "") {
            drink.name = beerWineName.getText().toString();
        } else
            drink.name = "Beer";
        drink.quantity = Double.parseDouble(beerWineQuantity.getText().toString());
        drink.type = types.get(beerWineTypeSpinner.getSelectedItemPosition());
        drink.degrees = drink.type.degrees;

        return drink;
    }
}