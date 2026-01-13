package com.example.movieguide.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieguide.R;
import com.example.movieguide.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context context;
    private List<Movie> movieList;
    private List<Movie> moviesFull;

    public MovieAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
        this.moviesFull = new ArrayList<>(movieList);
    }

    public void setMovies(List<Movie> newMovies) {
        this.movieList = newMovies;
        this.moviesFull = new ArrayList<>(newMovies);
        notifyDataSetChanged();
    }

    // Метод фільтрації
    public void filterList(String text) {
        movieList.clear();

        if (text.isEmpty()) {
            movieList.addAll(moviesFull);
        } else {
            text = text.toLowerCase();
            for (Movie item : moviesFull) {
                if (item.getTitle().toLowerCase().contains(text)) {
                    movieList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);

        holder.title.setText(movie.getTitle());
        holder.rating.setText("⭐ " + movie.getVoteAverage());
        holder.releaseDate.setText(movie.getReleaseDate());

        Glide.with(context)
                .load(movie.getPosterPath())
                .into(holder.poster);

       holder.itemView.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(context, com.example.movieguide.DetailActivity.class);

            intent.putExtra("title", movie.getTitle());
            intent.putExtra("overview", movie.getOverview());
            intent.putExtra("poster", movie.getPosterPath());
            intent.putExtra("rating", movie.getVoteAverage());
           intent.putExtra("id", movie.getId());

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        ImageView poster;
        TextView title, rating, releaseDate;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.imagePoster);
            title = itemView.findViewById(R.id.textTitle);
            rating = itemView.findViewById(R.id.textRating);
            releaseDate = itemView.findViewById(R.id.textReleaseDate);
        }
    }
}