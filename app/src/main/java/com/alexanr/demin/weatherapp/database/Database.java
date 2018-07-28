package com.alexanr.demin.weatherapp.database;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

public class Database {

    private static final String DATABASE_NAME = "CitiesDB";
    private static Database database;
    private CitiesDB citiesDB;

    private Database(Context context) {
        citiesDB = Room.databaseBuilder(context, CitiesDB.class, DATABASE_NAME).allowMainThreadQueries().build();
    }

    public static void init(Context context) {
        if (database == null) {
            database = new Database(context);
        }
    }

    @NonNull
    public static Database get() {
        return database;
    }

    public CitiesDB getDataBase() {
        return citiesDB;
    }

    public boolean lineIsExist(String name) {
        List<City> list = citiesDB.citiesDao().getAll();
        for (City city : list) {
            if (city.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public long getIdByName(String name) {
        List<City> list = citiesDB.citiesDao().getAll();
        for (City city : list) {
            if (city.getName().equals(name)) {
                return city.getId();
            }
        }
        return -1L;
    }

    public List<City> getCityList() {
        return citiesDB.citiesDao().getAll();
    }
}
