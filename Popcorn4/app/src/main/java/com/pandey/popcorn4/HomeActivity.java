package com.pandey.popcorn4;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.firebase.iid.FirebaseInstanceId;
import com.pandey.popcorn4.base.BaseActivity;
import com.pandey.popcorn4.fvrtmovies.FvrtMovieActivity;
import com.pandey.popcorn4.movie.PopularMoviesFragment;
import com.pandey.popcorn4.movie.data.MoviesResponseDto;
import com.pandey.popcorn4.moviedetails.MovieDetailsActivity;
import com.pandey.popcorn4.news.NewsFragment;
import com.pandey.popcorn4.search.MovieSearchFragment;

import butterknife.ButterKnife;


public class HomeActivity extends BaseActivity implements
        PopularMoviesFragment.PopularMoviesFragmentListener, NewsFragment.NewsFragmentInteractionListener ,
        MovieSearchFragment.MovieSearchFragmentInteractionListener {

    private static final String MOVIE_ID = "MOVIE_ID";
    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        startFragment(PopularMoviesFragment.newInstance(), true);

        // Firebase registration token
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        //To do//
                        return;
                    }
                    // Get the Instance ID token//
                    String token = task.getResult().getToken();
                    String msg = getResources().getString(R.string.fcm_token, token);
                    Log.d(TAG, msg);

                });
    }

    @Override
    protected int getLayoutResId() {
        return 0;
    }

    @Override
    public void onMovieDetailClicked(int movieId) {
        Intent newActivity =  new Intent(this, MovieDetailsActivity.class);
        newActivity.putExtra(MOVIE_ID, movieId);
        startActivity(newActivity);
    }

    @Override
    public void onSearchIconClicked() {
        startFragment(MovieSearchFragment.newInstance(), true);
    }

    @Override
    public void onFvrtMovieIconClicked() {
        startActivity(new Intent(this, FvrtMovieActivity.class));
    }

    @Override
    public void onNewsFragmentInteractionListener() {

    }

    @Override
    public void onMovieSelectedFromSearch(@Nullable MoviesResponseDto moviesResponseDto) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra(MOVIE_ID, moviesResponseDto.getId());
        startActivity(intent);
    }
}
