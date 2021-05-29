package com.example.carcenter.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.carcenter.Custom.Custom_Price;
import com.example.carcenter.JavaClass.ProductDetailActivity;
import com.example.carcenter.Model.ProductsModel;
import com.example.carcenter.R;

import java.util.List;

public class SaleManagementAdapter extends RecyclerView.Adapter<SaleManagementAdapter.ViewHolder> {

    private List<ProductsModel> productsModelList;
    private SaleManagementAdapter.OnItemOnCLick itemOnLick;

    public SaleManagementAdapter(List<ProductsModel> productsModelList, SaleManagementAdapter.OnItemOnCLick itemOnLick) {
        this.productsModelList = productsModelList;
        this.itemOnLick = itemOnLick;
    }

    @NonNull
    @Override
    public SaleManagementAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_sale_manage, viewGroup, false);
        return new SaleManagementAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SaleManagementAdapter.ViewHolder viewHolder, int position) {
        String imageUrl = productsModelList.get(position).getProduct_Image().get(0);
        String company = productsModelList.get(position).getProduct_Company();
        String name = productsModelList.get(position).getProduct_Name();
        String version = productsModelList.get(position).getProduct_Version();
        String year = String.valueOf(productsModelList.get(position).getProduct_Year());
        String price = String.valueOf(productsModelList.get(position).getProduct_Price());
        String username = productsModelList.get(position).getUser_Name();
        String id = String.valueOf(productsModelList.get(position).getProduct_Id());
        String postApproval = productsModelList.get(position).getProduct_PostApproval();

        viewHolder.setProductImage(imageUrl);
        viewHolder.setProductCompany(company);
        viewHolder.setProductName(name);
        viewHolder.setProductVersion(version);
        viewHolder.setProductYear(year);
        viewHolder.setProductPrice(price);
        viewHolder.setProductUserName(username);
        viewHolder.setId_sale(id);
        viewHolder.setPostApproval_sale(postApproval);


        viewHolder.checkBox_sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewHolder.checkBox_sale.isChecked()){
                    String status_sale = "Đã duyệt";
                    itemOnLick.onClick(status_sale,productsModelList.get(position).getProduct_Id());
                }
            }
        });
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
        private TextView productUserName;
        private TextView id_sale;
        private TextView postApproval_sale;
        private CheckBox checkBox_sale;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_ImageView);
            productCompany = itemView.findViewById(R.id.product_Company_tv);
            productName = itemView.findViewById(R.id.product_Name_tv);
            productVersion = itemView.findViewById(R.id.product_Version_tv);
            productYear = itemView.findViewById(R.id.product_Year_tv);
            productPrice = itemView.findViewById(R.id.product_Price_tv);
            productUserName = itemView.findViewById(R.id.product_UserName_tv);
            id_sale = itemView.findViewById(R.id.sale_id);
            postApproval_sale = itemView.findViewById(R.id.sale_status_tv);
            checkBox_sale = itemView.findViewById(R.id.checkbox_sale);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), ProductDetailActivity.class);
                    intent.putExtra("productDetail", productsModelList.get(getPosition()));
                    itemView.getContext().startActivity(intent);
                }
            });
        }


        private void setProductImage(String imageUrl){
            Glide.with(itemView).load(imageUrl).centerCrop().into(productImage);
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
            productUserName.setText(userName);
        }
        private void setId_sale(String id){
            id_sale.setText("Mã: "+id);
        }
        private void setPostApproval_sale(String postApproval){
            postApproval_sale.setText(postApproval);
        }
    }

    public interface OnItemOnCLick {
        void onClick(String stutus_sale, int id);
    }
}