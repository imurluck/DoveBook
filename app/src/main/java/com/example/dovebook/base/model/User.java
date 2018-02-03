package com.example.dovebook.base.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by zzx on 18-1-28.
 */

public class User {

    private String userId;

    private String userName;

    private String userPassword;

    private Long userPhone;

    private String userEmail;
    @SerializedName("userBgpath")
    private String userBgPath;
    @SerializedName("userAvatarpath")
    private String userAvatarPath;

    private String userSex;

    private Integer userAge;

    private String userProfile;
    @SerializedName("userTaskcapacity")
    private Integer userTaskCapacity;

    private Float userAccuracy;
    @SerializedName("userPhoneverified")
    private Boolean userPhoneVerified;
    @SerializedName("userEmailverified")
    private Boolean userEmailVerified;
    @SerializedName("userIsrequester")
    private Boolean userIsRequester;

    private Date userArrive;

    private Date userDepart;

    private String userLatitude;

    private String userLongitude;
    @SerializedName("createdat")
    private long createAt;
    @SerializedName("updatedat")
    private long updateAt;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(Long userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserBgPath() {
        return userBgPath;
    }

    public void setUserBgPath(String userBgPath) {
        this.userBgPath = userBgPath;
    }

    public String getUserAvatarPath() {
        return userAvatarPath;
    }

    public void setUserAvatarPath(String userAvatarPath) {
        this.userAvatarPath = userAvatarPath;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    public String getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(String userProfile) {
        this.userProfile = userProfile;
    }

    public Integer getUserTaskCapacity() {
        return userTaskCapacity;
    }

    public void setUserTaskCapacity(Integer userTaskCapacity) {
        this.userTaskCapacity = userTaskCapacity;
    }

    public Float getUserAccuracy() {
        return userAccuracy;
    }

    public void setUserAccuracy(Float userAccuracy) {
        this.userAccuracy = userAccuracy;
    }

    public Boolean getUserPhoneVerified() {
        return userPhoneVerified;
    }

    public void setUserPhoneVerified(Boolean userPhoneVerified) {
        this.userPhoneVerified = userPhoneVerified;
    }

    public Boolean getUserEmailVerified() {
        return userEmailVerified;
    }

    public void setUserEmailVerified(Boolean userEmailVerified) {
        this.userEmailVerified = userEmailVerified;
    }

    public Boolean getUserIsRequester() {
        return userIsRequester;
    }

    public void setUserIsRequester(Boolean userIsRequester) {
        this.userIsRequester = userIsRequester;
    }

    public Date getUserArrive() {
        return userArrive;
    }

    public void setUserArrive(Date userArrive) {
        this.userArrive = userArrive;
    }

    public Date getUserDepart() {
        return userDepart;
    }

    public void setUserDepart(Date userDepart) {
        this.userDepart = userDepart;
    }

    public String getUserLatitude() {
        return userLatitude;
    }

    public void setUserLatitude(String userLatitude) {
        this.userLatitude = userLatitude;
    }

    public String getUserLongitude() {
        return userLongitude;
    }

    public void setUserLongitude(String userLongitude) {
        this.userLongitude = userLongitude;
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
