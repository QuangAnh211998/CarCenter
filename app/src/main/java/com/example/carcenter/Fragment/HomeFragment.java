package com.example.carcenter.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carcenter.Adapter.CompanyAdapter;
import com.example.carcenter.Adapter.ProductsAdapter;
import com.example.carcenter.Adapter.ProvinceAdapter;
import com.example.carcenter.JavaClass.SearchActivity;
import com.example.carcenter.Model.CompanyModel;
import com.example.carcenter.Model.ProductsModel;
import com.example.carcenter.Model.ProvinceModel;
import com.example.carcenter.R;
import com.example.carcenter.Network.APIRequest;
import com.example.carcenter.Custom.BottomSheetProvince;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomeFragment extends Fragment {

    private List<CompanyModel> companyModelList;
    private CompanyAdapter companyAdapter;
    private RecyclerView category_RecyclerView;

    List<ProvinceModel> provinceModelList;

    private List<ProductsModel> productsModelList;
    private ProductsAdapter productsAdapter;
    private RecyclerView product_RecyclerView;

    private Spinner spinner_sort;
    private ImageButton search_imageButton;
    private LinearLayout province_Layout;
    private TextView tv_province;

    String sort;
    String province = "Toàn quốc";
    String category_name = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, viewGroup, false);
        search_imageButton = view.findViewById(R.id.search_imageButton);
        search_imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SearchActivity.class));
            }
        });

        spinner_sort = view.findViewById(R.id.spinner_sort);
        province_Layout = view.findViewById(R.id.province_linearlayout);
        tv_province = view.findViewById(R.id.tv_province);

        ////////// Category
        category_RecyclerView = view.findViewById(R.id.category_recyclerView);
        LinearLayoutManager layoutManagerCategory = new LinearLayoutManager(getActivity());
        layoutManagerCategory.setOrientation(LinearLayoutManager.HORIZONTAL);
        category_RecyclerView.setLayoutManager(layoutManagerCategory);
        companyModelList = new ArrayList<CompanyModel>();
        companyAdapter = new CompanyAdapter(companyModelList, onCLickCategory);
        category_RecyclerView.setAdapter(companyAdapter);
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

        provinceModelList = new ArrayList<>();

        getDataCompany();
        getDataProduct();
        getDataProvince();
        SpinnerOnClick();
        OnclickProvince();
        return view;
    }


    ////// get show bottom sheet chọn tỉnh
    private void OnclickProvince() {
        province_Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetProvince bottomSheetProvince = new BottomSheetProvince(provinceModelList, new ProvinceAdapter.OnItemOnCLick() {
                    @Override
                    public void onClick(String name) {
                        tv_province.setText(name);
                        province = name;
                        String query;
                        if (sort.equals("Giá thấp->cao") && category_name == null) {
                            query = "SELECT * FROM products WHERE user_LivingArea = '" + name + "' ORDER BY product_Price";
                            getDataProductbyKey(query);
                        } else if (sort.equals("Giá cao->thấp") && category_name == null) {
                            query = "SELECT * FROM products WHERE user_LivingArea = '" + name + "' ORDER BY product_Price DESC";
                            getDataProductbyKey(query);
                        } else if(!sort.equals("Giá thấp->cao") && !sort.equals("Giá cao->thấp") && category_name != null){
                            query = "SELECT * FROM products WHERE product_Company ='"+category_name+"' AND user_LivingArea ='"+name+"' ORDER BY product_Id DESC";
                            getDataProductbyKey(query);
                        } else if(sort.equals("Giá thấp->cao") && category_name != null){
                            query = "SELECT * FROM products WHERE product_Company ='"+category_name+"' AND user_LivingArea ='"+name+"' ORDER BY product_Price";
                            getDataProductbyKey(query);
                        }else if(sort.equals("Giá cao->thấp") && category_name != null){
                            query = "SELECT * FROM products WHERE product_Company ='"+category_name+"' AND user_LivingArea ='"+name+"' ORDER BY product_Price DESC";
                            getDataProductbyKey(query);
                        }else {
                            query = "SELECT * FROM products WHERE user_LivingArea = '" + name + "' ORDER BY product_Id DESC";
                            getDataProductbyKey(query);
                        }
                    }
                });
                bottomSheetProvince.show(getFragmentManager(), bottomSheetProvince.getTag());
            }
        });
    }


    ////// get product khi chọn spinner
    private void SpinnerOnClick() {
        String[] listprice = new String[]{"Không sắp xếp", "Giá thấp->cao", "Giá cao->thấp"};
        final ArrayAdapter<String> arrprice = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, listprice);
        spinner_sort.setAdapter(arrprice);
        spinner_sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String query;
                sort = arrprice.getItem(position);
                if (province.equals("Toàn quốc") && category_name == null) {
                    if (sort.equals("Giá thấp->cao")) {
                        query = "SELECT * FROM products ORDER BY product_Price";
                        getDataProductbyKey(query);
                    } else if (sort.equals("Giá cao->thấp")) {
                        query = "SELECT * FROM products ORDER BY product_Price DESC";
                        getDataProductbyKey(query);
                    }
                } else if(!province.equals("Toàn quốc") && category_name == null) {
                    if (sort.equals("Giá thấp->cao")) {
                        query = "SELECT * FROM products WHERE user_LivingArea = '" + province + "' ORDER BY product_Price";
                        getDataProductbyKey(query);
                    } else if (sort.equals("Giá cao->thấp")) {
                        query = "SELECT * FROM products WHERE user_LivingArea = '" + province + "' ORDER BY product_Price DESC";
                        getDataProductbyKey(query);
                    }
                }else if(province.equals("Toàn quốc") && category_name != null){
                    if (sort.equals("Giá thấp->cao")) {
                        query = "SELECT * FROM products WHERE product_Company = '" + category_name + "' ORDER BY product_Price";
                        getDataProductbyKey(query);
                    } else if (sort.equals("Giá cao->thấp")) {
                        query = "SELECT * FROM products WHERE product_Company = '" + category_name + "' ORDER BY product_Price DESC";
                        getDataProductbyKey(query);
                    }
                }else if(!province.equals("Toàn quốc") && category_name != null){
                    if (sort.equals("Giá thấp->cao")) {
                        query = "SELECT * FROM products WHERE product_Company ='"+category_name+"' AND user_LivingArea ='"+province+"' ORDER BY product_Price";
                        getDataProductbyKey(query);
                    } else if (sort.equals("Giá cao->thấp")) {
                        query = "SELECT * FROM products WHERE product_Company ='"+category_name+"' AND user_LivingArea ='"+province+"' ORDER BY product_Price DESC";
                        getDataProductbyKey(query);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    ////// interface onclick categoryadapter
    private CompanyAdapter.OnItemOnCLick onCLickCategory = new CompanyAdapter.OnItemOnCLick() {
        @Override
        public void onClick(String name) {
            category_name = name;
            String query;
            if(!province.equals("Toàn quốc") && !sort.equals("Giá thấp->cao") && !sort.equals("Giá cao->thấp")){
                query = "SELECT * FROM products WHERE product_Company ='"+name+"' AND user_LivingArea ='"+province+"' ORDER BY product_Id DESC";
                getDataProductbyKey(query);
            }else if(!province.equals("Toàn quốc") && sort.equals("Giá thấp->cao")){
                query = "SELECT * FROM products WHERE product_Company ='"+name+"' AND user_LivingArea ='"+province+"' ORDER BY product_Price";
                getDataProductbyKey(query);
            }else if(!province.equals("Toàn quốc") && sort.equals("Giá cao->thấp")){
                query = "SELECT * FROM products WHERE product_Company ='"+name+"' AND user_LivingArea ='"+province+"' ORDER BY product_Price DESC";
                getDataProductbyKey(query);
            }else if(province.equals("Toàn quốc") && sort.equals("Giá thấp->cao")){
                query = "SELECT * FROM products WHERE product_Company = '" + name + "' ORDER BY product_Price";
                getDataProductbyKey(query);
            }else if(province.equals("Toàn quốc") && sort.equals("Giá thấp->cao")){
                query = "SELECT * FROM products WHERE product_Company = '" + name + "' ORDER BY product_Price DESC";
                getDataProductbyKey(query);
            }else {
                query = "SELECT * FROM products WHERE product_Company = '" + name + "' ORDER BY product_Id DESC";
                getDataProductbyKey(query);
            }
        }
    };


    ////// get dữ liệu hãng
    @SuppressLint("CheckResult")
    private void getDataCompany() {
        APIRequest.getCompany(getContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonElement -> {
                    Log.e("category", jsonElement.toString());
                    Gson gson = new Gson();
                    ArrayList<CompanyModel> companyModels = gson.fromJson(jsonElement.getAsJsonArray(), new TypeToken<ArrayList<CompanyModel>>() {
                    }.getType());
                    companyModelList.addAll(companyModels);
                    companyAdapter.notifyDataSetChanged();
                }, throwable -> {

                });
    }


    ////// get dữ liệu product
    @SuppressLint("CheckResult")
    private void getDataProduct() {
        productsModelList.clear();
        APIRequest.getProduct(getActivity())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonElement -> {
//                    Log.e("product", jsonElement.toString());
                    Gson gson = new Gson();
                    ArrayList<ProductsModel> productsModels = gson.fromJson(jsonElement.getAsJsonArray(),
                            new TypeToken<ArrayList<ProductsModel>>() {
                            }.getType());
                    productsModelList.addAll(productsModels);
                    productsAdapter.notifyDataSetChanged();
                }, throwable -> {

                });
    }


    ////// get dữ liệu product khi click
    @SuppressLint("CheckResult")
    public void getDataProductbyKey(String query) {
        productsModelList.clear();
        APIRequest.getProductbyKey(getContext(), query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonElement -> {
                    Log.e("getproduct", jsonElement.toString());
                    Gson gson = new Gson();
                    ArrayList<ProductsModel> productsModels = gson.fromJson(jsonElement.getAsJsonArray(), new TypeToken<ArrayList<ProductsModel>>() {
                    }.getType());
                    productsModelList.addAll(productsModels);
                    productsAdapter.notifyDataSetChanged();
                    if (productsModels.get(0).getProduct_Company() != null) {
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    Toast.makeText(getContext(), "Không có xe nào đang bán", Toast.LENGTH_LONG).show();
                });
    }


    ////// get Province
    @SuppressLint("CheckResult")
    private void getDataProvince() {
        APIRequest.getProvince(getContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonElement -> {
//                    Log.e("province", jsonElement.toString());
                    Gson gson = new Gson();
                    ArrayList<ProvinceModel> provinceModels = gson.fromJson(jsonElement.getAsJsonArray(),
                            new TypeToken<ArrayList<ProvinceModel>>() {
                            }.getType());
                    provinceModelList.addAll(provinceModels);
                }, throwable -> {

                });
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

}
