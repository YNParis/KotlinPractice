package com.demos.kotlin.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.demos.kotlin.R;
import com.demos.kotlin.fragment.BlankFragment;
import com.demos.kotlin.fragment.BlankFragment2;
import com.demos.kotlin.fragment.BlankFragment3;
import com.demos.kotlin.views.tablayout.ChromeTabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 仿Chrome浏览器的tab切换效果
 */
public class ChromeTabActivity extends AppCompatActivity {

    private List<String> titles;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chrome_tab);
        initData();
        initView();
    }

    private void initView() {
        ChromeTabLayout chromeTabLayout = findViewById(R.id.chrome_tab_view);
        chromeTabLayout.init(this, getSupportFragmentManager(), fragments, titles);
    }

    private void initData() {
        titles = new ArrayList<>();
        titles.add("tab1");
        titles.add("tab2");
        titles.add("tab3");
        titles.add("tab4");
        fragments = new ArrayList<>();
        fragments.add(new BlankFragment());
        fragments.add(new BlankFragment2());
        fragments.add(new BlankFragment());
        fragments.add(new BlankFragment3());
    }


}
