package com.pandey.popcorn4.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import butterknife.ButterKnife;


public abstract class BaseCustomView extends FrameLayout {

    private Context mContext;

    public BaseCustomView(Context context) {
        super(context);
        this.mContext = context;
        initLayout();
    }

    public BaseCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initLayout();
    }

    public BaseCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initLayout();

    }

    public void initLayout() {
        View rootView = LayoutInflater.from(
                mContext).inflate(getLayoutFile(), this, false
        );
        ButterKnife.bind(this, rootView);
        addView(rootView);
        initListener();
    }

    public void initListener(){

    }

    @LayoutRes
    public abstract int getLayoutFile();
}
