package com.demos.kotlin.views.cardviewpager;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class CardViewPager extends ViewPager {
    public CardViewPager(@NonNull Context context) {
        super(context);
    }

    public CardViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
}
