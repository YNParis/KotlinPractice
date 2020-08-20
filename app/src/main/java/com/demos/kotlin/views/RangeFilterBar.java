package com.demos.kotlin.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.demos.kotlin.R;

/**
 * Created by Administrator on 2017/10/30.
 */

public class RangeFilterBar extends View {

    Paint paint;

    Paint textPaint;

    private int color_line_normal;

    private int color_line_select;

    private float leftProgress = 0f;

    private float rightProgress = 1f;
    private float knobWidth = 30;

    private float pressX;

    private float stroke_width_normal;

    private float stroke_width_select;

    private int num = 100;

    Rect bounds = new Rect();


    private OnProgressChangeListener onProgressChangeListener;

    public void setOnProgressChangeListener(OnProgressChangeListener onProgressChangeListener) {
        this.onProgressChangeListener = onProgressChangeListener;
    }

    public interface OnProgressChangeListener {
        void onLeftProgressChange(float progress);

        void onRightProgressChange(float progress);
    }

    public RangeFilterBar(Context context) {
        super(context);
        init(context, null, 0);
    }

    public RangeFilterBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public RangeFilterBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    public RangeFilterBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.RangeFilterBar, defStyleAttr, 0);
        color_line_normal = a.getColor(R.styleable.RangeFilterBar_color_line_normal, Color.parseColor("#dedede"));
        color_line_select = a.getColor(R.styleable.RangeFilterBar_color_line_select, Color.parseColor("#238dfb"));
        stroke_width_normal = a.getDimension(R.styleable.RangeFilterBar_stroke_width_normal, 2f);
        stroke_width_select = a.getDimension(R.styleable.RangeFilterBar_stroke_width_select, 4f);

        float text_size = a.getDimension(R.styleable.RangeFilterBar_text_size, 24f);
        int text_color = a.getColor(R.styleable.RangeFilterBar_text_color, color_line_select);
        a.recycle();
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(text_size);
        textPaint.setColor(text_color);
        textPaint.setStyle(Paint.Style.STROKE);
    }

    public void setLeftProgress(float progress) {
        float end_progress;
        end_progress = rightProgress;
        if (progress <= 0f) {
            progress = 0f;
        }
        if (progress >= end_progress) {
            progress = end_progress;
        }
        this.leftProgress = progress;
        if (onProgressChangeListener != null) {
            onProgressChangeListener.onLeftProgressChange(progress);
        }
        invalidate();
    }

    public void setRightProgress(float progress) {
        float start_progress;
        start_progress = leftProgress;
        if (progress <= start_progress) {
            progress = start_progress;
        }
        if (progress >= 1f) {
            progress = 1f;
        }
        this.rightProgress = progress;
        if (onProgressChangeListener != null) {
            onProgressChangeListener.onRightProgressChange(progress);
        }
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 获取对应高度
        int height = getHeight();
        // 获取对应宽度
        int width = getWidth();

        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        //左侧按钮
        float startProgressX = width * leftProgress;
        canvas.drawRoundRect(new RectF(startProgressX, 0, startProgressX + knobWidth, height), 5, 5, paint);
        //右侧按钮
        float endProgressX = width * rightProgress;
        canvas.drawRoundRect(new RectF(endProgressX - knobWidth, 0, endProgressX, height), 5, 5, paint);

        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRoundRect(new RectF(startProgressX, 0, startProgressX + knobWidth, height), 5, 5, paint);
        canvas.drawRoundRect(new RectF(endProgressX - knobWidth, 0, endProgressX, height), 5, 5, paint);

        //画刻度值
        String left_num = String.valueOf((int) (leftProgress * num));
        String right_num = String.valueOf((int) (rightProgress * num));
        textPaint.getTextBounds(right_num, 0, right_num.length(), bounds);
        canvas.drawText(left_num, startProgressX - textPaint.getTextSize() * left_num.length(), height - (height - textPaint.getTextSize()) / 2, textPaint);
        canvas.drawText(right_num, endProgressX, height - (height - textPaint.getTextSize()) / 2, textPaint);
    }

    /**
     * @param pointX
     * @param pointY
     * @return 0表示触摸点不在两头按钮范围内，1表示触摸点落在左侧按钮范围内，2表示触摸点落在右侧按钮范围内
     */
    private int checkTouchStatus(float pointX, float pointY) {
        //注释掉的是Y轴上触摸点是否落在按钮的高度范围之内，之所以注释是为了触摸的范围大一点
//        if (pointY >= (getHeight() - leftProgressIcon.getHeight()) / 2.0f && pointY <= getHeight() - (getHeight() - leftProgressIcon.getHeight()) / 2.0f) {
        if (pointX >= getWidth() * leftProgress && pointX <= getWidth() * leftProgress + knobWidth) {
            return 1;
        }
        if (pointX >= getWidth() * rightProgress - knobWidth && pointX <= getWidth() * rightProgress) {
            return 2;
        }
//        }
        return 0;
    }

    private int touchStatus;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //down的时候记录下触摸点X位置以及触摸点是否落在有效范围的状态
                touchStatus = checkTouchStatus(event.getX(), event.getY());
                pressX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                //触摸点落在有效范围内则跟随手指移动
                float change_progress = (event.getX() - pressX) / getWidth();
                if (touchStatus == 1) {
                    setLeftProgress(leftProgress + change_progress);
                } else if (touchStatus == 2) {
                    setRightProgress(rightProgress + change_progress);
                }
                pressX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                touchStatus = 0;
                break;
            case MotionEvent.ACTION_CANCEL:
                touchStatus = 0;
                break;
        }
        return true;
    }
}
