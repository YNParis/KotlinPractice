package com.demos.kotlin.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
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
    private int cachedPages = 3;
    private boolean mIsScrolled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view_pager);
        initData();
        viewPager = findViewById(R.id.view_pager_card);
        viewPager.setPageTransformer(true, new CardPageTransformer(cachedPages));
        MyViewPageAdapter adapter = new MyViewPageAdapter(getSupportFragmentManager(), mList);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                currentIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //检测滑动到第一页或者最后一页
                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        mIsScrolled = false;
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:
                        mIsScrolled = true;
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        if (!mIsScrolled) {
                            checkHasNext(null);
                        }
                        mIsScrolled = true;
                        break;

                }

            }
        });
        viewPager.setOffscreenPageLimit(cachedPages);//设置缓存view 的个数（实际有3个，缓存2个+正在显示的1个）
        viewPager.setAdapter(adapter);
    }

    private void initData() {
        for (int i = 0; i < 2; i++) {
            mList.add(new BlankFragment());
            mList.add(new BlankFragment2());
            mList.add(new BlankFragment3());
        }
    }

    /**
     * 检查是否有上一张或者下一张
     *
     * @return 有下一张
     */
    private boolean checkHasNext(@Nullable String tag) {
        boolean isDrag = TextUtils.isEmpty(tag);
        if (currentIndex == 0 && (isDrag || "0".equals(tag))) {
            ToastUtil.show(this, "已经是第一张了！");
            return false;
        }
        if (currentIndex == mList.size() - 1 && (isDrag || "1".equals(tag))) {
            ToastUtil.show(this, "已经是最后一张了！");
            return false;
        }
        return true;
    }


    public void toPre(View view) {
        if (checkHasNext("0"))
            viewPager.setCurrentItem(--currentIndex, true);
    }

    public void toNext(View view) {
        if (checkHasNext("1"))
            viewPager.setCurrentItem(++currentIndex, true);
    }
}
