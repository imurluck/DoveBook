package com.example.dovebook.sharedetail;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dovebook.R;
import com.example.dovebook.base.BaseToolbarActivity;
import com.example.dovebook.images.ImageManager;
import com.example.dovebook.share.model.Moment;
import com.example.dovebook.share.model.MomentPicture;
import com.example.dovebook.utils.DateUtil;
import com.example.dovebook.widget.recycler.RecyclerAdapter;

import butterknife.BindView;

public class ShareDetailActivity extends BaseToolbarActivity
        implements ShareDetailContract.View {

    private static final String TAG = "ShareDetailActivity";

    private Animation showAnimation;
    private Animation hideAnimation;

    private ShareDetailContract.Presenter mPresenter;

    @BindView(R.id.share_detail_nest_scroll)
    NestedScrollView mScrollView;

    //vote_button
    @BindView(R.id.action_button_vote)
    FloatingActionButton mActionButton;
    //moment值
    private Moment mMoment;
    //用户名称
    @BindView(R.id.share_detail_user_name)
    TextView mUserName;
    //用户头像
    @BindView(R.id.share_detail_user_portrait)
    ImageView mUserPortrait;
    //用户性别
    @BindView(R.id.share_detail_user_sex)
    ImageView mUserSex;
    //moment最新更新时间
    @BindView(R.id.share_detail_moment_publish_time)
    TextView mMomentPublishTime;
    //moment文字内容
    @BindView(R.id.share_detail_moment_content)
    TextView mMomentContent;
    //图片序列
    @BindView(R.id.share_detail_moment_picture_recycler)
    RecyclerView mPictureRecycler;
    //点赞图标
    //@BindView(R.id.share_detail_vote)
    //ImageView mMomentVote;
    //点赞数量显示
    @BindView(R.id.share_detail_vote_count)
    TextView mMomentVoteCount;
    //图片序列适配器
    private RecyclerAdapter<MomentPicture> mAdapter;

    /**
     * 初始化布局
     * @return 布局view
     */
    @Override
    protected View initContentView() {
        View view = getLayoutInflater().inflate(R.layout.activity_share_detail, null);
        return view;
    }

    /**
     * 初始化Toolbar的标题
     * @return
     */
    @Override
    protected String initToolbarTitle() {
        return "详情";
    }

    /**
     * 初始化由其他activity传过来的变量
     * @param bundle 当activity在特定情况下销毁时保存的数据，重新恢复过来
     * @return
     */
    @Override
    protected boolean initArgs(Bundle bundle) {
        //得到由ShareFragment传过来的moment
        mMoment = (Moment) getIntent().getParcelableExtra("moment");
        return true;
    }

    /**
     * 初始化一些设置
     */
    @Override
    protected void initOptions() {
        mPresenter = new ShareDetailPresenter(this);
        initViews();
    }

    /**
     * 初始化View
     */
    private void initViews() {
        showAnimation = AnimationUtils.loadAnimation(this, R.anim.action_button_show);
        hideAnimation = AnimationUtils.loadAnimation(this, R.anim.action_button_hide);
        mScrollView.setOnScrollChangeListener(new VerticalOnScrollListener());
        mMomentVoteCount.setText(mMoment.getMomentVoteCount() + "赞");
        //设置根布局背景色
        mRootLayout.setBackgroundColor(getResources().getColor(R.color.background_light_grey));
        //显示moment的信息
        if (mMoment != null) {
            mUserName.setText(mMoment.getUserName());
            ImageManager.getInstance().loadImage(this, mMoment.getUserAvatarPath(),
                    mUserPortrait);
            if (mMoment.getUserSex().equals("女")) {
                Log.e(TAG, "initViews: " + "女");
                ImageManager.getInstance().loadImage(this, R.drawable.ic_woman, mUserSex);

            } else {
                Log.e(TAG, "initViews: " + "男");
                ImageManager.getInstance().loadImage(this, R.drawable.ic_man, mUserSex);
            }
            mMomentPublishTime.setText(DateUtil.timeStampToDate(mMoment.getUpdateAt()));
            mMomentContent.setText(mMoment.getMomentContent());
        }
        mAdapter = new RecyclerAdapter<MomentPicture>() {
            @Override
            protected int getItemViewType(int position, MomentPicture momentPicture) {
                return R.layout.share_detail_picture_recycler_item;
            }

            @Override
            protected ViewHolder onCreateViewHolder(View root, int viewType) {
                return new ShareDetailActivity.ViewHolder(root);
            }
        };
        mAdapter.add(mMoment.getPictureList());
        mPictureRecycler.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        mPictureRecycler.setNestedScrollingEnabled(false);
        mPictureRecycler.setFocusable(false);
        mPictureRecycler.setAdapter(mAdapter);
        mActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.vote();
            }
        });
    }

    /**
     * momnet的图片序列VieHolder
     */
    class ViewHolder extends RecyclerAdapter.ViewHolder<MomentPicture> {
        @BindView(R.id.share_detail_picture_recycler_item_picture)
        ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(final MomentPicture momentPicture) {
            ImageManager.getInstance().loadImage(ShareDetailActivity.this, momentPicture.getPicturePath(),
                    img);
        }
    }

    class VerticalOnScrollListener implements NestedScrollView.OnScrollChangeListener {
        private boolean isShow = true;
        @Override
        public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
            if ((scrollY - oldScrollY) > 0 && isShow) {
                showAnimation.cancel();
                mActionButton.startAnimation(hideAnimation);
                isShow = false;
            } else if ((scrollY - oldScrollY) < 0 && !isShow) {
                hideAnimation.cancel();
                mActionButton.startAnimation(showAnimation);
                Log.e(TAG, "onScrollChange: ");
                isShow = true;
            }
        }
    }

    @Override
    protected void initHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


}
