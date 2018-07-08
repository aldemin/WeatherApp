package com.alexanr.demin.weatherapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alexanr.demin.weatherapp.R;
import com.alexanr.demin.weatherapp.fragment.TodayFragment;
import com.alexanr.demin.weatherapp.network.WeatherLoader;
import com.alexanr.demin.weatherapp.network.model.WeatherRequest;
import com.alexanr.demin.weatherapp.util.Constants;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    View header;
    TextView temp;
    TextView headerCity;
    EditText enterCity;
    Button enterCityOkBtn;

    WeatherLoader weatherLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.main_drawer);
        navigationView = findViewById(R.id.nav_view);
        header = navigationView.inflateHeaderView(R.layout.header);
        enterCity = findViewById(R.id.edit_city);
        enterCityOkBtn = findViewById(R.id.button_ok);
        temp = header.findViewById(R.id.header_weather_temp);
        headerCity = header.findViewById(R.id.header_city);

        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //setCity(null);

        if (getCity() == null) {
            initTextWatcherListener();
            initOkBtnListener();
        } else {
            loadWeather();
        }

    }

    private String getCity() {
        final String emptyCity = null;
        return getSharedPreferences(Constants.PREF_NAME_TAG, MODE_PRIVATE).getString(Constants.PREF_CITY_TAG, emptyCity);
    }

    private void setCity(String city) {
        getSharedPreferences(Constants.PREF_NAME_TAG, MODE_PRIVATE).edit().putString(Constants.PREF_CITY_TAG, city).apply();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.main_menu_today:
                break;
            case R.id.main_menu_week:
                break;
            case R.id.main_menu_settings:
                break;
            case R.id.main_menu_write:
                break;
            case R.id.main_menu_about:
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void inflateFragment(WeatherRequest request) {
        Intent intent = new Intent().putExtra(Constants.WEATHER_TEMP_TAG, request.getMain().getTemp())
                .putExtra(Constants.WEATHER_TEMP_MAX_TAG, request.getMain().getTemp_max())
                .putExtra(Constants.WEATHER_TEMP_MIN_TAG, request.getMain().getTemp_min())
                .putExtra(Constants.WEATHER_HUMIDITY_TAG, request.getMain().getHumidity())
                .putExtra(Constants.WEATHER_PRESSURE_TAG, request.getMain().getPressure())
                .putExtra(Constants.WEATHER_PARAMS_TAG, request.getWeather()[0].getMain());

        TodayFragment fragment = new TodayFragment();
        fragment.setArguments(intent.getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.main_layout_fragment, fragment).commit();
    }

    private void initTextWatcherListener() {
        this.enterCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() > 0) {
                    enterCityOkBtn.setEnabled(true);
                } else {
                    enterCityOkBtn.setEnabled(false);
                }
            }
        });
    }

    private void initOkBtnListener() {
        this.enterCityOkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCity(enterCity.getText().toString());
                loadWeather();
            }
        });
    }

    private void loadWeather() {
        final TextView loadingText = findViewById(R.id.main_loading_progress);
        loadingText.setText(R.string.loading);

        findViewById(R.id.main_city_enter).setVisibility(View.GONE);
        findViewById(R.id.main_loading).setVisibility(View.VISIBLE);

        weatherLoader = new WeatherLoader(new WeatherLoader.WeatherListener() {
            @Override
            public void onWeatherResponse(WeatherRequest request) {
                String tmp = String.format("+%.0f",(request.getMain().getTemp()) - 273.15);
                temp.setText(tmp);
                headerCity.setText(request.getName());
                findViewById(R.id.main_city_enter_layout).setVisibility(View.GONE);
                findViewById(R.id.main_drawer).setVisibility(View.VISIBLE);
                inflateFragment(request);
            }

            @Override
            public void onWeatherFailure(String msg) {
                findViewById(R.id.main_city_enter).setVisibility(View.GONE);
                findViewById(R.id.main_loading).setVisibility(View.VISIBLE);
                loadingText.setText(R.string.error);
            }
        });
        weatherLoader.init();
        weatherLoader.doRequest(getCity());
    }
}
