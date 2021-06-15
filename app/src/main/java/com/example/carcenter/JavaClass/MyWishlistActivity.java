package com.example.carcenter.JavaClass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.carcenter.Adapter.MySaleAdapter;
import com.example.carcenter.Adapter.SaleManagementAdapter;
import com.example.carcenter.Adapter.WishlistAdapter;
import com.example.carcenter.Model.ProductsModel;
import com.example.carcenter.Model.WishlistModel;
import com.example.carcenter.Network.APIRequest;
import com.example.carcenter.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;
import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MyWishlistActivity extends AppCompatActivity {

    private SharedPreferences saveSignIn;
    private SharedPreferences.Editor editor;

    private List<ProductsModel> productsModelList;
    private List<WishlistModel> wishlistModelList;
    private WishlistAdapter wishlistAdapter;
    private RecyclerView wishlist_RecyclerView;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wishlist);
        if (Build.VERSION.SDK_INT >= 22) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(ContextCompat.getColor(MyWishlistActivity.this, R.color.colorGrey));
        }

        EventBus.getDefault().register(this);
        saveSignIn = getSharedPreferences("saveSignIn", Context.MODE_PRIVATE);
        editor = saveSignIn.edit();

        init();
        getWishlist();
        EventToobar();
    }


    private void EventToobar(){
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @SuppressLint("CheckResult")
    private void getWishlist() {
        int user_id = saveSignIn.getInt("user_Id", -1);

        APIRequest.getDataWishlist(getApplicationContext(), user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonElement -> {
                    Gson gson = new Gson();
                    ArrayList<WishlistModel> wishlistModels = gson.fromJson(jsonElement.getAsJsonArray(), new TypeToken<ArrayList<WishlistModel>>() {
                    }.getType());
                    wishlistModelList.addAll(wishlistModels);
                    for (int i = 0; i < wishlistModelList.size(); i++) {
                        int product_id = wishlistModelList.get(i).getProduct_Id();
                        getDataProductbyKey(product_id);
                    }
                }, throwable -> {

                });
    }

    @SuppressLint("CheckResult")
    public void getDataProductbyKey(int product_id) {
        String query = "SELECT * FROM products WHERE product_Id ='"+product_id+"' ORDER BY product_Id";
        productsModelList.clear();

        APIRequest.getProductbyKey(getApplicationContext(), query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonElement -> {
                    Gson gson = new Gson();
                    ArrayList<ProductsModel> productsModels = gson.fromJson(jsonElement.getAsJsonArray(), new TypeToken<ArrayList<ProductsModel>>() {
                    }.getType());
                    productsModelList.addAll(productsModels);
                    wishlistAdapter.notifyDataSetChanged();
                }, throwable -> {
                });
    }

    private void init(){
        mToolbar = findViewById(R.id.toolbarWishlist);
        wishlist_RecyclerView = findViewById(R.id.wishlist_RyceclerView);

        wishlistModelList = new ArrayList<>();

        LinearLayoutManager layoutManager_Product = new LinearLayoutManager(this);
        layoutManager_Product.setOrientation(LinearLayoutManager.VERTICAL);
        wishlist_RecyclerView.setLayoutManager(layoutManager_Product);
        productsModelList = new ArrayList<>();
        wishlistAdapter = new WishlistAdapter(MyWishlistActivity.this,productsModelList, wishlistModelList);
        wishlist_RecyclerView.setAdapter(wishlistAdapter);
    }
}