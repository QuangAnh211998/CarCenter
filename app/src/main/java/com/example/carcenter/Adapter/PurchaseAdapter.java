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

public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.ViewHolder> {

    private List<PurchaseModel> purchaseModelList;

    public PurchaseAdapter(List<PurchaseModel> purchaseModelList) {
        this.purchaseModelList = purchaseModelList;
    }

    @NonNull
    @Override
    public PurchaseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.products_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseAdapter.ViewHolder viewHolder, int position) {
        String title = purchaseModelList.get(position).getPurchase_title();
        String price_range = purchaseModelList.get(position).getPurchase_price_range();
        String content = purchaseModelList.get(position).getPurchase_content();
        String username = purchaseModelList.get(position).getPurchase_userName();
        String userphone = purchaseModelList.get(position).getPurchase_userPhone();
        String useraddress = purchaseModelList.get(position).getPurchase_userAddress();

        viewHolder.setPurchase_title(title);
        viewHolder.setPurchase_price_range(price_range);
        viewHolder.setPurchase_content(content);
        viewHolder.setPurchase_userName(username);
        viewHolder.setPurchase_userPhone(userphone);
        viewHolder.setPurchase_userAddress(useraddress);
    }

    @Override
    public int getItemCount() {
        return purchaseModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView purchase_title;
        private TextView purchase_price_range;
        private TextView purchase_content;
        private TextView purchase_userName;
        private TextView purchase_userPhone;
        private TextView purchase_userAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            purchase_title = itemView.findViewById(R.id.purchase_title_tv);
            purchase_price_range = itemView.findViewById(R.id.purchase_priceRange_tv);
            purchase_content = itemView.findViewById(R.id.purchase_content_tv);
            purchase_userName = itemView.findViewById(R.id.purchase_userName_tv);
            purchase_userPhone = itemView.findViewById(R.id.purchase_userPhone_tv);
            purchase_userAddress = itemView.findViewById(R.id.purchase_userAddress_tv);
        }

        private void setPurchase_title(String title){
            purchase_title.setText(title);
        }
        private void setPurchase_price_range(String price_range){
            purchase_title.setText(price_range);
        }
        private void setPurchase_content(String content){
            purchase_title.setText(content);
        }
        private void setPurchase_userName(String userName){
            purchase_title.setText(userName);
        }
        private void setPurchase_userPhone(String userPhone){
            purchase_title.setText(userPhone);
        }
        private void setPurchase_userAddress(String userAddress){
            purchase_title.setText(userAddress);
        }
    }
}
