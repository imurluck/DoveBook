package com.example.dovebook.contact;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.dovebook.R;
import com.example.dovebook.base.BaseActivity;
import com.example.dovebook.login.UserManager;

import butterknife.BindView;

/**
 * Created by zyd on 2018/4/12.
 */

public class contactManager extends BaseActivity implements SideBar.OnTouchingLetterChangedListener {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.contact_empty)
    View mEmptyView;
    @BindView(R.id.sideBar)
    SideBar mSideBar;
    @BindView(R.id.dialog)
    TextView mTextView;

    contactAdapter adapter;
    private contactPresenter mContactPresenter;
    public UserManager mUserManager;

    @Override
    protected View initContentView() {
        View view = getLayoutInflater().inflate(R.layout.activity_contact, null);
        return view;
    }

    @Override
    protected void initOptions() {
        super.initOptions();
        if (mUserManager == null) {
            mUserManager = new UserManager(this);
        }
        mSideBar.setTextView(mTextView);
        mContactPresenter = new contactPresenter(this);
        adapter = new contactAdapter(this);
        initData();
        mRecyclerView.setAdapter(adapter);

    }

    void initData() {
        mContactPresenter.initData();
    }


    /*
     ***************************显示空界面*****************
     */
    public void showEmptyView() {
        if (mEmptyView.getVisibility() == View.GONE) {
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }

    /**
     *SideBar回调，用于改变当前显示的联系人
     */
    @Override
    public void onTouchingLetterChanged(String s) {
        for (int i = 0; i < adapter.mUserList.size(); i++) {
            char headerWord = adapter.getFirstChar(i);
            //将手指按下的字母与列表中相同字母开头的项找出来
            if (s.equals(headerWord)) {
                //将列表选中哪一个
                ((LinearLayoutManager)mRecyclerView.getLayoutManager()).scrollToPositionWithOffset(i,0);
                return;
            }
        }
    }

}
