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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carcenter.JavaClass.ProductDetailActivity;
import com.example.carcenter.JavaClass.UpdatePurchaseActivity;
import com.example.carcenter.Model.PurchaseModel;
import com.example.carcenter.Network.APIRequest;
import com.example.carcenter.R;

import org.json.JSONObject;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MyPurchaseAdapter extends RecyclerView.Adapter<MyPurchaseAdapter.ViewHolder> {

    private Context context;
    private List<PurchaseModel> purchaseModelList;

    public MyPurchaseAdapter(Context context, List<PurchaseModel> purchaseModelList) {
        this.context = context;
        this.purchaseModelList = purchaseModelList;
    }

    @NonNull
    @Override
    public MyPurchaseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_my_purchase, viewGroup, false);

        return new MyPurchaseAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPurchaseAdapter.ViewHolder viewHolder, int position) {
        String title = purchaseModelList.get(position).getPurchase_Title();
        String price_range = purchaseModelList.get(position).getPurchase_PriceRange();
        String content = purchaseModelList.get(position).getPurchase_Content();
        String username = purchaseModelList.get(position).getPurchase_UserName();

        viewHolder.setPurchase_title(title);
        viewHolder.setPurchase_price_range(price_range);
        viewHolder.setPurchase_content(content);
        viewHolder.setPurchase_userName(username);

        viewHolder.delete_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmDelete(purchaseModelList.get(position).getPurchase_Id(), position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return purchaseModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView purchase_title;
        private TextView purchase_price_range;
        private TextView purchase_content;
        private TextView myPurchase_Name;
        private TextView delete_purchase;
        private TextView update_purchase;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            purchase_title = itemView.findViewById(R.id.myPurchase_title_tv);
            purchase_price_range = itemView.findViewById(R.id.myPurchase_priceRange_tv);
            purchase_content = itemView.findViewById(R.id.myPurchase_content_tv);
            myPurchase_Name = itemView.findViewById(R.id.myPurchase_Name_tv);
            delete_purchase = itemView.findViewById(R.id.myPurchase_Delete_tv);
            update_purchase = itemView.findViewById(R.id.myPurchase_Update_tv);

            update_purchase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), UpdatePurchaseActivity.class);
                    intent.putExtra("update_purchase", purchaseModelList.get(getPosition()));
                    itemView.getContext().startActivity(intent);
                }
            });
        }

        private void setPurchase_title(String title){
            purchase_title.setText(title);
        }
        private void setPurchase_price_range(String price_range){
            purchase_price_range.setText(price_range);
        }
        private void setPurchase_content(String content){
            purchase_content.setText(content);
        }
        private void setPurchase_userName(String userName){
            myPurchase_Name.setText(userName);
        }

    }

    private void ConfirmDelete(final int id, int position){
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(context);
        dialogXoa.setMessage("Bạn có muốn xóa tin này không?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                String query = "DELETE FROM purchase WHERE purchase_Id ='"+id+"'";
                DeleteMyPurchase(query, position);
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
    private void DeleteMyPurchase(String query, int position){

        APIRequest.UpdateAndDelete(context,query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonElement -> {
                    Log.e("resetpass", jsonElement.toString());
                    JSONObject jsonObject = new JSONObject(jsonElement.toString());
                    String status = jsonObject.getString("status");
                    if(status.equals("success")) {
                        purchaseModelList.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_LONG).show();
                });
    }
}
