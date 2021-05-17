package com.example.carcenter.Custom;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carcenter.Adapter.ProvinceAdapter;
import com.example.carcenter.Model.ProvinceModel;
import com.example.carcenter.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class BottomSheetPrice extends BottomSheetDialogFragment {
    private List<ProvinceModel> provinceModelList;
    private ProvinceAdapter.OnItemOnCLick itemOnCLick;

    public BottomSheetPrice(List<ProvinceModel> provinceModelList, ProvinceAdapter.OnItemOnCLick itemOnCLick) {
        this.provinceModelList = provinceModelList;
        this.itemOnCLick = itemOnCLick;
    }

    private RecyclerView recyclerView;
    private ProvinceAdapter provinceAdapter;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_bottomsheet_price, null);
        bottomSheetDialog.setContentView(view);

        recyclerView = view.findViewById(R.id.price_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        provinceAdapter = new ProvinceAdapter(provinceModelList, new ProvinceAdapter.OnItemOnCLick() {
            @Override
            public void onClick(String name) {
                itemOnCLick.onClick(name);
                dismiss();
            }
        });
        recyclerView.setAdapter(provinceAdapter);
        provinceAdapter.notifyDataSetChanged();

        return bottomSheetDialog;
    }
}

