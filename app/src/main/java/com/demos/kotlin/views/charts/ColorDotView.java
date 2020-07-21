package com.demos.kotlin.views.charts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.demos.kotlin.R;

public class ColorDotView extends View {
    private int backgroundColor;

    public ColorDotView(Context context) {
        super(context);
        backgroundColor = context.getResources().getColor(R.color.colorPrimary);
    }

    public ColorDotView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        backgroundColor = context.getResources().getColor(R.color.colorPrimary);
    }

    public ColorDotView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        backgroundColor = context.getResources().getColor(R.color.colorPrimary);
    }

    public ColorDotView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        backgroundColor = context.getResources().getColor(R.color.colorPrimary);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int cx = getWidth() / 2;
        int cy = getHeight() / 2;
        int r = Math.min(cx, cy);
        Paint paint = new Paint();
        paint.setColor(backgroundColor);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(cx, cy, r, paint);
    }

    public void setBackgroundColor(int color) {
        backgroundColor = color;
        postInvalidate();
    }
}
