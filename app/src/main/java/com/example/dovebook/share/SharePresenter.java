package com.example.dovebook.share;

import android.util.Log;

import com.example.dovebook.base.model.User;
import com.example.dovebook.common.Constant;
import com.example.dovebook.net.Api;
import com.example.dovebook.net.HttpManager;
import com.example.dovebook.share.model.Moment;
import com.example.dovebook.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zzx on 18-1-28.
 */

public class SharePresenter implements ShareContract.Presenter {

    private static final String TAG = "SharePresenter";
    //MVP中的V
    private ShareFragment mView;

    //RXjava截断器
    private Disposable mDisposable;

    private List<Moment> mAllUserList;

    private List<Moment> mFriendList;

    //已登录用户的userId
    private String mUserId = "255883c7-0643-11e8-bd05-00163e0ac98c";
    //分享开始位置
    private int mAllStartPosition = 0;
    //分享末位置
    private int mAllEndPosition = 10;

    private int mFriendStartPosition = 0;

    private int mFriendEndPosition = 10;
    //当服务端全部用户分享没有更多数据时
    private boolean isAllUserNoMore;
    //当服务端好友分享没有更多数据时
    private boolean isFriendNoMore;
    //当前数据是否能加载更多
    private boolean canLoadMore;

    private Type mCurrentType;

    private enum Type {
        ALL, FRIEND
    }

    public SharePresenter(ShareFragment view) {
        this.mView = view;
        mCurrentType = Type.ALL;
    }

    /**
     * 当进入此fragment时，进行的数据请求
     */
    @Override
    public void getInitData() {
        if (mCurrentType == Type.ALL) {
            if (mAllUserList != null && mAllUserList.size() == 0) {
                getAllUserListFromServer();
            }
            if (mAllUserList == null) {
                mAllUserList = new ArrayList<>();
                getAllUserListFromServer();
            }
        }
        if (mCurrentType == Type.FRIEND) {
            if (mFriendList != null && mFriendList.size() == 0) {
                getFriendListFromServer(mUserId);
            }
            if (mFriendList == null) {
                mFriendList = new ArrayList<>();
                getFriendListFromServer(mUserId);
            }
        }
    }

    /**
     * 从服务器中得到所有用户的分享,每次10条
     *
     * @return 返回Moment序列
     */
    @Override
    public void getAllUserListFromServer() {
        Api api = HttpManager.getInstance().getApiService(Constant.BASE_URL);
        api.selectAllMoment(mAllStartPosition, mAllEndPosition)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Moment>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(List<Moment> moments) {
                        if (moments == null || moments.size() == 0) {
                            //如果获取到的序列数量为0,并且开始位置为0,说明服务器中没有数据
                            if (mAllStartPosition == 0) {
                                mView.showEmptyView();
                            } else {//如果获取到的序列数量为0，并且开始位置不为0,说明上次获取的数据刚好是10条，且
                                //最后一条刚好时服务器中的最后一条
                                isAllUserNoMore = true;
                            }
                        } else {//获取的序列不为0
                            if (moments.size() < 10) {
                                isAllUserNoMore = true;
                                //ToastUtil.shortToast("木有更多数据了...");
                            }
                            mAllStartPosition += 10;
                            mAllEndPosition += 10;
                            mAllUserList.addAll(moments);
                            mView.adapter.add(moments);
                        }
                        mView.hideLoadMore();
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.shortToast("网络不好,请稍候再试");
                        Log.e(TAG, "getAllUserList onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 从服务器中得到所有好友的分享,每次10条
     *
     * @param userId
     * @return 返回Moment序列
     */
    @Override
    public void getFriendListFromServer(String userId) {
        Api api = HttpManager.getInstance().getApiService(Constant.BASE_URL);
        api.selectMomentsFromAllFriend(userId, mFriendStartPosition, mFriendEndPosition)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Moment>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Moment> moments) {
                        if (moments == null || moments.size() == 0) {
                            //如果获取到的序列数量为0,并且开始位置为0,说明服务器中没有数据
                            if (mFriendStartPosition == 0) {
                                mView.showEmptyView();
                            } else {//如果获取到的序列数量为0，并且开始位置不为0,说明上次获取的数据刚好是10条，且
                                //最后一条刚好时服务器中的最后一条
                                isFriendNoMore = true;
                            }
                        } else {//获取的序列不为0
                            if (moments.size() < 10) {
                                isFriendNoMore = true;
                            }
                            mFriendStartPosition += 10;
                            mFriendEndPosition += 10;
                            mFriendList.addAll(moments);
                            mView.adapter.add(moments);
                        }
                        mView.hideLoadMore();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e.getMessage().equals("Null is not a valid element")) {
                            mView.showEmptyView();
                        }
                        Log.e(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 判断数据服务器端是否还有数据，包含两种，全部用户或者朋友，具体判断由presenter完成
     *
     * @return 还有则返回true，没有则返回false; 默认返回true
     */
    @Override
    public boolean canLoadMore() {
        if (mCurrentType == Type.ALL) {
            return !isAllUserNoMore;
        }
        if (mCurrentType == Type.FRIEND) {
            return !isFriendNoMore;
        }
        return true;
    }

    /**
     * 上拉加载更多数据
     */
    @Override
    public void loadMore() {
        if (mCurrentType == Type.ALL) {
            getAllUserListFromServer();
        }
        if (mCurrentType == Type.FRIEND) {
            getFriendListFromServer(mUserId);
        }
    }

    /**
     * 登录测试，非正式用途！！！
     */
    public void login() {
        Api api = HttpManager.getInstance().getApiService(Constant.BASE_LOGIN_URL);

        api.login("zzxx", "1234567")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(User user) {
                        Log.e(TAG, "loginOnNext: " + user.toString());
                        //getSelfListFromServer("02a618bf-0241-11e8-bd05-00163e0ac98c");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "loginOnError: " + e.getMessage());
                        //getSelfListFromServer("02a618bf-0241-11e8-bd05-00163e0ac98c");


                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
