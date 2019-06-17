package com.pandey.popcorn.movies.fragment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pandey.popcorn.R;
import com.pandey.popcorn.movies.fragment.data.Movie;

import java.util.List;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PopularMovieAdapter extends RecyclerView.Adapter<PopularMovieAdapter.PopularMovieViewHolder> {

    private List<Movie> popularMovieList;
    private Context context;

    public PopularMovieAdapter(List<Movie> movieList, Context context) {
        this.popularMovieList = movieList;
        this.context = context;
    }

    @NonNull
    @Override
    public PopularMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_popular_movie, parent, false);
        return new PopularMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularMovieViewHolder holder, int position) {
        Movie movie= popularMovieList.get(position);

        Glide.with(context)
                .load(movie.getPoster_path())
                .into(holder.movieImage);
        holder.movieTitle.setText(String.valueOf(movie.getTitle()));
        holder.movieDescription.setText(String.valueOf(movie.getOverview()));
        holder.movieVoteCount.setText(String.valueOf(movie.getVote_count()));
    }

    @Override
    public int getItemCount() {
        return popularMovieList.size();
    }

    class PopularMovieViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.movie_image)
        ImageView movieImage;

        @BindView(R.id.movie_title)
        TextView movieTitle;

        @BindView(R.id.movie_vote_count)
        TextView movieVoteCount;

        @BindView(R.id.movie_description)
        TextView movieDescription;

        PopularMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
