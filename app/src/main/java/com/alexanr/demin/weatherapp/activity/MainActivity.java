package com.alexanr.demin.weatherapp.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alexanr.demin.weatherapp.R;
import com.alexanr.demin.weatherapp.database.City;
import com.alexanr.demin.weatherapp.database.Database;
import com.alexanr.demin.weatherapp.fragment.TodayFragment;
import com.alexanr.demin.weatherapp.network.WeatherLoader;
import com.alexanr.demin.weatherapp.network.request.WeatherRequest;
import com.alexanr.demin.weatherapp.util.CityParser;
import com.alexanr.demin.weatherapp.util.Preferences;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    View header;
    TextView headerTemp;
    TextView headerCity;
    TextView headerLastUpd;
    FloatingActionButton FAB;

    AlertDialog.Builder dialog;

    TodayFragment todayFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.main_drawer);
        navigationView = findViewById(R.id.nav_view);
        header = navigationView.inflateHeaderView(R.layout.header);
        headerTemp = header.findViewById(R.id.header_weather_temp);
        headerCity = header.findViewById(R.id.header_city);
        headerLastUpd = header.findViewById(R.id.header_last_upd);
        FAB = findViewById(R.id.fab);

        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        initNewCityDialog();
        initSetNewCityListener();
        initFABListener();

        if (Preferences.get().getCity() == null) {
            dialog.show();
        } else {
            loadWeather();
        }

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

    private void inflateFragment() {
        if (todayFragment == null) {
            todayFragment = new TodayFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.main_layout_fragment, todayFragment).commit();
        } else {
            todayFragment.update();
        }
    }

    private void inflateHeader() {
        findViewById(R.id.header_loading).setVisibility(View.GONE);
        City city = Database.get().getDataBase().citiesDao().getByName(Preferences.get().getCity());
        headerTemp.setText(String.format("+%.0f", (city.getTemperature() - 273.15)));
        headerCity.setText(String.valueOf(city.getName()));
        headerLastUpd.setText(city.getLastUpd());
    }

    private void initNewCityDialog() {
        dialog = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.change_city_dialog, null);
        final EditText editText = dialogView.findViewById(R.id.change_city);
        dialog.setView(dialogView)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (editText.getText() != null) {
                            Preferences.get().setCity(editText.getText().toString());
                            loadWeather();
                        }
                    }
                })
                .setCancelable(false)
                .create();
    }

    private void initSetNewCityListener() {
        headerCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initNewCityDialog();
                dialog.show();
            }
        });
    }

    private void initFABListener() {
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadWeather();
            }
        });
    }

    private void loadWeather() {
        WeatherLoader.get().setListener(new WeatherLoader.WeatherListener() {
            @Override
            public void onWeatherResponse(WeatherRequest request) {
                if (Database.get().lineIsExist(request.getName())) {
                    City city = CityParser.get().parse(request, Preferences.get().setAndGetLastUpdTime());
                    city.setId(Database.get().getIdByName(request.getName()));
                    Database.get().getDataBase().citiesDao().update(city);
                } else {
                    Database.get().getDataBase().citiesDao().insert(CityParser.get().parse(request, Preferences.get().setAndGetLastUpdTime()));
                }
                Preferences.get().setCity(request.getName());
                inflateFragment();
                inflateHeader();
            }
        });
        WeatherLoader.get().doRequest(Preferences.get().getCity());
    }
}
