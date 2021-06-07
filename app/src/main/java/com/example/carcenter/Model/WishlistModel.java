package com.example.carcenter.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class WishlistModel implements Parcelable {

    private int wishlist_Id;
    private int user_Id;
    private int product_Id;

    public WishlistModel(int wishlist_Id, int user_Id, int product_Id) {
        this.wishlist_Id = wishlist_Id;
        this.user_Id = user_Id;
        this.product_Id = product_Id;
    }

    protected WishlistModel(Parcel in) {
        wishlist_Id = in.readInt();
        user_Id = in.readInt();
        product_Id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(wishlist_Id);
        dest.writeInt(user_Id);
        dest.writeInt(product_Id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<WishlistModel> CREATOR = new Creator<WishlistModel>() {
        @Override
        public WishlistModel createFromParcel(Parcel in) {
            return new WishlistModel(in);
        }

        @Override
        public WishlistModel[] newArray(int size) {
            return new WishlistModel[size];
        }
    };

    public int getWishlist_Id() {
        return wishlist_Id;
    }

    public void setWishlist_Id(int wishlist_Id) {
        this.wishlist_Id = wishlist_Id;
    }

    public int getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(int user_Id) {
        this.user_Id = user_Id;
    }

    public int getProduct_Id() {
        return product_Id;
    }

    public void setProduct_Id(int product_Id) {
        this.product_Id = product_Id;
    }
}
