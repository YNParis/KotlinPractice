package com.demos.kotlin.views.cardviewpager;

import android.util.Log;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

public class CardPageTransformer implements ViewPager.PageTransformer {
    private int loadPageCount;

    public CardPageTransformer(int loadPageCount) {
        this.loadPageCount = loadPageCount;
    }

    private static final float MIN_SCALE = 0.75f;
    private static final float MIN_ALPHA = 0.5f;
    private static final float NORMAL_SCALE = 0.9f;
    private static final int NORMAL_OFFSET = 100;

    public void transformPage(View view, float position) {
        Log.e("viewpager", "position:" + position);
        int pageWidth = view.getWidth();
        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(0);
        } else if (position <= 0)// a页滑动至b页 ； a页从 0.0 -1 ；b页从1 ~ 0.0
        { // [-1,0]
            // Use the default slide transition when moving to the left page
            view.setAlpha(1);
            view.setTranslationX(0);
            view.setScaleX(NORMAL_SCALE);
            view.setScaleY(NORMAL_SCALE);
        } else if (position <= loadPageCount) {
            //缓冲两个在后面，有透明度效果
            view.setAlpha(Math.max(1 - position, MIN_ALPHA));
            view.setTranslationX((pageWidth - NORMAL_OFFSET) * -position);
            float scaleFactor = MIN_SCALE + (NORMAL_SCALE - MIN_SCALE) * (1 - position);
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
        } else { // (3,+Infinity]
            view.setAlpha(0);
        }
    }
}
