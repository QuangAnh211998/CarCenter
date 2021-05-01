package com.example.carcenter.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.carcenter.JavaClass.Portpurchase;
import com.example.carcenter.R;

public class Purchasefragment extends Fragment {

    private TextView tvdtmua;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_purchase, container, false);

        tvdtmua = view.findViewById(R.id.postPurchase);

        tvdtmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Portpurchase.class));
            }
        });
        return view;
    }
}
