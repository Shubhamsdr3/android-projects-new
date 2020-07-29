package com.pandey.popcorn4.customeviews;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import com.pandey.popcorn4.R;
import com.pandey.popcorn4.base.BaseActivity;

import butterknife.BindView;

public class CustomActivity extends BaseActivity {

    @BindView(R.id.color_drop_view)
    DonutView vColorDropsView;

    @BindView(R.id.button_press_me)
    Button vPressMeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        vPressMeButton.setOnClickListener(v -> {
            vColorDropsView.setCircleColor(getResources().getColor(R.color.light_green_movie_color));
            vColorDropsView.setLabelColor(Color.MAGENTA);
            vColorDropsView.setLabelText("Help");
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_custom;
    }
}
