package com.alexanr.demin.weatherapp.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.alexanr.demin.weatherapp.R;

public class AboutActivity extends AppCompatActivity {

    private static final String GIT_LINK = "https://github.com/aldemin/WeatherApp";
    private static final String LEADER_LINK = "https://geekbrains.ru/users/41700";

    private TextView git;
    private TextView leader;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initViews();
        inflateBar();

        initListener(git, GIT_LINK);
        initListener(leader, LEADER_LINK);
    }

    private void initViews() {
        git = findViewById(R.id.about_git);
        leader = findViewById(R.id.about_leader);
        toolbar = findViewById(R.id.about_toolbar);
    }

    private void inflateBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(R.string.about_app);
    }

    private void initListener(TextView view, final String link) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                startActivity(intent);
            }
        };
        view.setOnClickListener(listener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        git.setOnClickListener(null);
        leader.setOnClickListener(null);
        super.onDestroy();
    }
}
