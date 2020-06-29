package com.demos.kotlin.views.tablayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class ChromeViewPager extends ViewPager {
    private boolean canScroll = true;

    public ChromeViewPager(@NonNull Context context) {
        super(context);
    }

    public ChromeViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    //决定事件是否中断
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;//不拦截事件, 让嵌套的子viewpager有机会响应触摸事件
    }

    public void setCanScroll(boolean canScroll) {
        this.canScroll = canScroll;
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        return canScroll;
    }
}
