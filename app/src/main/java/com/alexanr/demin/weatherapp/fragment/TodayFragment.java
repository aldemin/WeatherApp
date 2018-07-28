package com.alexanr.demin.weatherapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alexanr.demin.weatherapp.R;
import com.alexanr.demin.weatherapp.database.City;
import com.alexanr.demin.weatherapp.database.Database;
import com.alexanr.demin.weatherapp.util.Constants;
import com.alexanr.demin.weatherapp.util.Preferences;

public class TodayFragment extends Fragment implements UpdatableFragment {

    private TextView temp;
    private TextView tempMax;
    private TextView tempMin;
    private TextView weatherParams;
    private TextView humidity;
    private TextView pressure;
    private TextView lastUpd;
    private TextView tempLabel;
    private TextView maxTempLabel;
    private TextView minTempLabel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today, container, false);

        temp = view.findViewById(R.id.today_temp_value);
        tempMax = view.findViewById(R.id.today_temp_max_value);
        tempMin = view.findViewById(R.id.today_temp_min_value);
        weatherParams = view.findViewById(R.id.today_weather_params);
        humidity = view.findViewById(R.id.today_hum_value);
        pressure = view.findViewById(R.id.today_pres_value);
        lastUpd = view.findViewById(R.id.today_last_upd);
        tempLabel = view.findViewById(R.id.today_temp_label);
        maxTempLabel = view.findViewById(R.id.today_temp_max_label);
        minTempLabel = view.findViewById(R.id.today_temp_min_label);

        update();

        return view;
    }

    public void update() {
        City city = Database.get().getDataBase().citiesDao().getByName(Preferences.get().getCity());
        String temStr;
        String temStrMax;
        String temStrMin;
        if (Preferences.get().getMeasure().equals(Constants.CELSIUS)) {
            tempLabel.setText(getString(R.string.celsius_temp_sign));
            maxTempLabel.setText(getString(R.string.celsius_temp_sign));
            minTempLabel.setText(getString(R.string.celsius_temp_sign));
            temStr = String.format("+%.0f", (city.getTemperature() - 273.15));
            temStrMax = String.format("+%.0f", (city.getMaxTemperature() - 273.15));
            temStrMin = String.format("+%.0f", (city.getMinTemperature() - 273.15));
        } else {
            tempLabel.setText(getString(R.string.fahrenheit_temp_sign));
            maxTempLabel.setText(getString(R.string.fahrenheit_temp_sign));
            minTempLabel.setText(getString(R.string.fahrenheit_temp_sign));
            temStr = String.format("+%d", city.getTemperature());
            temStrMax = String.format("+%d", city.getMaxTemperature());
            temStrMin = String.format("+%d", city.getMinTemperature());
        }
        temp.setText(String.valueOf(temStr));
        tempMax.setText(temStrMax);
        tempMin.setText(temStrMin);

        weatherParams.setText(city.getWeatherParams());
        humidity.setText(String.valueOf(city.getHumidity()));
        pressure.setText(String.valueOf(city.getPressure()));
        lastUpd.setText(String.valueOf(city.getLastUpd()));
    }

}
