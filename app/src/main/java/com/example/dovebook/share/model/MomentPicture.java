package com.example.dovebook.share.model;

/**
 * Created by zzx on 18-1-31.
 */

import com.google.gson.annotations.SerializedName;

/**
 * 每条moment分享的picture图片类
 */
public class MomentPicture {

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
}
