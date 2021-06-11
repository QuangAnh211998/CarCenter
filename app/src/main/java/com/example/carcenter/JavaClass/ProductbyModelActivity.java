package com.example.carcenter.JavaClass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carcenter.Adapter.ProductsAdapter;
import com.example.carcenter.Model.ProductsModel;
import com.example.carcenter.Network.APIRequest;
import com.example.carcenter.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProductbyModelActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;

    private List<ProductsModel> productsModelList;
    private ProductsAdapter productsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productby_model);
        if (Build.VERSION.SDK_INT >= 22) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(ContextCompat.getColor(ProductbyModelActivity.this, R.color.colorGrey));

            init();
            setmToolbar();
            getdata();
        }
    }

    private void setmToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getdata() {
        Intent intent = getIntent();
        String pro_company = intent.getStringExtra("product_company");
        String pro_name = intent.getStringExtra("product_name");
        int product_id = intent.getIntExtra("product_id", -1);
        getProductbyuser(product_id, pro_company, pro_name);

    }


    @SuppressLint("CheckResult")
    private void getProductbyuser(int product_id, String company, String name) {
        String approval = "Đã duyệt";
        String query = "SELECT * FROM products WHERE product_Company ='"+company+"' AND product_Name = '"+name+"' " +
                "AND product_PostApproval = '"+approval+"' ORDER BY product_Id";

        APIRequest.getProductbyKey(getApplicationContext(), query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonElement -> {
                    Gson gson = new Gson();
                    ArrayList<ProductsModel> productsModels = gson.fromJson(jsonElement.getAsJsonArray(), new TypeToken<ArrayList<ProductsModel>>() {
                    }.getType());
                    for (int i = 0; i < productsModels.size(); i++) {
                        if (productsModels.get(i).getProduct_Id() == product_id) {
                            productsModels.remove(i);
                        }
                    }
                    productsModelList.addAll(productsModels);
                    productsAdapter.notifyDataSetChanged();
                    if (productsModelList.get(0).getProduct_Company() != null) {
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Không có xe nào", Toast.LENGTH_LONG).show();
                });
    }

    private void init() {
        mToolbar = findViewById(R.id.toolbar_productbyModel);
        mRecyclerView = findViewById(R.id.productbyModel_rcl);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        productsModelList = new ArrayList<>();
        productsAdapter = new ProductsAdapter(productsModelList);
        mRecyclerView.setAdapter(productsAdapter);
    }
}