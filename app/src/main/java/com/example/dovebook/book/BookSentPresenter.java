package com.example.dovebook.book;

import android.util.Log;

import com.example.dovebook.bean.Book;
import com.example.dovebook.bean.Copy;
import com.example.dovebook.book.model.DataManager;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Created by zjs on 2018/3/13.
 */

public class BookSentPresenter implements BookContract.BookSendPresenter {

    private static final String TAG = "BookPresenter";

    //copy开始结束位置，直接获取所有数据
    private static final int COPYSTARTPOSITION = 0;

    private static final int COPYENDPOSITION = 10;

    private Disposable mDisposable;

    //view
    private BookSent_fragment mBookSentView;
    //model
    private DataManager mManager;

    //暂存所有的Copy，用于请求对应CopyId的图书信息
    private List<Copy> mCopies;

    //保存书籍
    private List<Book> mBooks;

    public BookSentPresenter(BookSent_fragment bookView) {
        this.mBookSentView = bookView;
        if (mManager == null) {
            mManager = new DataManager(this);
        }
    }

    public void getInitData() {
        getBooks();
    }

    public void getBooks() {
        if (mBooks == null || mBooks.size() == 0)
            mManager.getAllBooks();
    }

    public void getAllBooksCallback(List<Book> books) {
        Log.d(TAG, "getAllBooksCallback: ");
        this.mBooks = books;
        mBookSentView.mSentAdapter.add(books);
    }

}
