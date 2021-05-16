package com.example.carcenter.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carcenter.Adapter.CategoryAdapter;
import com.example.carcenter.Adapter.ProductsAdapter;
import com.example.carcenter.Adapter.ProvinceAdapter;
import com.example.carcenter.JavaClass.SearchActivity;
import com.example.carcenter.Model.CategoryModel;
import com.example.carcenter.Model.ProductsModel;
import com.example.carcenter.Model.ProvinceModel;
import com.example.carcenter.Model.Users;
import com.example.carcenter.R;
import com.example.carcenter.Network.APIRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomeFragment extends Fragment {

    public static final String TAG = "tagHomeFragment";
    private List<CategoryModel> categoryModelList;
    private CategoryAdapter categoryAdapter;
    private RecyclerView category_RecyclerView;

    private Spinner spinner_Province;
    private ProvinceAdapter provinceAdapter;

    private List<ProductsModel> productsModelList;
    private ProductsAdapter productsAdapter;
    private RecyclerView product_RecyclerView;
    private Spinner spinner_sort;
    private ImageButton search_imageButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_home, viewGroup, false);
        search_imageButton = view.findViewById(R.id.search_imageButton);
        search_imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SearchActivity.class));
            }
        });

        ////////// Category
        category_RecyclerView = view.findViewById(R.id.category_recyclerView);

        LinearLayoutManager layoutManagerCategory = new LinearLayoutManager(getActivity());
        layoutManagerCategory.setOrientation(LinearLayoutManager.HORIZONTAL);
        category_RecyclerView.setLayoutManager(layoutManagerCategory);
        categoryModelList = new ArrayList<CategoryModel>();
        categoryAdapter = new CategoryAdapter(categoryModelList,onCLick);
        category_RecyclerView.setAdapter(categoryAdapter);
        ////////// Category

        ////////// Products
        product_RecyclerView = view.findViewById(R.id.productRecyclerView);
        LinearLayoutManager layoutManager_Product = new LinearLayoutManager(getActivity());
        layoutManager_Product.setOrientation(LinearLayoutManager.VERTICAL);
        product_RecyclerView.setLayoutManager(layoutManager_Product);
        productsModelList = new ArrayList<ProductsModel>();
        productsAdapter = new ProductsAdapter(productsModelList);
        product_RecyclerView.setAdapter(productsAdapter);
        ////////// Products

        ///////// Province
        spinner_sort = view.findViewById(R.id.spinner_sort);

        String[] listprice = new String[]{"Không sắp xếp","Giá thấp->cao","Giá cao->thấp"};

        final ArrayAdapter<String> arrprice = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, listprice);
        spinner_sort.setAdapter(arrprice);
        spinner_sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String query;
                String sort = arrprice.getItem(position);
                if(sort.equals("Không sắp xếp")){
                    getDataProduct();
                }else if(sort.equals("Giá thấp->cao")){
                    query = "SELECT * FROM products ORDER BY product_Price";
                    getDataProductWithkey(query);
                }else if(sort.equals("Giá cao->thấp")){
                    query = "SELECT * FROM products ORDER BY product_Price DESC";
                    getDataProductWithkey(query);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ///////// Province

        getDataCategory();
        getDataProduct();
        return view;
    }

    private CategoryAdapter.OnItemOnCLick onCLick = new CategoryAdapter.OnItemOnCLick() {
        @Override
        public void onClick(String name) {
            String query = "SELECT * FROM products WHERE product_Company = '"+name+"' ORDER BY product_Id DESC";
            getDataProductWithkey(query);
        }
    };

    private List<ProvinceModel> provinceModelList(){
        List<ProvinceModel> provinceModelList = new ArrayList<>();
        provinceModelList.add(new ProvinceModel(1, "Hà Nội"));
        provinceModelList.add(new ProvinceModel(2, "Đà Năng"));
        provinceModelList.add(new ProvinceModel(3, "TP HCM"));
        provinceModelList.add(new ProvinceModel(4, "Hưng Yên"));
        provinceModelList.add(new ProvinceModel(5, "Nam Định"));
        provinceModelList.add(new ProvinceModel(6, "Nghệ An"));

        return provinceModelList;
    }

    @SuppressLint("CheckResult")
    private void getDataCategory(){
        APIRequest.getCategory(getContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonElement -> {
//                    Log.e("getData",jsonElement.toString());
                    Gson gson = new Gson();
                    ArrayList<CategoryModel> categoryModels = gson.fromJson(jsonElement.getAsJsonArray(),new TypeToken<ArrayList<CategoryModel>>(){}.getType());
                    categoryModelList.addAll(categoryModels);
                    categoryAdapter.notifyDataSetChanged();
                }, throwable -> {

                });
    }

    @SuppressLint("CheckResult")
    private void getDataProduct(){
        productsModelList.clear();
        APIRequest.getProduct(getActivity())
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

    @SuppressLint("CheckResult")
    public void getDataProductWithkey(String query){
        productsModelList.clear();
        APIRequest.getProductbyCompany(getContext(), query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonElement -> {
                    Log.e("getproduct", jsonElement.toString());
                    Gson gson = new Gson();
                    ArrayList<ProductsModel> productsModels = gson.fromJson(jsonElement.getAsJsonArray(), new TypeToken<ArrayList<ProductsModel>>(){}.getType());
                    productsModelList.addAll(productsModels);
                    productsAdapter.notifyDataSetChanged();
                    if(TextUtils.isEmpty(productsModels.get(0).getProduct_Company())){
                        Toast.makeText(getContext(), "ok ok", Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    Toast.makeText(getContext(), "Không có xe nào đang bán", Toast.LENGTH_LONG).show();
                });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

}
