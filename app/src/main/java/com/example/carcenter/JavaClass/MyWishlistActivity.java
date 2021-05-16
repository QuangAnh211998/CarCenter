package com.example.carcenter.JavaClass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.carcenter.Model.ProductsModel;
import com.example.carcenter.Network.APIRequest;
import com.example.carcenter.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;
import org.simple.eventbus.EventBus;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MyWishlistActivity extends AppCompatActivity {

    private SharedPreferences saveSignIn;
    private SharedPreferences.Editor editor;

    private int wishlist_product_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wishlist);

        if(Build.VERSION.SDK_INT>=22){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(ContextCompat.getColor(MyWishlistActivity.this,R.color.colorGrey));
        }

        EventBus.getDefault().register(this);
        saveSignIn = getSharedPreferences("saveSignIn", Context.MODE_PRIVATE);
        editor = saveSignIn.edit();
        getWishlist();
    }

    @SuppressLint("CheckResult")
    private void getWishlist(){
        int user_id = saveSignIn.getInt("user_Id", -1);

        APIRequest.getWishlist(getApplicationContext(), user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonElement -> {
                    JSONObject jsonObject = new JSONObject(jsonElement.toString());
                    wishlist_product_id = jsonObject.getInt("product_Id");
                }, throwable -> {

                });
    }
}