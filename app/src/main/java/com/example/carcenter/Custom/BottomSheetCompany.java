package com.example.carcenter.Custom;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carcenter.Adapter.BottomSheetCompanyAdapter;
import com.example.carcenter.Adapter.ProvinceAdapter;
import com.example.carcenter.Model.CompanyModel;
import com.example.carcenter.Model.ProvinceModel;
import com.example.carcenter.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class BottomSheetCompany extends BottomSheetDialogFragment {

    private List<CompanyModel> companyModelList;
    private BottomSheetCompanyAdapter.OnItemOnCLick itemOnCLick;

    public BottomSheetCompany(List<CompanyModel> companyModelList, BottomSheetCompanyAdapter.OnItemOnCLick itemOnCLick) {
        this.companyModelList = companyModelList;
        this.itemOnCLick = itemOnCLick;
    }

    private RecyclerView recyclerView;
    private BottomSheetCompanyAdapter bottomSheetCompanyAdapter;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_bottomsheet_company, null);
        bottomSheetDialog.setContentView(view);

        recyclerView = view.findViewById(R.id.company_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        bottomSheetCompanyAdapter = new BottomSheetCompanyAdapter(companyModelList, new BottomSheetCompanyAdapter.OnItemOnCLick() {
            @Override
            public void onClick(String name, int id) {
                itemOnCLick.onClick(name, id);
                dismiss();
            }
        });
        recyclerView.setAdapter(bottomSheetCompanyAdapter);
        bottomSheetCompanyAdapter.notifyDataSetChanged();

        return bottomSheetDialog;
    }
}

