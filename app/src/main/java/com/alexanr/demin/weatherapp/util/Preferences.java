package com.alexanr.demin.weatherapp.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

public class Preferences {

    private static final String EMPTY_CITY = null;
    private static final String EMPTY_TIME = null;

    private static final String DATE_PATTERN = "dd.MM.yyyy, HH:mm";

    private static Preferences preferences;
    private SharedPreferences sharedPreferences;

    private Preferences(Context context) {
        sharedPreferences = context.getSharedPreferences(Constants.PREF_NAME_TAG, MODE_PRIVATE);
    }

    public static void init(Context context) {
        if (preferences == null) {
            preferences = new Preferences(context);
        }
    }

    public static Preferences get() {
        return preferences;
    }

    public String getCity() {
        return sharedPreferences.getString(Constants.PREF_CITY_TAG, EMPTY_CITY);
    }

    public void setCity(String city) {
        sharedPreferences.edit().putString(Constants.PREF_CITY_TAG, city).apply();
    }

    public String getLastUpdTime() {
        return sharedPreferences.getString(Constants.PREF_LAST_UPD_TIME_TAG, EMPTY_TIME);
    }

    public String setAndGetLastUpdTime() {
        String date = new SimpleDateFormat(DATE_PATTERN).format(new Date());
        sharedPreferences.edit().putString(Constants.PREF_LAST_UPD_TIME_TAG, date).apply();
        return date;
    }

    public String getMeasure() {
        return sharedPreferences.getString(Constants.PREF_MEASURE_TAG, Constants.CELSIUS);
    }

    public void setMeasure(String measure) {
        sharedPreferences.edit().putString(Constants.PREF_MEASURE_TAG, measure).apply();
    }
}
