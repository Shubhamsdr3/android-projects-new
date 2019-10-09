package com.pandey.popcorn4.movie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pandey.popcorn4.R;
import com.pandey.popcorn4.movie.data.MovieInfo;
import com.pandey.popcorn4.movie.data.MoviesResponseDto;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifImageView;

public class PopularMovieAdapter
        extends RecyclerView.Adapter<PopularMovieAdapter.PopularMovieViewHolder> {

    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w92";

    @NonNull
    private List<MovieInfo> popularMovieList;

    @NonNull
    private Context context;

    @Nullable
    private AdapterClickCallback mAdapterCallback;

    public PopularMovieAdapter(@NonNull List<MovieInfo> movieList,
                               @NonNull Context context,
                               @Nullable AdapterClickCallback mAdapterCallback) {
        this.popularMovieList = movieList;
        this.context = context;
        this.mAdapterCallback = mAdapterCallback;
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
        MovieInfo movie= popularMovieList.get(position);
        String imageBaseUrl = IMAGE_BASE_URL + movie.getMoviePoster();
        Glide.with(context)
                .load(imageBaseUrl)
                .into(holder.movieImage);
        holder.movieTitle.setText(movie.getMovieTitle());
        holder.movieDescription.setText(movie.getMovieMovieDescription());
        holder.movieVoteCount.setText(movie.getMovieVoteCount());
        holder.bind(movie);
    }

    @NonNull
    private AdapterClickCallback getCallback() {
        if (mAdapterCallback == null) {
            return (movieDto) -> {
                throw new IllegalStateException("Bhai define to krde callback!");
            };
        }
        return mAdapterCallback;
    }

    @Override
    public int getItemCount() {
        return popularMovieList.size();
    }

    void removeItem(int position) {
        popularMovieList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, popularMovieList.size());
    }

    void restoreItem(MovieInfo item, int position) {
        popularMovieList.add(position, item);
        notifyItemInserted(position);
    }

    MovieInfo getItem(int position) {
        return popularMovieList.get(position);
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

        void bind(@NonNull MovieInfo movieInfo) {
            itemView.setOnClickListener(v -> getCallback().onAdapterItemClick(movieInfo));
        }
    }

    public interface AdapterClickCallback {
        void onAdapterItemClick(@NonNull MovieInfo movieInfo);
    }
}

