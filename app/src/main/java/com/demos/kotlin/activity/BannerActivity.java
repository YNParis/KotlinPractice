package com.demos.kotlin.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

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
import java.util.Timer;
import java.util.TimerTask;

public class BannerActivity extends AppCompatActivity {

    Handler handler;
    ViewPager viewPager;
    List<Fragment> mList = new ArrayList<>();
    private ViewPager.OnPageChangeListener onPageChangeListener;
    private Timer timer;
    private TimerTask timerTask;
    private String tag = "banner";
    private int period = 5000, delay = 5000, currentIndex = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        initData();
        initViewPager();
        initTimer();
    }

    private void initTimer() {
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
                        resetTimer();
                        break;
                }
                super.handleMessage(msg);
            }
        };
        startTimer();
    }

    private void initViewPager() {
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
                currentIndex = position;
                Log.e(tag, "OnPageChangeListener--------------onPageSelected");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e(tag, "OnPageChangeListener--------------onPageScrollStateChanged");
            }
        };
        viewPager.addOnPageChangeListener(onPageChangeListener);
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        stopTimer();
                        break;
                    case MotionEvent.ACTION_UP:
                        resetTimer();
                        break;
                }
                return false;
            }
        });

    }

    private void initData() {
        mList.add(new BlankFragment());
        mList.add(new BlankFragment2());
        mList.add(new BlankFragment3());
    }

    private void startTimer() {

        if (timer == null) {
            timer = new Timer();
        }
        if (timerTask == null) {
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    handler.sendEmptyMessage(1);
                }
            };
        }
        timer.schedule(timerTask, delay, period);
        Log.e(tag, "--------------startTimer");
    }

    private void stopTimer() {
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        Log.e(tag, "--------------stopTimer");
    }

    private void resetTimer() {
        //重置
        Log.e(tag, "---------reset");
        stopTimer();
        startTimer();
    }

    public void setFragmentWithIndex(int index) {
        viewPager.setCurrentItem(index);
    }

    @Override
    protected void onDestroy() {
        viewPager.removeOnPageChangeListener(onPageChangeListener);
        stopTimer();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        stopTimer();
        super.onStop();
    }
}
