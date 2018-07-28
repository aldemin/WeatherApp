package com.alexanr.demin.weatherapp;

import android.app.Application;

import com.alexanr.demin.weatherapp.database.Database;
import com.alexanr.demin.weatherapp.network.WeatherLoader;
import com.alexanr.demin.weatherapp.util.Parser;
import com.alexanr.demin.weatherapp.util.Preferences;

public class WeatherApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Database.init(this);
        Preferences.init(this);
        WeatherLoader.init();
        Parser.init();
    }
}
