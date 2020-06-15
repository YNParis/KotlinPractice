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

    List<Fragment> mList;
    FragmentManager mFragmentmanager;

    public MyViewPageAdapter(FragmentManager fm, List<Fragment> mList) {
        super(fm);
        this.mList = mList;
        this.mFragmentmanager = fm;
    }

    @Override
    public Fragment getItem(int position) {
       /* if (position < mList.size()) {
            return mList.get(position);
        }*/
//        return mList.get(position % mList.size());
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
        return Integer.MAX_VALUE;
    }

}
