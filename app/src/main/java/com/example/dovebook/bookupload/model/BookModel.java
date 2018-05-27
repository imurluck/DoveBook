package com.example.dovebook.bookupload.model;

import android.util.Log;

import com.example.dovebook.bean.Book;
import com.example.dovebook.bean.Copy;
import com.example.dovebook.bean.OrderBean;
import com.example.dovebook.bookupload.BookUploadContract;
import com.example.dovebook.bookupload.BookUploadPresenter;
import com.example.dovebook.common.Constant;
import com.example.dovebook.common.ResposeStatus;
import com.example.dovebook.net.Api;
import com.example.dovebook.net.HttpManager;
import com.example.dovebook.utils.StringUtil;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.Retrofit;

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

        Api api = HttpManager.getInstance().getApiService(Constant.BASE_BOOK_URL);
        api.insertBook(book.getBookPages(), book.getBookPrice(), bookImage, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<Book>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<Book> bookResponse) {
                        switch (bookResponse.code()) {
                            case ResposeStatus.CONFLICT:

                            case ResposeStatus.CREATED:
                                //成功上传图书
                                Log.d(TAG, "onNext: " + bookResponse);
                                map.clear();
                                mPresenter.onSuccessCompleteUpload();

                                break;
                            default:
                                break;
                        }

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

    @Override
    public void getBookInfoByIsbn(String isbn) {
        Book book;

        Api api = HttpManager.getInstance().getApiService(Constant.DOUBAN_ISBN_URL);
        api.getBookInfoByIsbn(isbn)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DoubanBook>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DoubanBook doubanBook) {
                        Log.d(TAG, "onNext: 请求成功！");
                        mPresenter.queryBookInfoCallBack(handleBook(doubanBook));
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


    public void insertACopy(String bookId, String userId, String copyLatitude, String copyLongitude, String copyDetailloc, boolean copyStatus, long createdat, long updatedat) {

        Api api = HttpManager.getInstance().getApiService(Constant.BASE_COPY_URL);

        Map<String, RequestBody> requestBodyMap = new HashMap<>();

        requestBodyMap.put("bookId", parseRequestBody(bookId));
        requestBodyMap.put("userId", parseRequestBody(userId));
        requestBodyMap.put("copyLatitude", parseRequestBody(copyLatitude));
        requestBodyMap.put("copyLongitude", parseRequestBody(copyLongitude));
        requestBodyMap.put("copyDetailloc", parseRequestBody(copyDetailloc));

        api.insertCopy(requestBodyMap, copyStatus)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Copy>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Copy copy) {
                        Log.d(TAG, "onNext: copy上传成功！");
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


    private void insertAnOrder(String copyId, String preordersId, String userId, boolean ordersStates, int ordersCredit, long ordersStart, long ordersEnd, long createDate, long updateDate) {
        Api api = HttpManager.getInstance().getApiService(Constant.BASE_ORDER_URL);

        Map<String, RequestBody> requestBodyMap = new HashMap<>();

        requestBodyMap.put("copyId", parseRequestBody(copyId));
        requestBodyMap.put("preordersId", parseRequestBody(preordersId));
        requestBodyMap.put("userId", parseRequestBody(userId));


        api.insertOrder(requestBodyMap, ordersStates, ordersCredit, ordersStart, ordersEnd, createDate, updateDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OrderBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(OrderBean orderBean) {
                        Log.d(TAG, "onNext: order 生成成功！");
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

    private Book handleBook(DoubanBook doubanBook) {
        Book book = new Book();

        book.setBookTitle(doubanBook.title);
        String authors = "";
        for (String a : doubanBook.author
                ) {
            authors += a;
        }
        book.setBookAuthor(authors);
        book.setBookPublisher(doubanBook.publisher);
        book.setBookPubdate(doubanBook.pubdate);
        book.setBookPages("" + doubanBook.pages);
        book.setBookPrice(doubanBook.price.replace("元", ""));
        book.setBookSummary(doubanBook.summary);

        if (!StringUtil.isNull(doubanBook.image)) {
            //判断图片路径非空
            book.setBookImagepath(doubanBook.image);
        } else {
            book.setBookImagepath(null);
        }

        Log.d(TAG, "handleBook: " + book.toString());
        return book;
    }

    public static RequestBody parseRequestBody(String value) {
        return RequestBody.create(MediaType.parse("text/plain"), value);
    }


}
