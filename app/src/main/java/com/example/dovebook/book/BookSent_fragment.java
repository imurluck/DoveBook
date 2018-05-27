package com.example.dovebook.book;


import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dovebook.R;
import com.example.dovebook.base.BaseFragment;
import com.example.dovebook.bean.Book;
import com.example.dovebook.bookdetail.BookDetailsActivity;
import com.example.dovebook.bookupload.BookUploadActivity;
import com.example.dovebook.images.ImageManager;
import com.example.dovebook.widget.recycler.RecyclerAdapter;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookSent_fragment extends BaseFragment implements BookContract.View {

    private static final String TAG = "BookSent_fragment";

    private BookSentPresenter mPresenter;

    @BindView(R.id.book_sent_recycler)
    RecyclerView mSentRecycler;

    @BindView(R.id.bt_book_add)
    FloatingActionButton mBookAddButton;


    RecyclerAdapter<Book> mSentAdapter;

    public BookSent_fragment() {

        mPresenter = new BookSentPresenter(this);

        mSentAdapter = new RecyclerAdapter<Book>() {
            @Override
            protected int getItemViewType(int position, Book book) {
                return R.layout.book_sent_recyler_item;
            }

            @Override
            protected ViewHolder onCreateViewHolder(View root, int viewType) {
                return new BookSent_fragment.ViewHolder(root);
            }
        };

    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_book_sent;
    }

    @Override
    protected void initData() {
        mPresenter.getInitData();
    }

    @Override
    protected void initWidget(View view) {
        mSentRecycler.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mSentRecycler.setAdapter(mSentAdapter);

        mBookAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BookUploadActivity.class);
                startActivity(intent);
            }
        });
    }

    public void showEmptyView() {

    }

    class ViewHolder extends RecyclerAdapter.ViewHolder<Book> {

        @BindView(R.id.book_sent_title)
        TextView title;
        @BindView(R.id.book_sent_image)
        ImageView image;
        @BindView(R.id.book_sent_author)
        TextView author;
        @BindView(R.id.book_sent_root)
        RelativeLayout mRoot;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(final Book book) {
            title.setText(book.getBookTitle());
            author.setText(book.getBookAuthor());
            ImageManager.getInstance().loadImage(mContext,
                    book.getBookImagepath(),
                    image);
            mRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), BookDetailsActivity.class);
                    intent.putExtra("book",book);
                    startActivity(intent);
                }

            });
        }
    }


}
