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

public class TodayFragment extends Fragment {

    TextView temp;
    TextView tempMax;
    TextView tempMin;
    TextView weatherParams;
    TextView humidity;
    TextView pressure;
    TextView lastUpd;

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

        update();

        return view;
    }

    public void update() {
        City city = Database.get().getDataBase().citiesDao().getByName(Preferences.get().getCity());
        String tmp = String.format("+%.0f",(city.getTemperature() - 273.15));
        temp.setText(String.valueOf(tmp));

        tmp = String.format("+%.0f",(city.getMaxTemperature() - 273.15));
        tempMax.setText(tmp);

        tmp = String.format("+%.0f",(city.getMinTemperature() - 273.15));
        tempMin.setText(tmp);

        weatherParams.setText(city.getName());
        humidity.setText(String.valueOf(city.getHumidity()));
        pressure.setText(String.valueOf(city.getPressure()));
        lastUpd.setText(String.valueOf(city.getLastUpd()));
    }

}
