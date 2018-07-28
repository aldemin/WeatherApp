package com.alexanr.demin.weatherapp.util;

public class Constants {

    //Preferences keys
    public static final String PREF_NAME_TAG = "myPref";
    public static final String PREF_CITY_TAG = "prefCity";
    public static final String PREF_LAST_UPD_TIME_TAG = "prefTime";
    public static final String PREF_MEASURE_TAG = "prefMeasure";

    //Measure systems
    public static final String CELSIUS = "Celsius";
    public static final String FAHRENHEIT = "Fahrenheit";

    //Weather params keys
    public static final String WEATHER_TEMP_TAG = "temperature";
    public static final String WEATHER_TEMP_MAX_TAG = "temperatureMax";
    public static final String WEATHER_TEMP_MIN_TAG = "temperatureMin";
    public static final String WEATHER_PARAMS_TAG = "weatherParams";
    public static final String WEATHER_HUMIDITY_TAG = "humidity";
    public static final String WEATHER_PRESSURE_TAG = "pressure";

    //Requests cods
    public static final String REQUEST_TAG = "request";
    public static final int REQUEST_OK_TAG = 200;
    public static final int REQUEST_FAILED_TAG = 404;

    //BroadCasts
    public static final String BROADCAST_DB_TAG = "broadcastDb";
    public static final String BROADCAST_RETROFIT_TAG = "broadcastRetrofit";
}
