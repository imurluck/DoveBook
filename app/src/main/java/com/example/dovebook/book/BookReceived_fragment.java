package com.example.dovebook.book;

import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dovebook.R;
import com.example.dovebook.base.BaseFragment;
import com.example.dovebook.bean.Book;
import com.example.dovebook.images.ImageManager;
import com.example.dovebook.widget.recycler.RecyclerAdapter;

import butterknife.BindView;


public class BookReceived_fragment extends BaseFragment {


    @BindView(R.id.book_received_recycler)
    RecyclerView mReceivedRecycler;

    RecyclerAdapter<Book> mReceivedAdapter;


    private BookReceivedPresenter mReceivedPresenter;


    public BookReceived_fragment() {
        // Required empty public constructor
        mReceivedPresenter = new BookReceivedPresenter(this);

        mReceivedAdapter = new RecyclerAdapter<Book>() {
            @Override
            protected int getItemViewType(int position, Book book) {
                return R.layout.book_received_recycler_item;
            }

            @Override
            protected ViewHolder<Book> onCreateViewHolder(View root, int viewType) {
                return new BookReceived_fragment.ViewHolder(root);
            }
        };
    }

    @Override
    protected void initWidget(View view) {

        mReceivedRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mReceivedRecycler.setAdapter(mReceivedAdapter);

    }

    @Override
    protected void initData() {
        mReceivedPresenter.initData();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_book_received;
    }

    class ViewHolder extends RecyclerAdapter.ViewHolder<Book> {

        @BindView(R.id.book_received_image)
        ImageView bookImage;
        @BindView(R.id.book_received_title)
        TextView bookTitle;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Book book) {
            ImageManager.getInstance().loadImage(getContext(), book.getBookImagepath(), bookImage);
            bookTitle.setText(book.getBookTitle());
        }
    }
}
