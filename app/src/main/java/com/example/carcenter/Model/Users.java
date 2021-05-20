package com.example.carcenter.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Users {

    private int user_Id;
    private String user_Name;
    private String user_Email;
    private String user_Phone;
    private String user_Address;
    private String user_LivingArea;
    private String user_PassWord;
    private String user_Type;
    private int user_Money;
    private Date user_Create_day;

    public Users(int user_Id, String user_Name, String user_Email, String user_Phone, String user_Address, String user_LivingArea,
                 String user_PassWord, String user_Type, int user_Money, Date user_Create_day) {
        this.user_Id = user_Id;
        this.user_Name = user_Name;
        this.user_Email = user_Email;
        this.user_Phone = user_Phone;
        this.user_Address = user_Address;
        this.user_LivingArea = user_LivingArea;
        this.user_PassWord = user_PassWord;
        this.user_Type = user_Type;
        this.user_Money = user_Money;
        this.user_Create_day = user_Create_day;
    }

    public int getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(int user_Id) {
        this.user_Id = user_Id;
    }

    public String getUser_Name() {
        return user_Name;
    }

    public void setUser_Name(String user_Name) {
        this.user_Name = user_Name;
    }

    public String getUser_Email() {
        return user_Email;
    }

    public void setUser_Email(String user_Email) {
        this.user_Email = user_Email;
    }

    public String getUser_Phone() {
        return user_Phone;
    }

    public void setUser_Phone(String user_Phone) {
        this.user_Phone = user_Phone;
    }

    public String getUser_Address() {
        return user_Address;
    }

    public void setUser_Address(String user_Address) {
        this.user_Address = user_Address;
    }

    public String getUser_LivingArea() {
        return user_LivingArea;
    }

    public void setUser_LivingArea(String user_LivingArea) {
        this.user_LivingArea = user_LivingArea;
    }

    public String getUser_PassWord() {
        return user_PassWord;
    }

    public void setUser_PassWord(String user_PassWord) {
        this.user_PassWord = user_PassWord;
    }

    public String getUser_Type() {
        return user_Type;
    }

    public void setUser_Type(String user_Type) {
        this.user_Type = user_Type;
    }

    public int getUser_Money() {
        return user_Money;
    }

    public void setUser_Money(int user_Money) {
        this.user_Money = user_Money;
    }

    public Date getUser_Create_day() {
        return user_Create_day;
    }

    public void setUser_Create_day(Date user_Create_day) {
        this.user_Create_day = user_Create_day;
    }
}