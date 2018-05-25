package com.example.dovebook.contact;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.dovebook.R;
import com.example.dovebook.base.model.Friend;
import com.example.dovebook.common.Constant;
import com.example.dovebook.common.ResposeStatus;
import com.example.dovebook.contact.model.contactManager;
import com.example.dovebook.contact.model.dbManager;
import com.example.dovebook.login.UserManager;
import com.example.dovebook.net.Api;
import com.example.dovebook.net.HttpManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by zyd on 2018/4/13.
 */

public class contactPresenter {

    //    public List<Friend> mFriendList;
    private static String TAG = "contactPresenter";
    //管理数据库
    public dbManager mDbManager;
    //MYyFriend数据库
    public SQLiteDatabase db;
    private contactActivity mContactActivity;
    private ContentValues mValues;
    //    private int startPosition = 0;
    //    private int endPosition = 20;
    private contactManager mContactManager;


    public contactPresenter(contactActivity view) {
        mContactActivity = view;
        mDbManager = new dbManager(mContactActivity, "MyFriend.db", null, 1);
        mContactManager = new contactManager(contactPresenter.this);
    }

    public void initData() {
        //如果是第一次进入应用，发送网络请求或读本地数据库获得好友列表
        if (mContactManager.ismFriendListNull()) {
//            Log.d(TAG, "initData: friendlistnull");
//            mFriendList = new ArrayList<>();
//            db = mDbManager.getWritableDatabase();
            mContactManager.initmFriendList();
            //如果数据库为空则发送网络请求，获取好友列表
            if (mContactManager.isDBNull()) {
                Log.d(TAG, "initData: dbnull");
                Api api = HttpManager.getInstance().getApiService(Constant.BASE_GET_CONTACT_LIST_URL);
                api.getFriends(UserManager.getUserId())
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
//                mContactManager.sendNetworkRequestAndGetContactList();

            } else {//数据库不为空，则读本地数据库获取好友列表
//                Cursor cursor=db.query("Friend",null,null,null,null,null,null);
//                if(cursor.moveToFirst()){
//                    do{
//                        Friend friend=new Friend();
//                        friend.setUserId(cursor.getString(cursor.getColumnIndex("userId")));
//                        friend.setUserName(cursor.getString(cursor.getColumnIndex("userName")));
//                        friend.setUserAvatarPath(cursor.getString(cursor.getColumnIndex("userAvatarPath")));
//                        friend.setFriendId(cursor.getString(cursor.getColumnIndex("friendId")));
//                        mFriendList.add(friend);
//                    }while (cursor.moveToNext());
//                }
                mContactManager.getContactListFromDB();
//                if (mFriendList != null) {
//                    mContactActivity.adapter.add(mFriendList);
//                }
                mContactActivity.adapter.add(mContactManager.getFriendList());
//                mContactActivity.showWhenSearchNothing();
                return;
            }
        } else {
            Log.d(TAG, "initData: friendlistnotnull");
            mContactActivity.adapter.add(mContactManager.getFriendList());
        }
    }

//    /**
//     * 查找好友列表中含关键字的好友
//     */
//
//    public List<Friend> searchFromFriendList(String key) {
//        List<Friend> temp = new ArrayList<>();
//        for (int i = 0; i < mFriendList.size(); i++) {
//            if (mFriendList.get(i).getUserName().contains(key)) {
//                temp.add(mFriendList.get(i));
//            }
//        }
//        return temp;
//    }

    public void searchNothing() {
//        if (mFriendList == null) {
//            mFriendList = new ArrayList<>();
//        }
        if (mContactManager.ismFriendListNull()) {
            mContactManager.initmFriendList();
        }
//        mFriendList.clear();
        mContactManager.clearFriendList();
        mContactActivity.adapter.notifyDataSetChanged();
//        mFriendList = mContactManager.getFriendListFromDB();
        mContactManager.getContactListFromDB();
        mContactActivity.adapter.clearThenAddAll(mContactManager.getFriendList());
    }

    public void searchSomething(String s) {
        mContactActivity.adapter.clearThenAddAll(mContactManager.searchFromFriendList(s));
    }

//    public void handleFriendList(){
//        if (mFriendList == null) {
//            mContactActivity.showEmptyView();
//        } else {
//            mContactManager.sortContactListFromFirstChar(mFriendList);
//            if (mValues == null) {
//                mValues = new ContentValues();
//            }
//            for (int i = 0; i < mFriendList.size(); i++) {
//                mValues.put("userId", mFriendList.get(i).getUserId());
//                mValues.put("userName", mFriendList.get(i).getUserName());
//                mValues.put("userAvatarpath", mFriendList.get(i).getUserAvatarPath());
//                mValues.put("friendId", mFriendList.get(i).getFriendId());
//                db.insert("Friend", null, mValues);
//                mValues.clear();
//            }
//            mContactActivity.adapter.add(mFriendList);
//        }
//    }

    public void onSendNetworkRequestAndDeleteContact(final Friend friend) {
//        mContactManager.sendNetworkRequestAndDeleteContact(friend);
        Log.d(TAG, "onSendNetworkRequestAndDeleteContact: " + friend.getFriendId());
        Api api = HttpManager.getInstance().getApiService(Constant.BASE_DELETE_FRIEND_URL);
        api.deleteFriend(friend.getFriendId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<String> sResponse) {
                        Log.d(TAG, "onNext: 123456");
//                            mContactManager.deleteFriendFromFriendList(friend);
//                            mContactManager.deleteContactFromDB(friend);
//                            mContactActivity.adapter.clearThenAddAll(mContactManager.getFriendList());
                        switch (sResponse.code()) {

                            case ResposeStatus.OK:
                                Toast.makeText(mContactActivity,"删除成功",Toast.LENGTH_SHORT).show();
                                mContactManager.deleteFriendFromFriendList(friend);
                                mContactManager.deleteContactFromDB(friend);
                                mContactActivity.adapter.clearThenAddAll(mContactManager.getFriendList());
                                break;
                            case ResposeStatus.NOCONTENT:
                            default:
                                Toast.makeText(mContactActivity, "删除异常", Toast.LENGTH_SHORT).show();
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

    public Context getView() {
        return mContactActivity;
    }

}
