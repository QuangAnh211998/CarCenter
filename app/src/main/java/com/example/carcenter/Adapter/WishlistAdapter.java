package com.example.carcenter.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.carcenter.Custom.Custom_Price;
import com.example.carcenter.JavaClass.ProductDetailActivity;
import com.example.carcenter.Model.ProductsModel;
import com.example.carcenter.Model.WishlistModel;
import com.example.carcenter.Network.APIRequest;
import com.example.carcenter.R;

import org.json.JSONObject;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {

    private Context context;
    private List<ProductsModel> productsModelList;
    private List<WishlistModel> wishlistModelList;

    public WishlistAdapter(Context context, List<ProductsModel> productsModelList, List<WishlistModel> wishlistModelList) {
        this.context = context;
        this.productsModelList = productsModelList;
        this.wishlistModelList = wishlistModelList;
    }

    @NonNull
    @Override
    public WishlistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_my_sale, viewGroup, false);
        return new WishlistAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistAdapter.ViewHolder viewHolder, int position) {
        String imageUrl = productsModelList.get(position).getProduct_Image();
        String company = productsModelList.get(position).getProduct_Company();
        String name = productsModelList.get(position).getProduct_Name();
        String version = productsModelList.get(position).getProduct_Version();
        String year = String.valueOf(productsModelList.get(position).getProduct_Year());
        String price = String.valueOf(productsModelList.get(position).getProduct_Price());
        String username = productsModelList.get(position).getUser_Name();

        viewHolder.setProductImage(imageUrl);
        viewHolder.setProductCompany(company);
        viewHolder.setProductName(name);
        viewHolder.setProductVersion(version);
        viewHolder.setProductYear(year);
        viewHolder.setProductPrice(price);
        viewHolder.setProductUserName(username);

        viewHolder.mySale_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmDelete(wishlistModelList.get(position).getWishlist_Id(), position);
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
//            Picasso.get().load(imageUrl).into(productImage);
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

    private void ConfirmDelete(final int id, int position){
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(context);
        dialogXoa.setMessage("Bạn có muốn bỏ lưu tin này không?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                String query = "DELETE FROM wishlist WHERE wishlist_id ='"+id+"'";
                DeleteMySale(query, position);
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

            }
        });
        dialogXoa.show();
    }

    @SuppressLint("CheckResult")
    private void DeleteMySale(String query, int position){

        APIRequest.UpdateAndDelete(context,query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonElement -> {
                    Log.e("resetpass", jsonElement.toString());
                    JSONObject jsonObject = new JSONObject(jsonElement.toString());
                    String status = jsonObject.getString("status");
                    if(status.equals("success")) {
                        productsModelList.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(context, "Bỏ lưu thành công", Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    Toast.makeText(context, "Bỏ lưu thất bại", Toast.LENGTH_SHORT).show();
                });
    }
}
