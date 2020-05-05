package com.pandey.popcorn4;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.pandey.popcorn4.base.BaseActivity;
import com.pandey.popcorn4.movie.PopularMovieFragment;
import com.pandey.popcorn4.movie.data.MoviesResponseDto;
import com.pandey.popcorn4.moviedetails.MovieDetailsActivity;
import com.pandey.popcorn4.search.MovieSearchFragment;

import timber.log.Timber;


public class HomeActivity extends BaseActivity implements
        PopularMovieFragment.PopularMovieFragmentListener , MovieSearchFragment.MovieSearchFragmentListener {

    private static final String MOVIE_ID = "MOVIE_ID";

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Firebase registration token
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        //To do
                        return;
                    }
                    // Get the Instance ID token
                    String token = task.getResult().getToken();
                    String msg = getResources().getString(R.string.fcm_token, token);
                    Timber.d(msg);

                });

        PopApplication.getInstance().getGlobalBuses().toObservable().subscribe(s -> {
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        });

        startFragment(PopularMovieFragment.Companion.newInstance(), true);
    }

    @Override
    protected int getLayoutResId() {
        return 0;
    }

//    @Override
//    public void onMovieDetailClicked(@NonNull View view, int movieId) {
//
//    }
//
//    @Override
//    public void onSearchBarClicked() {
//        startFragment(new MovieSearchFragment(), true);
//    }
//
//    @Override
//    public void onFvrtMovieIconClicked(@NonNull View view) {
//
//
////        startActivity(new Intent(this, FvrtMovieActivity.class));
//    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseAnalytics mfireBaseAnalytics = PopApplication.getFirebaseAnalytics();
        Bundle bundle = new Bundle();
        bundle.putString("action", "actionStr");
        bundle.putString("label", "labelStr");
        mfireBaseAnalytics.logEvent("category", bundle);
    }

    @Override
    public void onMovieSelectedFromSearch(@Nullable MoviesResponseDto moviesResponseDto) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra(MOVIE_ID, moviesResponseDto.getId());
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    @Override
    public void onMovieClicked(View view, int movieId) {
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                        this ,
                        view,
                        getString(R.string.picture_transition_name)
                );
        Intent newActivity =  new Intent(this, MovieDetailsActivity.class);
        newActivity.putExtra(MOVIE_ID, movieId);
        startActivity(newActivity, options.toBundle());
    }
}
