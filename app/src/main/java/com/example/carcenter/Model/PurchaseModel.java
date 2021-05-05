package com.example.carcenter.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class PurchaseModel implements Parcelable {

    private int purchase_Id;
    private String purchase_Title;
    private String purchase_PriceRange;
    private String purchase_Content;
    private String purchase_UserName;
    private String purchase_UserPhone;
    private String purchase_UserAddress;

    public PurchaseModel() {
    }

    public PurchaseModel(int purchase_Id, String purchase_Title, String purchase_PriceRange, String purchase_Content, String purchase_UserName,
                         String purchase_UserPhone, String purchase_UserAddress) {
        this.purchase_Id = purchase_Id;
        this.purchase_Title = purchase_Title;
        this.purchase_PriceRange = purchase_PriceRange;
        this.purchase_Content = purchase_Content;
        this.purchase_UserName = purchase_UserName;
        this.purchase_UserPhone = purchase_UserPhone;
        this.purchase_UserAddress = purchase_UserAddress;
    }

    protected PurchaseModel(Parcel in) {
        purchase_Id = in.readInt();
        purchase_Title = in.readString();
        purchase_PriceRange = in.readString();
        purchase_Content = in.readString();
        purchase_UserName = in.readString();
        purchase_UserPhone = in.readString();
        purchase_UserAddress = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(purchase_Id);
        dest.writeString(purchase_Title);
        dest.writeString(purchase_PriceRange);
        dest.writeString(purchase_Content);
        dest.writeString(purchase_UserName);
        dest.writeString(purchase_UserPhone);
        dest.writeString(purchase_UserAddress);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PurchaseModel> CREATOR = new Creator<PurchaseModel>() {
        @Override
        public PurchaseModel createFromParcel(Parcel in) {
            return new PurchaseModel(in);
        }

        @Override
        public PurchaseModel[] newArray(int size) {
            return new PurchaseModel[size];
        }
    };

    public int getPurchase_Id() {
        return purchase_Id;
    }

    public void setPurchase_Id(int purchase_Id) {
        this.purchase_Id = purchase_Id;
    }

    public String getPurchase_Title() {
        return purchase_Title;
    }

    public void setPurchase_Title(String purchase_Title) {
        this.purchase_Title = purchase_Title;
    }

    public String getPurchase_PriceRange() {
        return purchase_PriceRange;
    }

    public void setPurchase_PriceRange(String purchase_PriceRange) {
        this.purchase_PriceRange = purchase_PriceRange;
    }

    public String getPurchase_Content() {
        return purchase_Content;
    }

    public void setPurchase_Content(String purchase_Content) {
        this.purchase_Content = purchase_Content;
    }

    public String getPurchase_UserName() {
        return purchase_UserName;
    }

    public void setPurchase_UserName(String purchase_UserName) {
        this.purchase_UserName = purchase_UserName;
    }

    public String getPurchase_UserPhone() {
        return purchase_UserPhone;
    }

    public void setPurchase_UserPhone(String purchase_UserPhone) {
        this.purchase_UserPhone = purchase_UserPhone;
    }

    public String getPurchase_UserAddress() {
        return purchase_UserAddress;
    }

    public void setPurchase_UserAddress(String purchase_UserAddress) {
        this.purchase_UserAddress = purchase_UserAddress;
    }
}