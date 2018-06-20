package com.example.dovebook.base.model;

import android.os.Parcel;
import android.os.Parcelable;


public class Friend extends User implements Parcelable{
    private String friendId;

    public Friend() {
        super();
    }

    protected Friend(Parcel in) {
        super(in);
        friendId=in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest,flags);
        dest.writeString(friendId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Friend> CREATOR = new Creator<Friend>() {
        @Override
        public Friend createFromParcel(Parcel in) {
            return new Friend(in);
        }

        @Override
        public Friend[] newArray(int size) {
            return new Friend[size];
        }
    };

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

}
