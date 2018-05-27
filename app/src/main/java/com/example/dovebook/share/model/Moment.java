package com.example.dovebook.share.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by zzx on 18-1-24.
 */

public class Moment implements Parcelable {

    private String momentId;

    private String userId;
    //分享时用户所在纬度
    private double momentLatitude;
    //分享时用户所在经度
    private double momentLongitude;
    //分享时用户所在地理范围
    private String commentDetailloc;
    //分享更新时间
    @SerializedName("updatedat")
    private long updateAt;
    //分享创建时间
    @SerializedName("createdat")
    private long createAt;
    //分享内容
    private String momentContent;
    //点赞次数
    private int momentVoteCount;
    //图片序列
    @SerializedName("picpath")
    private List<MomentPicture> pictureList;
    //用户名
    private String userName;
    //用户性别
    private String userSex;
    //用户头像地址
    @SerializedName("userAvatarpath")
    private String userAvatarPath;
    //点赞次数
    private int commentCount;

    public String getMomentId() {
        return momentId;
    }

    public void setMomentId(String momentId) {
        this.momentId = momentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getMomentLatitude() {
        return momentLatitude;
    }

    public void setMomentLatitude(double momentLatitude) {
        this.momentLatitude = momentLatitude;
    }

    public double getMomentLongitude() {
        return momentLongitude;
    }

    public void setMomentLongitude(double momentLongitude) {
        this.momentLongitude = momentLongitude;
    }

    public String getCmomentDetailloc() {
        return commentDetailloc;
    }

    public void setCmomentDetailloc(String cmomentDetailloc) {
        this.commentDetailloc = cmomentDetailloc;
    }

    public long getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(long updateAt) {
        this.updateAt = updateAt;
    }

    public long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(long createAt) {
        this.createAt = createAt;
    }

    public String getMomentContent() {
        return momentContent;
    }

    public void setMomentContent(String momentContent) {
        this.momentContent = momentContent;
    }

    public int getMomentVoteCount() {
        return momentVoteCount;
    }

    public void setMomentVoteCount(int momentVoteCount) {
        this.momentVoteCount = momentVoteCount;
    }

    public List<MomentPicture> getPictureList() {
        return pictureList;
    }

    public void setPictureList(List<MomentPicture> pictureList) {
        this.pictureList = pictureList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserAvatarPath() {
        return userAvatarPath;
    }

    public void setUserAvatarPath(String userAvatarPath) {
        this.userAvatarPath = userAvatarPath;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(momentId);
        out.writeString(userId);
        out.writeDouble(momentLatitude);
        out.writeDouble(momentLongitude);
        out.writeString(commentDetailloc);
        out.writeLong(updateAt);
        out.writeLong(createAt);
        out.writeString(momentContent);
        out.writeInt(momentVoteCount);
        out.writeList(pictureList);
        out.writeString(userName);
        out.writeString(userSex);
        out.writeString(userAvatarPath);
        out.writeInt(commentCount);
    }

    public static final Parcelable.Creator<Moment> CREATOR = new Parcelable
            .Creator<Moment>() {

        @Override
        public Moment createFromParcel(Parcel parcel) {
            return new Moment(parcel);
        }

        @Override
        public Moment[] newArray(int i) {
            return new Moment[i];
        }
    };

    private Moment(Parcel in) {
        momentId = in.readString();
        userId = in.readString();
        momentLatitude = in.readDouble();
        momentLongitude = in.readDouble();
        commentDetailloc = in.readString();
        updateAt = in.readLong();
        createAt = in.readLong();
        momentContent = in.readString();
        momentVoteCount = in.readInt();
        pictureList = in.readArrayList(MomentPicture.class.getClassLoader());
        userName = in.readString();
        userSex = in.readString();
        userAvatarPath = in.readString();
        commentCount = in.readInt();
    }
}
