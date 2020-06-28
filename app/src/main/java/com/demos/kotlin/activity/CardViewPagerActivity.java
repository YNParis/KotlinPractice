package com.demos.kotlin.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.demos.kotlin.R;
import com.demos.kotlin.adaper.MyViewPageAdapter;
import com.demos.kotlin.fragment.BlankFragment;
import com.demos.kotlin.fragment.BlankFragment2;
import com.demos.kotlin.fragment.BlankFragment3;
import com.demos.kotlin.utils.ToastUtil;
import com.demos.kotlin.views.cardviewpager.CardPageTransformer;

import java.util.ArrayList;
import java.util.List;

public class CardViewPagerActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private List<Fragment> mList = new ArrayList<>();
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view_pager);
        initData();
        viewPager = findViewById(R.id.view_pager_card);
        viewPager.setPageTransformer(true, new CardPageTransformer());
        MyViewPageAdapter adapter = new MyViewPageAdapter(getSupportFragmentManager(), mList);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == currentIndex) {

                }
            }

            @Override
            public void onPageSelected(int position) {
                currentIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setOffscreenPageLimit(3);//设置缓存view 的个数（实际有3个，缓存2个+正在显示的1个）
//        viewPager.setPageMargin(20);//设置viewpager每个页卡的间距，与gallery的spacing属性类似
        viewPager.setAdapter(adapter);
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            mList.add(new BlankFragment());
            mList.add(new BlankFragment2());
            mList.add(new BlankFragment3());
        }
    }

    public void toPre(View view) {
        if (currentIndex == 0) {
            ToastUtil.show(this, "当前是第一张");
            return;
        }

        viewPager.setCurrentItem(--currentIndex, true);
    }

    public void toNext(View view) {

        if (currentIndex == mList.size() - 1) {
            ToastUtil.show(this, "当前是最后一张");
            return;
        }
        viewPager.setCurrentItem(++currentIndex, true);
    }
}
