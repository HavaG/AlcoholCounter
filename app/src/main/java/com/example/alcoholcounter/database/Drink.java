package com.example.alcoholcounter.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "drinkItem")
public class Drink {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    public long id;

    //probably no need
    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "unit")
    public double unit;

    @ColumnInfo(name = "quantity")
    public double quantity;

    @ColumnInfo(name = "degrees")
    public double degrees;

    @ColumnInfo(name = "date")
    public Date date;
}
