package com.example.carcenter.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Product_ImageModel implements Parcelable {
    private String product_Image;

    public Product_ImageModel(String product_Image) {
        this.product_Image = product_Image;
    }

    public Product_ImageModel(){

    }

    protected Product_ImageModel(Parcel in) {
        product_Image = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(product_Image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Product_ImageModel> CREATOR = new Creator<Product_ImageModel>() {
        @Override
        public Product_ImageModel createFromParcel(Parcel in) {
            return new Product_ImageModel(in);
        }

        @Override
        public Product_ImageModel[] newArray(int size) {
            return new Product_ImageModel[size];
        }
    };

    public String getProduct_Image() {
        return product_Image;
    }

    public void setProduct_Image(String product_Image) {
        this.product_Image = product_Image;
    }
}
