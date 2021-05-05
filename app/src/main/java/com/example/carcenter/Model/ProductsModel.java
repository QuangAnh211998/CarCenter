package com.example.carcenter.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductsModel implements Parcelable {

    private int product_Id;
    private String product_Company;
    private String product_Name;
    private String product_Version;
    private int product_Year;
    private String product_MadeIn;
    private String product_Status;
    private int product_KmWent;
    private String product_Type;
    private int product_Price;
    private String product_OutSide;
    private String product_InSide;
    private int product_Door;
    private int product_Seat;
    private String product_Gear;
    private String product_DriveTrain;
    private String product_Fuel;
    private int product_Consume;
    private ArrayList<String> product_Image;
    private String product_Content;
    private String product_UserName;
    private String product_UserPhone;
    private String product_UserAddress;
    private String product_UserLivingArea;
    private int user_Id;

    public ProductsModel() {

    }

    public ProductsModel(int product_Id, String product_Company, String product_Name, String product_Version, int product_Year,
                         String product_MadeIn, String product_Status, int product_KmWent, String product_Type, int product_Price,
                         String product_OutSide, String product_InSide, int product_Door, int product_Seat, String product_Gear,
                         String product_DriveTrain, String product_Fuel, int product_Consume, ArrayList<String> product_Image,
                         String product_Content, String product_UserName, String product_UserPhone, String product_UserAddress,
                         String product_UserLivingArea, int user_Id) {
        this.product_Id = product_Id;
        this.product_Company = product_Company;
        this.product_Name = product_Name;
        this.product_Version = product_Version;
        this.product_Year = product_Year;
        this.product_MadeIn = product_MadeIn;
        this.product_Status = product_Status;
        this.product_KmWent = product_KmWent;
        this.product_Type = product_Type;
        this.product_Price = product_Price;
        this.product_OutSide = product_OutSide;
        this.product_InSide = product_InSide;
        this.product_Door = product_Door;
        this.product_Seat = product_Seat;
        this.product_Gear = product_Gear;
        this.product_DriveTrain = product_DriveTrain;
        this.product_Fuel = product_Fuel;
        this.product_Consume = product_Consume;
        this.product_Image = product_Image;
        this.product_Content = product_Content;
        this.product_UserName = product_UserName;
        this.product_UserPhone = product_UserPhone;
        this.product_UserAddress = product_UserAddress;
        this.product_UserLivingArea = product_UserLivingArea;
        this.user_Id = user_Id;
    }


    protected ProductsModel(Parcel in) {
        product_Id = in.readInt();
        product_Company = in.readString();
        product_Name = in.readString();
        product_Version = in.readString();
        product_Year = in.readInt();
        product_MadeIn = in.readString();
        product_Status = in.readString();
        product_KmWent = in.readInt();
        product_Type = in.readString();
        product_Price = in.readInt();
        product_OutSide = in.readString();
        product_InSide = in.readString();
        product_Door = in.readInt();
        product_Seat = in.readInt();
        product_Gear = in.readString();
        product_DriveTrain = in.readString();
        product_Fuel = in.readString();
        product_Consume = in.readInt();
        product_Image = in.createStringArrayList();
        product_Content = in.readString();
        product_UserName = in.readString();
        product_UserPhone = in.readString();
        product_UserAddress = in.readString();
        product_UserLivingArea = in.readString();
        user_Id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(product_Id);
        dest.writeString(product_Company);
        dest.writeString(product_Name);
        dest.writeString(product_Version);
        dest.writeInt(product_Year);
        dest.writeString(product_MadeIn);
        dest.writeString(product_Status);
        dest.writeInt(product_KmWent);
        dest.writeString(product_Type);
        dest.writeInt(product_Price);
        dest.writeString(product_OutSide);
        dest.writeString(product_InSide);
        dest.writeInt(product_Door);
        dest.writeInt(product_Seat);
        dest.writeString(product_Gear);
        dest.writeString(product_DriveTrain);
        dest.writeString(product_Fuel);
        dest.writeInt(product_Consume);
        dest.writeStringList(product_Image);
        dest.writeString(product_Content);
        dest.writeString(product_UserName);
        dest.writeString(product_UserPhone);
        dest.writeString(product_UserAddress);
        dest.writeString(product_UserLivingArea);
        dest.writeInt(user_Id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProductsModel> CREATOR = new Creator<ProductsModel>() {
        @Override
        public ProductsModel createFromParcel(Parcel in) {
            return new ProductsModel(in);
        }

        @Override
        public ProductsModel[] newArray(int size) {
            return new ProductsModel[size];
        }
    };

    public int getProduct_Id() {
        return product_Id;
    }

    public void setProduct_Id(int product_Id) {
        this.product_Id = product_Id;
    }

    public String getProduct_Company() {
        return product_Company;
    }

    public void setProduct_Company(String product_Company) {
        this.product_Company = product_Company;
    }

    public String getProduct_Name() {
        return product_Name;
    }

    public void setProduct_Name(String product_Name) {
        this.product_Name = product_Name;
    }

    public String getProduct_Version() {
        return product_Version;
    }

    public void setProduct_Version(String product_Version) {
        this.product_Version = product_Version;
    }

    public int getProduct_Year() {
        return product_Year;
    }

    public void setProduct_Year(int product_Year) {
        this.product_Year = product_Year;
    }

    public String getProduct_MadeIn() {
        return product_MadeIn;
    }

    public void setProduct_MadeIn(String product_MadeIn) {
        this.product_MadeIn = product_MadeIn;
    }

    public String getProduct_Status() {
        return product_Status;
    }

    public void setProduct_Status(String product_Status) {
        this.product_Status = product_Status;
    }

    public int getProduct_KmWent() {
        return product_KmWent;
    }

    public void setProduct_KmWent(int product_KmWent) {
        this.product_KmWent = product_KmWent;
    }

    public String getProduct_Type() {
        return product_Type;
    }

    public void setProduct_Type(String product_Type) {
        this.product_Type = product_Type;
    }

    public int getProduct_Price() {
        return product_Price;
    }

    public void setProduct_Price(int product_Price) {
        this.product_Price = product_Price;
    }

    public String getProduct_OutSide() {
        return product_OutSide;
    }

    public void setProduct_OutSide(String product_OutSide) {
        this.product_OutSide = product_OutSide;
    }

    public String getProduct_InSide() {
        return product_InSide;
    }

    public void setProduct_InSide(String product_InSide) {
        this.product_InSide = product_InSide;
    }

    public int getProduct_Door() {
        return product_Door;
    }

    public void setProduct_Door(int product_Door) {
        this.product_Door = product_Door;
    }

    public int getProduct_Seat() {
        return product_Seat;
    }

    public void setProduct_Seat(int product_Seat) {
        this.product_Seat = product_Seat;
    }

    public String getProduct_Gear() {
        return product_Gear;
    }

    public void setProduct_Gear(String product_Gear) {
        this.product_Gear = product_Gear;
    }

    public String getProduct_DriveTrain() {
        return product_DriveTrain;
    }

    public void setProduct_DriveTrain(String product_DriveTrain) {
        this.product_DriveTrain = product_DriveTrain;
    }

    public String getProduct_Fuel() {
        return product_Fuel;
    }

    public void setProduct_Fuel(String product_Fuel) {
        this.product_Fuel = product_Fuel;
    }

    public int getProduct_Consume() {
        return product_Consume;
    }

    public void setProduct_Consume(int product_Consume) {
        this.product_Consume = product_Consume;
    }

    public ArrayList<String> getProduct_Image() {
        return product_Image;
    }

    public void setProduct_Image(ArrayList<String> product_Image) {
        this.product_Image = product_Image;
    }

    public String getProduct_Content() {
        return product_Content;
    }

    public void setProduct_Content(String product_Content) {
        this.product_Content = product_Content;
    }

    public String getProduct_UserName() {
        return product_UserName;
    }

    public void setProduct_UserName(String product_UserName) {
        this.product_UserName = product_UserName;
    }

    public String getProduct_UserPhone() {
        return product_UserPhone;
    }

    public void setProduct_UserPhone(String product_UserPhone) {
        this.product_UserPhone = product_UserPhone;
    }

    public String getProduct_UserAddress() {
        return product_UserAddress;
    }

    public void setProduct_UserAddress(String product_UserAddress) {
        this.product_UserAddress = product_UserAddress;
    }

    public String getProduct_UserLivingArea() {
        return product_UserLivingArea;
    }

    public void setProduct_UserLivingArea(String product_UserLivingArea) {
        this.product_UserLivingArea = product_UserLivingArea;
    }

    public int getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(int user_Id) {
        this.user_Id = user_Id;
    }

    public static Creator<ProductsModel> getCREATOR() {
        return CREATOR;
    }
}