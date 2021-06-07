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

import com.example.carcenter.Adapter.PurchaseManagamentAdapter;
import com.example.carcenter.Adapter.SaleManagementAdapter;
import com.example.carcenter.Model.ProductsModel;
import com.example.carcenter.Model.PurchaseModel;
import com.example.carcenter.Network.APIRequest;
import com.example.carcenter.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PurchaseManagementFragment extends Fragment {

    private List<PurchaseModel> purchaseModelList;
    private PurchaseManagamentAdapter purchaseManagamentAdapter;
    private RecyclerView purchase_recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_purchase_management, viewGroup, false);

        purchase_recyclerView = view.findViewById(R.id.recyclerView_purchase);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        purchase_recyclerView.setLayoutManager(layoutManager);
        purchaseModelList = new ArrayList<>();
        purchaseManagamentAdapter = new PurchaseManagamentAdapter(purchaseModelList,onItemOnCLick);
        purchase_recyclerView.setAdapter(purchaseManagamentAdapter);

        getDataPurchasetbyKey();
        return view;
    }

    private PurchaseManagamentAdapter.OnItemOnCLick onItemOnCLick = new PurchaseManagamentAdapter.OnItemOnCLick() {
        @Override
        public void onClick(String stutus, int id) {
            UpdatePurchase(stutus, id);
        }
    };

    ////// get dữ liệu product theo trạng thái
    @SuppressLint("CheckResult")
    public void getDataPurchasetbyKey() {
        String approval = "Chờ duyệt";
        String query = "SELECT * FROM purchase WHERE purchase_PostApproval ='"+approval+"' ORDER BY purchase_Id";
        purchaseModelList.clear();

        APIRequest.getPurchasebyKey(getContext(), query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonElement -> {
                    Gson gson = new Gson();
                    ArrayList<PurchaseModel> purchaseModels = gson.fromJson(jsonElement.getAsJsonArray(), new TypeToken<ArrayList<PurchaseModel>>() {
                    }.getType());
                    purchaseModelList.addAll(purchaseModels);
                    purchaseManagamentAdapter.notifyDataSetChanged();
                }, throwable -> {
                });
    }

    @SuppressLint("CheckResult")
    private void UpdatePurchase(String status, int id ){
        String query = "UPDATE purchase SET purchase_PostApproval = '"+status+"' WHERE purchase_Id ='" +id+ "'";

        APIRequest.UpdateAndDelete(getContext(),query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonElement -> {
                    JSONObject jsonObject = new JSONObject(jsonElement.toString());
                    String aaa = jsonObject.getString("status");
                    if(aaa.equals("success")) {
                        getDataPurchasetbyKey();
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    Toast.makeText(getContext(), "Duyệt bài thất bại", Toast.LENGTH_LONG).show();
                });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}