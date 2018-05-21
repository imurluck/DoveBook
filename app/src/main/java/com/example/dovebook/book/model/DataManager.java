package com.example.dovebook.book.model;

import android.util.Log;

import com.example.dovebook.bean.Book;
import com.example.dovebook.bean.Copy;
import com.example.dovebook.book.BookSentPresenter;
import com.example.dovebook.common.Constant;
import com.example.dovebook.net.Api;
import com.example.dovebook.net.HttpManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 处理数据操作
 */
public class DataManager {

    private static final String TAG = "CopyModel";
    //测试userId
    public static final String USER_ID = "255883c7-0643-11e8-bd05-00163e0ac98c";

    //book开始结束位置，直接获取所有数据
    private int bookStartPosition = 0;
    private int bookEndPosition = 10;

    private boolean hasMoreBook = true;

    static HashMap<String, Book> allBooksSet = new HashMap<>();

    private BookSentPresenter mSentPresenter;

    public DataManager(BookSentPresenter presenter) {
        this.mSentPresenter = presenter;
    }

    public void getAllBooks() {

        if (hasMoreBook) {
            Api api = HttpManager.getInstance().getApiService(Constant.BASE_URL);
            api.selectAllBooks(bookStartPosition, bookEndPosition)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<Book>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(List<Book> books) {
                            Log.d(TAG, "onNext: 图书单次请求");
                            if (books == null || books.size() == 0) {
                                //no more books
                                hasMoreBook = false;
//                                mSentPresenter.getAllBooksCallback(new ArrayList<Book>(allBooksSet.values()));
                                Log.d(TAG, "onNext:222 "+books.size());
                                getAllCopies();
                            } else if (books.size() < 10) {
                                //no more books
                                Log.d(TAG, "onNext:111 "+books.size());
                                hasMoreBook = false;
                                addBookListToMap(books);
//                                mSentPresenter.getAllBooksCallback(new ArrayList<Book>(allBooksSet.values()));
                                getAllCopies();
                            } else {
                                //has more book
                                bookStartPosition += 10;
                                bookEndPosition += 10;
                                addBookListToMap(books);
                                getAllBooks();
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

    public void getAllCopies() {

        Api api = HttpManager.getInstance().getApiService(Constant.BASE_USERCOPY_URL);

        api.selectCopiesByUserId(USER_ID, 0, 5)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Copy>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Copy> copies) {
                        Log.d(TAG, "请求Copy成功 : ");
                        mSentPresenter.getAllBooksCallback(getBooksByCopies(copies));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public List<Book> getBooksByCopies(List<Copy> copies) {
        List<Book> books = new ArrayList<>();
        for (Copy copy : copies) {
            books.add(allBooksSet.get(copy.getBookId()));
        }
        return books;
    }

    private void addBookListToMap(List<Book> books) {
        for (Book book : books) {
            allBooksSet.put(book.getBookId(), book);
        }
    }

//    public void getBookById(String bookId) {
//        Api api = HttpManager.getInstance().getApiService(Constant.BASE_BOOK_URL);
//
//        api.selectBookById(bookId)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Book>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(Book book) {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
//    }
}
