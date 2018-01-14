package com.example.dovebook.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.example.dovebook.R;

public abstract class BaseActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private LinearLayout mRootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        mRootLayout = (LinearLayout) findViewById(R.id.root_layout_base);
        mRootLayout.addView(initContentView());
        initToolbar();
        initOptions();
    }

    /**
     * 进行一些初始化设置，默认为空，子类需要设置则由子类重写
     */
    protected void initOptions() {

    }

    /**
     * 设置内容布局，不同子类有不同的布局，由子类来完成
     * @return 返回一个view
     */
    protected abstract View initContentView();

    /**
     * 初始化Toolbar名称，由子类来完成
     * @return 返回toolbar的名称
     */
    protected abstract String initToolbarTitle();

    /**
     * 设置toolbar名称，此方法用于子类中toolbar名称需要变化的情况
     * @param title
     */
    protected void setToolbarTitle(String title) {
        mToolbar.setTitle(title);
    }

    /**
     * 初始化Toolbar
     */
    protected void initToolbar() {
        mToolbar = (Toolbar) this.findViewById(R.id.toolbar_base);
        mToolbar.setTitle(initToolbarTitle());
        setSupportActionBar(mToolbar);
        initHomeButton();
    }

    /**
     * 此处为空实现，默认不显示HomeButton，子类可以重写此方法来完成对HomeButton的设置
     */
    protected void initHomeButton() {

    }




}
