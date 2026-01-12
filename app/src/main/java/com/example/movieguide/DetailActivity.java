package com.example.movieguide;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageButton btnBack = findViewById(R.id.btnBack);
        ImageButton btnFavorite = findViewById(R.id.btnFavorite);

        ImageView poster = findViewById(R.id.detailPoster);
        TextView title = findViewById(R.id.detailTitle);
        TextView rating = findViewById(R.id.detailRating);
        TextView overview = findViewById(R.id.detailOverview);

        btnBack.setOnClickListener(v -> {
            finish();
        });

        btnFavorite.setOnClickListener(v -> {
            Toast.makeText(this, "Додано в улюблене!", Toast.LENGTH_SHORT).show();
        });

        String movieTitle = getIntent().getStringExtra("title");
        String movieOverview = getIntent().getStringExtra("overview");
        String moviePoster = getIntent().getStringExtra("poster");
        double movieRating = getIntent().getDoubleExtra("rating", 0);

        title.setText(movieTitle);
        overview.setText(movieOverview);
        rating.setText("⭐ " + movieRating);

        Glide.with(this).load(moviePoster).into(poster);
    }
}