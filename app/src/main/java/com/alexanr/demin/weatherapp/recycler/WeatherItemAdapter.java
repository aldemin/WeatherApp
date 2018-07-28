package com.alexanr.demin.weatherapp.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alexanr.demin.weatherapp.R;
import com.alexanr.demin.weatherapp.database.City;

import java.util.ArrayList;
import java.util.List;

public class WeatherItemAdapter extends RecyclerView.Adapter<WeatherItemAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView city;
        private TextView temperature;
        private TextView weatherParams;

        ViewHolder(View itemView) {
            super(itemView);
            city = itemView.findViewById(R.id.weather_item_city);
            temperature = itemView.findViewById(R.id.weather_item_temp);
            weatherParams = itemView.findViewById(R.id.weather_item_params);
        }

        void bind(City item) {
            this.city.setText(item.getName());
            this.temperature.setText(String.valueOf(item.getTemperature()));
            this.weatherParams.setText(item.getWeatherParams());
        }
    }

    private List<City> weatherList = new ArrayList<>();

    public void setWeatherList(List<City> list) {
        this.weatherList.addAll(list);
        notifyDataSetChanged();
    }

    public void setWeatherItem(City item) {
        this.weatherList.add(item);
        notifyDataSetChanged();
    }

    public void cleanWeatherItems() {
        this.weatherList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_weather_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(weatherList.get(position));
    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }
}
