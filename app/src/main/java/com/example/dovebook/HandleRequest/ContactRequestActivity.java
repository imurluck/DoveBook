package com.example.dovebook.HandleRequest;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dovebook.R;
import com.example.dovebook.base.BaseToolbarActivity;
import com.example.dovebook.base.model.Friend;
import com.example.dovebook.contact.utils.JudgeUtil;
import com.example.dovebook.images.ImageManager;
import com.example.dovebook.widget.recycler.RecyclerAdapter;

import butterknife.BindView;

public class ContactRequestActivity extends BaseToolbarActivity {

    private static final String TAG = "ContactRequestActivity";
    @BindView(R.id.request_recyclerView)
    RecyclerView request_recyclerView;
    @BindView(R.id.contactRequest_empty)
    View mEmptyView;

    RecyclerAdapter<Friend> contactRequestAdapter;
    ContactRequestPresenter mContactRequestPresenter;

    @Override
    protected View initContentView() {
        View view=getLayoutInflater().inflate(R.layout.activity_contact_request,null);
        return view;
    }

    @Override
    protected String initToolbarTitle() {
        return "好友请求";
    }

    @Override
    protected void initOptions() {
        mContactRequestPresenter=new ContactRequestPresenter(this);
        contactRequestAdapter = new RecyclerAdapter<Friend>() {
            @Override
            protected int getItemViewType(int position, Friend friend) {
                return R.layout.handle_request_recycler_item;
            }

            @Override
            protected ViewHolder<Friend> onCreateViewHolder(View root, int viewType) {
                return new ContactRequestActivity.ViewHolder(root);
            }
        };
        request_recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        request_recyclerView.setAdapter(contactRequestAdapter);
        mContactRequestPresenter.initData();
    }

    @Override
    protected void initHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public void showEmptyView() {
        if (mEmptyView.getVisibility() == View.GONE) {
            request_recyclerView.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }

    class ViewHolder extends RecyclerAdapter.ViewHolder<Friend> {

        @BindView(R.id.agree)
        Button agree;
        @BindView(R.id.disagree)
        Button disagree;
        @BindView(R.id.circle_image)
        ImageView UserImage;
        @BindView(R.id.text_name)
        TextView userName;


        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(final Friend friend) {
            ImageManager.getInstance().loadImage(friend.getUserAvatarPath(), UserImage);
            userName.setText(friend.getUserName());
            agree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mContactRequestPresenter.agreeRequest(friend);
                }
            });

            disagree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mContactRequestPresenter.disagreeRequest(friend);
                }
            });
        }

    }


}
