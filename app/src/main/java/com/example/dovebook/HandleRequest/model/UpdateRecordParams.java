package com.example.dovebook.HandleRequest.model;

public class UpdateRecordParams {
    private String friendFromid;
    private String friendToid;
    private Boolean friendIsfriend;

    public String getFriendFromid() {
        return friendFromid;
    }

    public void setFriendFromid(String friendFromid) {
        this.friendFromid = friendFromid;
    }

    public String getFriendToid() {
        return friendToid;
    }

    public void setFriendToid(String friendToid) {
        this.friendToid = friendToid;
    }

    public Boolean getFriendIsfriend() {
        return friendIsfriend;
    }

    public void setFriendIsfriend(Boolean friendIsfriend) {
        this.friendIsfriend = friendIsfriend;
    }
}
