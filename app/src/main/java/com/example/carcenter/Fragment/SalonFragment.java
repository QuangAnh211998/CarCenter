package com.example.carcenter.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carcenter.Adapter.SalonAdapter;
import com.example.carcenter.Model.SalonModel;
import com.example.carcenter.R;

import java.util.ArrayList;
import java.util.List;

public class SalonFragment extends Fragment {

    private List<SalonModel> salonModelList;
    private SalonAdapter salonAdapter;
    private RecyclerView salon_recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_salon, container, false);

        salon_recyclerView = view.findViewById(R.id.salon_recyclerView);

        LinearLayoutManager layoutManager_Salon = new LinearLayoutManager(getContext());
        layoutManager_Salon.setOrientation(LinearLayoutManager.VERTICAL);
        salon_recyclerView.setLayoutManager(layoutManager_Salon);

        salonModelList = new ArrayList<SalonModel>();
        salonModelList.add(new SalonModel(1, R.drawable.salon, "Salon Quang Anh",
                "Chuyên xe cũ chất lượng, giá cả hợp lý nhất khu vực hà nội", "51 Quan Nhân, Thanh Xuân, Hà Nội",
                "0346945454", "quanganh98@gmail.com", "abcd"));
        salonModelList.add(new SalonModel(1, R.drawable.salon, "Auto Quang Anh", "Đại lý chính hãng Vinfast",
                "51 Quan Nhân, Thanh Xuân, Hà Nội", "0346945454", "quanganh98@gmail.com", "abcd"));
        salonModelList.add(new SalonModel(1, R.drawable.salon, "Salon Quang Anh",
                "Chuyên xe cũ chất lượng, giá cả hợp lý nhất khu vực hà nội", "51 Quan Nhân, Thanh Xuân, Hà Nội",
                "0346945454", "quanganh98@gmail.com", "abcd"));
        salonModelList.add(new SalonModel(1, R.drawable.salon, "Auto Quang Anh", "Đại lý Vinfast",
                "Hà Nội", "0346945454", "quanganh98@gmail.com", "abcd"));
        salonModelList.add(new SalonModel(1, R.drawable.salon, "Salon Quang Anh", "Chuyên xe cũ chất lượng",
                "Hà Nội", "0346945454", "quanganh98@gmail.com", "abcd"));

        salonAdapter = new SalonAdapter(salonModelList);
        salon_recyclerView.setAdapter(salonAdapter);
        salonAdapter.notifyDataSetChanged();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
