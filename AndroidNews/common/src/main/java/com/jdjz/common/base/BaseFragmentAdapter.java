package com.jdjz.common.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jdjz.common.commonutils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tchl on 2016-11-14.
 */
public class BaseFragmentAdapter extends FragmentPagerAdapter {
    List<Fragment> fragmentList = new ArrayList<Fragment>();
    private List<String> mTitles;

    public BaseFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList){
        super(fm);
        this.fragmentList = fragmentList;
    }

    public BaseFragmentAdapter(FragmentManager fm,List<Fragment> fragmentList,List<String> mTitles){
        super(fm);
        this.fragmentList = fragmentList;
        this.mTitles = mTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return !CollectionUtils.isNullOrEmpty(mTitles)?mTitles.get(position):"";
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
