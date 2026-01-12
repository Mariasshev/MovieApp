package com.example.movieguide;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Детальніше про фільм");
        }

        ImageView poster = findViewById(R.id.detailPoster);
        TextView title = findViewById(R.id.detailTitle);
        TextView rating = findViewById(R.id.detailRating);
        TextView overview = findViewById(R.id.detailOverview);

        String movieTitle = getIntent().getStringExtra("title");
        String movieOverview = getIntent().getStringExtra("overview");
        String moviePoster = getIntent().getStringExtra("poster");
        double movieRating = getIntent().getDoubleExtra("rating", 0);

        title.setText(movieTitle);
        overview.setText(movieOverview);
        rating.setText("⭐ " + movieRating);

        Glide.with(this).load(moviePoster).into(poster);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}