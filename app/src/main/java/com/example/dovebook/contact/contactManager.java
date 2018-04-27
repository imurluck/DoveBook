package com.example.dovebook.contact;

import android.content.Context;
import android.os.Binder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dovebook.R;
import com.example.dovebook.base.BaseActivity;
import com.example.dovebook.base.BaseToolbarActivity;
import com.example.dovebook.base.model.User;
import com.example.dovebook.login.UserManager;

import java.util.List;

import butterknife.BindView;

/**
 * Created by zyd on 2018/4/12.
 */

public class contactManager extends BaseToolbarActivity {

    private static String TAG = "contactManager";

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.contact_empty)
    View mEmptyView;
    @BindView(R.id.sideBar)
    SideBar mSideBar;
    @BindView(R.id.dialog)
    TextView mTextView;
    @BindView(R.id.contact_root)
    RelativeLayout mRelativeLayout;

    contactAdapter adapter;
    private contactPresenter mContactPresenter;
    public UserManager mUserManager;

    @Override
    protected View initContentView() {
        View view = getLayoutInflater().inflate(R.layout.activity_contact, null);
        return view;
    }

    @Override
    protected String initToolbarTitle() {
        return "通讯录";
    }


    @Override
    protected void initOptions() {
        super.initOptions();
        if (mUserManager == null) {
            mUserManager = new UserManager(this);
        }
        mSideBar.setTextView(mTextView);
        /**
         * SideBar回调，用于改变当前显示的联系人
         */
        mSideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                for (int i = 0; i < adapter.mUserList.size(); i++) {
                    String headerWord = adapter.getFirstChar(i);
                    Log.d(TAG, "onTouchingLetterChanged: " + headerWord);
                    //将手指按下的字母与列表中相同字母开头的项找出来
                    if (s.equals(headerWord)) {
                        //将列表选中哪一个
                        ((LinearLayoutManager) mRecyclerView.getLayoutManager()).scrollToPositionWithOffset(i, 10);
                        return;
                    }
                }
            }
        });
        mContactPresenter = new contactPresenter(this);
        adapter = new contactAdapter(this){

            @Override
            public Context getContext() {
                return contactManager.this;
            }
        };
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayout);
        mRecyclerView.setAdapter(adapter);
        initData();
    }

    void initData() {
        mContactPresenter.initData();
    }


    /*
     ***************************显示空界面*****************
     */
    public void showEmptyView() {
        if (mEmptyView.getVisibility() == View.GONE) {
            mSideBar.setVisibility(View.GONE);
            mTextView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.GONE);
            mRelativeLayout.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.contact_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_contact:break;
            case R.id.search_contact:break;
        }
        return super.onOptionsItemSelected(item);
    }
}
