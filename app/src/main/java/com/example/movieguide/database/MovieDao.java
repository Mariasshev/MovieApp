package com.example.movieguide.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.movieguide.models.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    // Зберегти / оновити
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(Movie movie);

    // Видалити фільм
    @Delete
    void deleteMovie(Movie movie);

    // Отримати всі з вішлісту
    @Query("SELECT * FROM favorite_movies")
    List<Movie> getAllFavorites();

    @Query("SELECT EXISTS (SELECT 1 FROM favorite_movies WHERE id = :id)")
    boolean isFavorite(int id);

    @Query("SELECT * FROM favorite_movies WHERE id = :id LIMIT 1")
    Movie getMovieById(int id);
}