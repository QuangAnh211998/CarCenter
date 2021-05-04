package com.example.carcenter.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class CategoryModel implements Parcelable {

    private int category_Id;
    private String category_Name;
    private String category_Image;

    public CategoryModel() {
    }

    public CategoryModel(int category_Id, String category_Name, String category_Image) {
        this.category_Id = category_Id;
        this.category_Name = category_Name;
        this.category_Image = category_Image;
    }

    protected CategoryModel(Parcel in) {
        category_Id = in.readInt();
        category_Name = in.readString();
        category_Image = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(category_Id);
        dest.writeString(category_Name);
        dest.writeString(category_Image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CategoryModel> CREATOR = new Creator<CategoryModel>() {
        @Override
        public CategoryModel createFromParcel(Parcel in) {
            return new CategoryModel(in);
        }

        @Override
        public CategoryModel[] newArray(int size) {
            return new CategoryModel[size];
        }
    };

    public int getCategory_Id() {
        return category_Id;
    }

    public void setCategory_Id(int category_Id) {
        this.category_Id = category_Id;
    }

    public String getCategory_Name() {
        return category_Name;
    }

    public void setCategory_Name(String category_Name) {
        this.category_Name = category_Name;
    }

    public String getCategory_Image() {
        return category_Image;
    }

    public void setCategory_Image(String category_Image) {
        this.category_Image = category_Image;
    }
}
