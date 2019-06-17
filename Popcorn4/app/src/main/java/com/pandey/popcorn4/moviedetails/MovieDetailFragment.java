package com.pandey.popcorn4.moviedetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pandey.popcorn4.R;
import com.pandey.popcorn4.base.BaseFragment;
import com.pandey.popcorn4.base.TitleTextToolbar;
import com.pandey.popcorn4.moviedetails.data.MovieGenresDto;
import com.pandey.popcorn4.moviedetails.data.MovieResponseDto;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailFragment extends BaseFragment implements MovieDetailPresenter.MovieDetailView {

    private static final String MOVIE_ID = "MOVIE_ID";
    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/original";

    private int movieId;

    @BindView(R.id.movie_poster)
    ImageView vMoviePosterImage;

    @BindView(R.id.movie_title_text)
    TextView vMovieTitleView;

    @BindView(R.id.movie_tagline)
    TextView vMovieTagline;

    @BindView(R.id.movie_language)
    TextView vMoviedLanguage;

    @BindView(R.id.movie_genres)
    TextView vMovieGenres;

    @BindView(R.id.movie_budget)
    TextView vMovieBudget;

    @BindView(R.id.release_date)
    TextView vReleaseDate;

    public MovieDetailFragment() {
    }

    static MovieDetailFragment newInstance(int movieId) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putInt(MOVIE_ID, movieId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initLayout() {
        if(getArguments() != null) {
            movieId = getArguments().getInt(MOVIE_ID);
        }
        MovieDetailPresenter movieDetailPresenter = new MovieDetailPresenter(this);
        movieDetailPresenter.getMovieDetail(movieId);
    }

    @Override
    public void initListeners() {

    }

    @Nullable
    @Override
    public FrameLayout getToolBar() {
        return new TitleTextToolbar(Objects.requireNonNull(getActivity()), "Movie Detail", true);
    }

    @Override
    public int getLayoutFile() {
        return R.layout.layout_movie;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onMovieDetailCompleted(MovieResponseDto movie) {
        String imageBaseUrl = IMAGE_BASE_URL +  movie.getPoster_path();
        Glide.with(Objects.requireNonNull(getContext()))
                .load(imageBaseUrl)
                .into(vMoviePosterImage);
        vMovieTitleView.setText(movie.getTitle());
        vMovieTagline.setText(movie.getTagline());
        vMovieBudget.setText(getString(R.string.movie_budget, String.valueOf(movie.getBudget())));
        vMoviedLanguage.setText(movie.getOriginal_language());
        for (MovieGenresDto genres: movie.getGenres()) {
            vMovieGenres.setText(genres.getName());
        }
        vReleaseDate.setText(movie.getRelease_date());
    }
}
