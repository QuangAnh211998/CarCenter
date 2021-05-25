package com.example.carcenter.Admin;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import com.example.carcenter.Adapter.PostSaleManagementAdapter;
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

public class PostSaleFragment extends Fragment {

    private List<ProductsModel> productsModelList;
    private PostSaleManagementAdapter postSaleManagementAdapter;
    private RecyclerView sale_RecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_sale, viewGroup, false);

        sale_RecyclerView = view.findViewById(R.id.sale_recyclerView);

        LinearLayoutManager layoutManager_Product = new LinearLayoutManager(getActivity());
        layoutManager_Product.setOrientation(LinearLayoutManager.VERTICAL);
        sale_RecyclerView.setLayoutManager(layoutManager_Product);
        productsModelList = new ArrayList<ProductsModel>();
        postSaleManagementAdapter = new PostSaleManagementAdapter(productsModelList);
        sale_RecyclerView.setAdapter(postSaleManagementAdapter);

        getDataProductbyKey();
        return view;
    }

    ////// get dữ liệu product theo trạng thái
    @SuppressLint("CheckResult")
    public void getDataProductbyKey() {
        String approval = "Chờ duyệt";
        String query = "SELECT * FROM products WHERE product_PostApproval ='"+approval+"' ORDER BY product_Id";
        productsModelList.clear();
        APIRequest.getProductbyKey(getContext(), query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonElement -> {
                    Gson gson = new Gson();
                    ArrayList<ProductsModel> productsModels = gson.fromJson(jsonElement.getAsJsonArray(), new TypeToken<ArrayList<ProductsModel>>() {
                    }.getType());
                    productsModelList.addAll(productsModels);
                    postSaleManagementAdapter.notifyDataSetChanged();
                    if (productsModels.get(0).getProduct_Company() != null) {
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    Toast.makeText(getContext(), "Không có tin nào", Toast.LENGTH_LONG).show();
                });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}