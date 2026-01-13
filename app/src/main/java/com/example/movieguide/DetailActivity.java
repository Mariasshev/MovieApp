package com.example.movieguide;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.movieguide.database.AppDatabase;
import com.example.movieguide.database.MovieDao;
import com.example.movieguide.models.Movie;

public class DetailActivity extends AppCompatActivity {

    private Movie currentMovie;
    private MovieDao movieDao;

    private RatingBar userRatingBar;
    private EditText inputComment;
    private ImageButton btnFavorite;
    private Button btnSave;

    private boolean isFavorite = false;   // Стан фільму

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        movieDao = AppDatabase.getInstance(this).movieDao();

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnFavorite = findViewById(R.id.btnFavorite);

        ImageView poster = findViewById(R.id.detailPoster);
        TextView title = findViewById(R.id.detailTitle);
        TextView rating = findViewById(R.id.detailRating);
        TextView overview = findViewById(R.id.detailOverview);

        userRatingBar = findViewById(R.id.ratingBarUser);
        inputComment = findViewById(R.id.inputComment);
        btnSave = findViewById(R.id.btnSaveReview);

        currentMovie = new Movie();
        currentMovie.setId(getIntent().getIntExtra("id", 0));
        currentMovie.setTitle(getIntent().getStringExtra("title"));
        currentMovie.setOverview(getIntent().getStringExtra("overview"));
        currentMovie.setPosterPath(getIntent().getStringExtra("poster"));
        currentMovie.setVoteAverage(getIntent().getDoubleExtra("rating", 0));
        currentMovie.setReleaseDate(getIntent().getStringExtra("releaseDate"));

        title.setText(currentMovie.getTitle());
        overview.setText(currentMovie.getOverview());
        rating.setText("⭐ " + currentMovie.getVoteAverage());
        Glide.with(this).load(currentMovie.getPosterPath()).into(poster);

        // Перевірки
        if (movieDao.isFavorite(currentMovie.getId())) {
            isFavorite = true;
            Movie savedMovie = movieDao.getMovieById(currentMovie.getId());
            userRatingBar.setRating(savedMovie.getMyRating());
            inputComment.setText(savedMovie.getMyComment());

            btnSave.setText("Оновити запис");
        } else {
            isFavorite = false;
        }

        updateFavoriteIcon();

        btnBack.setOnClickListener(v -> finish());

        // Кнопка для вішлісту
        btnFavorite.setOnClickListener(v -> {
            if (isFavorite) {
                movieDao.deleteMovie(currentMovie);
                isFavorite = false;
                Toast.makeText(this, "Видалено з обраного", Toast.LENGTH_SHORT).show();
            } else {
                saveMovieToDatabase();
                isFavorite = true;
                Toast.makeText(this, "Додано в обране", Toast.LENGTH_SHORT).show();
            }
            // Миттєва зміна іконки
            updateFavoriteIcon();
        });

        btnSave.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Зберегти рецензію?")
                    .setMessage("Зберегти зміни у щоденник?")
                    .setPositiveButton("Так", (dialog, which) -> {
                        saveMovieToDatabase();
                        isFavorite = true;
                        updateFavoriteIcon();
                        Toast.makeText(this, "Рецензію збережено!", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Ні", null)
                    .show();
        });
    }

    // Зміна іконки
    private void updateFavoriteIcon() {
        if (isFavorite) {
            btnFavorite.setImageResource(R.drawable.heart);
        } else {
            btnFavorite.setImageResource(R.drawable.heart_outline);
        }
    }

    private void saveMovieToDatabase() {
        float userRating = userRatingBar.getRating();
        String userComment = inputComment.getText().toString();

        currentMovie.setMyRating(userRating);
        currentMovie.setMyComment(userComment);

        movieDao.insertMovie(currentMovie);
    }
}