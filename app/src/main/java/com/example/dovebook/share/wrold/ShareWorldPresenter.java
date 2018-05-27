package com.example.dovebook.share.wrold;

import android.util.Log;

import com.example.dovebook.base.model.User;
import com.example.dovebook.common.Constant;
import com.example.dovebook.net.Api;
import com.example.dovebook.net.ExceptionEngine;
import com.example.dovebook.net.HttpManager;
import com.example.dovebook.net.process.ProcessInfo;
import com.example.dovebook.net.process.ProcessListener;
import com.example.dovebook.share.model.Moment;
import com.example.dovebook.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * Created by zzx on 18-4-10.
 */

public class ShareWorldPresenter implements ShareWorldContract.Presenter {

    private static final int DROP_DOWN_REFRESH = 0;
    private static final int PULL_UP_LOADING = 1;

    private static final String TAG = "ShareWorldPresenter";
    //MVP中的V
    private ShareWorldFragment mView;

    //RXjava截断器
    private Disposable mDisposable;

    private List<Moment> mAllUserList;

    private List<Moment> mFriendList;

    //已登录用户的userId
    //private String mUserId = "f2edfaa3-eb93-11e7-89cb-54ab3abf07bf";
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

    private boolean isInRequesting = false;



    public ShareWorldPresenter(ShareWorldFragment view) {
        this.mView = view;
    }

    /**
     * 当进入此fragment时，进行的数据请求
     */
    @Override
    public void getInitData() {
        login();
        if (mAllUserList != null && mAllUserList.size() == 0) {
            getAllUserListFromServer(PULL_UP_LOADING);
        }
        if (mAllUserList == null) {
            mAllUserList = new ArrayList<>();
            getAllUserListFromServer(PULL_UP_LOADING);
        }
    }

    /**
     * 从服务器中得到所有用户的分享,每次10条
     * @return 返回Moment序列
     */
    @Override
    public void getAllUserListFromServer(int type) {
        getAllUserListFromServer(type, mAllStartPosition, mAllEndPosition);
    }


    private  void getAllUserListFromServer(final int type, int allStartPosition,
                                            int allEndPosition) {
        if (isInRequesting) {
            return;
        }
        isInRequesting = true;
        Api api = HttpManager.getInstance().getApiService(Constant.BASE_URL);
        api.selectAllMoment(allStartPosition, allEndPosition)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<List<Moment>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(Response<List<Moment>> response) {
                        isInRequesting = false;
                        List<Moment> moments = response.body();
                        if (type == DROP_DOWN_REFRESH) {
                            if (moments == null || moments.size() == 0) {
                                ToastUtil.shortToast("没有更新数据了！");
                            } else {
                                mAllUserList.addAll(0, moments);
                            }
                            return ;
                        }
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
                        isInRequesting = false;
                        ToastUtil.shortToast(ExceptionEngine.handleException(e).message);
                        Log.e(TAG, "getAllUserList onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    /**
     * 判断数据服务器端是否还有数据，包含两种，全部用户或者朋友，具体判断由presenter完成
     * @return 还有则返回true，没有则返回false; 默认返回true
     */
    @Override
    public boolean canLoadMore() {
        return !isAllUserNoMore;
    }

    /**
     * 上拉加载更多数据
     */
    @Override
    public void loadMore() {
        getAllUserListFromServer(PULL_UP_LOADING);
    }

    /**
     * 网络上传进度监听器 {@link ProcessListener}
     */
    private ProcessListener mProcessListener = new ProcessListener() {
        @Override
        public void onError(Exception e) {
            ToastUtil.shortToast(ExceptionEngine.handleException(e).message);
        }

        @Override
        public void onProcess(ProcessInfo info) {
            Log.e(TAG, "onProcess: " + info.getProcess());
        }
    };

    /**
     * 上传一个分享, 详情请看{@link Api#addOneMoment(Map, List)}
     * @param paraMap
     * @param momentPics
     */
    @Override
    public void addOneMomentToServer(Map<String, RequestBody> paraMap, List<MultipartBody.Part> momentPics) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("[{\"key\":\"Content-Type\",\"value\":\"application/x-www-form-urlencoded\",\"description\":\"\"}]"), mUserId);
        paraMap.put("userId", requestBody);
        HttpManager.getInstance().getApiService(Constant.BASE_URL, mProcessListener)
                .addOneMoment(paraMap, momentPics)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<Moment>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<Moment> response) {
                        Moment moment = response.body();
                        if (moment != null) {
                            mAllUserList.add(0, moment);
                            mView.adapter.add(0, moment);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        ToastUtil.shortToast(ExceptionEngine.handleException(e).message);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
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
