package com.example.alcoholcounter.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface DrinksDao {
    @Query("SELECT * FROM drinkItem")
    List<Drink> getAll();

    @Query("SELECT * FROM drinkItem WHERE id = :id LIMIT 1")
    Drink get(int id);

    @Insert
    long insert(Drink drinkItems);

    @Delete
    void deleteItem(Drink drinkItem);

    @Update
    void update(Drink drinkItem);
}
