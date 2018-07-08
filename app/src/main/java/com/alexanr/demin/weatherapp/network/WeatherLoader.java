package com.alexanr.demin.weatherapp.network;

import com.alexanr.demin.weatherapp.network.model.WeatherRequest;

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

    private Weather weather;
    private WeatherListener listener;

    public interface WeatherListener {
        void onWeatherResponse(WeatherRequest request);

        void onWeatherFailure(String msg);
    }

    public WeatherLoader(WeatherListener listener) {
        this.listener = listener;
    }

    public void init() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(createHttpClient())
                .build();
        weather = retrofit.create(Weather.class);
    }

    private OkHttpClient createHttpClient() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        return client.build();
    }

    public void doRequest(String city) {
        weather.loadWeather(city, API_KEY)
                .enqueue(new Callback<WeatherRequest>() {
                    @Override
                    public void onResponse(Call<WeatherRequest> call, Response<WeatherRequest> response) {
                        if (response.body() != null)
                            listener.onWeatherResponse(response.body());
                    }

                    @Override
                    public void onFailure(Call<WeatherRequest> call, Throwable t) {
                        t.printStackTrace();
                        //System.out.println(t.getMessage());
                        listener.onWeatherFailure(t.getMessage());
                    }
                });

    }

}
