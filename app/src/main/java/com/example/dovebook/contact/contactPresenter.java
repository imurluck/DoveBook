package com.example.dovebook.contact;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcelable;
import android.support.annotation.MainThread;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.dovebook.HandleRequest.ContactRequestActivity;
import com.example.dovebook.R;
import com.example.dovebook.base.model.Friend;
import com.example.dovebook.common.Constant;
import com.example.dovebook.common.ResposeStatus;
import com.example.dovebook.contact.model.contactManager;
import com.example.dovebook.contact.model.dbManager;
import com.example.dovebook.contact.utils.JudgeUtil;
import com.example.dovebook.login.UserManager;
import com.example.dovebook.net.Api;
import com.example.dovebook.net.HttpManager;
import com.example.dovebook.utils.ToastUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import retrofit2.http.GET;

/**
 * Created by zyd on 2018/4/13.
 */

public class contactPresenter {

    //    public List<Friend> mFriendList;
    private static String TAG = "contactPresenter";
    //管理数据库
    public dbManager mDbManager;
    private contactActivity mContactActivity;
    private contactManager mContactManager;


    public contactPresenter(contactActivity view) {
        mContactActivity = view;
        mDbManager = new dbManager(mContactActivity, "MyFriend.db", null, 1);
        mContactManager = new contactManager(contactPresenter.this);
    }

    public void initData() {
        Log.d(TAG, "initData: ");
        //获取好友请求列表
        getRequest();
        //如果是第一次进入应用，发送网络请求或读本地数据库获得好友列表
        if (mContactManager.ismFriendListNull()) {
//            Log.d(TAG, "initData: friendlistnull");
//            mFriendList = new ArrayList<>();
//            db = mDbManager.getWritableDatabase();
            mContactManager.initmFriendList();
            //如果数据库为空则发送网络请求，获取好友列表
            if (mContactManager.isDBNull()) {
                Log.d(TAG, "initData: dbnull");
                Api api2 = HttpManager.getInstance().getApiService(Constant.BASE_GET_CONTACT_LIST_URL);
                api2.getFriends(UserManager.getUser().getUserId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<List<Friend>>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(List<Friend> users) {
                                //如果好友列表为空，显示空页面
                                if (users.size() == 0) {
                                    Log.d(TAG, "onNext: users are null");
                                    mContactActivity.showEmptyView();
                                } else {//否则对好友列表排序，刷新并显示
                                    mContactManager.sortContactListFromFirstChar(users);
                                    mContactManager.refreshContactList(users);
//                                    if (mValues == null) {
//                                        mValues = new ContentValues();
//                                    }
//                                    for (int i = 0; i < users.size(); i++) {
//                                        mValues.put("userId", users.get(i).getUserId());
//                                        mValues.put("userName", users.get(i).getUserName());
//                                        mValues.put("userAvatarpath", users.get(i).getUserAvatarPath());
//                                        mValues.put("friendId", users.get(i).getFriendId());
//                                        db.insert("Friend", null, mValues);
//                                        mValues.clear();
//                                    }
                                    mContactManager.putContactListToDB();
                                    mContactActivity.adapter.add(mContactManager.getFriendList());
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                //暂时对返回异常做联系人为空处理，需要修改！！！
                                mContactActivity.showEmptyView();
                            }

                            @Override
                            public void onComplete() {

                            }
                        });

            } else {//数据库不为空，则读本地数据库获取好友列表
                mContactManager.getContactListFromDB();
                mContactActivity.adapter.add(mContactManager.getFriendList());
                return;
            }
        } else {
            Log.d(TAG, "initData: friendlistnotnull");
            mContactActivity.adapter.add(mContactManager.getFriendList());
        }

    }


    public void searchNothing() {

        if (mContactManager.ismFriendListNull()) {
            mContactManager.initmFriendList();
        }
        mContactManager.clearFriendList();
        mContactActivity.adapter.notifyDataSetChanged();
//        mFriendList = mContactManager.getFriendListFromDB();
        mContactManager.getContactListFromDB();
        mContactActivity.adapter.clearThenAddAll(mContactManager.getFriendList());
    }

    public void searchSomething(String s) {
        mContactActivity.adapter.clearThenAddAll(mContactManager.searchFromFriendList(s));
    }


    public void onSendNetworkRequestAndDeleteContact(final Friend friend) {
//        mContactManager.sendNetworkRequestAndDeleteContact(friend);
        Log.d(TAG, "onSendNetworkRequestAndDeleteContact: " + friend.getFriendId());
        Api api = HttpManager.getInstance().getApiService(Constant.BASE_DELETE_FRIEND_URL);
        api.deleteFriend(friend.getFriendId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<Void>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<Void> sResponse) {

                        switch (sResponse.code()) {

                            case ResposeStatus.OK:
                                Toast.makeText(mContactActivity, "删除成功", Toast.LENGTH_SHORT).show();
                                mContactManager.deleteFriendFromFriendList(friend);
                                mContactManager.deleteContactFromDB(friend);
                                mContactActivity.adapter.deleteAFriend(friend);
                                break;
                            case ResposeStatus.NOCONTENT:
                                ToastUtil.shortToast(sResponse.code() + "no content");
                                break;
                            default:
                                Toast.makeText(mContactActivity, "未知删除异常", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void onToHandleRequestActivityClickListener() {
        JudgeUtil.setChangeSign(false);
        Intent intent = new Intent(mContactActivity, ContactRequestActivity.class);
        Log.d(TAG, String.format(String.format("onToHandleRequestActivityClickListener: " + new Gson().toJson(mContactManager.getFriendRequestList()))));
        intent.putParcelableArrayListExtra("contactRequestList", (ArrayList<? extends Parcelable>) mContactManager.getFriendRequestList());
        mContactActivity.startActivity(intent);
    }

    public void ContactListChange() {
        getRequest();
        mContactManager.getContactListFromDB();
        mContactActivity.adapter.clearThenAddAll(mContactManager.getFriendList());
    }

    public void getRequest(){
        Api api1 = HttpManager.getInstance().getApiService(Constant.BASE_GET_REQUESTS_URL);
        api1.getRequests(UserManager.getUser().getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<List<Friend>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<List<Friend>> sResponse) {
                        Log.d(TAG, "onNext: ");

                        switch (sResponse.code()){
                            case ResposeStatus.OK:
                                if (sResponse.body() != null && sResponse.body().size() != 0) {
                                    mContactActivity.showNewRequestTip();
                                    mContactManager.refreshFriendRequestList(sResponse.body());
                                }
                                break;
                            case ResposeStatus.NOCONTENT:
                                mContactActivity.hideNewRequestTip();
                                mContactManager.refreshFriendRequestList(null);
                                break;
                            default:

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: ");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
