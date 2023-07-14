package com.example.project.Calendar;

import static com.example.project.Calendar.CalendarTools.selectedDate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.project.R;

import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class DailyActivity extends AppCompatActivity {


        private TextView dayOfMonthTextView;
        private TextView dayOfWeekTextView;
        private ListView hourListView;

        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_daily_calendar);
            initWidgets();
            Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.customBackground)));
            getSupportActionBar().setTitle("");
        }

        private void initWidgets()
        {
            dayOfMonthTextView = findViewById(R.id.monthDayText);
            dayOfWeekTextView = findViewById(R.id.dayOfWeekTV);
            hourListView = findViewById(R.id.hourListView);
        }

        @Override
        protected void onResume()
        {
            super.onResume();
            setDayView();
        }

        private void setDayView()
        {
            dayOfMonthTextView.setText(CalendarTools.monthDayFromDate(selectedDate));
            String dayOfWeek = selectedDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
            dayOfWeekTextView.setText(dayOfWeek);
            setHourAdapter();
        }

        private void setHourAdapter()
        {
            HourAdapter hourAdapter = new HourAdapter(getApplicationContext(), hourEventList());
            hourListView.setAdapter(hourAdapter);
        }

        private ArrayList<HourOfEvent> hourEventList()
        {
            ArrayList<HourOfEvent> list = new ArrayList<>();

            for(int hour = 0; hour < 24; hour++)
            {
                LocalTime time = LocalTime.of(hour, 0);
                ArrayList<Event> events = Event.eInDateAndTime(selectedDate, time);
                HourOfEvent hourOfEvent = new HourOfEvent(time, events);
                list.add(hourOfEvent);
            }

            return list;
        }

        public void prevDayOnClick(View view)
        {
            selectedDate = selectedDate.minusDays(1);
            setDayView();
        }

        public void nextDayOnClick(View view)
        {
            selectedDate = selectedDate.plusDays(1);
            setDayView();
        }

        public void eventOnClick(View view)
        {
            startActivity(new Intent(this, EventDetailsActivity.class));
        }

}