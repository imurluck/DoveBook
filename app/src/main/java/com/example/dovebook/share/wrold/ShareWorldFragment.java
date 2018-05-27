package com.example.dovebook.share.wrold;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dovebook.R;
import com.example.dovebook.base.BaseFragment;
import com.example.dovebook.images.ImageManager;
import com.example.dovebook.share.model.Moment;
import com.example.dovebook.sharedetail.ShareDetailActivity;
import com.example.dovebook.utils.DateUtil;
import com.example.dovebook.widget.popupwindow.AddSharePopWindow;
import com.example.dovebook.widget.recycler.RecyclerAdapter;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by zzx on 18-4-10.
 */

public class ShareWorldFragment extends BaseFragment implements ShareWorldContract.View {


    private static final String TAG = "ShareWorldFragment";

    @BindView(R.id.share_world_root_layout)
    RelativeLayout mRootLayout;

    @BindView(R.id.share_world_fragment_recycler)
    RecyclerView mRecycler;
    //空页面显示
    @BindView(R.id.share_world_empty)
    View mEmptyView;

    @BindView(R.id.share_world_nest_scroll)
    NestedScrollView mNestedScrollView;
    //加载更多页面显示
    @BindView(R.id.share_world_load_more)
    TextView mLoadMoreTv;
    @BindView(R.id.share_world_no_more)
    TextView mNoMoreView;

    @BindView(R.id.share_world_add_share)
    FloatingActionButton mAddShareButton;
    //RecyclerView的适配器
    RecyclerAdapter<Moment> adapter;
    //MVP中的Presenter
    private ShareWorldPresenter mPresenter;

    private AddSharePopWindow mAddSharePopWindow;

    public ShareWorldFragment() {
        mPresenter = new ShareWorldPresenter(this);
        adapter = new RecyclerAdapter<Moment>() {
            @Override
            protected int getItemViewType(int position, Moment moment) {
                return R.layout.share_recycler_item;
            }

            @Override
            protected RecyclerAdapter.ViewHolder<Moment> onCreateViewHolder(View root, int viewType) {
                return new ShareWorldFragment.ViewHolder(root);
            }
        };
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_share_world;
    }
    /**
     * 初始化一些数据，但要牢记在这个方法中不要new 对象
     //防止replace后重复创建对象，以造成内存吃紧，大的对象最好是直接在构造函数中创建，或者是在业务逻辑中确认不会
     // 重复创建可以在里面创建
     */
    @Override
    protected void initData() {
        mPresenter.getInitData();
    }

    public void addOneMomentToServer(Map<String, RequestBody> paraMap,  List<MultipartBody.Part> momentPic) {
        mPresenter.addOneMomentToServer(paraMap, momentPic);
    }

    @Override
    protected void initWidget(View view) {
        super.initWidget(view);
        mAddSharePopWindow = new AddSharePopWindow(this);
        //为NestedScrollView添加滑动监听器
        mNestedScrollView.setOnScrollChangeListener(new ShareWorldFragment.VerticalOnScrollListener());
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,
                false));
        mRecycler.setAdapter(adapter);
        mRecycler.setNestedScrollingEnabled(false);
        //mRecycler.addOnScrollListener(new MyScrollListener());
        mAddShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAddSharePopWindow == null) {
                    mAddSharePopWindow = new AddSharePopWindow(ShareWorldFragment.this);
                }
                mAddSharePopWindow.showAtLocation(mRootLayout, Gravity.CENTER,
                        0, 0);
            }
        });
    }

    public void getPicsFromAblum() {
        Matisse.from(ShareWorldFragment.this)
                .choose(MimeType.allOf())
                .countable(true)
                .maxSelectable(9)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(AddSharePopWindow.CHOOSE_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mAddSharePopWindow.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void hideAddSharePopWindow() {
        if (mAddSharePopWindow.isShowing()) {
            mAddSharePopWindow.dismiss();
        }
    }

    /**
     * 当RecyclerView数据为空时显示空页面
     */
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

    /**
     * 当加载更多数据时，显示加载页面
     */
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

    /**
     * 没有更多数据时显示
     */
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

    /**
     * 监听NestedScrollView滑动到底部类
     */
    class VerticalOnScrollListener implements NestedScrollView.OnScrollChangeListener {

        @Override
        public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
            //Log.e(TAG, "onScrollChange: lastChild is " + v.getChildAt(v.getChildCount() - 1).getClass().getSimpleName());
            //int lastChild = v.getChildAt(v.getChildCount() - 1).getMeasuredHeight();
            //int lastChildHeight = v.getChildAt(v.getChildCount() - 1).getHeight();
            //int total = v.getMeasuredHeight();
            //Log.e(TAG, "onScrollChange: lastChild = " + lastChild);
            //Log.e(TAG, "onScrollChange: lastChildHeight = " + lastChildHeight);
            //Log.e(TAG, "onScrollChange: v.getMeasuredHeight = " + total);
            //Log.e(TAG, "onScrollChange: scrollY = " + scrollY + "   lastChild - v = " + (lastChild - total));
            if ((scrollY >= (v.getChildAt(v.getChildCount() - 1)).getMeasuredHeight()
                    - v.getMeasuredHeight()) && scrollY > oldScrollY) {
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
