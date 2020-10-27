package com.demos.kotlin.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 选定范围的进度条
 */

public class RangeFilterBar extends View {

    Paint paint;

    Paint textPaint;

    private float leftProgress = 0f;

    private float rightProgress = 1f;
    private int knobWidth = 30;

    private float pressX;

    private int max = 120;
    private int min = 15;

    private int textViewWidth = 70;
    private int textKnobSpace = 10;
    private int textSize = 30;
    private int textColor = 0xA6000000;
    private float knobRadius = 5f;

    private int leftValue = min;
    private int rightValue = max;

    RectF bounds = new RectF();

    private OnRangeChangedListener onRangeChangedListener;

    public RangeFilterBar(Context context) {
        super(context);
        init(context);
    }

    public RangeFilterBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RangeFilterBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public RangeFilterBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void init(Context context) {
        paint = new Paint();
        paint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(textSize);
        textPaint.setColor(textColor);
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setStrokeWidth(2f);
    }

    public void setRange(int min, int max) {
        this.min = min;
        this.max = max;
        setLeftValue();
        setRightValue();
        invalidate();
    }

    public void setLeftProgress(float progress) {
        float end_progress;
        end_progress = rightProgress - (knobWidth * 1.0f / (getWidth() - textViewWidth * 2)) * 2;
        if (progress <= 0f) {
            progress = 0f;
        }
        if (progress >= end_progress) {
            progress = end_progress;
        }
        this.leftProgress = progress;
        setLeftValue();
        if (onRangeChangedListener != null) {
            onRangeChangedListener.onRangeChanged(leftValue, rightValue);
        }
        invalidate();
    }

    private void setLeftValue() {
        leftValue = (int) (leftProgress * (max - min) + min);
    }

    private void setRightValue() {
        rightValue = (int) (rightProgress * (max - min) + min);
    }

    public void setRightProgress(float progress) {
        float start_progress;
        start_progress = leftProgress + (knobWidth * 1.0f / (getWidth() - textViewWidth * 2)) * 2;
        if (progress <= start_progress) {
            progress = start_progress;
        }
        if (progress >= 1f) {
            progress = 1f;
        }
        this.rightProgress = progress;
        setRightValue();
        if (onRangeChangedListener != null) {
            onRangeChangedListener.onRangeChanged(leftValue, rightValue);
        }
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 获取对应高度
        int height = getHeight();
        // 获取对应宽度
        int width = getWidth() - textViewWidth * 2;

        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        //左侧按钮
        float startProgressX = width * leftProgress + textViewWidth;
        bounds.set(((int) startProgressX), 0, (int) (startProgressX + knobWidth), height);
        canvas.drawRoundRect(bounds, knobRadius, knobRadius, paint);
        //右侧按钮
        float endProgressX = width * rightProgress + textViewWidth;
        bounds.set(((int) endProgressX - knobWidth), 0, (int) (endProgressX), height);
        canvas.drawRoundRect(bounds, knobRadius, knobRadius, paint);

        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        bounds.set(((int) startProgressX), 0, (int) (startProgressX + knobWidth), height);
        canvas.drawRoundRect(bounds, knobRadius, knobRadius, paint);
        bounds.set(((int) endProgressX - knobWidth), 0, (int) (endProgressX), height);
        canvas.drawRoundRect(bounds, knobRadius, knobRadius, paint);

        //画刻度值
        String left_num = String.valueOf(leftValue);
        String right_num = String.valueOf(rightValue);
        canvas.drawText(left_num, startProgressX - textPaint.measureText(left_num) - textKnobSpace, height - (height - textPaint.getTextSize()) / 2, textPaint);
        canvas.drawText(right_num, endProgressX + textKnobSpace, height - (height - textPaint.getTextSize()) / 2, textPaint);
    }

    /**
     * @param pointX
     * @param pointY
     * @return 0表示触摸点不在两头按钮范围内，1表示触摸点落在左侧按钮范围内，2表示触摸点落在右侧按钮范围内,3表示在两个按钮中间
     */
    private int checkTouchStatus(float pointX, float pointY) {
        //注释掉的是Y轴上触摸点是否落在按钮的高度范围之内，之所以注释是为了触摸的范围大一点
//        if (pointY >= (getHeight() - leftProgressIcon.getHeight()) / 2.0f && pointY <= getHeight() - (getHeight() - leftProgressIcon.getHeight()) / 2.0f) {

        int leftKnobLeftEdge = (int) ((getWidth() - textViewWidth * 2) * leftProgress + textViewWidth);
        int leftKnobRightEdge = (int) ((getWidth() - textViewWidth * 2) * leftProgress + textViewWidth + knobWidth);
        int rightKnobLeftEdge = (int) ((getWidth() - textViewWidth * 2) * rightProgress + textViewWidth - knobWidth);
        int rightKnobRightEdge = (int) ((getWidth() - textViewWidth * 2) * rightProgress + textViewWidth);

        if (pointX >= leftKnobLeftEdge && pointX <= leftKnobRightEdge) {
            return 1;
        }
        if (pointX >= rightKnobLeftEdge && pointX <= rightKnobRightEdge) {
            return 2;
        }
        if (pointX > leftKnobRightEdge && pointX < rightKnobLeftEdge) {
            return 3;
        }
//        }
        return 0;
    }

    private int touchStatus;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("touch", "onTouchEvent event.getX:" + event.getX() + " width:" + getWidth());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //down的时候记录下触摸点X位置以及触摸点是否落在有效范围的状态
                touchStatus = checkTouchStatus(event.getX(), event.getY());
                pressX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                //触摸点落在有效范围内则跟随手指移动
                float change_progress = (event.getX() - pressX) / (getWidth() - textViewWidth * 2);
                if (touchStatus == 1) {
                    setLeftProgress(leftProgress + change_progress);
                } else if (touchStatus == 2) {
                    setRightProgress(rightProgress + change_progress);
                }
                pressX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                Log.e("touch", "onTouchEvent event.getX:" + event.getX() + " width:" + getWidth());
                if (touchStatus == 3) {
                    float progress = (event.getX() - textViewWidth) / (getWidth() - textViewWidth * 2);
                    if (event.getX() / getWidth() < 0.5) {
                        setLeftProgress(progress);
                    } else {
                        setRightProgress(progress);
                    }
                }
                touchStatus = 0;
                break;
            case MotionEvent.ACTION_CANCEL:
                touchStatus = 0;
                break;
        }
        return true;
    }

    public interface OnRangeChangedListener {
        void onRangeChanged(int left, int right);
    }

    public void setOnRangeChangedListener(OnRangeChangedListener onRangeChangedListener) {
        this.onRangeChangedListener = onRangeChangedListener;
    }
}
