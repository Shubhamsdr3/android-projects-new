package com.pandey.popcorn4.movie.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.pandey.popcorn4.R;
import com.pandey.popcorn4.base.BaseCustomView;

import butterknife.BindView;

public class MovieStarsView  extends BaseCustomView {

    @BindView(R.id.stars_container)
    ViewGroup vStarsContainer;

    public MovieStarsView(Context context) {
        super(context);
    }

    public MovieStarsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MovieStarsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void showStars(int numberOfStars) {
        for (int i = 0; i < numberOfStars; i++) {
            View rootView  = LayoutInflater.from(getContext()).inflate(R.layout.star_view, vStarsContainer, false);
            vStarsContainer.addView(rootView);
        }
    }

    @Override
    public int getLayoutFile() {
        return R.layout.movie_stars_view;
    }
}
