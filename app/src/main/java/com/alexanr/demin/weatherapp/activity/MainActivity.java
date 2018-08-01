package com.alexanr.demin.weatherapp.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.alexanr.demin.weatherapp.R;
import com.alexanr.demin.weatherapp.database.City;
import com.alexanr.demin.weatherapp.database.Database;
import com.alexanr.demin.weatherapp.fragment.EnterDialogFragment;
import com.alexanr.demin.weatherapp.fragment.HistoryFragment;
import com.alexanr.demin.weatherapp.fragment.TodayFragment;
import com.alexanr.demin.weatherapp.network.WeatherLoader;
import com.alexanr.demin.weatherapp.util.Constants;
import com.alexanr.demin.weatherapp.util.Parser;
import com.alexanr.demin.weatherapp.util.Preferences;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public final static String BROADCAST = "com.alexanr.demin.weatherapp.activity";

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private View header;
    private TextView headerTemp;
    private TextView headerCity;
    private TextView headerLastUpd;
    private TextView headerMeasureLabel;
    private FloatingActionButton FAB;
    private EnterDialogFragment dialog;
    private TodayFragment todayFragment;
    private HistoryFragment historyFragment;
    private BroadcastReceiver receiver;
    private IntentFilter intentFilter;

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
        headerMeasureLabel = header.findViewById(R.id.header_weather_temp_label);
        FAB = findViewById(R.id.fab);

        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        initNewCityDialog();
        initFABListener();
        initReceiver();

        Preferences.get().setCity(null);

        if (Preferences.get().getCity() == null) {
            dialog.show(getSupportFragmentManager(), "dialog");
        } else {
            WeatherLoader.get().doRequest(Preferences.get().getCity(), getApplicationContext(), true);
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
                inflateTodayFragment();
                break;
            case R.id.main_menu_week:
                //inflateHistoryFragment();
                break;
            case R.id.main_menu_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case R.id.main_menu_write:
                startActivity(new Intent(this, WriteToActivity.class));
                break;
            case R.id.main_menu_about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void inflateTodayFragment() {
        if (todayFragment == null) {
            todayFragment = new TodayFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.main_layout_fragment, todayFragment).commit();
        } else {
            todayFragment.update();
        }
        FAB.setVisibility(View.VISIBLE);
    }

/*    private void inflateHistoryFragment() {
        if (historyFragment == null) {
            historyFragment = new HistoryFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.main_layout_fragment, historyFragment).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_layout_fragment, historyFragment).commit();
        }
        FAB.setVisibility(View.GONE);
    }*/

    private void inflateHeader() {
        findViewById(R.id.header_loading).setVisibility(View.GONE);
        City city = Database.get().getDataBase().citiesDao().getByName(Preferences.get().getCity());
        if (Preferences.get().getMeasure().equals(Constants.CELSIUS)) {
            headerTemp.setText(String.format("+%.0f", (city.getTemperature() - 273.15)));
            headerMeasureLabel.setText(R.string.celsius_temp_sign);

        } else {
            headerTemp.setText(String.format("+%d", city.getTemperature()));
            headerMeasureLabel.setText(R.string.fahrenheit_temp_sign);
        }
        headerCity.setText(String.valueOf(city.getName()));
        headerLastUpd.setText(city.getLastUpd());
    }

    private void initNewCityDialog() {
        dialog = new EnterDialogFragment();
        dialog.setCancelable(false);
/*        final View dialogView = getLayoutInflater().inflate(R.layout.change_city_dialog, null);
        final EditText editText = dialogView.findViewById(R.id.change_city);
        final RadioButton celsiusBtn = dialogView.findViewById(R.id.city_dialog_celsius);
        final Button gpsBtn = dialogView.findViewById(R.id.city_dialog_gps_btn);
        final TextView gps = dialogView.findViewById(R.id.change_dialo_gps);
        final LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        celsiusBtn.setChecked(true);
        dialog.setView(dialogView)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (editText.getText() != null) {
                                    if (editText.getVisibility() == View.VISIBLE) {
                                        Preferences.get().setCity(editText.getText().toString());
                                        WeatherLoader.get().doRequest(Preferences.get().getCity(), getApplicationContext());
                                    } else {
                                        WeatherLoader.get().doRequest(latitude, longitude, getApplicationContext());
                                    }
                                }
                                if (celsiusBtn.isChecked()) {
                                    Preferences.get().setMeasure(Constants.CELSIUS);
                                } else {
                                    Preferences.get().setMeasure(Constants.FAHRENHEIT);
                                }
                            }
                        })
                .setCancelable(false)
                .create();*/

    }

    private void initFABListener() {
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeatherLoader.get().doRequest(Preferences.get().getCity(), getApplicationContext(), true);
            }
        });
    }

    private void initReceiver() {
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int cod = intent.getIntExtra(Constants.REQUEST_TAG, Constants.REQUEST_FAILED_TAG);
                if (cod == Constants.REQUEST_OK_TAG) {
                    setWeather(intent);
                }
            }
        };
        intentFilter = new IntentFilter(Constants.BROADCAST_UPDATE_TAG);
        registerReceiver(receiver, intentFilter);
    }

    private void setWeather(Intent intent) {
        if (Database.get().lineIsExist(intent.getStringExtra(Constants.PREF_CITY_TAG))) {
            City city = Parser.get().intentParseToCity(intent);
            city.setId(Database.get().getIdByName(intent.getStringExtra(Constants.PREF_CITY_TAG)));
            Database.get().getDataBase().citiesDao().update(city);
        } else {
            Database.get().getDataBase().citiesDao().insert(Parser.get().intentParseToCity(intent));
        }
        Preferences.get().setCity(intent.getStringExtra(Constants.PREF_CITY_TAG));
        inflateTodayFragment();
        inflateHeader();
    }

    @Override
    public void finish() {
        unregisterReceiver(receiver);
        super.finish();
    }
}
