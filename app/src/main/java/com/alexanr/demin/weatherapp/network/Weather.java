package com.alexanr.demin.weatherapp.network;

import com.alexanr.demin.weatherapp.network.request.WeatherRequest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Weather {
    @GET("data/2.5/weather")
    Call<WeatherRequest> loadWeather(@Query("q") String cityCountry, @Query("appid") String keyApi);

    Call<WeatherRequest> loadWeather(@Query("lat") String latitude,@Query("lon") String longitude, @Query("appid") String keyApi);
}
