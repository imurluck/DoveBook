package com.example.dovebook.share.friend;

import com.example.dovebook.common.Constant;
import com.example.dovebook.net.Api;
import com.example.dovebook.net.ExceptionEngine;
import com.example.dovebook.net.HttpManager;
import com.example.dovebook.share.model.Moment;
import com.example.dovebook.share.wrold.ShareWorldFragment;
import com.example.dovebook.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ShareFriendPresenter implements ShareFriendContract.Presenter {


    private static final int DROP_DOWN_REFRESH = 0;
    private static final int PULL_UP_LOADING = 1;

    private static final String TAG = "ShareFriendPresenter";
    //MVP中的V
    private ShareFriendFragment mView;

    //RXjava截断器
    private Disposable mDisposable;


    private List<Moment> mFriendList;

    //已登录用户的userId
    private String mUserId = "f2edfaa3-eb93-11e7-89cb-54ab3abf07bf";

    private int mFriendStartPosition = 0;

    private int mFriendEndPosition = 10;
    //当服务端好友分享没有更多数据时
    private boolean isFriendNoMore;

    private boolean isInRequesting = false;

    public ShareFriendPresenter(ShareFriendFragment view) {
        mView = view;
    }

    /**
     * 当进入此fragment时，进行的数据请求
     */
    @Override
    public void getInitData() {
        if (mFriendList != null && mFriendList.size() == 0) {
            getFriendListFromServer(mUserId);
        }
        if (mFriendList == null) {
            mFriendList = new ArrayList<>();
            getFriendListFromServer(mUserId);
        }
    }

    /**
     * 从服务器中得到所有好友的分享,每次10条
     * @param userId
     * @return 返回Moment序列
     */
    @Override
    public void getFriendListFromServer(String userId) {
        getFriendListFromServer(userId, mFriendStartPosition, mFriendEndPosition);
    }


    public void getFriendListFromServer(String userId, int startPosition,
                                        int endPosition) {
        if (isInRequesting) {
            return ;
        }
        isInRequesting = true;
        Api api = HttpManager.getInstance().getApiService(Constant.BASE_URL);
        api.selectMomentsFromAllFriend(userId, startPosition, endPosition)
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
                        isInRequesting = false;
                        ToastUtil.shortToast(ExceptionEngine.handleException(e).message);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    @Override
    public boolean canLoadMore() {
        return !isFriendNoMore;
    }

    @Override
    public void loadMore() {
        getFriendListFromServer(mUserId);
    }
}
