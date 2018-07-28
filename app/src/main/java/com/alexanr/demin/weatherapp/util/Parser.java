package com.alexanr.demin.weatherapp.util;

import android.content.Intent;

import com.alexanr.demin.weatherapp.database.City;
import com.alexanr.demin.weatherapp.network.request.WeatherRequest;

import java.util.List;

public class Parser {

    private static Parser cityParser;

    private Parser() {
    }

    public static void init() {
        if (cityParser == null) {
            cityParser = new Parser();
        }
    }

    public static Parser get() {
        return cityParser;
    }

/*    public City requestParseToCity(WeatherRequest request, String lastUpd) {
        City city = new City();
        city.setName(request.getName());
        city.setTemperature((int) request.getMain().getTemp());
        city.setMaxTemperature((int) request.getMain().getTemp_max());
        city.setMinTemperature((int) request.getMain().getTemp_min());
        city.setHumidity((int) request.getMain().getHumidity());
        city.setPressure((int) request.getMain().getPressure());
        city.setLastUpd(lastUpd);
        city.setWeatherParams((int) request.getId());
        return city;
    }*/

    public City intentParseToCity(Intent intent) {
        City city = new City();
        city.setName(intent.getStringExtra(Constants.PREF_CITY_TAG));
        city.setTemperature(intent.getIntExtra(Constants.WEATHER_TEMP_TAG, 0));
        city.setMaxTemperature(intent.getIntExtra(Constants.WEATHER_TEMP_MAX_TAG, 0));
        city.setMinTemperature(intent.getIntExtra(Constants.WEATHER_TEMP_MIN_TAG, 0));
        city.setHumidity(intent.getIntExtra(Constants.WEATHER_HUMIDITY_TAG, 0));
        city.setPressure(intent.getIntExtra(Constants.WEATHER_PRESSURE_TAG, 0));
        city.setLastUpd(intent.getStringExtra(Constants.PREF_LAST_UPD_TIME_TAG));
        city.setWeatherParams(intent.getStringExtra(Constants.WEATHER_PARAMS_TAG));
        return city;
    }

    public Intent requestParseToIntent(WeatherRequest request, String lastUpd) {
        Intent intent = new Intent();
        intent.putExtra(Constants.PREF_CITY_TAG, request.getName());
        intent.putExtra(Constants.WEATHER_TEMP_TAG, (int) request.getMain().getTemp());
        intent.putExtra(Constants.WEATHER_TEMP_MAX_TAG, (int) request.getMain().getTemp_max());
        intent.putExtra(Constants.WEATHER_TEMP_MIN_TAG, (int) request.getMain().getTemp_min());
        intent.putExtra(Constants.WEATHER_HUMIDITY_TAG, (int) request.getMain().getHumidity());
        intent.putExtra(Constants.WEATHER_PRESSURE_TAG, (int) request.getMain().getPressure());
        intent.putExtra(Constants.PREF_LAST_UPD_TIME_TAG, lastUpd);
        intent.putExtra(Constants.WEATHER_PARAMS_TAG, request.getWeather()[0].getMain());
        return intent;
    }
}
