package com.example.carcenter.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carcenter.Model.CategoryModel;
import com.example.carcenter.Model.CompanyModel;
import com.example.carcenter.R;

import java.util.List;

public class BottomSheetCategoryAdapter extends RecyclerView.Adapter<BottomSheetCategoryAdapter.ViewHolder> {
    private List<CategoryModel> categoryModelList;
    private OnItemOnCLick itemOnLick;

    public BottomSheetCategoryAdapter(List<CategoryModel> categoryModelList, OnItemOnCLick itemOnCLick) {
        this.categoryModelList = categoryModelList;
        this.itemOnLick = itemOnCLick;
    }


    @NonNull
    @Override
    public BottomSheetCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_province, viewGroup, false);
        return new BottomSheetCategoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BottomSheetCategoryAdapter.ViewHolder viewHolder, int position) {
        String name = categoryModelList.get(position).getCategory_Name();

        viewHolder.setTv_province(name);
    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_province;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_province = itemView.findViewById(R.id.tv_provinceName);
        }
        private void setTv_province(String name){
            tv_province.setText(name);

            itemView.setOnClickListener(v -> itemOnLick.onClick(name));
        }
    }

    public interface OnItemOnCLick {
        void onClick(String name);
    }
}
