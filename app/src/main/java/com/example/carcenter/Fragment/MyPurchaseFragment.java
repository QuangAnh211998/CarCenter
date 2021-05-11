package com.example.carcenter.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
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

import com.example.carcenter.Adapter.MyPurchaseAdapter;
import com.example.carcenter.Adapter.MySaleAdapter;
import com.example.carcenter.Model.ProductsModel;
import com.example.carcenter.Model.PurchaseModel;
import com.example.carcenter.Network.APIRequest;
import com.example.carcenter.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MyPurchaseFragment extends Fragment {

    private List<PurchaseModel> purchaseModelList;
    private MyPurchaseAdapter myPurchaseAdapter;
    private RecyclerView my_purchase_recyclerView;

    private SharedPreferences saveSignIn;
    private SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_purchase, viewGroup, false);

        EventBus.getDefault().register(this);
        saveSignIn = getContext().getSharedPreferences("saveSignIn", Context.MODE_PRIVATE);
        editor = saveSignIn.edit();


        my_purchase_recyclerView = view.findViewById(R.id.my_purchase_recyclerView);

        LinearLayoutManager layoutManager_purchase = new LinearLayoutManager(getActivity());
        layoutManager_purchase.setOrientation(LinearLayoutManager.VERTICAL);
        my_purchase_recyclerView.setLayoutManager(layoutManager_purchase);
        purchaseModelList = new ArrayList<PurchaseModel>();
        myPurchaseAdapter = new MyPurchaseAdapter(purchaseModelList);
        my_purchase_recyclerView.setAdapter(myPurchaseAdapter);

        getMyPurchase();
        return view;
    }
    @SuppressLint("CheckResult")
    private void getMyPurchase(){
        int user_id = saveSignIn.getInt("user_Id", -1);

        APIRequest.getMyPurchase(getActivity(), user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonElement -> {
                    //Log.e("product", jsonElement.toString());
                    Gson gson = new Gson();
                    ArrayList<PurchaseModel> purchaseModels = gson.fromJson(jsonElement.getAsJsonArray(), new TypeToken<ArrayList<PurchaseModel>>(){}.getType());
                    purchaseModelList.addAll(purchaseModels);
                    myPurchaseAdapter.notifyDataSetChanged();
                }, throwable -> {

                });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}