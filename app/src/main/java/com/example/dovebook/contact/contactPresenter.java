package com.example.dovebook.contact;

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


    List<User> mUserList;
    private contactManager mContactManager;
    private int startPosition = 0;
    private int endPosition = 10;


    public contactPresenter(contactManager view) {
        mContactManager = view;
    }

    public void initData() {
        Api api = HttpManager.getInstance().getApiService(Constant.BASE_URL);
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
                            mContactManager.showEmptyView();
                        }else {//否则对好友列表排序，刷新并显示
                            sortUserFromFirstChar(users);
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
        Collections.sort(list, new Comparator<User>(){
            @Override
            public int compare(User user, User t1) {
                return user.getUserName().compareTo(t1.getUserName());
            }
        });
    }

}
