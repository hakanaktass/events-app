package com.example.project.NotificationListener;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<RecyclerViewDataModel> recyclerViewDataModels;

    public RecyclerViewAdapter(Context context, ArrayList<RecyclerViewDataModel> recyclerViewDataModels,
                               RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.recyclerViewDataModels = recyclerViewDataModels;
        this.recyclerViewInterface = recyclerViewInterface;
    }//public RecyclerViewAdapter(Context context)

    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.layout_single_notification, parent, false);

        return new RecyclerViewAdapter.MyViewHolder(view, recyclerViewInterface);
    }//public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.applicationLogo.setImageResource(recyclerViewDataModels.get(position).getLogoId());
        holder.notificationTitle.setText(recyclerViewDataModels.get(position).getDataModelTitle());
        holder.notificationText.setText(recyclerViewDataModels.get(position).getDataModelText());
    }//public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position)

    @Override
    public int getItemCount() {
        return recyclerViewDataModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView applicationLogo;
        TextView notificationTitle, notificationText;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            applicationLogo = itemView.findViewById(R.id.applicationLogo);
            notificationTitle = itemView.findViewById(R.id.notificationTitle);
            notificationText = itemView.findViewById(R.id.notificationText);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface != null) {
                        int notificationPosition = getBindingAdapterPosition();

                        if(notificationPosition != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onNotificationClick(notificationPosition);
                        }
                    }//if(recyclerViewInterface != null)
                }//public void onClick(View view)
            });//itemView.setOnClickListener(new View.OnClickListener())

        }//public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface)

    }//public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface)
}//public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>
