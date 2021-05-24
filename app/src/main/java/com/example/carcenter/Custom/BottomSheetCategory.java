package com.example.carcenter.Custom;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carcenter.Adapter.BottomSheetCategoryAdapter;
import com.example.carcenter.Adapter.BottomSheetCompanyAdapter;
import com.example.carcenter.Model.CategoryModel;
import com.example.carcenter.Model.CompanyModel;
import com.example.carcenter.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class BottomSheetCategory extends BottomSheetDialogFragment {
    private List<CategoryModel> categoryModelList;
    private BottomSheetCategoryAdapter.OnItemOnCLick itemOnCLick;

    public BottomSheetCategory(List<CategoryModel> categoryModelList, BottomSheetCategoryAdapter.OnItemOnCLick itemOnCLick) {
        this.categoryModelList = categoryModelList;
        this.itemOnCLick = itemOnCLick;
    }

    private RecyclerView recyclerView;
    private BottomSheetCategoryAdapter bottomSheetCategoryAdapter;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_bottomsheet_category, null);
        bottomSheetDialog.setContentView(view);

        recyclerView = view.findViewById(R.id.category_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        bottomSheetCategoryAdapter = new BottomSheetCategoryAdapter(categoryModelList, new BottomSheetCategoryAdapter.OnItemOnCLick() {
            @Override
            public void onClick(String name) {
                itemOnCLick.onClick(name);
                dismiss();
            }
        });
        recyclerView.setAdapter(bottomSheetCategoryAdapter);
        bottomSheetCategoryAdapter.notifyDataSetChanged();

        return bottomSheetDialog;
    }
}
