package com.example.dovebook.test;

import com.example.dovebook.bean.Book;
import com.example.dovebook.common.Constant;
import com.example.dovebook.net.Api;
import com.example.dovebook.net.HttpManager;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TestPresenter {

    //book开始结束位置，直接获取所有数据
    private static final int BOOKSTARTPOSITION = 0;

    private static final int BOOKENDPOSITION = 10;


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
                            //获得图书数据
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
     * 删除图书
     * @param bookId
     */
    public void deleteABook(String bookId) {
        Api api = HttpManager.getInstance().getApiService(Constant.BASE_BOOK_URL);
        api.deleteBook(bookId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Book>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Book book) {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}

