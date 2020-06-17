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

import com.demos.kotlin.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class CustomTabHostActivity extends AppCompatActivity {

    private TabLayout tabHost;
    private List<String> tabList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_tab_host);
        initData();
        initView();
    }

    private void initData() {
        tabList.add("养老保险");
        tabList.add("工伤保险");
        tabList.add("失业保险");
        tabList.add("生育保险");
        tabList.add("医疗保险");
    }


    private void initView() {

        tabHost = findViewById(R.id.tab_layout);
        tabHost.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setTabText(tab, true);
//                setCustomTabBackground(tabHost, tabHost.getTabCount(), tabHost.getSelectedTabPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                setTabText(tab, false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        for (int i = 0; i < tabList.size(); i++) {
            TabLayout.Tab tab = tabHost.newTab();
            tab.setText(tabList.get(i));
            if (i == 0) {
                //默认选中第一个
                tab.select();
                ViewCompat.setBackground(tab.view, tabHost.getContext().getDrawable(R.drawable.selector_tab_background_left));
            } else if (i == tabList.size() - 1) {
                ViewCompat.setBackground(tab.view, tabHost.getContext().getDrawable(R.drawable.selector_tab_background_right));
            }
            tabHost.addTab(tab);
        }
    }

    public void setCustomTabBackground(TabLayout tabLayout, int tabListSize, int position) {
        TabLayout.Tab tabAt = tabLayout.getTabAt(position);
        if (tabAt != null) {
            if (position == 0) {
                ViewCompat.setBackground(tabAt.view, tabLayout.getContext().getDrawable(R.drawable.selector_tab_background_left));
            } else if (position == tabListSize - 1) {
                ViewCompat.setBackground(tabAt.view, tabLayout.getContext().getDrawable(R.drawable.selector_tab_background_right));
            }
        }
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
