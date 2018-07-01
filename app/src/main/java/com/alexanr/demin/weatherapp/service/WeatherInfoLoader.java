package com.alexanr.demin.weatherapp.service;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;

import com.alexanr.demin.weatherapp.R;
import com.alexanr.demin.weatherapp.activity.MainActivity;
import com.alexanr.demin.weatherapp.fragment.TodayFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherInfoLoader extends IntentService {

    public WeatherInfoLoader() {
        super("WeatherInfoLoader");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            String city = intent.getStringExtra(MainActivity.CITY_TAG);
            URL url = new URL(String.format("http://api.openweathermap.org/data/2.5/weather?q=%s", city));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("x-api-key", getApplicationContext().getString(R.string.api_key));

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuilder stringBuilder = new StringBuilder();
            String tmp;
            while ((tmp = reader.readLine()) != null) {
                stringBuilder.append(tmp).append("\n");
            }
            reader.close();
            connection.disconnect();

            PendingIntent pi = intent.getParcelableExtra(MainActivity.PI_TAG);
            Intent outIntent = new Intent();

            parseJSON(outIntent, stringBuilder.toString());

            pi.send(WeatherInfoLoader.this, MainActivity.STATUS_FINISH, outIntent);
        } catch (PendingIntent.CanceledException | IOException e) {
            e.printStackTrace();
        }
    }

    private void parseJSON(Intent intent, String JSON) {
        try {
            JSONObject jsonObject = new JSONObject(JSON);
            intent.putExtra(TodayFragment.TEMP_KEY, jsonObject.getJSONObject("main").getDouble("temp"));
            intent.putExtra(TodayFragment.TEMP_MAX_KEY, jsonObject.getJSONObject("main").getDouble("temp_max"));
            intent.putExtra(TodayFragment.TEMP_MIN_KEY, jsonObject.getJSONObject("main").getDouble("temp_min"));
            intent.putExtra(TodayFragment.WEATHER_PARAMS_KEY, jsonObject.getJSONArray("weather").getJSONObject(0).getString("main"));
            intent.putExtra(TodayFragment.HUMIDITY_KEY, jsonObject.getJSONObject("main").getString("humidity"));
            intent.putExtra(TodayFragment.PRESSURE_KEY, jsonObject.getJSONObject("main").getString("pressure"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
