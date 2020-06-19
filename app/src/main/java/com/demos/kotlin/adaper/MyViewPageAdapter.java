package com.demos.kotlin.adaper;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.demos.kotlin.fragment.BlankFragment;
import com.demos.kotlin.fragment.BlankFragment2;
import com.demos.kotlin.fragment.BlankFragment3;

import java.util.List;

/**
 * Created by Administrator on 2017/5/2.
 */

public class MyViewPageAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mList;
    private FragmentManager fragmentManager;

    public MyViewPageAdapter(FragmentManager fm, List<Fragment> mList) {
        super(fm);
        this.mList = mList;
        this.fragmentManager = fm;
    }

    @Override
    public Fragment getItem(int position) {
        //轮播
        switch (position % mList.size()) {
            case 0:
                return new BlankFragment();
            case 1:
                return new BlankFragment2();
            case 2:
            default:
                return new BlankFragment3();
        }
    }

    @Override
    public int getCount() {
        //循环播放
        return Integer.MAX_VALUE;
    }

}
