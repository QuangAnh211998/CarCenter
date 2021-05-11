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
import android.widget.Toast;

import com.example.carcenter.Adapter.MySaleAdapter;
import com.example.carcenter.Adapter.ProductsAdapter;
import com.example.carcenter.Model.ProductsModel;
import com.example.carcenter.Network.APIRequest;
import com.example.carcenter.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MySaleFragment extends Fragment {

    private List<ProductsModel> productsModelList;
    private MySaleAdapter mySaleAdapter;
    private RecyclerView my_sale_recyclerView;

    private SharedPreferences saveSignIn;
    private SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_sale, viewGroup, false);

        EventBus.getDefault().register(this);
        saveSignIn = getContext().getSharedPreferences("saveSignIn", Context.MODE_PRIVATE);
        editor = saveSignIn.edit();


        my_sale_recyclerView = view.findViewById(R.id.my_sale_recyclerView);

        LinearLayoutManager layoutManager_Product = new LinearLayoutManager(getActivity());
        layoutManager_Product.setOrientation(LinearLayoutManager.VERTICAL);
        my_sale_recyclerView.setLayoutManager(layoutManager_Product);
        productsModelList = new ArrayList<ProductsModel>();
        mySaleAdapter = new MySaleAdapter(productsModelList);
        my_sale_recyclerView.setAdapter(mySaleAdapter);

        getMyPost();
        return view;
    }

    @SuppressLint("CheckResult")
    private void getMyPost(){
        int user_id = saveSignIn.getInt("user_Id", -1);

        APIRequest.getMyPost(getActivity(), user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonElement -> {
                    //Log.e("product", jsonElement.toString());
                    Gson gson = new Gson();
                    ArrayList<ProductsModel> productsModels = gson.fromJson(jsonElement.getAsJsonArray(), new TypeToken<ArrayList<ProductsModel>>(){}.getType());
                    Log.e("product", productsModels.get(0).getProduct_Image().get(0));
                    productsModelList.addAll(productsModels);
                    mySaleAdapter.notifyDataSetChanged();
                }, throwable -> {

                });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}