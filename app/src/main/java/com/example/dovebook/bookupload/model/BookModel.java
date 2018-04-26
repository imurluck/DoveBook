package com.example.dovebook.bookupload.model;

import android.util.Log;

import com.example.dovebook.book.model.Book;
import com.example.dovebook.bookupload.BookUploadContract;
import com.example.dovebook.bookupload.BookUploadPresenter;
import com.example.dovebook.common.Constant;
import com.example.dovebook.net.Api;
import com.example.dovebook.net.HttpManager;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class BookModel implements BookUploadContract.DataModel {

    private static final String TAG = "BookModel";

    private BookUploadPresenter mPresenter;

    Map<String, RequestBody> map;

    public BookModel(BookUploadPresenter presenter) {
        if (map == null) {
            map = new HashMap<>();
        }
        mPresenter = presenter;
    }

    @Override
    public void uploadBook(Book book, MultipartBody.Part bookImage) {

        map.put("bookTitle", parseRequestBody(book.getBookTitle()));
        map.put("bookAuthor", parseRequestBody(book.getBookAuthor()));
        map.put("bookPublisher", parseRequestBody(book.getBookPublisher()));
        map.put("bookIsbn", parseRequestBody(book.getBookIsbn()));
        map.put("bookSummary", parseRequestBody(book.getBookSummary()));
        map.put("bookPubdate", parseRequestBody(book.getBookPubdate()));

        Api api = HttpManager.getInstance().getApiService(Constant.BASE_BOOK_INSERT_URL);
        api.insertABook(book.getBookPages(), book.getBookPrice(), bookImage, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Book>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Book book) {
                        Log.d(TAG, "onNext: " + book);
                        map.clear();
                        mPresenter.onSuccessCompleteUpload();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.getMessage());
                        mPresenter.onErrorCompletedUpload();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public static RequestBody parseRequestBody(String value) {
        return RequestBody.create(MediaType.parse("text/plain"), value);
    }

}
