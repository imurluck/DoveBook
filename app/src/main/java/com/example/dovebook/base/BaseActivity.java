package com.example.dovebook.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.dovebook.R;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    private  final String TAG = getClass().getSimpleName();

    //@BindView(R.id.root_layout_base)
    protected RelativeLayout mRootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        Log.e(TAG, "onCreate: ");
        mRootLayout = (RelativeLayout) findViewById(R.id.root_layout_base);
        if (initArgs(savedInstanceState)) {
            mRootLayout.addView(initContentView(),new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
            ButterKnife.bind(this);
            initOptions();
        } else {
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "onRestart: ");
    }

    /**
     * 初始化从其他Activity传过来的参数
     * @param bundle
     * @return 参数错误则返回false，即没必要进行下一步界面的初始化，正确则返回true，默认返回true
     */
    protected boolean initArgs(Bundle bundle) {
        return true;
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
     * 点击导航返回按钮时则finish当前Activity
     * @return
     */
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }


}
