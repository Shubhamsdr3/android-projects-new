package com.pandey.popcorn4.movie.customviews;

import android.content.Context;
import android.graphics.Outline;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.pandey.popcorn4.R;
import com.pandey.popcorn4.base.BaseCustomView;
import com.pandey.popcorn4.movie.data.MovieInfo;

import butterknife.BindView;

public class MovieCardView extends BaseCustomView {

    @BindView(R.id.movie_poster)
    CardView vMovieCardView;

    @BindView(R.id.movie_poster_image)
    ImageView vMoviePosterImage;

    @BindView(R.id.movie_title)
    TextView vMovieTitle;

    @BindView(R.id.movie_stars_view)
    MovieStarsView vMovieStarsView;

    public MovieCardView(Context context) {
        super(context);
    }

    public MovieCardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MovieCardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setMovieData(@NonNull MovieInfo movieInfo) {

    }

    @Override
    public int getLayoutFile() {
        return R.layout.movie_card_view;
    }
}
