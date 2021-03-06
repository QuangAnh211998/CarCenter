package com.example.carcenter.Admin;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.carcenter.Adapter.SaleManagementAdapter;
import com.example.carcenter.Model.ProductsModel;
import com.example.carcenter.Network.APIRequest;
import com.example.carcenter.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SaleManagementFragment extends Fragment {

    private List<ProductsModel> productsModelList;
    private SaleManagementAdapter saleManagementAdapter;
    private RecyclerView sale_RecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sale_management, viewGroup, false);

        sale_RecyclerView = view.findViewById(R.id.sale_recyclerView);

        LinearLayoutManager layoutManager_Product = new LinearLayoutManager(getActivity());
        layoutManager_Product.setOrientation(LinearLayoutManager.VERTICAL);
        sale_RecyclerView.setLayoutManager(layoutManager_Product);
        productsModelList = new ArrayList<>();
        saleManagementAdapter = new SaleManagementAdapter(productsModelList,onItemOnCLick);
        sale_RecyclerView.setAdapter(saleManagementAdapter);

        getDataProductbyKey();
        return view;
    }


    private SaleManagementAdapter.OnItemOnCLick onItemOnCLick = new SaleManagementAdapter.OnItemOnCLick() {
        @Override
        public void onClick(String stutus_sale, int id) {
            UpdateProducts(stutus_sale, id);
        }
    };

    ////// get d??? li???u product theo tr???ng th??i
    @SuppressLint("CheckResult")
    public void getDataProductbyKey() {
        String approval = "Ch??? duy???t";
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
                    saleManagementAdapter.notifyDataSetChanged();
                }, throwable -> {
                });
    }

    @SuppressLint("CheckResult")
    private void UpdateProducts(String status_sale, int id ){
        String query = "UPDATE products SET product_PostApproval = '"+status_sale+"' WHERE product_Id ='" +id+ "'";

        APIRequest.UpdateAndDelete(getContext(),query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonElement -> {
                    JSONObject jsonObject = new JSONObject(jsonElement.toString());
                    String status = jsonObject.getString("status");
                    if(status.equals("success")) {
                        getDataProductbyKey();
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    Toast.makeText(getContext(), "Duy???t b??i th???t b???i", Toast.LENGTH_LONG).show();
                });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}