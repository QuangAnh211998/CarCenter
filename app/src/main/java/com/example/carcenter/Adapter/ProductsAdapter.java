package com.example.carcenter.Adapter;

import android.content.Intent;
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
import com.example.carcenter.Custom.Custom_Price;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private List<ProductsModel> productsModelList;

    public ProductsAdapter(List<ProductsModel> productsModelList) {
        this.productsModelList = productsModelList;
    }

    @NonNull
    @Override
    public ProductsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_products, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsAdapter.ViewHolder viewHolder, int position) {
        String imageUrl = productsModelList.get(position).getProduct_Image().get(0);
        String company = productsModelList.get(position).getProduct_Company();
        String name = productsModelList.get(position).getProduct_Name();
        String version = productsModelList.get(position).getProduct_Version();
        String year = String.valueOf(productsModelList.get(position).getProduct_Year());
        String price = String.valueOf(productsModelList.get(position).getProduct_Price());
        String status = productsModelList.get(position).getProduct_Status();
        String username = productsModelList.get(position).getUser_Name();
        String livingArea = productsModelList.get(position).getUser_LivingArea();

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
            productImage = itemView.findViewById(R.id.product_ImageView);
            productCompany = itemView.findViewById(R.id.product_Company_tv);
            productName = itemView.findViewById(R.id.product_Name_tv);
            productVersion = itemView.findViewById(R.id.product_Version_tv);
            productYear = itemView.findViewById(R.id.product_Year_tv);
            productPrice = itemView.findViewById(R.id.productPrice_tv);
            productStatus = itemView.findViewById(R.id.product_Status_tv);
            productUserName = itemView.findViewById(R.id.product_UserName_tv);
            productUserLivingAre = itemView.findViewById(R.id.productLivingArea_tv);

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
