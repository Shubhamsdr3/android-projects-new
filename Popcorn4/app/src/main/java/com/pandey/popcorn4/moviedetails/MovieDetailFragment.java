package com.pandey.popcorn4.moviedetails;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.pandey.popcorn4.AppConfig;
import com.pandey.popcorn4.R;
import com.pandey.popcorn4.base.BaseFragment;
import com.pandey.popcorn4.moviedetails.data.MovieGenresDto;
import com.pandey.popcorn4.moviedetails.data.MovieDto;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;

public class MovieDetailFragment extends
        BaseFragment<MovieDetailFragment.MovieDetailFragmentListener>
        implements MovieDetailPresenter.MovieDetailView {

    private static final String MOVIE_ID = "MOVIE_ID";

    private int movieId;

    @BindView(R.id.movie_poster)
    ImageView vMoviePosterImage;

    @BindView(R.id.movie_title_text)
    TextView vMovieTitleView;

    @BindView(R.id.movie_tagline)
    TextView vMovieTagline;

    @BindView(R.id.movie_language)
    TextView vMovieLanguage;

    @BindView(R.id.movie_genres)
    TextView vMovieGenres;

    @BindView(R.id.movie_budget)
    TextView vMovieBudget;

    @BindView(R.id.release_date)
    TextView vReleaseDate;

    @BindView(R.id.loading_animation)
    LottieAnimationView vLoadingAnimation;

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

    @Override
    public void onMovieDetailsFetching() {
        vLoadingAnimation.playAnimation();
    }

    @Override
    public void onMovieDetailCompleted(MovieDto movie) {
        vLoadingAnimation.setVisibility(View.GONE);
        String imageBaseUrl = AppConfig.getMovieImageBaseUrl() +  movie.getPosterPath();
        Glide.with(Objects.requireNonNull(getContext()))
                .load(imageBaseUrl)
                .into(vMoviePosterImage);

        vMovieTitleView.setText(movie.getTitle());
        vMovieTagline.setText(movie.getTagline());
        vMovieBudget.setText(getString(R.string.movie_budget, String.valueOf(movie.getBudget())));
        vMovieLanguage.setText(movie.getOriginal_language());
        for (MovieGenresDto genres: movie.getGenres()) {
            vMovieGenres.setText(genres.getName());
        }
        vReleaseDate.setText(movie.getRelease_date());
    }

    @Nullable
    @Override
    public FrameLayout getToolBar() {
        return null;
    }

    @NonNull
    @Override
    protected Class<MovieDetailFragmentListener> getListenerClass() {
        return MovieDetailFragmentListener.class;
    }

    @Override
    public int getLayoutFile() {
        return R.layout.layout_movie;
    }

    interface MovieDetailFragmentListener {

    }
}
