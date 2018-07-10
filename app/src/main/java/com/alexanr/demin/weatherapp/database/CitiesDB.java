package com.alexanr.demin.weatherapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {City.class}, version = 1, exportSchema = false)
public abstract class CitiesDB extends RoomDatabase {
    public abstract CitiesDao citiesDao();
}
