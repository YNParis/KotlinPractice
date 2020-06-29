package com.demos.kotlin.fragment;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.demos.kotlin.R;
import com.demos.kotlin.adaper.MyViewPageAdapter;
import com.demos.kotlin.views.cardviewpager.CardPageTransformer;
import com.demos.kotlin.views.tablayout.ChromeViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment2 extends Fragment {

    private ChromeViewPager viewPager;
    private List<Fragment> mList = new ArrayList<>();
    private int currentIndex = 0;
    private int cachedPages = 3;
    private boolean mIsScrolled;

    public BlankFragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initData();
        View view = inflater.inflate(R.layout.fragment_blank_fragment2, container, false);
        viewPager = view.findViewById(R.id.view_pager_fragment_2);
        viewPager.setPageTransformer(true, new CardPageTransformer(3));
        viewPager.setCanScroll(true);
        MyViewPageAdapter adapter = new MyViewPageAdapter(getChildFragmentManager(), mList);
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
        viewPager.setOffscreenPageLimit(3);//设置缓存view 的个数（实际有3个，缓存2个+正在显示的1个）
        viewPager.setAdapter(adapter);
        return view;
    }

    /**
     * 检查是否有上一张或者下一张
     *
     * @return 有下一张
     */
    private boolean checkHasNext(@Nullable String tag) {
        boolean isDrag = TextUtils.isEmpty(tag);
        if (currentIndex == 0 && (isDrag || "0".equals(tag))) {
            ((ViewPagerFragment) getParentFragment()).toPre();
//            ToastUtil.show(getActivity(), "已经是第一张了！");
            return false;
        }
        if (currentIndex == mList.size() - 1 && (isDrag || "1".equals(tag))) {
            ((ViewPagerFragment) getParentFragment()).toNext();
//            ToastUtil.show(getActivity(), "已经是最后一张了！");
            return false;
        }
        return true;
    }

    private void initData() {
        for (int i = 0; i < 5; i++) {
            mList.add(new BlankFragment());
        }
    }
}
