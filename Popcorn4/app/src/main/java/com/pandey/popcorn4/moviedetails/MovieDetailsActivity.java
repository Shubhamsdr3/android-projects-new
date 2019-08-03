package com.pandey.popcorn4.moviedetails;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import com.pandey.popcorn4.base.BaseActivity;

public class MovieDetailsActivity extends BaseActivity {

    private static final String MOVIE_ID = "MOVIE_ID";
    private int moveId ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getIntent()!= null) {
            moveId = getIntent().getIntExtra(MOVIE_ID, 0);
        }
        Fragment fragment = MovieDetailFragment.newInstance(moveId);
        startFragment(fragment, true);
    }

    @Override
    protected int getLayoutResId() {
        return 0;
    }

}
