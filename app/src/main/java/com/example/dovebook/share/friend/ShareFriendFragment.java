package com.example.dovebook.share.friend;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.BaseBundle;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dovebook.R;
import com.example.dovebook.base.BaseFragment;
import com.example.dovebook.images.ImageManager;
import com.example.dovebook.share.model.Moment;
import com.example.dovebook.share.wrold.ShareWorldFragment;
import com.example.dovebook.share.wrold.ShareWorldPresenter;
import com.example.dovebook.sharedetail.ShareDetailActivity;
import com.example.dovebook.utils.DateUtil;
import com.example.dovebook.widget.recycler.RecyclerAdapter;

import butterknife.BindView;


public class ShareFriendFragment extends BaseFragment implements ShareFriendContract.View {

    private static final String TAG = "ShareFriendFragment";

    @BindView(R.id.share_friend_root_layout)
    RelativeLayout mRootLayout;

    @BindView(R.id.share_friend_fragment_recycler)
    RecyclerView mRecycler;
    //空页面显示
    @BindView(R.id.share_friend_empty)
    View mEmptyView;

    @BindView(R.id.share_friend_nest_scroll)
    NestedScrollView mNestedScrollView;
    //加载更多页面显示
    @BindView(R.id.share_friend_load_more)
    TextView mLoadMoreTv;
    @BindView(R.id.share_friend_no_more)
    TextView mNoMoreView;

    //RecyclerView的适配器
    RecyclerAdapter<Moment> adapter;
    //MVP中的Presenter
    private ShareFriendPresenter mPresenter;

    public ShareFriendFragment() {
        // Required empty public constructor
        mPresenter = new ShareFriendPresenter(this);
        adapter = new RecyclerAdapter<Moment>() {
            @Override
            protected int getItemViewType(int position, Moment moment) {
                return R.layout.share_recycler_item;
            }

            @Override
            protected RecyclerAdapter.ViewHolder<Moment> onCreateViewHolder(View root, int viewType) {
                return new ShareFriendFragment.ViewHolder(root);
            }
        };
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_share_friend;
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getInitData();
    }

    @Override
    protected void initWidget(View view) {
        super.initWidget(view);
        mNestedScrollView.setOnScrollChangeListener(new VerticalOnScrollListener());
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,
                false));
        mRecycler.setAdapter(adapter);
        mRecycler.setNestedScrollingEnabled(false);
    }

    @Override
    public void showEmptyView() {
        if (mEmptyView.getVisibility() == View.GONE) {
            mEmptyView.setVisibility(View.VISIBLE);
        }
        if (mRecycler.getVisibility() == View.VISIBLE) {
            mRecycler.setVisibility(View.GONE);
        }
        if (mLoadMoreTv.getVisibility() == View.VISIBLE) {
            mLoadMoreTv.setVisibility(View.GONE);
        }
        if (mNoMoreView.getVisibility() == View.VISIBLE) {
            mNoMoreView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showLoadMoreView() {
        if (mLoadMoreTv.getVisibility() == View.GONE) {
            mLoadMoreTv.setVisibility(View.VISIBLE);
        }
        if (mEmptyView.getVisibility() == View.VISIBLE) {
            mEmptyView.setVisibility(View.GONE);
        }
        if (mNoMoreView.getVisibility() == View.VISIBLE) {
            mNoMoreView.setVisibility(View.GONE);
        }
    }

    @Override
    public void hideLoadMore() {
        if (mLoadMoreTv.getVisibility() == View.VISIBLE) {
            mLoadMoreTv.setVisibility(View.GONE);
        }
    }

    @Override
    public void showNoMoreView() {
        if (mNoMoreView.getVisibility() == View.GONE) {
            mNoMoreView.setVisibility(View.VISIBLE);
        }
        if (mEmptyView.getVisibility() == View.VISIBLE) {
            mEmptyView.setVisibility(View.GONE);
        }
        if (mLoadMoreTv.getVisibility() == View.VISIBLE) {
            mLoadMoreTv.setVisibility(View.GONE);
        }
    }

    class VerticalOnScrollListener implements NestedScrollView.OnScrollChangeListener {

        @Override
        public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
            if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight()
                    - v.getMeasuredHeight())) && (scrollY > oldScrollY)) {
                if (adapter.getItemCount() > 0) {
                    if (mPresenter.canLoadMore()) {
                        showLoadMoreView();
                        mPresenter.loadMore();
                    } else {
                        showNoMoreView();
                    }
                }
            }
        }
    }

    class ViewHolder extends RecyclerAdapter.ViewHolder<Moment> {
        //用户头像
        @BindView(R.id.share_item_user_portrait)
        ImageView userPortraitImg;
        //用户名
        @BindView(R.id.share_item_user_name)
        TextView userNameTv;
        //评论数
        @BindView(R.id.share_item_comment_count)
        TextView commentCountTv;
        //点赞数
        @BindView(R.id.share_item_vote_count)
        TextView voteCountTv;
        //分享内容
        @BindView(R.id.share_item_content)
        TextView contentTv;
        //发布时间
        @BindView(R.id.share_item_publish_date)
        TextView publishDateTv;
        //分享的图片
        @BindView(R.id.share_item_publish_img)
        ImageView publishImg;
        //根布局
        @BindView(R.id.share_item_root)
        RelativeLayout root;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(final Moment moment) {
            userNameTv.setText(moment.getUserName());
            ImageManager.getInstance().loadImage(mContext,
                    moment.getUserAvatarPath(),
                    userPortraitImg);
            ImageManager.getInstance().loadImage(mContext,
                    moment.getPictureList().get(0).getPicturePath(),
                    publishImg);
            contentTv.setText(moment.getMomentContent());
            voteCountTv.setText(String.valueOf(moment.getMomentVoteCount()));
            commentCountTv.setText(String.valueOf(moment.getCommentCount()));
            publishDateTv.setText(DateUtil.timeStampToDate(moment.getUpdateAt()));
            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ShareDetailActivity.class);
                    intent.putExtra("moment", moment);
                    startActivity(intent);
                }
            });
        }
    }
}
