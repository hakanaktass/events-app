package com.example.project.MainScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import com.example.project.Calendar.CalendarTODOActivity;
import com.example.project.Crypto.CryptoCurrencyActivity;
import com.example.project.News.NewsMainActivity;
import com.example.project.R;
import com.example.project.WeatherActivity;

import java.util.Objects;

public class ApiAppsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_apps);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.customBackground)));
        getSupportActionBar().setTitle("General");
    }

    public void weatherOnClick(View view) {
        Intent intent = new Intent(ApiAppsActivity.this, WeatherActivity.class);
        startActivity(intent);
        finish();

    }

    public void calendarOnClick(View view) {
        Intent intent = new Intent(ApiAppsActivity.this, CalendarTODOActivity.class);
        startActivity(intent);
        finish();

    }

    public void cryptoOnClock(View view) {
        Intent intent = new Intent(ApiAppsActivity.this, CryptoCurrencyActivity.class);
        startActivity(intent);
        finish();

    }

    public void newsOnClick(View view) {
        Intent intent = new Intent(ApiAppsActivity.this, NewsMainActivity.class);
        startActivity(intent);
        finish();

    }
}