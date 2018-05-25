package com.example.dovebook.bookdetail;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dovebook.R;
import com.example.dovebook.base.BaseActivity;
import com.example.dovebook.bean.Book;
import com.example.dovebook.images.ImageManager;
import com.example.dovebook.utils.StringUtil;

import butterknife.BindView;

public class BookDetailsActivity extends BaseActivity {

    private static final String TAG = "BookDetailsActivity";

    @BindView(R.id.book_detail_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.book_detail_collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.book_detail_image)
    ImageView mBookImage;
    @BindView(R.id.book_detail_title)
    TextView mBookTitle;
    @BindView(R.id.book_detail_author)
    TextView mBookAuthor;
    @BindView(R.id.book_detail_summary)
    TextView mBookSummary;
    @BindView(R.id.book_detail_publisher)
    TextView mBookPublisher;
    @BindView(R.id.book_detail_isbn)
    TextView mBookIsbn;
    @BindView(R.id.book_detail_pages)
    TextView mBookPage;
    @BindView(R.id.book_detail_price)
    TextView mBookPrice;
    @BindView(R.id.book_detail_authorintro)
    TextView mBookAuthorIntro;
    @BindView(R.id.book_detail_pubdate)
    TextView mBookPubdate;
    @BindView(R.id.book_detail_authorintro_root)
    LinearLayout mBookAuthIntroRoot;
    @BindView(R.id.book_detail_summary_root)
    LinearLayout mBookSummaryRoot;


    private Book mBook;

    @Override
    protected View initContentView() {
        View view = getLayoutInflater().inflate(R.layout.activity_book_detail, null);
        return view;
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        mBook = (Book) getIntent().getParcelableExtra("book");
        return true;
    }

    @Override
    protected void initOptions() {
        Log.d(TAG, "initOptions: " + mBook.toString());
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (StringUtil.isNull(mBook.getBookAnthorintro())) {
            Log.d(TAG, "initOptions: no author intro");
            mBookAuthIntroRoot.setVisibility(View.GONE);
        } else {
            mBookAuthorIntro.setText(mBook.getBookAnthorintro());
        }
        if (StringUtil.isNull(mBook.getBookSummary())) {
            Log.d(TAG, "initOptions: no summary");
            mBookSummaryRoot.setVisibility(View.GONE);
        } else {
            mBookSummary.setText(mBook.getBookSummary());
        }
        mCollapsingToolbar.setTitle(mBook.getBookTitle());
        mBookTitle.setText(mBook.getBookTitle());
        ImageManager.getInstance().loadImage(this, mBook.getBookImagepath(), mBookImage);
        mBookAuthor.setText("作者：" + mBook.getBookAuthor());
        mBookPublisher.setText("出版社：" + mBook.getBookPublisher());
        mBookPubdate.setText("出版时间：" + mBook.getBookPubdate());

        mBookPage.setText("页数：" + mBook.getBookPages() + "页");
        mBookPrice.setText("定价：" + String.valueOf(mBook.getBookPrice()));
        mBookIsbn.setText("Isbn：" + mBook.getBookIsbn());
    }
}
