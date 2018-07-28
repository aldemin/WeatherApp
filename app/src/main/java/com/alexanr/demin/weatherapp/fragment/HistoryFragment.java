package com.alexanr.demin.weatherapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexanr.demin.weatherapp.R;
import com.alexanr.demin.weatherapp.database.Database;
import com.alexanr.demin.weatherapp.recycler.WeatherItemAdapter;

import java.util.Objects;

public class HistoryFragment extends Fragment implements UpdatableFragment {

    RecyclerView recyclerView;
    WeatherItemAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new WeatherItemAdapter();
        recyclerView.setAdapter(adapter);
        update();
        return view;
    }

    public void update() {
        adapter.setWeatherList(Database.get().getCityList());
    }
}
