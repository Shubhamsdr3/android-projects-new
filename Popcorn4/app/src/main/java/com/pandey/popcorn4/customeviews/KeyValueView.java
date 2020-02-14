package com.pandey.popcorn4.customeviews;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Pair;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.pandey.popcorn4.R;
import com.pandey.popcorn4.base.BaseCustomView;

import butterknife.BindView;

public class KeyValueView extends BaseCustomView {

    @BindView(R.id.name)
    TextView vName;

    @BindView(R.id.value)
    TextView vValue;

    public KeyValueView(Context context) {
        super(context);
    }

    public KeyValueView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public KeyValueView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setData(Pair<String, String> pair) {
        vName.setText(pair.first);
        vName.setTextColor(getResources().getColor(R.color.title_color_white));
        vValue.setText(pair.second);
        vValue.setTextColor(getResources().getColor(R.color.title_color_white));
    }

    @Override
    public int getLayoutFile() {
        return R.layout.pair_view;
    }
}
