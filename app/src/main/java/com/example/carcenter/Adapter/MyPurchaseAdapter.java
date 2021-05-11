package com.example.carcenter.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carcenter.Model.PurchaseModel;
import com.example.carcenter.R;

import java.util.List;

public class MyPurchaseAdapter extends RecyclerView.Adapter<MyPurchaseAdapter.ViewHolder> {

    private List<PurchaseModel> purchaseModelList;

    public MyPurchaseAdapter(List<PurchaseModel> purchaseModelList) {
        this.purchaseModelList = purchaseModelList;
    }

    @NonNull
    @Override
    public MyPurchaseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_purchase_item, viewGroup, false);

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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            purchase_title = itemView.findViewById(R.id.myPurchase_title_tv);
            purchase_price_range = itemView.findViewById(R.id.myPurchase_priceRange_tv);
            purchase_content = itemView.findViewById(R.id.myPurchase_content_tv);
            myPurchase_Name = itemView.findViewById(R.id.myPurchase_Name_tv);
            delete_purchase = itemView.findViewById(R.id.myPurchase_Delete_tv);

            delete_purchase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

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
}
