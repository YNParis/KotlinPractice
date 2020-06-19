package com.demos.kotlin.views.tablayout;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.demos.kotlin.R;
import com.demos.kotlin.adaper.ChromeTabViewPageAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

/**
 * 仿谷歌浏览器的标签页效果
 * 使用TabLayout + ViewPager实现
 */
public class ChromeTabLayout extends FrameLayout {
    private Context mContext;
    private List<Fragment> fragments;
    private List<String> titles;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private FragmentManager fragmentManager;

    public ChromeTabLayout(Context context, FragmentManager fm, List<Fragment> fragments, List<String> titles) {
        super(context);
    }

    public ChromeTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChromeTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ChromeTabLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * 初始化，必须调用
     *
     * @param context
     * @param fm
     * @param fragments
     * @param titles
     */
    public void init(Context context, FragmentManager fm, List<Fragment> fragments, List<String> titles) {
        this.mContext = context;
        this.fragmentManager = fm;
        this.fragments = fragments;
        this.titles = titles;
        initView();
    }


    /**
     * 加载页面
     */
    private void initView() {
        //设置页面属性
        View fatherView =
                LayoutInflater.from(mContext).inflate(R.layout.layout_chrome_tab, this, true);
        viewPager = fatherView.findViewById(R.id.chrome_view_pager);
        tabLayout = fatherView.findViewById(R.id.chrome_tab_layout);
        for (int i = 0; i < titles.size(); i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            if (i == 0) {
                //默认选中第一个
                tab.select();
                ViewCompat.setBackground(tab.view, tabLayout.getContext().getDrawable(R.drawable.selector_tab_background_left));
            } else if (i == titles.size() - 1) {
                ViewCompat.setBackground(tab.view, tabLayout.getContext().getDrawable(R.drawable.selector_tab_background_right));
            }
            tabLayout.addTab(tab);
        }
        ChromeTabViewPageAdapter pageAdapter = new ChromeTabViewPageAdapter(fragmentManager, fragments, titles);
        viewPager.setAdapter(pageAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setTabText(tab, true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                setTabText(tab, false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void setTabText(TabLayout.Tab tab, boolean selected) {
        String text = tab.getText().toString().trim();
        SpannableString spStr = new SpannableString(text);
        if (selected) {
            spStr.setSpan(new AbsoluteSizeSpan(45), 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spStr.setSpan(new StyleSpan(Typeface.BOLD), 0, text.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                spStr.setSpan(new ForegroundColorSpan(mContext.getColor(R.color.colorWhite)), 0, text.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                spStr.setSpan(new ForegroundColorSpan(mContext.getColor(R.color.colorTabBorder)), 0, text.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            }
            spStr.setSpan(new AbsoluteSizeSpan(40), 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spStr.setSpan(new StyleSpan(Typeface.NORMAL), 0, text.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        tab.setText(spStr);
    }

}
