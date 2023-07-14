package com.example.project.Calendar;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    private final ArrayList<LocalDate> days;
    public final View pView;
    public final TextView dayInMonth;
    private final CalendarTODOAdapter.OnItemListener onItemListener;
    public CalendarViewHolder(@NonNull View itemView, CalendarTODOAdapter.OnItemListener onItemListener, ArrayList<LocalDate> days)
    {
        super(itemView);
        pView = itemView.findViewById(R.id.parentView);
        dayInMonth = itemView.findViewById(R.id.cellDayText);
        this.onItemListener = onItemListener;
        itemView.setOnClickListener(this);
        this.days = days;
    }

    @Override
    public void onClick(View view)
    {
        onItemListener.onItemClick(getAdapterPosition(), days.get(getAdapterPosition()));
    }
}