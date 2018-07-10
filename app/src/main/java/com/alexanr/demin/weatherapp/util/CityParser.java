package com.alexanr.demin.weatherapp.util;

import com.alexanr.demin.weatherapp.database.City;
import com.alexanr.demin.weatherapp.network.request.WeatherRequest;

public class CityParser {

    private static CityParser cityParser;

    private CityParser(){
    }

    public static void init() {
        if (cityParser == null) {
            cityParser = new CityParser();
        }
    }

    public static CityParser get() {
        return cityParser;
    }

    public City parse(WeatherRequest request, String lastUpd) {
        City city = new City();
        city.setName(request.getName());
        city.setTemperature((int) request.getMain().getTemp());
        city.setMaxTemperature((int) request.getMain().getTemp_max());
        city.setMinTemperature((int) request.getMain().getTemp_min());
        city.setHumidity((int  )request.getMain().getHumidity());
        city.setPressure((int) request.getMain().getPressure());
        city.setLastUpd(lastUpd);
        return city;
    }
}
