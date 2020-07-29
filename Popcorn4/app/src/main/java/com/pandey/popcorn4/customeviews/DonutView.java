package com.pandey.popcorn4.customeviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.pandey.popcorn4.R;

public class DonutView extends View {

    private Rect bounds;

    private Paint basePaint;

    private Context mContext;


    public DonutView(Context context) {
        super(context);
        initView(context);
    }

    public DonutView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public DonutView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(@NonNull Context context) {
        bounds = new Rect();
        basePaint = new Paint();
        this.mContext = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        basePaint.setColor(mContext.getResources().getColor(R.color.colorAccent));

        canvas.drawCircle(
                bounds.exactCenterX(),
                bounds.exactCenterY(),
                bounds.width() / 7f,
                basePaint
        );
    }

    public void setCircleColor(int color) {

    }

    public void setLabelColor(int magenta) {
    }

    public void setLabelText(String help) {
    }
}
