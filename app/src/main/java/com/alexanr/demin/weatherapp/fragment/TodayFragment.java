package com.alexanr.demin.weatherapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alexanr.demin.weatherapp.R;
import com.alexanr.demin.weatherapp.util.Constants;

public class TodayFragment extends Fragment {

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

        String tmp = String.format("+%.0f",(bundle.getFloat(Constants.WEATHER_TEMP_TAG) - 273.15));
        temp.setText(tmp);

        tmp = String.format("+%.0f",(bundle.getFloat(Constants.WEATHER_TEMP_MAX_TAG) - 273.15));
        tempMax.setText(tmp);

        tmp = String.format("+%.0f",(bundle.getFloat(Constants.WEATHER_TEMP_MIN_TAG) - 273.15));
        tempMin.setText(tmp);

        weatherParams.setText(bundle.getString(Constants.WEATHER_PARAMS_TAG).toUpperCase());
        humidity.setText(String.valueOf(bundle.getFloat(Constants.WEATHER_HUMIDITY_TAG)));
        pressure.setText(String.valueOf(bundle.getFloat(Constants.WEATHER_PRESSURE_TAG)));
        return view;
    }

}
