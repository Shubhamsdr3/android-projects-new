package com.pandey.popcorn4.moviedetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.TaskStackBuilder;
import androidx.fragment.app.Fragment;

import com.pandey.popcorn4.base.BaseActivity;
import com.pandey.popcorn4.deeplinks.AppDeepLinkWithBackStack;
import com.pandey.popcorn4.deeplinks.AppDeeplink;
import com.pandey.popcorn4.deeplinks.DeeplinkUtils;
import com.pandey.popcorn4.deeplinks.WebDeeplink;
import com.pandey.popcorn4.mediaplayer.MediaPlayerActivity;

@AppDeeplink({"movieDetail", "md"})
public class MovieDetailsActivity
        extends BaseActivity
        implements MovieDetailFragment.MovieDetailFragmentListener {

    public static final String MOVIE_ID = "MOVIE_ID";
    private int moveId ;

    @WebDeeplink({"movieDetail", "md"})
    @AppDeepLinkWithBackStack({"movieDetail", "md"})
    public static TaskStackBuilder deepLinkHandler(Context context) {
        TaskStackBuilder homeTask = DeeplinkUtils.homeRedirectionStack(context);
        Intent addLoadIntent = new Intent(context, MovieDetailsActivity.class);
        homeTask.addNextIntent(addLoadIntent);
        return homeTask;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent() != null) {
            moveId = getIntent().getIntExtra(MOVIE_ID, 0);
        }
        Fragment fragment = MovieDetailFragment.newInstance(moveId);
        startFragment(fragment, true);
    }

    @Override
    protected int getLayoutResId() {
        return 0;
    }

    @Override
    public void onMoviePosterClicked() {
//        startFragment(MediaPlayerFragment.newInstance(moveId), true);
        Intent intent = new Intent(this, MediaPlayerActivity.class);
        intent.putExtra(MOVIE_ID, moveId);
        startActivity(intent);
    }
}
