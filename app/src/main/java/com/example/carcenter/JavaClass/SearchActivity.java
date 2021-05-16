package com.example.carcenter.JavaClass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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

public class SearchActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private SearchView searchView;
    private RecyclerView search_recyclerView;

    private List<ProductsModel> productsModelList;
    private ProductsAdapter productsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        if(Build.VERSION.SDK_INT>=22){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(ContextCompat.getColor(SearchActivity.this,R.color.colorWhite));
        }

        Innit();
        ActionToolBar();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Search();
                LinearLayoutManager layoutManager_Product = new LinearLayoutManager(getApplicationContext());
                layoutManager_Product.setOrientation(LinearLayoutManager.VERTICAL);
                search_recyclerView.setLayoutManager(layoutManager_Product);
                productsModelList = new ArrayList<ProductsModel>();
                productsAdapter = new ProductsAdapter(productsModelList);
                search_recyclerView.setAdapter(productsAdapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
    }

    @SuppressLint("CheckResult")
    private void Search(){
        String key = searchView.getQuery().toString().trim();
        APIRequest.Search(getApplicationContext(),key )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonElement -> {
                    //Log.e("product", jsonElement.toString());
                    Gson gson = new Gson();
                    ArrayList<ProductsModel> productsModels = gson.fromJson(jsonElement.getAsJsonArray(), new TypeToken<ArrayList<ProductsModel>>(){}.getType());
                    Log.e("product", productsModels.get(0).getProduct_Image().get(0));
                    productsModelList.addAll(productsModels);
                    productsAdapter.notifyDataSetChanged();
                }, throwable -> {

                });
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Innit(){
        toolbar = findViewById(R.id.toolbarseach);
        searchView = findViewById(R.id.searchView);
        search_recyclerView = findViewById(R.id.search_recyclerView);
    }
}