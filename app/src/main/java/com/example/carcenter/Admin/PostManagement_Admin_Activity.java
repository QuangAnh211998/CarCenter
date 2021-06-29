package com.example.carcenter.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.carcenter.Adapter.PostManagamentAdapter;
import com.example.carcenter.Model.ProductsModel;
import com.example.carcenter.Network.APIRequest;
import com.example.carcenter.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PostManagement_Admin_Activity extends AppCompatActivity {

    private List<ProductsModel> productsModelList;
    private PostManagamentAdapter postManagamentAdapter;
    private RecyclerView browse_RecyclerView;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        if (Build.VERSION.SDK_INT >= 22) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(ContextCompat.getColor(PostManagement_Admin_Activity.this, R.color.colorGrey));
        }


        init();
        getproduct();
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
    public void getproduct() {
        APIRequest.getAllProduct(getApplicationContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonElement -> {
                    Gson gson = new Gson();
                    ArrayList<ProductsModel> productsModels = gson.fromJson(jsonElement.getAsJsonArray(), new TypeToken<ArrayList<ProductsModel>>() {
                    }.getType());
                    productsModelList.addAll(productsModels);
                    postManagamentAdapter.notifyDataSetChanged();
                }, throwable -> {
                });
    }

    private void init(){
        mToolbar = findViewById(R.id.toolbarbrowse);
        browse_RecyclerView= findViewById(R.id.browse_RyceclerView);

        LinearLayoutManager layoutManager_Product = new LinearLayoutManager(this);
        layoutManager_Product.setOrientation(LinearLayoutManager.VERTICAL);
        browse_RecyclerView.setLayoutManager(layoutManager_Product);
        productsModelList = new ArrayList<>();
        postManagamentAdapter = new PostManagamentAdapter(PostManagement_Admin_Activity.this,productsModelList);
        browse_RecyclerView.setAdapter(postManagamentAdapter);
    }
}