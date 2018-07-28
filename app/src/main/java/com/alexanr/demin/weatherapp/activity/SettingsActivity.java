package com.alexanr.demin.weatherapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alexanr.demin.weatherapp.R;
import com.alexanr.demin.weatherapp.network.WeatherLoader;
import com.alexanr.demin.weatherapp.util.Constants;
import com.alexanr.demin.weatherapp.util.Preferences;

public class SettingsActivity extends AppCompatActivity {

    private final static float MARGIN_DP = 8;

    private Toolbar toolbar;
    private MenuItem save;
    private EditText cityEdit;
    private TextView cityText;
    private RelativeLayout cityLayout;
    private TextView hint;
    private TextView measureText;

    private RelativeLayout.LayoutParams cityLayoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initViews();
        inflateBar();
        initHintParams();
        setDefViewParams();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (cityEdit.getVisibility() == View.VISIBLE) {
                cityEdit.setVisibility(View.GONE);
                cityEdit.setText("");
                cityText.setVisibility(View.VISIBLE);
                save.setVisible(false);
                changeHintBelow(R.id.settings_city);
                Preferences.get().setCity(cityText.getText().toString());
                if (!Preferences.get().getMeasure().equals(measureText.getText().toString())) {
                    measureText.setText(Preferences.get().getMeasure());
                    save.setVisible(false);
                }
            } else {
                finish();
            }
        } else if (item.getItemId() == R.id.settings_save) {
            cityEdit.setVisibility(View.GONE);
            cityText.setVisibility(View.VISIBLE);
            String city = firstUpperCase(cityEdit.getText().toString());
            if (!city.equals("")) {
                cityText.setText(city);
            }
            save.setVisible(false);
            changeHintBelow(R.id.settings_city);
            if (!Preferences.get().getMeasure().equals(measureText.getText().toString())) {
                Preferences.get().setMeasure(firstUpperCase(measureText.getText().toString()));
            }
            Preferences.get().setCity(cityText.getText().toString());
            WeatherLoader.get().doRequest(Preferences.get().getCity(), getApplicationContext());
        }
        return super.onOptionsItemSelected(item);
    }

    private String firstUpperCase(String word) {
        if (word == null || word.isEmpty()) return "";
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }

    private void inflateBar() {
        toolbar = findViewById(R.id.settings_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(R.string.settings);
    }

    private void initViews() {
        save = findViewById(R.id.settings_save);
        cityText = findViewById(R.id.settings_city);
        cityEdit = findViewById(R.id.settings_city_edit);
        cityLayout = findViewById(R.id.settings_city_layout);
        measureText = findViewById(R.id.settings_measure);
        hint = cityLayout.findViewById(R.id.settings_city_hint);
    }

    private void setDefViewParams() {
        cityText.setText(Preferences.get().getCity());
        if (Preferences.get().getMeasure().equals(Constants.CELSIUS)) {
            measureText.setText(firstUpperCase(Constants.CELSIUS));
        } else {
            measureText.setText(firstUpperCase(Constants.FAHRENHEIT));
        }

        cityText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityEdit.setVisibility(View.VISIBLE);
                cityText.setVisibility(View.GONE);
                save.setVisible(true);
                changeHintBelow(R.id.settings_city_edit);
            }
        });

        measureText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (measureText.getText().equals(Constants.CELSIUS)) {
                    measureText.setText(Constants.FAHRENHEIT);
                } else {
                    measureText.setText(Constants.CELSIUS);
                }
                if (!measureText.getText().equals(Preferences.get().getMeasure())) {
                    save.setVisible(true);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_bar_menu, menu);
        save = menu.findItem(R.id.settings_save);
        return true;
    }

    private void initHintParams() {
        cityLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        cityLayoutParams.leftMargin = (int) (MARGIN_DP * getApplicationContext().getResources().getDisplayMetrics().density);
        cityLayoutParams.rightMargin = (int) (MARGIN_DP * getApplicationContext().getResources().getDisplayMetrics().density);
    }

    private void changeHintBelow(int idBelow) {
        cityLayout.removeView(hint);
        cityLayoutParams.addRule(RelativeLayout.BELOW, idBelow);
        cityLayout.addView(hint, cityLayoutParams);
    }
}
