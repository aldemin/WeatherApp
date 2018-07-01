package com.alexanr.demin.weatherapp.activity;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.alexanr.demin.weatherapp.service.WeatherInfoLoader;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int WEATHER_LOAD_KEY = 1;
    private static final String CITY_PREF_KEY = "92hf3902hf02f";

    private final String preferenceFileName = "myPref";

    public static final String PI_TAG = "pending";
    public static final String CITY_TAG = "city";

    public final static int STATUS_FINISH = 200;

    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    View header;
    TextView temp;
    TextView headerCity;
    EditText enterCity;
    Button enterCityOkBtn;

    String cityValue;

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

        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        final SharedPreferences sharedPref = getSharedPreferences(preferenceFileName, MODE_PRIVATE);
        cityValue = sharedPref.getString(CITY_PREF_KEY, null);

        if (cityValue == null) {
            setTextWatcher();
            this.enterCityOkBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cityValue = enterCity.getText().toString();
                    sharedPref.edit().putString(CITY_PREF_KEY, cityValue).apply();
                    startService();

                }
            });
        } else {
            startService();
        }

    }

    private void startService() {
        findViewById(R.id.main_city_enter).setVisibility(View.GONE);
        findViewById(R.id.main_loading).setVisibility(View.VISIBLE);
        Intent intent = new Intent(getApplicationContext(), WeatherInfoLoader.class);
        PendingIntent pi = createPendingResult(WEATHER_LOAD_KEY, intent, 0);
        startService(intent.putExtra(PI_TAG, pi)
                .putExtra(CITY_TAG, cityValue));
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == STATUS_FINISH) {
            fillDrawer(data);
        }
    }

    private void fillDrawer(Intent data) {
        temp = header.findViewById(R.id.header_weather_temp);
        headerCity = header.findViewById(R.id.header_city);
        String tmp = String.format("+%.0f", (data.getDoubleExtra(TodayFragment.TEMP_KEY, 0) - 273.15));
        temp.setText(tmp);
        headerCity.setText(cityValue.toUpperCase());
        inflateFragment(data);
    }

    private void inflateFragment(Intent data) {
        TodayFragment fragment = new TodayFragment();
        fragment.setArguments(data.getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.main_layout_fragment, fragment).commit();
        findViewById(R.id.main_city_enter_layout).setVisibility(View.GONE);
        findViewById(R.id.main_drawer).setVisibility(View.VISIBLE);
    }

    private void setTextWatcher() {
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
}
