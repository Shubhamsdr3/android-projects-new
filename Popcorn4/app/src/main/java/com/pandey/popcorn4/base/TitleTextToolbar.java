package com.pandey.popcorn4.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.pandey.popcorn4.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TitleTextToolbar extends FrameLayout {

    @BindView(R.id.toolbar_parent)
    CardView vToolbarContainer;

    @BindView(R.id.toolbar_title)
    TextView vToolbarTitle;

    @BindView(R.id.nav_back)
    TextView vNavBack;

    private boolean allowBackPress;

    public TitleTextToolbar(@NonNull Context context) {
        super(context);
        initLayout();
    }

    public TitleTextToolbar(@NonNull Context context, @Nullable String title, boolean allowBackPress) {
        super(context);
        this.allowBackPress = allowBackPress;
        initLayout();
        setTitle(title);
    }

    private void initLayout() {
        inflate(getContext(), R.layout.default_toolbar, this);
        ButterKnife.bind(this);

        vNavBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) getContext();
                activity.onBackPressed();
            }
        });
        setAllowBackPress();
    }

    public void setTitle(@Nullable String title) {
        vToolbarTitle.setText(title);
    }

    private void setAllowBackPress() {
        if(allowBackPress) {
            vNavBack.setVisibility(VISIBLE);
        } else {
            vNavBack.setVisibility(GONE);
        }
    }
}
