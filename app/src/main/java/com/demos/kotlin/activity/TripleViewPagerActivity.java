package com.demos.kotlin.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.demos.kotlin.R;
import com.demos.kotlin.fragment.ViewPagerFragment;
import com.demos.kotlin.views.tablayout.ChromeTabView;

import java.util.ArrayList;
import java.util.List;

public class TripleViewPagerActivity extends AppCompatActivity {

    ChromeTabView chromeTabView;
    private List<String> titles;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_triple_view_pager);
        initData();
        initView();
    }

    private void initView() {
        chromeTabView = findViewById(R.id.chrome_view_triple);
        chromeTabView.init(this, getSupportFragmentManager(), fragments, titles, false);
    }

    private void initData() {
        titles = new ArrayList<>();
        titles.add("tab1");
        titles.add("tab2");
        titles.add("tab3");
        titles.add("tab4");
        fragments = new ArrayList<>();
        fragments.add(new ViewPagerFragment());
        fragments.add(new ViewPagerFragment());
        fragments.add(new ViewPagerFragment());
        fragments.add(new ViewPagerFragment());
    }
}
