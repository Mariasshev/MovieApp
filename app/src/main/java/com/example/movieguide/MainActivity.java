package com.example.movieguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.movieguide.adapters.MovieAdapter;
import com.example.movieguide.api.RetrofitClient;
import com.example.movieguide.api.TmdbApi;
import com.example.movieguide.models.Movie;
import com.example.movieguide.models.MovieResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private List<Movie> movies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewMovies);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadMovies();
    }

    private void loadMovies() {
        TmdbApi apiService = RetrofitClient.getClient();

        Call<MovieResponse> call = apiService.getPopularMovies("d9325d93c0e4d400afa13cd91affac55", "uk-UA");

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    movies = response.body().getMovies();

                    adapter = new MovieAdapter(MainActivity.this, movies);
                    recyclerView.setAdapter(adapter);

                    Log.d("MyMovieApp", "Завантажено фільмів: " + movies.size());
                } else {
                    Toast.makeText(MainActivity.this, "Помилка завантаження", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.d("MyMovieApp", "Error: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Немає інтернету!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}