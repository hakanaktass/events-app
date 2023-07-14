package com.example.project.News;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project.R;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class NewsDetailsActivity extends AppCompatActivity {
    NewsHeadlines headlines;
    TextView txt_title,txt_author,txt_detail,txt_content,txt_time;
    ImageView img_news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.customBackground)));
        getSupportActionBar().setTitle("");

        txt_title = findViewById(R.id.text_detail_title);
        txt_author = findViewById(R.id.text_detail_author);
        txt_detail = findViewById(R.id.text_detail_detail);
        txt_content = findViewById(R.id.text_detail_content);
        txt_time= findViewById(R.id.text_detail_time);
        img_news = findViewById(R.id.img_detail_news);

        headlines = (NewsHeadlines) getIntent().getSerializableExtra("data");

        txt_title.setText(headlines.getTitle());
        txt_author.setText(headlines.getAuthor());
        txt_detail.setText(headlines.getDescription());
        txt_content.setText(headlines.getContent());
        txt_time.setText(headlines.getPublishedAt());
        Picasso.get().load(headlines.getUrlToImage()).into(img_news);
    }
}