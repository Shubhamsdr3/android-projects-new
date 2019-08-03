package com.pandey.popcorn4.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pandey.popcorn4.R;
import com.pandey.popcorn4.movie.data.MoviesResponseDto;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieSearchAdapterFilterable extends
        RecyclerView.Adapter<MovieSearchAdapterFilterable.MovieSearchViewHolder> implements Filterable {

    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w92";

    @Nullable
    private List<MoviesResponseDto> movieFilteredList;

    @NonNull
    private List<MoviesResponseDto>  movieList;

    @NonNull
    private MovieSearchAdapterListener movieSearchAdapterListener;

    @NonNull
    private Context context;

    MovieSearchAdapterFilterable(@NonNull Context context,  @NonNull List<MoviesResponseDto> movieList,
                                 @NonNull MovieSearchAdapterListener movieSearchAdapterListener) {

        this.context = context;
        this.movieList = movieList;
        this.movieSearchAdapterListener = movieSearchAdapterListener;
    }

    @NonNull
    @Override
    public MovieSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_search_result, parent, false);
        return new MovieSearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieSearchViewHolder holder, int position) {
        //TODO: should be movieFilteredList.get(position)
        MoviesResponseDto movie = movieList.get(position);
        String imageBaseUrl = IMAGE_BASE_URL + movie.getPoster_path();
        holder.vMovieTitle.setText(movie.getTitle());
        Glide.with(context)
                .load(imageBaseUrl)
                .into(holder.vMovieImage);
        holder.vMovieDescription.setText(movie.getOverview());
        holder.vMovieVoteCount.setText(String.valueOf(movie.getVote_count()));
    }

    @Override
    public int getItemCount() {
      return movieList.size();
    }

    //TODO: where should this be called from?
    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();
                if(charString.isEmpty()) {
                    movieFilteredList = movieList;
                } else {

                    List<MoviesResponseDto> filteredList = new ArrayList<>();
                    for (MoviesResponseDto movie: movieList) {
                        if(movie.getTitle().toLowerCase().startsWith(charString.toLowerCase()) ||
                                movie.getOverview().toLowerCase().startsWith(charString.toLowerCase())) {
                            filteredList.add(movie);
                        }
                    }
                    movieFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = movieFilteredList;
                filterResults.count =  movieFilteredList.size();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if(results != null && results.count > 0 && results.values instanceof List<?>) {
                    movieFilteredList.addAll((List<MoviesResponseDto>) results.values);
                    notifyDataSetChanged();
                }
            }
        };
    }

    class MovieSearchViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.movie_image)
        ImageView vMovieImage;

        @BindView(R.id.movie_title)
        TextView vMovieTitle;

        @BindView(R.id.movie_vote_count)
        TextView vMovieVoteCount;

        @BindView(R.id.movie_description)
        TextView vMovieDescription;

        MovieSearchViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(v -> {
//                if (movieFilteredList != null) {
                movieSearchAdapterListener.onMovieSelected(movieList.get(getAdapterPosition()));
//                    }
            });
        }
    }

    public interface MovieSearchAdapterListener {
        void onMovieSelected(@Nullable MoviesResponseDto moviesResponseDto);
    }
}
