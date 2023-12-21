package com.bonc.ioc.chart.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.bonc.ioc.chart.R;

/**
 * 彩色圆点，使用setBackgroundColor设置颜色，默认colorTheme。
 */
public class ColorDotView extends View {
    private int backgroundColor;
    private boolean isSelected;
    private boolean isSquare;
    private int radius;

    public ColorDotView(Context context) {
        super(context);
        backgroundColor = context.getResources().getColor(R.color.colorTheme);
    }

    public ColorDotView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        backgroundColor = context.getResources().getColor(R.color.colorTheme);
    }

    public ColorDotView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        backgroundColor = context.getResources().getColor(R.color.colorTheme);
    }

    public ColorDotView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        backgroundColor = context.getResources().getColor(R.color.colorTheme);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int x = getWidth();
        int y = getHeight();
        if (isSquare) {
            radius = 0;
        } else {
            radius = Math.min(x, y) / 2;
        }
        Paint paint = new Paint();
        paint.setColor(backgroundColor);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(new RectF(0 + 8, 0 + 8, x - 8, y - 8), radius, radius, paint);
        if (isSelected ) {
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(2);
            canvas.drawRoundRect(new RectF(0, 0, x, y), radius, radius, paint);
        }
    }

    public void setBackgroundColor(int color) {
        backgroundColor = color;
        invalidate();
    }

    public void setOnSelected(boolean selected) {
        isSelected = selected;
        invalidate();
    }

    public boolean isCustomSelected() {
        return isSelected;
    }

    public void setIsSquare(boolean isSquare) {
        this.isSquare = isSquare;
        invalidate();
    }
}
