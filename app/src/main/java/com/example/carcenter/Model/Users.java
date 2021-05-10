package com.example.carcenter.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Users {

    @SerializedName("user_Id")
    @Expose
    private int userId;
    @SerializedName("user_Name")
    @Expose
    private String userName;
    @SerializedName("user_Email")
    @Expose
    private String userEmail;
    @SerializedName("user_Phone")
    @Expose
    private String userPhone;
    @SerializedName("user_Address")
    @Expose
    private String userAddress;
    @SerializedName("user_LivingArea")
    @Expose
    private String userLivingArea;
    @SerializedName("user_PassWord")
    @Expose
    private String userPassWord;
    @SerializedName("user_Type")
    @Expose
    private String userType;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserLivingArea() {
        return userLivingArea;
    }

    public void setUserLivingArea(String userLivingArea) {
        this.userLivingArea = userLivingArea;
    }

    public String getUserPassWord() {
        return userPassWord;
    }

    public void setUserPassWord(String userPassWord) {
        this.userPassWord = userPassWord;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

}