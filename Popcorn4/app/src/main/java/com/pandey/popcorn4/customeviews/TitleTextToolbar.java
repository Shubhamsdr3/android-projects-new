package com.pandey.popcorn4.customeviews;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
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

    @BindView(R.id.right_view)
    FrameLayout vParentRightView;

    private boolean allowBackPress;

    public TitleTextToolbar(@NonNull Context context) {
        super(context);
        initLayout();
    }

    public TitleTextToolbar(@NonNull Context context, @Nullable String title,  boolean allowBackPress) {
        super(context);
        this.allowBackPress = allowBackPress;
        initLayout();
        initListener();
        setTitle(title);
    }

    private void initLayout() {
        inflate(getContext(), R.layout.default_toolbar, this);
        ButterKnife.bind(this);
    }

    private void initListener() {
        vNavBack.setOnClickListener(v -> {
            AppCompatActivity activity = (AppCompatActivity) getContext();
            activity.onBackPressed();
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

    public void setRightView(@Nullable View vRightView) {
        if (vRightView != null && vRightView.getParent() != null) {
            ((ViewGroup) vRightView.getParent()).removeView(vRightView);
        }
        vParentRightView.addView(vRightView);
    }
}
