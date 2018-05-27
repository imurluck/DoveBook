package com.example.dovebook.widget.viewpager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;

import java.util.List;

public class ShareViewPagerAdapter extends FragmentStatePagerAdapter  {

    private String[] mTabTitleArray;
    private List<Fragment> mFragments;
    private Context mContext;

    public ShareViewPagerAdapter(FragmentManager fm, String[] tabTitles,
                                 Context context, List fragments) {
        super(fm);
        mContext = context;
        mFragments = fragments;
        mTabTitleArray = tabTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitleArray[position];
    }
}
