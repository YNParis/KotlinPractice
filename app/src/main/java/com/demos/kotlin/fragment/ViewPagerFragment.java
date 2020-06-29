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
import com.demos.kotlin.utils.ToastUtil;
import com.demos.kotlin.views.tablayout.ChromeTabView;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerFragment extends Fragment {

    private ChromeTabView chromeTabView;
    private List<String> titles;
    private List<Fragment> fragments;
    private int currentIndex;
    private boolean mIsScrolled;

    public ViewPagerFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewPagerFragment newInstance(String param1, String param2) {
        ViewPagerFragment fragment = new ViewPagerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_pager, container, false);
        initData();
        initView(view);
        return view;
    }

    private void initView(View view) {
        chromeTabView = view.findViewById(R.id.chrome_view_fragment);
        chromeTabView.init(getActivity(), getChildFragmentManager(), fragments, titles, true);
        chromeTabView.getViewPager().addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
//                currentIndex = position;
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
//                            checkHasNext(null);
                        }
                        mIsScrolled = true;
                        break;

                }

            }
        });
    }

    private void initData() {
        titles = new ArrayList<>();
        titles.add("child 1");
        titles.add("child 2");
        titles.add("child 3");
        titles.add("child 4");
        fragments = new ArrayList<>();
        fragments.add(new BlankFragment2());
        fragments.add(new BlankFragment2());
        fragments.add(new BlankFragment2());
        fragments.add(new BlankFragment2());
    }

    /**
     * 检查是否有上一张或者下一张
     *
     * @return 有下一张
     */
    private boolean checkHasNext(@Nullable String tag) {
        boolean isDrag = TextUtils.isEmpty(tag);
        if (currentIndex == 0 && (isDrag || "0".equals(tag))) {
            ToastUtil.show(getActivity(), "已经是第一张了！");
            return false;
        }
        if (currentIndex == fragments.size() - 1 && (isDrag || "1".equals(tag))) {
            ToastUtil.show(getActivity(), "已经是最后一张了！");
            return false;
        }
        return true;
    }

    public void toPre() {
        if (currentIndex != 0) {
            chromeTabView.getViewPager().setCurrentItem(--currentIndex);
        } else {
            ToastUtil.show(getActivity(), "已经是第一张了！");
        }

    }

    public void toNext() {
        if (currentIndex != fragments.size() - 1) {
            chromeTabView.getViewPager().setCurrentItem(++currentIndex);
        } else {
            ToastUtil.show(getActivity(), "已经是最后一张了！");
        }

    }

}
