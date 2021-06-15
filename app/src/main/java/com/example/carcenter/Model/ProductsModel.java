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
//    private ArrayList<String> product_Image;
    private String product_Image;
    private String product_Content;
    private String user_Name;
    private String user_Phone;
    private String user_Address;
    private String user_LivingArea;
    private int system_Air_Bag;
    private String system_ABS;
    private String system_EBA;
    private String system_ESP;
    private String system_Anti_Slip;
    private String system_Reverse_Warning;
    private String system_Anti_theft;
    private int user_Id;
    private String product_PostApproval;

    public ProductsModel() {
    }

    public ProductsModel(int product_Id, String product_Company, String product_Name, String product_Version, int product_Year,
                         String product_MadeIn, String product_Status, int product_KmWent, String product_Type, int product_Price,
                         String product_OutSide, String product_InSide, int product_Door, int product_Seat, String product_Gear,
                         String product_DriveTrain, String product_Fuel, int product_Consume, String product_Image,
                         String product_Content, String user_Name, String user_Phone, String user_Address, String user_LivingArea,
                         int system_Air_Bag, String system_ABS, String system_EBA, String system_ESP, String system_Anti_Slip,
                         String system_Reverse_Warning, String system_Anti_theft, int user_Id, String product_PostApproval) {
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
        this.user_Name = user_Name;
        this.user_Phone = user_Phone;
        this.user_Address = user_Address;
        this.user_LivingArea = user_LivingArea;
        this.system_Air_Bag = system_Air_Bag;
        this.system_ABS = system_ABS;
        this.system_EBA = system_EBA;
        this.system_ESP = system_ESP;
        this.system_Anti_Slip = system_Anti_Slip;
        this.system_Reverse_Warning = system_Reverse_Warning;
        this.system_Anti_theft = system_Anti_theft;
        this.user_Id = user_Id;
        this.product_PostApproval = product_PostApproval;
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
//        product_Image = in.createStringArrayList();
        product_Image = in.readString();
        product_Content = in.readString();
        user_Name = in.readString();
        user_Phone = in.readString();
        user_Address = in.readString();
        user_LivingArea = in.readString();
        system_Air_Bag = in.readInt();
        system_ABS = in.readString();
        system_EBA = in.readString();
        system_ESP = in.readString();
        system_Anti_Slip = in.readString();
        system_Reverse_Warning = in.readString();
        system_Anti_theft = in.readString();
        user_Id = in.readInt();
        product_PostApproval = in.readString();
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
        dest.writeString(product_Image);
//        dest.writeStringList(product_Image);
        dest.writeString(product_Content);
        dest.writeString(user_Name);
        dest.writeString(user_Phone);
        dest.writeString(user_Address);
        dest.writeString(user_LivingArea);
        dest.writeInt(system_Air_Bag);
        dest.writeString(system_ABS);
        dest.writeString(system_EBA);
        dest.writeString(system_ESP);
        dest.writeString(system_Anti_Slip);
        dest.writeString(system_Reverse_Warning);
        dest.writeString(system_Anti_theft);
        dest.writeInt(user_Id);
        dest.writeString(product_PostApproval);
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

    public String getProduct_Image() {
        return product_Image;
    }

    public void setProduct_Image(String product_Image) {
        this.product_Image = product_Image;
    }

    public String getProduct_Content() {
        return product_Content;
    }

    public void setProduct_Content(String product_Content) {
        this.product_Content = product_Content;
    }

    public String getUser_Name() {
        return user_Name;
    }

    public void setUser_Name(String user_Name) {
        this.user_Name = user_Name;
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

    public int getSystem_Air_Bag() {
        return system_Air_Bag;
    }

    public void setSystem_Air_Bag(int system_Air_Bag) {
        this.system_Air_Bag = system_Air_Bag;
    }

    public String getSystem_ABS() {
        return system_ABS;
    }

    public void setSystem_ABS(String system_ABS) {
        this.system_ABS = system_ABS;
    }

    public String getSystem_EBA() {
        return system_EBA;
    }

    public void setSystem_EBA(String system_EBA) {
        this.system_EBA = system_EBA;
    }

    public String getSystem_ESP() {
        return system_ESP;
    }

    public void setSystem_ESP(String system_ESP) {
        this.system_ESP = system_ESP;
    }

    public String getSystem_Anti_Slip() {
        return system_Anti_Slip;
    }

    public void setSystem_Anti_Slip(String system_Anti_Slip) {
        this.system_Anti_Slip = system_Anti_Slip;
    }

    public String getSystem_Reverse_Warning() {
        return system_Reverse_Warning;
    }

    public void setSystem_Reverse_Warning(String system_Reverse_Warning) {
        this.system_Reverse_Warning = system_Reverse_Warning;
    }

    public String getSystem_Anti_theft() {
        return system_Anti_theft;
    }

    public void setSystem_Anti_theft(String system_Anti_theft) {
        this.system_Anti_theft = system_Anti_theft;
    }

    public int getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(int user_Id) {
        this.user_Id = user_Id;
    }

    public String getProduct_PostApproval() {
        return product_PostApproval;
    }

    public void setProduct_PostApproval(String product_PostApproval) {
        this.product_PostApproval = product_PostApproval;
    }
}