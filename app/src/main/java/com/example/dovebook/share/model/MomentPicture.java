package com.example.dovebook.share.model;

/**
 * Created by zzx on 18-1-31.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * 每条moment分享的picture图片类
 */
public class MomentPicture implements Parcelable {

    //服务器中PicPath表中的Id
    @SerializedName("picpathId")
    private String picPathId;
    //分享Id
    private String momentId;
    @SerializedName("picpathPath")
    //图片所在地址url
    private String picturePath;
    //创建时间
    @SerializedName("createdat")
    private long createAt;
    //更新时间
    @SerializedName("updatedat")
    private long updateAt;

    public String getPicPathId() {
        return picPathId;
    }

    public void setPicPathId(String picPathId) {
        this.picPathId = picPathId;
    }

    public String getMomentId() {
        return momentId;
    }

    public void setMomentId(String momentId) {
        this.momentId = momentId;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(long createAt) {
        this.createAt = createAt;
    }

    public long getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(long updateAt) {
        this.updateAt = updateAt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(picPathId);
        out.writeString(momentId);
        out.writeString(picturePath);
        out.writeLong(createAt);
        out.writeLong(updateAt);
    }

    public static final Parcelable.Creator<MomentPicture> CREATOR = new Parcelable
            .Creator<MomentPicture>() {

        @Override
        public MomentPicture createFromParcel(Parcel parcel) {
            return new MomentPicture(parcel);
        }

        @Override
        public MomentPicture[] newArray(int i) {
            return new MomentPicture[i];
        }
    };

    private MomentPicture(Parcel in) {
        picPathId = in.readString();
        momentId = in.readString();
        picturePath = in.readString();
        createAt = in.readLong();
        updateAt = in.readLong();
    }
}
