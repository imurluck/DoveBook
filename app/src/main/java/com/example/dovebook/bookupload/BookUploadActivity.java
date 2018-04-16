package com.example.dovebook.bookupload;

import android.view.View;
import android.widget.EditText;

import com.example.dovebook.R;
import com.example.dovebook.base.BaseActivity;
import com.example.dovebook.book.model.Book;

import butterknife.BindView;

/**
 * Created by 28748 on 2018/4/15.
 */

public class BookUploadActivity extends BaseActivity implements BookUploadContract.UploadView{

    @BindView(R.id.et_book_title_upload)
    EditText bookTitle;
    @BindView(R.id.et_book_author_upload)
    EditText bookAuthor;
    @BindView(R.id.et_book_isbn_upload)
    EditText bookIsbn;
    @BindView(R.id.et_book_publisher_upload)
    EditText bookPublisher;
    @BindView(R.id.et_book_price_upload)
    EditText bookPrice;
    @BindView(R.id.et_book_page_upload)
    EditText bookPage;
    @BindView(R.id.et_book_publishdate_upload)
    EditText publishDate;
    @BindView(R.id.et_book_abstract_upload)
    EditText bookAbstract;

    private Book mBook;

    @Override
    protected View initContentView() {
        View view = getLayoutInflater().inflate(R.layout.activity_book_upload, null);
        return view;
    }

    @Override
    protected void initOptions() {
        mBook = new Book();
    }
}
