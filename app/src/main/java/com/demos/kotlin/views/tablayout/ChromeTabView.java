package com.demos.kotlin.views.tablayout;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
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
public class ChromeTabView extends FrameLayout {
    private Context mContext;
    private List<Fragment> fragments;
    private List<String> titles;
    private FragmentManager fragmentManager;

    public ChromeTabView(Context context, FragmentManager fm, List<Fragment> fragments, List<String> titles) {
        super(context);
    }

    public ChromeTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChromeTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ChromeTabView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
        ViewPager viewPager = fatherView.findViewById(R.id.chrome_view_pager);
        TabLayout tabLayout = fatherView.findViewById(R.id.chrome_tab_layout);
        for (int i = 0; i < titles.size(); i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            if (i == 0) {
                //默认选中第一个
                ViewCompat.setBackground(tab.view, mContext.getDrawable(R.drawable.selector_tab_background_left));
            } else if (i == titles.size() - 1) {
                ViewCompat.setBackground(tab.view, mContext.getDrawable(R.drawable.selector_tab_background_right));
            }
            tabLayout.addTab(tab);
        }
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
        ChromeTabViewPageAdapter pageAdapter = new ChromeTabViewPageAdapter(fragmentManager, fragments, titles);
        viewPager.setAdapter(pageAdapter);
        tabLayout.setupWithViewPager(viewPager);
        if (tabLayout.getTabCount() > 0)
            tabLayout.getTabAt(0).select();
    }

    /**
     * 改变字体样式
     *
     * @param tab
     * @param selected 选中状态
     */
    private void setTabText(TabLayout.Tab tab, boolean selected) {
        if (TextUtils.isEmpty(tab.getText())) return;
        String text = tab.getText().toString().trim();
        SpannableString spStr = new SpannableString(text);
        int textStyle = selected ? R.style.TabLayoutTextSelectedStyle : R.style.TabLayoutTextNormalStyle;
        spStr.setSpan(new TextAppearanceSpan(mContext, textStyle), 0, text.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tab.setText(spStr);
    }

}
