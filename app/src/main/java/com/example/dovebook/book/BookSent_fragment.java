package com.example.dovebook.book;


import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dovebook.R;
import com.example.dovebook.base.BaseFragment;
import com.example.dovebook.book.model.Book;
import com.example.dovebook.bookupload.BookUploadActivity;
import com.example.dovebook.images.ImageManager;
import com.example.dovebook.main.MainActivity;
import com.example.dovebook.utils.ToastUtil;
import com.example.dovebook.widget.recycler.RecyclerAdapter;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookSent_fragment extends BaseFragment implements BookContract.View {

    private static final String TAG = "BookSent_fragment";

    private BookPresenter mPresenter;

    @BindView(R.id.book_sent_recycler)
    RecyclerView mRecycler;

    @BindView(R.id.bt_book_add)
    FloatingActionButton mBookAddButton;


    RecyclerAdapter<Book> mAdapter;

    public BookSent_fragment() {

        mPresenter = new BookPresenter(this);

        mAdapter = new RecyclerAdapter<Book>() {
            @Override
            protected int getItemViewType(int position, Book book) {
                return R.layout.book_sent_recyler_item;
            }

            @Override
            protected ViewHolder<Book> onCreateViewHolder(View root, int viewType) {
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
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRecycler.setAdapter(mAdapter);

        mBookAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BookUploadActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void showEmptyView() {

    }

    class ViewHolder extends RecyclerAdapter.ViewHolder<Book> {

        @BindView(R.id.book_sent_title)
        TextView title;
        @BindView(R.id.book_sent_image)
        ImageView image;
        @BindView(R.id.book_sent_author)
        TextView author;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(final Book book) {
            Log.d(TAG, "onBind: running"+book.getBookTitle()+ book.getBookAuthor());
            title.setText(book.getBookTitle());
            author.setText(book.getBookAuthor());
            ImageManager.getInstance().loadImage(mContext,
                    book.getBookImagepath(),
                    image);
        }
    }


}
