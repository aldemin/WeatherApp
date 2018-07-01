package com.alexanr.demin.weatherapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alexanr.demin.weatherapp.R;

public class TodayFragment extends Fragment {

    public static final String TEMP_KEY = "temperature";
    public static final String TEMP_MAX_KEY = "temperatureMax";
    public static final String TEMP_MIN_KEY = "temperatureMin";
    public static final String WEATHER_PARAMS_KEY = "weatherParams";
    public static final String HUMIDITY_KEY = "humidity";
    public static final String PRESSURE_KEY = "pressure";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today, container, false);
        Bundle bundle = getArguments();
        TextView temp = view.findViewById(R.id.today_temp_value);
        TextView tempMax = view.findViewById(R.id.today_temp_max_value);
        TextView tempMin = view.findViewById(R.id.today_temp_min_value);
        TextView weatherParams = view.findViewById(R.id.today_weather_params);
        TextView humidity = view.findViewById(R.id.today_hum_value);
        TextView pressure = view.findViewById(R.id.today_pres_value);

        String tmp = String.format("+%.0f",(bundle.getDouble(TEMP_KEY) - 273.15));
        temp.setText(tmp);

        tmp = String.format("+%.0f",(bundle.getDouble(TEMP_MAX_KEY) - 273.15));
        tempMax.setText(tmp);

        tmp = String.format("+%.0f",(bundle.getDouble(TEMP_MIN_KEY) - 273.15));
        tempMin.setText(tmp);

        weatherParams.setText(bundle.getString(WEATHER_PARAMS_KEY).toUpperCase());
        humidity.setText(bundle.getString(HUMIDITY_KEY));
        pressure.setText(bundle.getString(PRESSURE_KEY));
        return view;
    }

}
