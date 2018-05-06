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
 * Created by zjs on 2018/3/13.
 */

public class BookPresenter implements BookContract.Presenter {

    private static final String TAG = "BookPresenter";

    //已登录用户的userId
    private String mUserId = "255883c7-0643-11e8-bd05-00163e0ac98c";

    //copy开始结束位置，直接获取所有数据
    private static final int COPYSTARTPOSITION = 0;

    private static final int COPYENDPOSITION = 100;

    //copy开始结束位置，直接获取所有数据
    private static final int BOOKSTARTPOSITION = 0;

    private static final int BOOKENDPOSITION = 10;

    private Disposable mDisposable;

    private BookSent_fragment mBookSentView;

    //暂存所有的Copy，用于请求对应CopyId的图书信息
    private List<Copy> mCopies;

    //保存书籍
    private List<Book> mBooks;

    public BookPresenter(BookSent_fragment bookView) {
        this.mBookSentView = bookView;
    }

    public void getInitData() {
        Log.d(TAG, "getInitData: running");
        getAllBooks();
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

    public void getAllBooks() {
        Api api = HttpManager.getInstance().getApiService(Constant.BASE_URL);
        api.selectAllBooks(BOOKSTARTPOSITION, BOOKENDPOSITION)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Book>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(List<Book> books) {
                        if (books != null) {
                            mBookSentView.mAdapter.add(books);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: 请求成功");
                    }
                });
    }
}
