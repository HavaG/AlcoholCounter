package com.example.alcoholcounter.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface UnitDao {
    @Query("SELECT * FROM unitItem")
    List<Unit> getAll();

    @Query("SELECT * FROM unitItem WHERE id = :id LIMIT 1")
    Unit get(int id);
}