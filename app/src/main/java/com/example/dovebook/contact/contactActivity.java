package com.example.dovebook.contact;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dovebook.HandleRequest.ContactRequestActivity;
import com.example.dovebook.R;
import com.example.dovebook.base.BaseActivity;
import com.example.dovebook.base.BaseToolbarActivity;
import com.example.dovebook.base.model.Friend;
import com.example.dovebook.contact.utils.JudgeUtil;
import com.example.dovebook.login.UserManager;


import butterknife.BindView;

public class contactActivity extends BaseActivity{

    private static final String TAG = "contactActivity";
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
    @BindView(R.id.edt_search)
    EditText mEditText;
    @BindView(R.id.img_delete)
    ImageView img_delete;
    @BindView(R.id.newRequestTip)
    ImageView newRequestTip;
    @BindView(R.id.toHandleRequestActivity)
    ImageView toHandleRequestActivity;

    public contactAdapter adapter;
    private contactPresenter mContactPresenter;
    public UserManager mUserManager;
    private boolean hasFocus;

//    @Override
//    protected View initContentView() {
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//        View view = getLayoutInflater().inflate(R.layout.activity_contact, null);
//        return view;
//    }

//    @Override
//    protected String initToolbarTitle() {
//        return "通讯录";
//    }


    @Override
    protected void initOptions() {
        super.initOptions();
        mUserManager = UserManager.getInstance();
        //初始化各种监听事件
        initListener();
        mSideBar.setTextView(mTextView);

        mContactPresenter = new contactPresenter(this);
        adapter = new contactAdapter(this) {

            @Override
            public Context getContext() {
                return contactActivity.this;
            }
        };
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
        adapter.setOnMenuClickListener(new contactAdapter.OnMenuClickListener() {
            @Override
            public void onClick(final Friend friend, View view) {
                Log.d(TAG, "onClick: ");
                PopupMenu popupMenu = new PopupMenu(contactActivity.this, view);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.select_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.delete:
                                mContactPresenter.onSendNetworkRequestAndDeleteContact(friend);
                                break;
                            case R.id.check:
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        mRecyclerView.setLayoutManager(linearLayout);
        mRecyclerView.setAdapter(adapter);
        Log.d(TAG, "initOptions: ");
        initData();
    }

    @Override
    protected View initContentView() {
        return getLayoutInflater().inflate(R.layout.activity_contact,null);
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

    /**
     *显示新好友请求提示
     */

    public void showNewRequestTip(){
        if(newRequestTip.getVisibility()==View.GONE){
            newRequestTip.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 隐藏新好友请求提示
     */
    public void hideNewRequestTip(){
        if(newRequestTip.getVisibility()==View.VISIBLE){
            newRequestTip.setVisibility(View.GONE);
        }
    }

//    /**
//     * 动态加载菜单，设置菜单监听
//     *
//     */
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.contact_menu, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.add_contact:
//                break;
//            case R.id.search_contact:
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }


    /**
     * 初始化各控件监听器
     */
    private void initListener() {
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
                        //跳转到该项
                        ((LinearLayoutManager) mRecyclerView.getLayoutManager()).scrollToPositionWithOffset(i, 10);
                        return;
                    }
                }
            }
        });
        /**
         * 搜索框内容变化触发回调
         */
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (hasFocus && mEditText.getText().length() > 0) {
                    onSearchSomething(mEditText.getText().toString());
                    img_delete.setVisibility(View.VISIBLE);
                } else {
                    img_delete.setVisibility(View.GONE);
                    onSearchNothing();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        /**
         * 搜索框焦点变化引起回调
         */
        mEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                hasFocus = b;
            }
        });

        /**
         *点击删除键
         */
        img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditText.setText("");
                onSearchNothing();
            }
        });

        /**
         *点击进入处理好友请求界面
         */
        toHandleRequestActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContactPresenter.onToHandleRequestActivityClickListener();
            }
        });

    }

//    @Override
//    protected void initHomeButton() {
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//    }

    public void onSearchNothing() {
        mContactPresenter.searchNothing();
    }

    public void onSearchSomething(String s) {
        mContactPresenter.searchSomething(s);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(JudgeUtil.isContactListChange()){
            mContactPresenter.ContactListChange();
        }
    }

}
