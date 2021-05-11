package com.example.carcenter.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carcenter.Model.ProductsModel;
import com.example.carcenter.R;
import com.example.carcenter.common.Custom_Price;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MySaleAdapter extends RecyclerView.Adapter<MySaleAdapter.ViewHolder> {

    private List<ProductsModel> productsModelList;

    public MySaleAdapter(List<ProductsModel> productsModelList) {
        this.productsModelList = productsModelList;
    }

    @NonNull
    @Override
    public MySaleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_sale_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MySaleAdapter.ViewHolder viewHolder, int position) {
        String imageUrl = productsModelList.get(position).getProduct_Image().get(0);
        String company = productsModelList.get(position).getProduct_Company();
        String name = productsModelList.get(position).getProduct_Name();
        String version = productsModelList.get(position).getProduct_Version();
        String year = String.valueOf(productsModelList.get(position).getProduct_Year());
        String price = String.valueOf(productsModelList.get(position).getProduct_Price());
        String username = productsModelList.get(position).getProduct_UserName();

        viewHolder.setProductImage(imageUrl);
        viewHolder.setProductCompany(company);
        viewHolder.setProductName(name);
        viewHolder.setProductVersion(version);
        viewHolder.setProductYear(year);
        viewHolder.setProductPrice(price);
        viewHolder.setProductUserName(username);
    }

    @Override
    public int getItemCount() {
        return productsModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView productImage;
        private TextView productCompany;
        private TextView productName;
        private TextView productVersion;
        private TextView productYear;
        private TextView productPrice;
        private TextView mySale_UserName;
        private TextView mySale_Delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.mySale_ImageView);
            productCompany = itemView.findViewById(R.id.mySale_Company_tv);
            productName = itemView.findViewById(R.id.mySale_Name_tv);
            productVersion = itemView.findViewById(R.id.mySale_Version_tv);
            productYear = itemView.findViewById(R.id.mySale_Year_tv);
            productPrice = itemView.findViewById(R.id.mySale_Price_tv);
            mySale_UserName = itemView.findViewById(R.id.mySale_UserName_tv);
            mySale_Delete = itemView.findViewById(R.id.mySale_Delete_tv);

            mySale_Delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }
        private void setProductImage(String imageUrl){
            Picasso.get().load(imageUrl).into(productImage);
        }
        private void setProductCompany(String company){
            productCompany.setText(company);
        }
        private void setProductName(String name){
            productName.setText(name);
        }
        private void setProductVersion(String version){
            productVersion.setText(version);
        }
        private void setProductYear(String nam){
            productYear.setText(nam);
        }
        private void setProductPrice(String price){
            productPrice.setText(Custom_Price.format(Long.parseLong(price)));
        }
        private void setProductUserName(String userName){
            mySale_UserName.setText(userName);
        }
    }
}
