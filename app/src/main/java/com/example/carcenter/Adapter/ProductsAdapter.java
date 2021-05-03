package com.example.carcenter.Adapter;

import android.content.Intent;
import android.media.tv.TvContentRating;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carcenter.JavaClass.ProductDetailActivity;
import com.example.carcenter.Model.ProductsModel;
import com.example.carcenter.R;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private List<ProductsModel> productsModelList;

    public ProductsAdapter(List<ProductsModel> productsModelList) {
        this.productsModelList = productsModelList;
    }

    @NonNull
    @Override
    public ProductsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.products_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsAdapter.ViewHolder viewHolder, int position) {
        int imageUrl = productsModelList.get(position).getProductImage();
        String company = productsModelList.get(position).getProductCompany();
        String name = productsModelList.get(position).getProductName();
        String version = productsModelList.get(position).getProductVersion();
        String year = productsModelList.get(position).getProductYear();
        String price = productsModelList.get(position).getProductPrice();
        String status = productsModelList.get(position).getProductStatus();
        String username = productsModelList.get(position).getProductUserName();
        String livingArea = productsModelList.get(position).getProductUserLivingAre();

        viewHolder.setProductImage(imageUrl);
        viewHolder.setProductCompany(company);
        viewHolder.setProductName(name);
        viewHolder.setProductVersion(version);
        viewHolder.setProductYear(year);
        viewHolder.setProductPrice(price);
        viewHolder.setProductStatus(status);
        viewHolder.setProductUserName(username);
        viewHolder.setProductUserLivingAre(livingArea);
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
        private TextView productStatus;
        private TextView productUserName;
        private TextView productUserLivingAre;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImageView);
            productCompany = itemView.findViewById(R.id.productCompany_tv);
            productName = itemView.findViewById(R.id.productName_tv);
            productVersion = itemView.findViewById(R.id.productVersion_tv);
            productYear = itemView.findViewById(R.id.productYear_tv);
            productPrice = itemView.findViewById(R.id.productPrice_tv);
            productStatus = itemView.findViewById(R.id.productStatus_tv);
            productUserName = itemView.findViewById(R.id.productUserName_tv);
            productUserLivingAre = itemView.findViewById(R.id.productLivingArea_tv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), ProductDetailActivity.class);
                    itemView.getContext().startActivity(intent);
                }
            });
        }


        private void setProductImage(int imageUrl){
            productImage.setImageResource(imageUrl);
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
            productPrice.setText(price);
        }
        private void setProductStatus(String status){
            productStatus.setText(status);
        }
        private void setProductUserName(String userName){
            productUserName.setText(userName);
        }
        private void setProductUserLivingAre(String livingAre){
            productUserLivingAre.setText(livingAre);
        }
    }
}
