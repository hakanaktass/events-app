package com.example.project.News;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{
    private Context context;
    private List<NewsHeadlines> headlines;
    private SelectListener listener;

    public CustomAdapter(android.content.Context context, List<NewsHeadlines> headlines, SelectListener listener) {
        this.context = context;
        this.headlines = headlines;
        this.listener = listener;

    }


    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       return new CustomAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.headline_list_items, parent, false));
    };

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.news_title.setText(headlines.get(position).getTitle());
        holder.news_source.setText(headlines.get(position).getSource().getName());
        if (headlines.get(position).getUrlToImage()!=null){
            Picasso.get().load(headlines.get(position).getUrlToImage()).into(holder.img_headline);
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listener.OnNewsClicked(headlines.get(position));
            }
        });
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView news_title,news_source;
        CardView cardView;
        ImageView img_headline;
        public ViewHolder(View itemView){
            super(itemView);
            news_title = itemView.findViewById(R.id.news_title);
            news_source = itemView.findViewById(R.id.news_source);
            cardView = itemView.findViewById(R.id.news_container);
            img_headline = itemView.findViewById(R.id.img_headline);

        }
    }

    @Override
    public int getItemCount() {

        return headlines.size();
    }
}
