package com.demos.kotlin.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.demos.kotlin.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 流式列表
 */
public class WrapLinearLayout extends ViewGroup {

    private Type mType;
    private List<WrapLine> mWrapLineGroup;

    public WrapLinearLayout(Context context) {
        this(context, null);
    }

    public WrapLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, R.style.WrapLinearLayoutDefault);
    }

    public WrapLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mType = new Type(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int withMode = MeasureSpec.getMode(widthMeasureSpec);
        int withSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int with = 0;
        int height = 0;
        int childCount = getChildCount();
        /**
         * 在调用childView。getMeasre之前必须先调用该行代码，用于对子View大小的测量
         */
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        /**
         * 计算宽度
         */
        switch (withMode) {
            case MeasureSpec.EXACTLY:
                with = withSize;
                break;
            case MeasureSpec.AT_MOST:
                for (int i = 0; i < childCount; i++) {
                    if (i != 0) {
                        with += mType.horizontal_space;
                    }
                    with += getChildAt(i).getMeasuredWidth();
                }
                with += getPaddingLeft() + getPaddingRight();
                with = with > withSize ? withSize : with;
                break;
            case MeasureSpec.UNSPECIFIED:
                for (int i = 0; i < childCount; i++) {
                    if (i != 0) {
                        with += mType.horizontal_space;
                    }
                    with += getChildAt(i).getMeasuredWidth();
                }
                with += getPaddingLeft() + getPaddingRight();
                break;
            default:
                with = withSize;
                break;

        }
        /**
         * 根据计算出的宽度，计算出所需要的行数
         */
        WrapLine wrapLine = new WrapLine();
        /**
         * 不能够在定义属性时初始化，因为onMeasure方法会多次调用
         */
        mWrapLineGroup = new ArrayList<>();
        for (int i = 0; i < childCount; i++) {
            if (wrapLine.lineWidth + getChildAt(i).getMeasuredWidth() + mType.horizontal_space > with) {
                if (wrapLine.lineView.size() == 0) {
                    wrapLine.addView(getChildAt(i));
                    mWrapLineGroup.add(wrapLine);
                    wrapLine = new WrapLine();
                } else {
                    mWrapLineGroup.add(wrapLine);
                    wrapLine = new WrapLine();
                    wrapLine.addView(getChildAt(i));
                }
            } else {
                wrapLine.addView(getChildAt(i));
            }
        }
        /**
         * 添加最后一行
         */
        if (wrapLine.lineView.size() > 0 && !mWrapLineGroup.contains(wrapLine)) {
            mWrapLineGroup.add(wrapLine);
        }
        /**
         * 计算宽度
         */
        height = getPaddingTop() + getPaddingBottom();
        for (int i = 0; i < mWrapLineGroup.size(); i++) {
            if (i != 0) {
                height += mType.vertical_space;
            }
            height += mWrapLineGroup.get(i).height;
        }
        switch (heightMode) {
            case MeasureSpec.EXACTLY:
                height = heightSize;
                break;
            case MeasureSpec.AT_MOST:
                height = height > heightSize ? heightSize : height;
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
            default:
                break;
        }
        setMeasuredDimension(with, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        t = getPaddingTop();
        for (int i = 0; i < mWrapLineGroup.size(); i++) {
            int left = getPaddingLeft();
            WrapLine wrapLine = mWrapLineGroup.get(i);
            int lastWidth = getMeasuredWidth() - wrapLine.lineWidth;
            for (int j = 0; j < wrapLine.lineView.size(); j++) {
                View view = wrapLine.lineView.get(j);
                if (isFull()) {//需要充满当前行时
                    view.layout(left, t, left + view.getMeasuredWidth() + lastWidth / wrapLine.lineView.size(), t + view.getMeasuredHeight());
                    left += view.getMeasuredWidth() + mType.horizontal_space + lastWidth / wrapLine.lineView.size();
                } else {
                    switch (getGrivate()) {
                        case 0://右对齐
                            view.layout(left + lastWidth, t, left + lastWidth + view.getMeasuredWidth(), t + view.getMeasuredHeight());
                            break;
                        case 2://居中对齐
                            view.layout(left + lastWidth / 2, t, left + lastWidth / 2 + view.getMeasuredWidth(), t + view.getMeasuredHeight());
                            break;
                        default://左对齐
                            view.layout(left, t, left + view.getMeasuredWidth(), t + view.getMeasuredHeight());
                            break;
                    }
                    left += view.getMeasuredWidth() + mType.horizontal_space;
                }
            }
            t += wrapLine.height + mType.vertical_space;
        }
    }

    /**
     * 用于存放一行子View
     */
    private final class WrapLine {
        private List<View> lineView = new ArrayList<View>();
        /**
         * 当前行中所需要占用的宽度
         */
        private int lineWidth = getPaddingLeft() + getPaddingRight();
        /**
         * 该行View中所需要占用的最大高度
         */
        private int height = 0;

        private void addView(View view) {
            if (lineView.size() != 0) {
                lineWidth += mType.horizontal_space;
            }
            height = height > view.getMeasuredHeight() ? height : view.getMeasuredHeight();
            lineWidth += view.getMeasuredWidth();
            lineView.add(view);
        }
    }

    /**
     * 对样式的初始化
     */
    private final static class Type {
        /*
         *对齐方式 right 0，left 1，center 2
         */
        private int grivate;
        /**
         * 水平间距,单位px
         */
        private float horizontal_space;
        /**
         * 垂直间距,单位px
         */
        private float vertical_space;
        /**
         * 是否自动填满
         */
        private boolean isFull;

        Type(Context context, AttributeSet attrs) {
            if (attrs == null) {
                return;
            }
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WrapLinearLayout);
            grivate = typedArray.getInt(R.styleable.WrapLinearLayout_grivate, grivate);
            horizontal_space = typedArray.getDimension(R.styleable.WrapLinearLayout_horizontal_space, horizontal_space);
            vertical_space = typedArray.getDimension(R.styleable.WrapLinearLayout_vertical_space, vertical_space);
            isFull = typedArray.getBoolean(R.styleable.WrapLinearLayout_isFull, isFull);
        }
    }

    public int getGrivate() {
        return mType.grivate;
    }

    public float getHorizontal_Space() {
        return mType.horizontal_space;
    }

    public float getVertical_Space() {
        return mType.vertical_space;
    }

    public boolean isFull() {
        return mType.isFull;
    }

    public void setGrivate(int grivate) {
        mType.grivate = grivate;
    }

    public void setHorizontal_Space(float horizontal_Space) {
        mType.horizontal_space = horizontal_Space;
    }

    public void setVertical_Space(float vertical_Space) {
        mType.vertical_space = vertical_Space;
    }

    public void setIsFull(boolean isFull) {
        mType.isFull = isFull;
    }

    /**
     * 每行子View的对齐方式
     */
    public final static class Gravite {
        public final static int RIGHT = 0;
        public final static int LEFT = 1;
        public final static int CENTER = 2;
    }

}
