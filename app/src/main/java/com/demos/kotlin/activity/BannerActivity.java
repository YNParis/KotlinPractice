package com.demos.kotlin.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.demos.kotlin.R;
import com.demos.kotlin.adaper.MyViewPageAdapter;
import com.demos.kotlin.fragment.BlankFragment;
import com.demos.kotlin.fragment.BlankFragment2;
import com.demos.kotlin.fragment.BlankFragment3;

import java.util.ArrayList;
import java.util.List;

public class BannerActivity extends AppCompatActivity {

    int currentIndex;//现在在哪个下表下
    Handler handler;
    ViewPager viewPager;
    TimerRunnable runnable;
    List<Fragment> mList = new ArrayList<>();
    private ViewPager.OnPageChangeListener onPageChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        initData();

        runnable = new TimerRunnable();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Log.e("tag", "currentIndex------------------" + currentIndex);
                switch (msg.what) {
                    case 1:
                        setFragmentWithIndex(++currentIndex);
                        Log.e("tag", "currentIndex------------------" + currentIndex);
                        break;
                    case 0:
                        try {
                            runnable.wait(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                }
                super.handleMessage(msg);
            }
        };
        viewPager = findViewById(R.id.view_pager);
        MyViewPageAdapter myViewPageAdapter = new MyViewPageAdapter(getSupportFragmentManager(), mList);
        viewPager.setAdapter(myViewPageAdapter);
        viewPager.setCurrentItem(currentIndex);
        onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e("tag", "OnPageChangeListener--------------onPageScrolled");
            }

            @Override
            public void onPageSelected(int position) {
                //TODO 计时开始，5秒后翻下一页
//                handler.postDelayed(runnable, 5000);
                Log.e("tag", "OnPageChangeListener--------------onPageSelected");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e("tag", "OnPageChangeListener--------------onPageScrollStateChanged");
            }
        };
        viewPager.addOnPageChangeListener(onPageChangeListener);
        handler.postDelayed(runnable, 3000);
    }

    private void initData() {
        currentIndex = 300;
        mList.add(new BlankFragment());
        mList.add(new BlankFragment2());
        mList.add(new BlankFragment3());
    }

    public void setFragmentWithIndex(int index) {
        viewPager.setCurrentItem(index);
    }

    class TimerRunnable implements Runnable {

        @Override
        public void run() {
            handler.sendEmptyMessage(1);
            handler.postDelayed(runnable, 3000);
        }
    }

    @Override
    protected void onDestroy() {
        viewPager.removeOnPageChangeListener(onPageChangeListener);
        super.onDestroy();
    }
}
