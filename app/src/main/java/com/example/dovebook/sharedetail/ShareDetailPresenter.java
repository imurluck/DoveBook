package com.example.dovebook.sharedetail;

/**
 * Created by zzx on 18-3-26.
 */

public class ShareDetailPresenter implements ShareDetailContract.Presenter {

    //是否已赞
    private boolean isVoted = false;

    //MVP V
    private ShareDetailContract.View mView;

    public ShareDetailPresenter(ShareDetailContract.View view) {
        mView = view;
    }

    /**
     * 点赞
     */
    @Override
    public void vote() {

    }
}
