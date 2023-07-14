package com.example.project.Calendar;

import static com.example.project.Calendar.CalendarTools.daysInWeekArrayList;
import static com.example.project.Calendar.CalendarTools.monthYearFromDate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.project.R;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;


public class CalendarWeekActivity extends AppCompatActivity implements CalendarTODOAdapter.OnItemListener {

    private TextView monthYearTextView;
    private RecyclerView calendarRecyclerView;
    private ListView eventListView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view);
        initWidgets();
        setWeekView();
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.customBackground)));
        getSupportActionBar().setTitle("");
    }

    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearTextView = findViewById(R.id.monthYearTV);
        eventListView = findViewById(R.id.eventListView);
    }

    private void setWeekView()
    {
        monthYearTextView.setText(monthYearFromDate(CalendarTools.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArrayList(CalendarTools.selectedDate);

        CalendarTODOAdapter calendarTODOAdapter = new CalendarTODOAdapter(days,this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarTODOAdapter);
        setEventAdpater();
    }


    public void previousWeekAction(View view)
    {
        CalendarTools.selectedDate = CalendarTools.selectedDate.minusWeeks(1);
        setWeekView();
    }

    public void nextWeekAction(View view)
    {
        CalendarTools.selectedDate = CalendarTools.selectedDate.plusWeeks(1);
        setWeekView();
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
        CalendarTools.selectedDate = date;
        setWeekView();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setEventAdpater();
    }

    private void setEventAdpater()
    {
        ArrayList<Event> dailyEvents = Event.eInDate(CalendarTools.selectedDate);
        EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), dailyEvents);
        eventListView.setAdapter(eventAdapter);
    }

    public void newEventAction(View view)
    {
        startActivity(new Intent(this, EventDetailsActivity.class));
    }

    public void dailyAction(View view)
    {
        startActivity(new Intent(this, DailyActivity.class));
    }
}