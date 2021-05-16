package com.example.carcenter.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.carcenter.Model.ProvinceModel;
import com.example.carcenter.R;

import java.util.List;

public class ProvinceAdapter extends ArrayAdapter<ProvinceModel> {


    public ProvinceAdapter(@NonNull Context context, int resource, @NonNull List<ProvinceModel> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup viewGroup) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_selected, viewGroup, false);

        TextView tv_selected = view.findViewById(R.id.tv_selected);
        ProvinceModel provinceModel = this.getItem(position);
        if(provinceModel != null){
            tv_selected.setText(provinceModel.getProvince_Name());
        }
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View view, @NonNull ViewGroup viewGroup) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_province, viewGroup, false);

        TextView provinceName_tv = view.findViewById(R.id.tv_provinceName);
        ProvinceModel provinceModelList = this.getItem(position);
        if(provinceModelList != null){
            provinceName_tv.setText(provinceModelList.getProvince_Name());
        }
        return view;
    }
}
