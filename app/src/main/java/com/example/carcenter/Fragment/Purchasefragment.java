package com.example.carcenter.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.carcenter.Adapter.PurchaseAdapter;
import com.example.carcenter.JavaClass.Portpurchase;
import com.example.carcenter.Model.PurchaseModel;
import com.example.carcenter.R;

import java.util.ArrayList;
import java.util.List;

public class Purchasefragment extends Fragment {

    private List<PurchaseModel> purchaseModelList;
    private PurchaseAdapter purchaseAdapter;
    private TextView postPurchase;
    private ImageButton search_imageButton;
    private RecyclerView purchase_recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_purchase, container, false);

        postPurchase = view.findViewById(R.id.postPurchase);
        search_imageButton = view.findViewById(R.id.btn_search);
        purchase_recyclerView = view.findViewById(R.id.purchase_recyclerView);

        LinearLayoutManager layoutManager_Purchase = new LinearLayoutManager(getContext());
        layoutManager_Purchase.setOrientation(LinearLayoutManager.VERTICAL);
        purchase_recyclerView.setLayoutManager(layoutManager_Purchase);

        purchaseModelList = new ArrayList<PurchaseModel>();
        purchaseModelList.add(new PurchaseModel("Cần mua Vinfast Lux A 2.0 2020", "800 - 1 Tỷ",
                "Cần mua gấp", "Quang Anh", "0346945454", "Hà Nội"));
        purchaseModelList.add(new PurchaseModel("Cần mua Vinfast Lux SA 2.0 2020", "Trên 1 Tỷ",
                "Cần mua gấp, rất gấp", "Quang Nam", "0972489988", "Hà Nội"));
        purchaseModelList.add(new PurchaseModel("Cần mua Vinfast Lux A 2.0 2020", "800 - 1 Tỷ",
                "Cần mua gấp", "Quang Anh", "0346945454", "Hà Nội"));
        purchaseModelList.add(new PurchaseModel("Cần mua Vinfast Lux SA 2.0 2020", "Trên 1 Tỷ",
                "Cần mua gấp, rất gấp", "Quang Nam", "0972489988", "Hà Nội"));
        purchaseModelList.add(new PurchaseModel("Cần mua Vinfast Lux A 2.0 2020", "800 - 1 Tỷ",
                "Cần mua gấp", "Quang Anh", "0346945454", "Hà Nội"));

        purchaseAdapter = new PurchaseAdapter(purchaseModelList);
        purchase_recyclerView.setAdapter(purchaseAdapter);
        purchaseAdapter.notifyDataSetChanged();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        postPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Portpurchase.class));
            }
        });
    }
}
