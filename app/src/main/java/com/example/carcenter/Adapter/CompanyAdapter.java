package com.example.carcenter.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carcenter.Model.CompanyModel;
import com.example.carcenter.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.ViewHolder> {

    private List<CompanyModel> companyModelList;
    private OnItemOnCLick itemOnLick;

    public CompanyAdapter(List<CompanyModel> companyModels, OnItemOnCLick itemOnLick) {
        this.companyModelList = companyModels;
        this.itemOnLick = itemOnLick;
    }

    @NonNull
    @Override
    public CompanyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_company, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyAdapter.ViewHolder viewHolder, int position) {
        String image = companyModelList.get(position).getCompany_Image();
        String name = companyModelList.get(position).getCompany_Name();

        viewHolder.setCompanyName(name);
        viewHolder.setCompanyImage(image);


    }

    @Override
    public int getItemCount() {
        return companyModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView companyLogo;
        private TextView companyName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            companyLogo = itemView.findViewById(R.id.category_Image);
            companyName = itemView.findViewById(R.id.category_Name);


        }

        private void setCompanyImage(String image) {
            Picasso.get().load(image).into(companyLogo);
        }

        private void setCompanyName(String name) {
            companyName.setText(name);

            itemView.setOnClickListener(v -> itemOnLick.onClick(name));
        }
    }

    public interface OnItemOnCLick {
        void onClick(String name);
    }
}

