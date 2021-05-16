package com.example.carcenter.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class ProvinceModel implements Parcelable {

    private int province_Id;
    private String province_Name;

    public ProvinceModel() {
    }

    public ProvinceModel(int province_Id, String province_Name) {
        this.province_Id = province_Id;
        this.province_Name = province_Name;
    }

    protected ProvinceModel(Parcel in) {
        province_Id = in.readInt();
        province_Name = in.readString();
    }

    public static final Creator<ProvinceModel> CREATOR = new Creator<ProvinceModel>() {
        @Override
        public ProvinceModel createFromParcel(Parcel in) {
            return new ProvinceModel(in);
        }

        @Override
        public ProvinceModel[] newArray(int size) {
            return new ProvinceModel[size];
        }
    };

    public int getProvince_Id() {
        return province_Id;
    }

    public void setProvince_Id(int province_Id) {
        this.province_Id = province_Id;
    }

    public String getProvince_Name() {
        return province_Name;
    }

    public void setProvince_Name(String province_Name) {
        this.province_Name = province_Name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(province_Id);
        dest.writeString(province_Name);
    }
}
