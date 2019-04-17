package com.example.alcoholcounter.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.alcoholcounter.R;
import com.example.alcoholcounter.database.Drink;
import com.example.alcoholcounter.database.DrinkCounterDB;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BeerDialogFragment extends DialogFragment {

    public static final String TAG = "NewCategoryDialogFragment";

    private DrinkCounterDB DB;
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
    private NumberPicker beerWineQuantity;
    private NumberPicker beerWineDegree;
    private DatePicker beerWineDate;
    private NumberPicker beerWineUnit;

    //átveszi az értékeket az xml-ből. Feltölti a Spinner-t adatokkal
    private View getContentView() {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_new_beer_wine, null);
        //Name
        beerWineName =contentView.findViewById(R.id.beerWineName);

        //Date
        beerWineDate = contentView.findViewById(R.id.beerWineDatePicker);

        //Quantity
        beerWineQuantity = contentView.findViewById(R.id.beerWineQuantityPicker);
        beerWineQuantity.setMinValue(1);
        beerWineQuantity.setMaxValue(100);
        beerWineQuantity.setWrapSelectorWheel(true);

        //Unit
        beerWineUnit =contentView.findViewById(R.id.beerWineUnitPicker);
        final String[] values= getResources().getStringArray(R.array.unit_picker);
        beerWineUnit.setMinValue(0);
        beerWineUnit.setMaxValue(values.length-1);
        beerWineUnit.setDisplayedValues(values);
        beerWineUnit.setWrapSelectorWheel(true);
        beerWineUnit.setValue(4);

        //Degree
        beerWineDegree = contentView.findViewById(R.id.beerWineDegreePicker);
        beerWineDegree.setMinValue(1);
        beerWineDegree.setMaxValue(100);
        beerWineDegree.setValue(4);

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
        return true;
    }

    //hozzáadja az új italt
    private Drink getDrink() {
        Drink drink = new Drink();
        //TODO: drink adataival feltölteni

        //TODO: lehet itt nem elég a "" hanem más ellenőzés kell
        if(beerWineName.getText().toString().length() != 0) {
            drink.name = beerWineName.getText().toString();
        } else
            drink.name = "Beer";

        drink.quantity = beerWineQuantity.getValue();

        double unit;
        switch (beerWineUnit.getValue()) {
            case 0: unit = 0.01; break;
            case 1: unit = 0.1; break;
            case 2: unit = 1; break;
            case 3: unit = 10; break;
            case 4: unit = 5; break;
            case 5: unit = 2; break;
            case 6: unit = 7.5; break;
            default: unit = 0;
        }
        drink.unit = unit;

        drink.degrees = beerWineDegree.getValue();

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, beerWineDate.getYear());
        c.set(Calendar.MONTH, beerWineDate.getMonth());
        c.set(Calendar.DAY_OF_MONTH, beerWineDate.getDayOfMonth());
        drink.date = c.getTime();

        return drink;
    }
}