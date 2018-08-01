package com.alexanr.demin.weatherapp.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alexanr.demin.weatherapp.R;
import com.alexanr.demin.weatherapp.network.WeatherLoader;
import com.alexanr.demin.weatherapp.util.Constants;
import com.alexanr.demin.weatherapp.util.Preferences;

public class EnterDialogFragment extends DialogFragment implements View.OnClickListener {

    TextView greeting;
    EditText editCity;
    LinearLayout cityLayout;
    ProgressBar progressBar;
    RadioButton celsiusBtn;
    BroadcastReceiver receiver;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enter_dialog, null);
        Button exitBtn = view.findViewById(R.id.enter_dialog_exit_btn);
        Button okBtn = view.findViewById(R.id.enter_dialog_ok_btn);
        celsiusBtn = view.findViewById(R.id.enter_dialog_celsius);
        greeting = view.findViewById(R.id.enter_dialog_greeting);
        editCity = view.findViewById(R.id.enter_dialog_edit_city);
        cityLayout = view.findViewById(R.id.enter_dialog_city_layout);
        progressBar = view.findViewById(R.id.enter_dialog_loading);

        exitBtn.setOnClickListener(this);
        okBtn.setOnClickListener(this);
        celsiusBtn.setChecked(true);

        initReceiver();
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.enter_dialog_ok_btn) {
            if (editCity.getText().toString().trim().equals("")) {
                greeting.setText(getText(R.string.empty_enter));
            } else {
                cityLayout.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                Preferences.get().setCity(editCity.getText().toString());
                WeatherLoader.get().doRequest(Preferences.get().getCity(), getContext(), false);
            }
        } else if (v.getId() == R.id.enter_dialog_exit_btn){
            getActivity().finish();
        }
    }

    private void initReceiver() {
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int cod = intent.getIntExtra(Constants.REQUEST_TAG, Constants.REQUEST_FAILED_TAG);
                if (cod == Constants.REQUEST_OK_TAG) {
                    Preferences.get().setCity(intent.getStringExtra(Constants.PREF_CITY_TAG));
                    if (celsiusBtn.isChecked()) {
                        Preferences.get().setMeasure(Constants.CELSIUS);
                    } else {
                        Preferences.get().setMeasure(Constants.FAHRENHEIT);
                    }
                    Intent receiverIntent = new Intent(Constants.BROADCAST_UPDATE_TAG);
                    receiverIntent.putExtras(intent);
                    getContext().sendBroadcast(receiverIntent);
                    dismiss();
                } else {
                    cityLayout.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    greeting.setText(intent.getStringExtra(Constants.REQUEST_MASSAGE_TAG));
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter(Constants.BROADCAST_LOAD_TAG);
        getContext().registerReceiver(receiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        getContext().unregisterReceiver(receiver);
        super.onDestroy();
    }
}
