package com.example.alcoholcounter.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface TypeDao {
    @Query("SELECT * FROM typeitem")
    List<Type> getAll();

    @Query("SELECT * FROM typeitem WHERE id = :id LIMIT 1")
    Type get(int id);

    @Insert
    long insert(Type typeItems);

    @Delete
    void deleteItem(Type typeItem);

    @Update
    void update(Type typeItem);
}
