package com.pandey.popcorn4.customeviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.pandey.popcorn4.R;


public class NetworkLoaderView extends View {

    //circle and text colors
    private int circleCol, labelCol;

    //label text
    private String circleText;

    //paint for drawing custom view
    private Paint circlePaint;

    private Path circlePath;

    private Rect rect;

    public NetworkLoaderView(Context context) {
        super(context);
        initView();
    }

    public NetworkLoaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public NetworkLoaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.NetworkLoaderView);
        circleText = typedArray.getString(R.styleable.NetworkLoaderView_circleLabel);
        circleCol = typedArray.getInteger(R.styleable.NetworkLoaderView_circleColor, 0);
        labelCol = typedArray.getInteger(R.styleable.NetworkLoaderView_labelColor, 0);

        typedArray.recycle();
        initView();
    }

    private void initView() {
        circlePaint = new Paint();
        circlePath = new Path();
        rect = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //get half of the width and height as we are working with a circle
        int viewWidthHalf = this.getMeasuredWidth() / 4;
        int viewHeightHalf = this.getMeasuredHeight() / 4;

        //get the radius as half of the width or height, whichever is smaller
        //subtract ten so that it has some space around it.
        int radius;
        if(viewWidthHalf > viewHeightHalf)
            radius = viewHeightHalf - 10;
        else
            radius = viewWidthHalf - 10;

        circlePaint.setStyle(Paint.Style.FILL_AND_STROKE );
        circlePaint.setAntiAlias(true);

        //set the paint color using the circle color specified
        circlePaint.setColor(circleCol);

        // Final step.
        canvas.drawCircle(viewWidthHalf, viewHeightHalf, radius, circlePaint);

        circlePath.reset();
        circlePath.addCircle(
                rect.exactCenterX(),
                rect.exactCenterY(),
                rect.width() / 6F,
                Path.Direction.CW
        );

        canvas.clipPath(circlePath, Region.Op.DIFFERENCE);

    }

    public int getCircleColor(){
        return circleCol;
    }

    public int getLabelColor(){
        return labelCol;
    }

    public String getLabelText(){
        return circleText;
    }

    public void setCircleColor(int newColor){
        //update the instance variable
        circleCol = newColor;

        //redraw the view
        invalidate();
        requestLayout();
    }
    public void setLabelColor(int newColor){
        //update the instance variable
        labelCol = newColor;
        //redraw the view

        invalidate();
        requestLayout();
    }

    public void setLabelText(String newLabel){
        //update the instance variable
        circleText  =newLabel;

        //redraw the view
        invalidate();
        requestLayout();
    }

}
