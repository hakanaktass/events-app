package com.example.project.Calendar;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;

import java.time.LocalDate;
import java.util.ArrayList;

class CalendarTODOAdapter extends RecyclerView.Adapter<CalendarViewHolder>
{
    private final ArrayList<LocalDate> daysArrayList;
    private final OnItemListener onItemListener;

    public CalendarTODOAdapter(ArrayList<LocalDate> daysArrayList, OnItemListener onItemListener)
    {
        this.daysArrayList = daysArrayList;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if(daysArrayList.size() > 15) //month view
            layoutParams.height = (int) (parent.getHeight() * 0.166666666);
        else // week view
            layoutParams.height = (int) parent.getHeight();

        return new CalendarViewHolder(view, onItemListener, daysArrayList);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position)
    {
        final LocalDate date = daysArrayList.get(position);

        holder.dayInMonth.setText(String.valueOf(date.getDayOfMonth()));

        if(date.equals(CalendarTools.selectedDate))
            holder.pView.setBackgroundColor(Color.LTGRAY);

        if(date.getMonth().equals(CalendarTools.selectedDate.getMonth()))
            holder.dayInMonth.setTextColor(Color.BLACK);
        else
            holder.dayInMonth.setTextColor(Color.LTGRAY);
    }

    @Override
    public int getItemCount()
    {
        return daysArrayList.size();
    }

    public interface  OnItemListener
    {
        void onItemClick(int position, LocalDate date);
    }
}
