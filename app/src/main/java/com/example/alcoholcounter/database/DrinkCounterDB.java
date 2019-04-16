package com.example.alcoholcounter.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

@Database(
        entities = {Drink.class, Type.class, Unit.class},
        version = 2,
        exportSchema = false
)

@TypeConverters(Converters.class)
public abstract class DrinkCounterDB extends RoomDatabase {
    public abstract DrinksDao DrinksDao();
    public abstract TypeDao TypeDao();
    public abstract UnitDao UnitDao();
    private static DrinkCounterDB INSTANCE;

    public synchronized static DrinkCounterDB getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                    context,
                    DrinkCounterDB.class,
                    "Drink-list")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
