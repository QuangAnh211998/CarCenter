package com.example.carcenter.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.carcenter.Adapter.ProvinceAdapter;
import com.example.carcenter.Adapter.PurchaseAdapter;
import com.example.carcenter.Custom.BottomSheetPrice;
import com.example.carcenter.Custom.BottomSheetProvince;
import com.example.carcenter.JavaClass.Postpurchase;
import com.example.carcenter.JavaClass.SearchActivity;
import com.example.carcenter.Model.ProvinceModel;
import com.example.carcenter.Model.PurchaseModel;
import com.example.carcenter.Network.APIRequest;
import com.example.carcenter.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Purchasefragment extends Fragment {

    private List<ProvinceModel> provinceModelList;
    private TextView province_tv;
    private TextView price_range_tv;

    private List<PurchaseModel> purchaseModelList;
    private PurchaseAdapter purchaseAdapter;
    private TextView postPurchase;
    private ImageButton search_imageButton;
    private RecyclerView purchase_recyclerView;

    private String price_range = "Mức giá";
    private String province = "Toàn quốc";
    private String status = "Đã duyệt";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_purchase, container, false);

        postPurchase = view.findViewById(R.id.postPurchase);
        search_imageButton = view.findViewById(R.id.btn_search);
        purchase_recyclerView = view.findViewById(R.id.purchase_recyclerView);
        province_tv = view.findViewById(R.id.province_purchase_tv);
        price_range_tv = view.findViewById(R.id.pricerange_sort_tv);

        LinearLayoutManager layoutManager_Purchase = new LinearLayoutManager(getContext());
        layoutManager_Purchase.setOrientation(LinearLayoutManager.VERTICAL);
        purchase_recyclerView.setLayoutManager(layoutManager_Purchase);
        purchaseModelList = new ArrayList<PurchaseModel>();
        purchaseAdapter = new PurchaseAdapter(getContext(), purchaseModelList);
        purchase_recyclerView.setAdapter(purchaseAdapter);
//        purchaseAdapter.notifyDataSetChanged();

        getDataPurchase();

        provinceModelList = new ArrayList<>();
        getDataProvince();
        ShowBottomSheet_Province();
        ShowBottomSheet_PriceRange();

        return view;
    }


    ////// show bottom sheet price range
    private void ShowBottomSheet_PriceRange() {
        price_range_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ProvinceModel> list = new ArrayList<>();
                list.add(new ProvinceModel(1, "Dưới 200 Triệu"));
                list.add(new ProvinceModel(2, "200 - 400 Triệu"));
                list.add(new ProvinceModel(3, "400 - 600 Triệu"));
                list.add(new ProvinceModel(4, "600 - 800 Triệu"));
                list.add(new ProvinceModel(5, "800 - 1 Tỷ"));
                list.add(new ProvinceModel(6, "Trên 1 Tỷ"));
                BottomSheetPrice bottomSheetPrice = new BottomSheetPrice(list, new ProvinceAdapter.OnItemOnCLick() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onClick(String name) {
                        price_range_tv.setText(name);
                        price_range = name;
//                        String query = "SELECT * FROM purchase WHERE purchase_PriceRange = '" + name + "' ORDER BY purchase_Id DESC";
//                        getDataPurchasebyKey(query);

                        String query;
                        if( province.equals("Toàn quốc")){
                            query = "SELECT * FROM purchase WHERE purchase_PriceRange = '"+name+"' AND purchase_PostApproval = '"+status+"'  ORDER BY purchase_Id DESC";
                            getDataPurchasebyKey(query);
                        }else if( !province.equals("Toàn quốc")){
                            query = "SELECT * FROM purchase WHERE purchase_PriceRange = '"+name+"' AND purchase_UserAddress LIKE '%"+province+"%' " +
                                    "AND purchase_PostApproval = '"+status+"'  ORDER BY purchase_Id DESC";
                            getDataPurchasebyKey(query);
                        }
                    }
                });
                bottomSheetPrice.show(getFragmentManager(), bottomSheetPrice.getTag());
            }
        });
    }

    ////// show bottom sheet province
    private void ShowBottomSheet_Province() {
        province_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BottomSheetProvince bottomSheetProvince = new BottomSheetProvince(provinceModelList, new ProvinceAdapter.OnItemOnCLick() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onClick(String name) {
                        province_tv.setText(name);
                        province = name;
//                        String query = "SELECT * FROM purchase WHERE purchase_UserAddress LIKE '%" + name + "%' ORDER BY purchase_Id DESC";
//                        getDataPurchasebyKey(query);

                        String query;
                        if( price_range.equals("Mức giá")){
                            query = "SELECT * FROM purchase WHERE purchase_UserAddress LIKE '%"+name+"%' AND purchase_PostApproval = '"+status+"' ORDER BY purchase_Id DESC";
                            getDataPurchasebyKey(query);
                        }else if( !price_range.equals("Mức giá")){
                            query = "SELECT * FROM purchase WHERE purchase_PriceRange = '"+price_range+"' AND purchase_UserAddress LIKE '%"+name+"%' " +
                                    "AND purchase_PostApproval = '"+status+"'  ORDER BY purchase_Id DESC";
                            getDataPurchasebyKey(query);
                        }
                    }
                });
                bottomSheetProvince.show(getFragmentManager(), bottomSheetProvince.getTag());
            }
        });
    }


    ////// get dữ liệu tin mua
    @SuppressLint("CheckResult")
    private void getDataPurchase() {
        APIRequest.getPurchase(getContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonElement -> {
//                    Log.e("getData",jsonElement.toString());
                    Gson gson = new Gson();
                    ArrayList<PurchaseModel> purchaseModels = gson.fromJson(jsonElement.getAsJsonArray(), new TypeToken<ArrayList<PurchaseModel>>() {
                    }.getType());
                    purchaseModelList.addAll(purchaseModels);
                    purchaseAdapter.notifyDataSetChanged();
                }, throwable -> {

                });
    }


    ////// get dữ liệu tin mua theo key
    @SuppressLint("CheckResult")
    private void getDataPurchasebyKey(String query) {
        purchaseModelList.clear();
        APIRequest.getPurchasebyKey(getContext(), query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonElement -> {
                    Log.e("key", jsonElement.toString());
                    Gson gson = new Gson();
                    ArrayList<PurchaseModel> purchaseModels = gson.fromJson(jsonElement.getAsJsonArray(), new TypeToken<ArrayList<PurchaseModel>>() {
                    }.getType());
                    purchaseModelList.addAll(purchaseModels);
                    purchaseAdapter.notifyDataSetChanged();
                }, throwable -> {

                });
    }


    ////// get Province
    @SuppressLint("CheckResult")
    private void getDataProvince() {
        APIRequest.getProvince(getContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonElement -> {
//                    Log.e("province",jsonElement.toString());
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

        postPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Postpurchase.class));
            }
        });

        search_imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SearchActivity.class));
            }
        });

    }
}
