package com.example.dovebook.book;

import android.util.Log;

import com.example.dovebook.base.model.User;
import com.example.dovebook.book.model.Book;
import com.example.dovebook.book.model.Copy;
import com.example.dovebook.common.Constant;
import com.example.dovebook.net.Api;
import com.example.dovebook.net.HttpManager;
import com.example.dovebook.utils.ToastUtil;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 28748 on 2018/3/13.
 */

public class BookPresenter implements BookContract.Presenter {

    private static final String TAG = "BookPresenter";

    //已登录用户的userId
    private String mUserId = "255883c7-0643-11e8-bd05-00163e0ac98c";

    //copy开始结束位置，直接获取所有数据
    private static final int COPYSTARTPOSITION = 0;

    private static final int COPYENDPOSITION = 100;

    //
    private Disposable mDisposable;

    private BookSent_fragment mBookSentView;

    //暂存所有的Copy，用于请求对应CopyId的图书信息
    private List<Copy> mCopies;

    //保存书籍
    private List<Book> mBooks;

    private static boolean loginTag = false;

    public BookPresenter(BookSent_fragment bookView) {
        this.mBookSentView = bookView;
    }


    @Override
    public void getInitData() {
//        if (loginTag == false) {
//            Log.d(TAG, "getInitData: 登陆调用");
//            login();
//            loginTag = true;
//        }
        if (mCopies == null) {
            mCopies = new ArrayList<>();
//            getAllCopy();
        }
    }


    @Override
    public void getAllCopy() {
        Api api = HttpManager.getInstance().getApiService(Constant.BASE_URL);
        api.selectAllBookCopy(mUserId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Copy>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(List<Copy> copies) {
                        if (mCopies != null)
                            mCopies.addAll(copies);
                        Log.d(TAG, "onNext: " + copies.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.shortToast("网络错误");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "网络请求成功");
                    }
                });
    }



    /**
     * 登陆测试数据
     */
//    public void login() {
//
//        Api api = HttpManager.getInstance().getApiService(Constant.BASE_LOGIN_URL);
//
//        api.login("zzxx", "1234567")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<User>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(User user) {
//                        Log.d(TAG, "loginOnNext: " + user.toString());
//                        //getSelfListFromServer("02a618bf-0241-11e8-bd05-00163e0ac98c");
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e(TAG, "loginOnError: " + e.getMessage());
//                        //getSelfListFromServer("02a618bf-0241-11e8-bd05-00163e0ac98c");
//
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.d(TAG, "onComplete: 登陆成功");
//                    }
//                });
//    }

}
