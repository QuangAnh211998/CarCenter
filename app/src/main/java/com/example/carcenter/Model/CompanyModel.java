package com.example.carcenter.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class CompanyModel implements Parcelable {

    private int company_Id;
    private String company_Name;
    private String company_Image;

    public CompanyModel() {
    }

    public CompanyModel(int company_Id, String company_Name, String company_Image) {
        this.company_Id = company_Id;
        this.company_Name = company_Name;
        this.company_Image = company_Image;
    }

    protected CompanyModel(Parcel in) {
        company_Id = in.readInt();
        company_Name = in.readString();
        company_Image = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(company_Id);
        dest.writeString(company_Name);
        dest.writeString(company_Image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CompanyModel> CREATOR = new Creator<CompanyModel>() {
        @Override
        public CompanyModel createFromParcel(Parcel in) {
            return new CompanyModel(in);
        }

        @Override
        public CompanyModel[] newArray(int size) {
            return new CompanyModel[size];
        }
    };

    public int getCompany_Id() {
        return company_Id;
    }

    public void setCompany_Id(int company_Id) {
        this.company_Id = company_Id;
    }

    public String getCompany_Name() {
        return company_Name;
    }

    public void setCompany_Name(String company_Name) {
        this.company_Name = company_Name;
    }

    public String getCompany_Image() {
        return company_Image;
    }

    public void setCompany_Image(String company_Image) {
        this.company_Image = company_Image;
    }
}
