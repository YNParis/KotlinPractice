package com.demos.kotlin.adaper;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/5/2.
 */

public class MyViewPageAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mList;
    private List<String> titles;
    private FragmentManager fragmentManager;

    public MyViewPageAdapter(FragmentManager fm, List<Fragment> mList) {
        super(fm);
        this.mList = mList;
        this.fragmentManager = fm;
    }

    public MyViewPageAdapter(FragmentManager fm, List<Fragment> mList, List<String> titles) {
        super(fm);
        if (mList != null)
            this.mList = mList;
        this.titles = titles;
        this.fragmentManager = fm;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
        //轮播
        /*switch (position % mList.size()) {
            case 0:
                return new BlankFragment();
            case 1:
                return new BlankFragment2();
            case 2:
            default:
                return new BlankFragment3();
        }*/
    }


    @Override
    public int getCount() {
        if (mList != null && titles != null) {
            return Math.min(mList.size(), titles.size());
        }
        //循环播放
        return Integer.MAX_VALUE;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles == null ? "" : titles.get(position);
    }
}
