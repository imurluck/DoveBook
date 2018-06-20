package com.example.dovebook.HandleRequest;

import android.os.Bundle;
import android.os.UserManager;
import android.util.Log;
import android.widget.Toast;

import com.example.dovebook.HandleRequest.model.UpdateFriendResponse;
import com.example.dovebook.HandleRequest.model.UpdateRecordParams;
import com.example.dovebook.base.model.Friend;
import com.example.dovebook.common.Constant;
import com.example.dovebook.contact.model.contactManager;
import com.example.dovebook.contact.utils.JudgeUtil;
import com.example.dovebook.net.Api;
import com.example.dovebook.net.HttpManager;
import com.example.dovebook.utils.ToastUtil;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ContactRequestPresenter {
    private static final String TAG = "ContactRequestPresenter";
    private ContactRequestActivity mContactRequestActivity;
    private ContactRequestManager mContactRequestManager;
    private contactManager mContactManager;
    private UpdateRecordParams mUpdateRecordParams;


    public ContactRequestPresenter(ContactRequestActivity contactRequestActivity){
        mContactRequestActivity=contactRequestActivity;
        mContactManager=new contactManager();
        mContactRequestManager=new ContactRequestManager(ContactRequestPresenter.this);
        Bundle bundle=mContactRequestActivity.getIntent().getExtras();
        mContactRequestManager.initContactRequestList(bundle.<Friend>getParcelableArrayList("contactRequestList"));
    }

    public void initData(){
        if(mContactRequestManager.getContactRequestList()==null||mContactRequestManager.getContactRequestList().size()==0){
            mContactRequestActivity.showEmptyView();
        }else{
            mContactRequestActivity.contactRequestAdapter.add(mContactRequestManager.getContactRequestList());
        }
    }

    public void agreeRequest(final Friend friend){
        mUpdateRecordParams=new UpdateRecordParams();
        mUpdateRecordParams.setFriendFromid(friend.getUserId());
        mUpdateRecordParams.setFriendToid(com.example.dovebook.login.UserManager.getUser().getUserId());
        mUpdateRecordParams.setFriendIsfriend(true);
        HttpManager.getInstance().getApiService(Constant.BASE_UPDATE_FRIEND_RECORD_URL)
                .updateFriendRecord(friend.getFriendId(),mUpdateRecordParams)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UpdateFriendResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UpdateFriendResponse updateFriendResponse) {
                        mContactManager.putContactToDB(friend);
                        JudgeUtil.setChangeSign(true);
                        mContactRequestManager.deleteRecord(friend);
                        mContactRequestActivity.contactRequestAdapter.deleteAData(friend);
                        ToastUtil.shortToast("Success");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void disagreeRequest(final Friend friend){
//        HttpManager.getInstance().getApiService(Constant.BASE_UPDATE_FRIEND_RECORD_URL)
//                .updateFriendRecord(friend.getFriendId(),friend.getUserId(), com.example.dovebook.login.UserManager.getUser().getUserId(),false)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<UpdateFriendResponse>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(UpdateFriendResponse updateFriendResponse) {
//                        JudgeUtil.setChangeSign(false);
//                        mContactRequestManager.deleteRecord(friend);
//                        mContactRequestActivity.contactRequestAdapter.add(mContactRequestManager.getContactRequestList());
//                        ToastUtil.shortToast("Success");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
    }




}
