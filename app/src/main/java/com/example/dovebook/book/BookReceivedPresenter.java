package com.example.dovebook.book;

import android.util.Log;

import com.example.dovebook.bean.Book;
import com.example.dovebook.book.model.DataManager;
import com.example.dovebook.common.Constant;
import com.example.dovebook.common.ResposeStatus;
import com.example.dovebook.net.Api;
import com.example.dovebook.net.HttpManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class BookReceivedPresenter {

    public static final String USER_ID = "255883c7-0643-11e8-bd05-00163e0ac98c";

    //book开始结束位置，直接获取所有数据
    private int bookStartPosition = 0;
    private int bookEndPosition = 10;
    private boolean hasMoreBook = true;

    private BookReceived_fragment mView;

    private DataManager mDataManager;

    private List<Book> mBooks = new ArrayList();

    public BookReceivedPresenter(BookReceived_fragment view) {
        this.mView = view;
    }

    public void initData() {
        getAllBooks();
    }

    private void getAllBooks() {
        if (hasMoreBook) {
            Api api = HttpManager.getInstance().getApiService(Constant.BASE_URL);
            api.selectAllBooks(bookStartPosition, bookEndPosition)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Response<List<Book>>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Response<List<Book>> listResponse) {
                            switch (listResponse.code()) {
                                case ResposeStatus.OK:
                                    List<Book> books = listResponse.body();
                                    if (books.size() < 10) {
                                        //no more books
                                        hasMoreBook = false;
                                        mBooks.addAll(books);
                                        mView.mReceivedAdapter.add(mBooks);
                                    } else if (books.size() == 10) {
                                        //has more book
                                        bookStartPosition += 10;
                                        bookEndPosition += 10;
                                        mBooks.addAll(books);
                                        getAllBooks();
                                    }
                                    break;
                                case ResposeStatus.NOCONTENT:
                                    hasMoreBook = false;
                                    mView.mReceivedAdapter.add(mBooks);
                                    break;
                                default:
                                    break;
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
    }
}

