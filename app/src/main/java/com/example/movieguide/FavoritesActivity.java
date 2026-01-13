package com.example.movieguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.movieguide.adapters.MovieAdapter;
import com.example.movieguide.database.AppDatabase;
import com.example.movieguide.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private List<Movie> favoriteMovies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        // Кнопка Назад
        ImageButton btnBack = findViewById(R.id.btnBackFromFavorites);
        btnBack.setOnClickListener(v -> finish());

        // Налаштування списку
        recyclerView = findViewById(R.id.recyclerViewFavorites);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Ініціалізація адаптеру
        adapter = new MovieAdapter(this, favoriteMovies);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFavorites();
    }

    private void loadFavorites() {
        // Звернення до бд
        favoriteMovies = AppDatabase.getInstance(this)
                .movieDao()
                .getAllFavorites();

        // Оновлення адаптеру - створення нового з даними
        adapter = new MovieAdapter(this, favoriteMovies);
        recyclerView.setAdapter(adapter);

    }
}