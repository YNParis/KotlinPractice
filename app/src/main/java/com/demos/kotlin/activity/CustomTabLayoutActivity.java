package com.demos.kotlin.activity;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.demos.kotlin.R;
import com.demos.kotlin.adaper.MyViewPageAdapter;
import com.demos.kotlin.fragment.BlankFragment3;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class CustomTabLayoutActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private List<String> tabList;
    private ViewPager viewPager;
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_tab_host);
        initData();
        initView();
    }

    private void initData() {
        tabList = new ArrayList<>();
        tabList.add("养老保险");
        tabList.add("工伤保险");
        tabList.add("失业保险");
        tabList.add("生育保险");
        tabList.add("医疗保险");
        fragmentList = new ArrayList<>();
    }

    private void initView() {

        viewPager = findViewById(R.id.view_pager_layout);
        tabLayout = findViewById(R.id.tab_layout);
        for (int i = 0; i < tabList.size(); i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setText(tabList.get(i));
            if (i == 0) {
                //默认选中第一个
                tab.select();
                ViewCompat.setBackground(tab.view, tabLayout.getContext().getDrawable(R.drawable.selector_tab_background_left));
            } else if (i == tabList.size() - 1) {
                ViewCompat.setBackground(tab.view, tabLayout.getContext().getDrawable(R.drawable.selector_tab_background_right));
            }
            tabLayout.addTab(tab);
            fragmentList.add(new BlankFragment3());
        }
        MyViewPageAdapter pageAdapter = new MyViewPageAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(new MyViewPageAdapter(getSupportFragmentManager(), fragmentList, tabList));
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
                spStr.setSpan(new ForegroundColorSpan(getColor(R.color.colorWhite)), 0, text.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                spStr.setSpan(new ForegroundColorSpan(getColor(R.color.colorTabBorder)), 0, text.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            }
            spStr.setSpan(new AbsoluteSizeSpan(40), 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spStr.setSpan(new StyleSpan(Typeface.NORMAL), 0, text.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        tab.setText(spStr);
    }
}
