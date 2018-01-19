package com.example.dovebook.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by zzx on 18-1-19.
 */

public abstract class BaseFragment extends Fragment {
    //根部据view
    protected View mRoot;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRoot == null) {
            //初始化当前的根布局，但是不在创建时就添加到container中，所以传入false
            mRoot = inflater.inflate(getContentLayoutId(), container, false);
            initWidget(mRoot);
        } else {
            if (mRoot.getParent() != null) {
                //如果mRoot回收不及时，父布局不为空时，就将其从父布局中移除，从而绑定到当前的父布局中
                ((ViewGroup) mRoot.getParent()).removeView(mRoot);
            }
        }
        return mRoot;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    /**
     * 绑定Activity，在onCreateView前调用
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        initArgs(getArguments());
    }

    /**
     * 得到内容布局id，由子类来完成
     * @return 资源文件id
     */
    protected abstract int getContentLayoutId();

    /**
     * 初始化控件
     * @param view
     */
    protected void initWidget(View view) {
        ButterKnife.bind(this, view);
    }

    /**
     * 在View创建之后初始化数据
     */
    protected void initData() {

    }

    /**
     * 在onAttch中初始化Bundle数据
     * @param bundle
     */
    protected void initArgs(Bundle bundle) {

    }


}
