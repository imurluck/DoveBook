package com.example.dovebook.contact.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.dovebook.base.model.Friend;
import com.example.dovebook.contact.contactPresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;



public class contactManager {
    private static final String TAG = "contactManager";
    //管理数据库
    public dbManager mDbManager;
    //MYyFriend数据库
    public SQLiteDatabase db;
//    public Context mContext;
    private contactPresenter mContactPresenter;
    public List<Friend> mFriendList;
    private ContentValues mValues;

    public contactManager(contactPresenter contactPresenter) {
//        mContext = context;
        mContactPresenter = contactPresenter;
        mDbManager = new dbManager(mContactPresenter.getView(), "MyFriend.db", null, 1);
        db = mDbManager.getWritableDatabase();
    }

    public void refreshContactList(List<Friend> list) {
//        Api api = HttpManager.getInstance().getApiService(Constant.BASE_GET_CONTACT_LIST_URL);
//        api.getFriends(UserManager.getUserId())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<List<Friend>>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(List<Friend> users) {
//                        Log.d(TAG, "onNext: ");
//                        mContactPresenter.mFriendList=users;
//                        mContactPresenter.handleFriendList();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d(TAG, "onError: ");
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
        mFriendList = list;
    }

    public void getContactListFromDB() {
        mFriendList.clear();
        Cursor cursor = db.query("Friend", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Friend friend = new Friend();
                friend.setUserId(cursor.getString(cursor.getColumnIndex("userId")));
                friend.setUserName(cursor.getString(cursor.getColumnIndex("userName")));
                friend.setUserAvatarPath(cursor.getString(cursor.getColumnIndex("userAvatarPath")));
                friend.setFriendId(cursor.getString(cursor.getColumnIndex("friendId")));
                mFriendList.add(friend);
            } while (cursor.moveToNext());
        }
    }

    /**
     * 对返回的好友列表排序
     */

    public void sortContactListFromFirstChar(List<Friend> list) {
        Collections.sort(list, new Comparator<Friend>() {
            @Override
            public int compare(Friend friend, Friend t1) {
                return friend.getUserName().compareTo(t1.getUserName());
            }
        });
    }

//    public void sendNetworkRequestAndDeleteContact(final Friend friend) {
//        Api api = HttpManager.getInstance().getApiService(Constant.BASE_DELETE_FRIEND_URL);
//        api.deleteFriend(friend.getFriendId())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(Object o) {
//                        mContactPresenter.deleteFriendFromFriendList(friend);
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
//    }
    public void putContactListToDB(){
//        if (mFriendList == null) {
//            mContactActivity.showEmptyView();
//        } else {
//            mContactManager.sortContactListFromFirstChar(mFriendList);
        if (mValues == null) {
            mValues = new ContentValues();
        }
        for (int i = 0; i < mFriendList.size(); i++) {
            mValues.put("userId", mFriendList.get(i).getUserId());
            mValues.put("userName", mFriendList.get(i).getUserName());
            mValues.put("userAvatarpath", mFriendList.get(i).getUserAvatarPath());
            mValues.put("friendId", mFriendList.get(i).getFriendId());
            db.insert("Friend", null, mValues);
            mValues.clear();
        }
//            mContactActivity.adapter.add(mFriendList);
    }

    public List<Friend> getFriendList(){
        return mFriendList;
    }

    public boolean isDBNull(){
        if(db.query("Friend", null, null, null, null, null, null).getCount() == 0){
            return true;
        }else {
            return false;
        }
    }

    public boolean ismFriendListNull(){
        if (mFriendList==null){
            return true;
        }else {
            return false;
        }
    }

    public void initmFriendList(){
        mFriendList=new ArrayList<>();
    }

    /**
     * 查找好友列表中含关键字的好友
     */

    public List<Friend> searchFromFriendList(String key) {
        List<Friend> temp = new ArrayList<>();
        for (int i = 0; i < mFriendList.size(); i++) {
            if (mFriendList.get(i).getUserName().contains(key)) {
                temp.add(mFriendList.get(i));
            }
        }
        return temp;
    }

    public void clearFriendList(){
        if(ismFriendListNull()){
            mFriendList=new ArrayList<>();
        }
        mFriendList.clear();
    }

    public void deleteFriendFromFriendList(Friend friend){
        Log.d(TAG, "deleteFriendFromFriendList: friend.name="+friend.getUserName());
        for(int i=0;i<mFriendList.size();i++){
            Log.d(TAG, "beforeDeleteFriendFromFriendList: "+mFriendList.get(i).getUserName());
        }
        for(int i=0;i<mFriendList.size();i++){
            Log.d(TAG, "deleteFriendFromFriendList: "+mFriendList.get(i).getUserName());
            if(mFriendList.get(i).getUserName().equals(friend.getUserName())){
                mFriendList.remove(i);
                break;
            }
        }
        for(int i=0;i<mFriendList.size();i++){
            Log.d(TAG, "afterDeleteFriendFromFriendList: "+mFriendList.get(i).getUserName());
        }
    }

    public void deleteContactFromDB(Friend friend){
        db.delete("Friend","userName=?",new String[]{friend.getUserName()});
    }

}
