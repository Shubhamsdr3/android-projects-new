package com.pandey.popcorn4.fvrtmovies;

import android.os.Bundle;

import com.pandey.popcorn4.base.BaseActivity;

public class FvrtMovieActivity extends BaseActivity implements FvrtMovieFragment.FvrtMovieFragmentListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startFragment(FvrtMovieFragment.newInstance(), true);
    }

    @Override
    protected int getLayoutResId() {
        return 0;
    }
}
