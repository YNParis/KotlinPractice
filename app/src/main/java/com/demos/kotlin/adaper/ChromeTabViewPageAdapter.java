package com.demos.kotlin.adaper;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/5/2.
 */

public class ChromeTabViewPageAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mList;
    private List<String> titles;
    private FragmentManager fragmentManager;

    public ChromeTabViewPageAdapter(FragmentManager fm, List<Fragment> mList, List<String> titles) {
        super(fm);
        this.mList = mList;
        this.titles = titles;
        this.fragmentManager = fm;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }


    @Override
    public int getCount() {
        if (mList != null && titles != null) {
            return Math.min(mList.size(), titles.size());
        }
        return 0;
    }

  /*  @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles == null ? "" : titles.get(position);
    }*/
}
