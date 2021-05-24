package com.example.carcenter.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carcenter.Model.CompanyModel;
import com.example.carcenter.Model.ProvinceModel;
import com.example.carcenter.R;

import java.util.List;

public class BottomSheetCompanyAdapter extends RecyclerView.Adapter<BottomSheetCompanyAdapter.ViewHolder> {
    private List<CompanyModel> companyModelList;
    private OnItemOnCLick itemOnLick;

    public BottomSheetCompanyAdapter(List<CompanyModel> companyModelList, OnItemOnCLick itemOnCLick) {
        this.companyModelList = companyModelList;
        this.itemOnLick = itemOnCLick;
    }


    @NonNull
    @Override
    public BottomSheetCompanyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_province, viewGroup, false);
        return new BottomSheetCompanyAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BottomSheetCompanyAdapter.ViewHolder viewHolder, int position) {
        String name = companyModelList.get(position).getCompany_Name();
        int id = companyModelList.get(position).getCompany_Id();

        viewHolder.setTv_province(name, id);
    }

    @Override
    public int getItemCount() {
        return companyModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_province;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_province = itemView.findViewById(R.id.tv_provinceName);
        }
        private void setTv_province(String name, int id){
            tv_province.setText(name);

            itemView.setOnClickListener(v -> itemOnLick.onClick(name, id));
        }
    }

    public interface OnItemOnCLick {
        void onClick(String name, int id);
    }
}
