package com.example.project.Calendar;

import static com.example.project.Calendar.CalendarTools.daysInMonthArrayList;
import static com.example.project.Calendar.CalendarTools.monthYearFromDate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.project.R;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class CalendarTODOActivity extends AppCompatActivity implements CalendarTODOAdapter.OnItemListener{

    private TextView mytext;

    private RecyclerView calendarRV;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        initWidgets();
        CalendarTools.selectedDate = LocalDate.now();
        sMView();
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.customBackground)));
        getSupportActionBar().setTitle("");
    }



    private void sMView()
    {
        mytext.setText(monthYearFromDate(CalendarTools.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArrayList();

        CalendarTODOAdapter calendarTODOAdapter = new CalendarTODOAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRV.setLayoutManager(layoutManager);
        calendarRV.setAdapter(calendarTODOAdapter);
    }
    private void initWidgets()
    {
        mytext = findViewById(R.id.monthYearTV);
        calendarRV = findViewById(R.id.calendarRecyclerView);

    }

    public void nextMonth(View view)
    {
        CalendarTools.selectedDate = CalendarTools.selectedDate.plusMonths(1);
        sMView();
    }
    public void previousMonth(View view)
    {
        CalendarTools.selectedDate = CalendarTools.selectedDate.minusMonths(1);
        sMView();
    }



    @Override
    public void onItemClick(int position, LocalDate date)
    {
        if(date != null)
        {
            CalendarTools.selectedDate = date;
            sMView();
        }
    }

    public void weekOnClick(View view)
    {
        startActivity(new Intent(CalendarTODOActivity.this, CalendarWeekActivity.class));
    }
}