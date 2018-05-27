package com.example.dovebook.share;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.dovebook.R;
import com.example.dovebook.base.BaseFragment;
import com.example.dovebook.share.friend.ShareFriendFragment;
import com.example.dovebook.share.wrold.ShareWorldFragment;
import com.example.dovebook.widget.viewpager.ShareViewPagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by zzx on 18-1-19.
 */

public class ShareFragment extends BaseFragment {

    @BindView(R.id.share_fragment_view_pager)
    ViewPager mViewPager;

    @BindView(R.id.share_fragment_tab_layout)
    TabLayout mTabLayout;

    private TabLayout.Tab mWorldTab;
    private TabLayout.Tab mFriendTab;

    private ShareWorldFragment mShareWorldFragment;
    private ShareFriendFragment mShareFriendFragment;

    private ArrayList<BaseFragment> mFragments;
    private String[] mTabTitles;

    private ShareViewPagerAdapter mAdapter;
    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_share;
    }

    private void initFragment() {
        if (mFragments == null) {
            mFragments = new ArrayList<>();
        }
        if (mShareWorldFragment == null) {
            mShareWorldFragment = new ShareWorldFragment();
            mFragments.add(mShareWorldFragment);
        }
        if (mShareFriendFragment == null) {
            mShareFriendFragment = new ShareFriendFragment();
            mFragments.add(mShareFriendFragment);
        }
    }

    private void initTabLayout() {
        mTabTitles = new String[] {
                "世界",
                "好友"
        };
        for (int i = 0; i < mTabTitles.length; i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(mTabTitles[i]));
        }
    }

    @Override
    protected void initWidget(View view) {
        initFragment();
        initTabLayout();
        if (mWorldTab != null || mFriendTab != null) {
            return ;
        }
        mAdapter = new ShareViewPagerAdapter(getFragmentManager(), mTabTitles,
                getActivity(), mFragments);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
