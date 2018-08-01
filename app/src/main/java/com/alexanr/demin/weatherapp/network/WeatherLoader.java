package com.alexanr.demin.weatherapp.network;

import android.content.Context;
import android.content.Intent;

import com.alexanr.demin.weatherapp.activity.MainActivity;
import com.alexanr.demin.weatherapp.network.request.WeatherRequest;
import com.alexanr.demin.weatherapp.util.Constants;
import com.alexanr.demin.weatherapp.util.Parser;
import com.alexanr.demin.weatherapp.util.Preferences;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherLoader {

    private final static String BASE_URL = "http://api.openweathermap.org/";
    private final static String API_KEY = "7d30ef6a6b2ea73af65fed06d8bb1c48";

    private static WeatherLoader weatherLoader;

    private static Weather weather;

    public static void init() {
        if (weatherLoader == null) {
            weatherLoader = new WeatherLoader();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(createHttpClient())
                    .build();
            weather = retrofit.create(Weather.class);
        }
    }

    private static OkHttpClient createHttpClient() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        return client.build();
    }

    public static WeatherLoader get() {
        return weatherLoader;
    }

    public void doRequest(String city, final Context context, final boolean update) {
        weather.loadWeather(city, API_KEY)
                .enqueue(new Callback<WeatherRequest>() {
                    @Override
                    public void onResponse(Call<WeatherRequest> call, Response<WeatherRequest> response) {
                        Intent intent;
                        if (update) {
                            intent = new Intent(Constants.BROADCAST_UPDATE_TAG);
                        } else {
                            intent = new Intent(Constants.BROADCAST_LOAD_TAG);
                        }
                        int requestCod;
                        if (response.body() != null) {
                            requestCod = response.body().getCod();
                            intent.putExtras(Parser.get().requestParseToIntent(response.body(), Preferences.get().setAndGetLastUpdTime()));
                        } else {
                            requestCod = 404;
                        }
                        intent.putExtra(Constants.REQUEST_TAG, requestCod);
                        intent.putExtra(Constants.REQUEST_MASSAGE_TAG, response.message());
                        context.sendBroadcast(intent);
                    }

                    @Override
                    public void onFailure(Call<WeatherRequest> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    public void doRequest(String latitude,String longitude, final Context context) {
        weather.loadWeather(latitude, longitude, API_KEY)
                .enqueue(new Callback<WeatherRequest>() {
                    @Override
                    public void onResponse(Call<WeatherRequest> call, Response<WeatherRequest> response) {
                        Intent intent = new Intent(MainActivity.BROADCAST);
                        int requestCod;
                        if (response.body() != null) {
                            requestCod = response.body().getCod();
                            intent.putExtras(Parser.get().requestParseToIntent(response.body(), Preferences.get().setAndGetLastUpdTime()));
                        } else {
                            requestCod = 404;
                        }
                        intent.putExtra(Constants.REQUEST_TAG, requestCod);
                        context.sendBroadcast(intent);
                    }

                    @Override
                    public void onFailure(Call<WeatherRequest> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }
}
