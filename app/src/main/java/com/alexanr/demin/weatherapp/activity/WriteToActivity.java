package com.alexanr.demin.weatherapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alexanr.demin.weatherapp.R;

public class WriteToActivity extends AppCompatActivity {

    private final static String DEV_EMAIL = "aldemindev@gmail.com";

    private EditText theme;
    private EditText massage;
    private Button sendBtn;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_to);
        initViews();
        initListeners();
        inflateBar();
        sendBtn.setEnabled(false);
    }

    private void initViews() {
        theme = findViewById(R.id.write_to_theme);
        massage = findViewById(R.id.write_to_massage);
        sendBtn = findViewById(R.id.write_to_sendBtn);
        toolbar = findViewById(R.id.write_to_toolbar);
    }

    private void inflateBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(R.string.write_to_developer);
    }

    private void initListeners() {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String themeText = theme.getText().toString();
                String massageText = massage.getText().toString();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{DEV_EMAIL});
                intent.putExtra(Intent.EXTRA_SUBJECT, themeText);
                intent.putExtra(Intent.EXTRA_TEXT, massageText);
                intent.setType("message/rfc822");
                startActivity(intent);
            }
        };
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (theme.getText().toString().trim().length() > 0 && massage.getText().toString().trim().length() > 0) {
                    sendBtn.setEnabled(true);
                } else {
                    sendBtn.setEnabled(false);
                }
            }
        };

        sendBtn.setOnClickListener(onClickListener);
        theme.addTextChangedListener(textWatcher);
        massage.addTextChangedListener(textWatcher);
    }

    @Override
    protected void onDestroy() {
        sendBtn.setOnClickListener(null);
        theme.addTextChangedListener(null);
        massage.addTextChangedListener(null);
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
