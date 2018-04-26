package com.example.dovebook.contact;

import android.util.Log;

import com.example.dovebook.base.model.User;
import com.example.dovebook.common.Constant;
import com.example.dovebook.login.UserManager;
import com.example.dovebook.net.Api;
import com.example.dovebook.net.HttpManager;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zyd on 2018/4/13.
 */

public class contactPresenter {
    
    private static String TAG="contactPresenter";
    
    List<User> mUserList;
    private contactManager mContactManager;
    private int startPosition = 0;
    private int endPosition = 20;


    public contactPresenter(contactManager view) {
        mContactManager = view;
    }

    public void initData() {
        Api api = HttpManager.getInstance().getApiService(Constant.BASE_UPDATE_URL);
        api.getFriends(mContactManager.mUserManager.getUser().getUserId(), startPosition, endPosition)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<User>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<User> users) {
                        //如果好友列表为空，显示空页面
                        if (users.size()==0) {
                            Log.d(TAG, "onNext: users are null");
                            mContactManager.showEmptyView();
                        }else {//否则对好友列表排序，刷新并显示
//                            Log.d(TAG, "onNext: users are not null");
                            sortUserFromFirstChar(users);
                            Log.d(TAG, "onNext: finishing sorting");
                            for(int i=0;i<users.size();i++){
                                Log.d(TAG, "onNext:after sorting "+users.get(i).getUserName());
                            }
                            mContactManager.adapter.add(users);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 对返回的好友列表排序
     */

    public void sortUserFromFirstChar(List<User> list){
        for(int i=0;i<list.size();i++){
            Log.d(TAG, "sortUserFromFirstChar: "+list.get(i).getUserName());
        }
        Log.d(TAG, "sortUserFromFirstChar: sorting");
        Collections.sort(list, new Comparator<User>(){
            @Override
            public int compare(User user, User t1) {
                return user.getUserName().compareTo(t1.getUserName());
            }
        });
    }

}
