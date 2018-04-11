package com.example.dovebook.book;

import android.util.Log;

import com.example.dovebook.book.model.Book;
import com.example.dovebook.common.Constant;
import com.example.dovebook.net.Api;
import com.example.dovebook.net.HttpManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 28748 on 2018/3/14.
 */

public class PresenterTest {

    //copy开始结束位置，直接获取所有数据
    private static final int BOOKSTARTPOSITION = 0;
    private static final int BOOKENDPOSITION = 5;

    private static final String TAG = "PresenterTest";

    private BookSent_fragment mBookSentView;

    List<Book> mBooks;


    public PresenterTest(BookSent_fragment view) {
        Log.d(TAG, "PresenterTest: running");
        this.mBookSentView = view;
    }

    public void getInitData() {
        Log.d(TAG, "getInitData: running");
        getAllBooks();

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
                            Log.d(TAG, "onNext: " + books.size());
                            mBookSentView.mAdapter.add(books);

                            for (Book book : books) {
                                Log.d(TAG, "onNext: " + book.toString());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.getMessage());
                        Log.e(TAG, "onError: 请求失败！");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: 请求成功");
                    }
                });
    }

}
