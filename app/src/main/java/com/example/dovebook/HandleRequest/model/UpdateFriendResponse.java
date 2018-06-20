package com.example.dovebook.HandleRequest.model;

import com.google.gson.annotations.SerializedName;

public class UpdateFriendResponse {
    private String friendId;
    @SerializedName("friendFromid")private String friendFromId;
    @SerializedName("friendToid")private String friendToId;
    private String friendSims;
    @SerializedName("friendIsfriend")private String friendIsFriend;
    @SerializedName("createdat")private String createdAt;
    @SerializedName("updateat")private String updateAt;

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public String getFriendFromId() {
        return friendFromId;
    }

    public void setFriendFromId(String friendFromId) {
        this.friendFromId = friendFromId;
    }

    public String getFriendToId() {
        return friendToId;
    }

    public void setFriendToId(String friendToId) {
        this.friendToId = friendToId;
    }

    public String getFriendSims() {
        return friendSims;
    }

    public void setFriendSims(String friendSims) {
        this.friendSims = friendSims;
    }

    public String getFriendIsFriend() {
        return friendIsFriend;
    }

    public void setFriendIsFriend(String friendIsFriend) {
        this.friendIsFriend = friendIsFriend;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }
}
