package com.pandey.popcorn4.movie.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import com.pandey.popcorn4.R;
import com.pandey.popcorn4.base.BaseCustomView;

import butterknife.BindView;

public class MovieSearchBar extends BaseCustomView {

    @BindView(R.id.search_icon)
    TextView vSearchIcon;

    @BindView(R.id.search_hint_text)
    AppCompatEditText vSearchEditText;

    public MovieSearchBar(Context context) {
        super(context);
    }

    public MovieSearchBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MovieSearchBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void initLayout() {
        super.initLayout();
    }

    public EditText getEditText() {
        return vSearchEditText;
    }

    @Override
    public int getLayoutFile() {
        return R.layout.movie_search_bar;
    }


}
