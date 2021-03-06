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
import com.example.carcenter.JavaClass.UpdateProductActivity;
import com.example.carcenter.Model.ProductsModel;
import com.example.carcenter.Network.APIRequest;
import com.example.carcenter.R;
import com.example.carcenter.Custom.Custom_Price;

import org.json.JSONObject;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MySaleAdapter extends RecyclerView.Adapter<MySaleAdapter.ViewHolder> {

    private Context context;
    private List<ProductsModel> productsModelList;

    public MySaleAdapter(Context context, List<ProductsModel> productsModelList) {
        this.context = context;
        this.productsModelList = productsModelList;
    }

    @NonNull
    @Override
    public MySaleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_my_sale, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MySaleAdapter.ViewHolder viewHolder, int position) {
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
                ConfirmDelete(productsModelList.get(position).getProduct_Id(), position);
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
        private TextView mySale_Update;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.wishlist_ImageView);
            productCompany = itemView.findViewById(R.id.wishlist_Company_tv);
            productName = itemView.findViewById(R.id.wishlist_Name_tv);
            productVersion = itemView.findViewById(R.id.wishlist_Version_tv);
            productYear = itemView.findViewById(R.id.wishlist_Year_tv);
            productPrice = itemView.findViewById(R.id.wishlist_Price_tv);
            mySale_UserName = itemView.findViewById(R.id.wishlist_UserName_tv);
            mySale_Delete = itemView.findViewById(R.id.wishlist_Delete_tv);
            mySale_Update = itemView.findViewById(R.id.mySale_Update_tv);

            mySale_Update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), UpdateProductActivity.class);
                    intent.putExtra("update_product", productsModelList.get(getPosition()));
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
        dialogXoa.setMessage("B???n c?? mu???n x??a tin n??y kh??ng?");
        dialogXoa.setPositiveButton("C??", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                DeleteMySale(id, position);
            }
        });
        dialogXoa.setNegativeButton("Kh??ng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

            }
        });
        dialogXoa.show();
    }

    @SuppressLint("CheckResult")
    private void DeleteMySale(int id, int position){

        APIRequest.DeleteProduct(context,id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonElement -> {
                    JSONObject jsonObject = new JSONObject(jsonElement.toString());
                    String status = jsonObject.getString("status");
                    if(status.equals("success")) {
                        productsModelList.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(context, "X??a th??nh c??ng", Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    Toast.makeText(context, "X??a th???t b???i", Toast.LENGTH_LONG).show();
                });
    }
}
