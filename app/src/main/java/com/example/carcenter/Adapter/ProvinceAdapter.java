package com.example.carcenter.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.carcenter.Model.ProvinceModel;
import com.example.carcenter.R;
import java.util.List;

public class ProvinceAdapter extends RecyclerView.Adapter<ProvinceAdapter.ViewHolder> {

    private List<ProvinceModel> provinceModelList;
    private OnItemOnCLick itemOnLick;

    public ProvinceAdapter(List<ProvinceModel> provinceModelList, OnItemOnCLick itemOnCLick) {
        this.provinceModelList = provinceModelList;
        this.itemOnLick = itemOnCLick;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_province, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        String name = provinceModelList.get(position).getProvince_Name();

        viewHolder.setTv_province(name);
    }

    @Override
    public int getItemCount() {
        return provinceModelList.size();
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
