package com.example.project.Calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.project.R;

import java.time.LocalTime;
import java.util.Objects;

public class EventDetailsActivity extends AppCompatActivity {
    private EditText eNameEditText;
    private TextView eDateTextView, eTimeTextView;

    private LocalTime time;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.customBackground)));
        getSupportActionBar().setTitle("");
        time = LocalTime.now();
        eDateTextView.setText("Date: " + CalendarTools.formattedDate(CalendarTools.selectedDate));
        eTimeTextView.setText("Time: " + CalendarTools.formattedTime(time));
    }

    private void initWidgets()
    {
        eNameEditText = findViewById(R.id.eventNameET);
        eDateTextView = findViewById(R.id.eventDateTV);
        eTimeTextView = findViewById(R.id.eventTimeTV);
    }

    public void saveEventAction(View view)
    {
        String eventName = eNameEditText.getText().toString();
        Event newEvent = new Event(eventName, CalendarTools.selectedDate, time);
        Event.eventsArrayList.add(newEvent);
        finish();
    }
}