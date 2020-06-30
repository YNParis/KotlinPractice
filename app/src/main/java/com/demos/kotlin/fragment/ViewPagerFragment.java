package com.demos.kotlin.fragment;


import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.demos.kotlin.R;
import com.demos.kotlin.adaper.ChromeTabViewPageAdapter;
import com.demos.kotlin.utils.ToastUtil;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerFragment extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private List<String> titles;
    private List<Integer> imgRrc;
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

        viewPager = view.findViewById(R.id.view_pager_fragment);
        tabLayout = view.findViewById(R.id.tab_layout_fragment);
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
//                            checkHasNext(null);
                        }
                        mIsScrolled = true;
                        break;

                }

            }
        });

        ChromeTabViewPageAdapter pageAdapter = new ChromeTabViewPageAdapter(getChildFragmentManager(), fragments, titles);
        viewPager.setAdapter(pageAdapter);
//        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < titles.size(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            getTabView(tab, i);
        }
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
        if (tabLayout.getTabCount() > 0)
            tabLayout.getTabAt(0).select();
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
        imgRrc = new ArrayList<>();
        imgRrc.add(R.drawable.ic_all);
        imgRrc.add(R.drawable.ic_insured);
        imgRrc.add(R.drawable.ic_org);
        imgRrc.add(R.drawable.ic_income);
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
            viewPager.setCurrentItem(--currentIndex);
        } else {
            ToastUtil.show(getActivity(), "已经是第一张了！");
        }

    }

    public void toNext() {
        if (currentIndex != fragments.size() - 1) {
            viewPager.setCurrentItem(++currentIndex);
        } else {
            ToastUtil.show(getActivity(), "已经是最后一张了！");
        }

    }

    /**
     * 改变字体样式
     *
     * @param tab
     * @param selected 选中状态
     */
    private void setTabText(TabLayout.Tab tab, boolean selected) {
        if (TextUtils.isEmpty(tab.getText())) return;
        String text = tab.getText().toString().trim();
        SpannableString spStr = new SpannableString(text);
        int textStyle = selected ? R.style.TabLayoutTextSelectedStyle : R.style.TabLayoutTextNormalStyle;
        spStr.setSpan(new TextAppearanceSpan(getContext(), textStyle), 0, text.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tab.setText(spStr);
    }

    private View getTabView(TabLayout.Tab tab, int position) {
        tab.setCustomView(R.layout.item_tab);
        View v = tab.getCustomView();
        ImageView imageView = v.findViewById(R.id.tab_img);
        TextView textView = v.findViewById(R.id.tab_title);
        textView.setText(titles.get(position));
        imageView.setImageResource(imgRrc.get(position));
        return v;
    }

}
