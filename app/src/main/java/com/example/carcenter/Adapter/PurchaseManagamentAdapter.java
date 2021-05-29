package com.example.carcenter.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carcenter.Model.PurchaseModel;
import com.example.carcenter.R;

import java.util.List;

public class PurchaseManagamentAdapter extends RecyclerView.Adapter<PurchaseManagamentAdapter.ViewHolder> {

    private List<PurchaseModel> purchaseModelList;
    private PurchaseManagamentAdapter.OnItemOnCLick itemOnLick;

    public PurchaseManagamentAdapter(List<PurchaseModel> purchaseModelList, PurchaseManagamentAdapter.OnItemOnCLick itemOnLick) {
        this.purchaseModelList = purchaseModelList;
        this.itemOnLick = itemOnLick;
    }

    @NonNull
    @Override
    public PurchaseManagamentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_purchase_manage, viewGroup, false);

        return new PurchaseManagamentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseManagamentAdapter.ViewHolder viewHolder, int position) {
        String id = String.valueOf(purchaseModelList.get(position).getPurchase_Id());
        String title = purchaseModelList.get(position).getPurchase_Title();
        String price_range = purchaseModelList.get(position).getPurchase_PriceRange();
        String content = purchaseModelList.get(position).getPurchase_Content();
        String username = purchaseModelList.get(position).getPurchase_UserName();
        String approval = purchaseModelList.get(position).getPurchase_PostApproval();

        viewHolder.setPurchase_id(id);
        viewHolder.setPurchase_title(title);
        viewHolder.setPurchase_price_range(price_range);
        viewHolder.setPurchase_content(content);
        viewHolder.setPurchase_userName(username);
        viewHolder.setPurchase_PostApproval(approval);

        viewHolder.checkBox_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewHolder.checkBox_purchase.isChecked()){
                    String status = "Đã duyệt";
                    itemOnLick.onClick(status,purchaseModelList.get(position).getPurchase_Id());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return purchaseModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView purchase_id;
        private TextView purchase_title;
        private TextView purchase_price_range;
        private TextView purchase_content;
        private TextView purchase_userName;
        private TextView purchase_PostApproval;
        private CheckBox checkBox_purchase;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            purchase_id = itemView.findViewById(R.id.purchase_id);
            purchase_title = itemView.findViewById(R.id.purchase_title_tv);
            purchase_price_range = itemView.findViewById(R.id.purchase_priceRange_tv);
            purchase_content = itemView.findViewById(R.id.purchase_content_tv);
            purchase_userName = itemView.findViewById(R.id.purchase_Name_tv);
            purchase_PostApproval = itemView.findViewById(R.id.purchase_status);
            checkBox_purchase = itemView.findViewById(R.id.checkbox_purchse);
        }
        private void setPurchase_id(String id){
            purchase_id.setText("Mã: "+id);
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
            purchase_userName.setText(userName);
        }
        private void setPurchase_PostApproval(String approval){
            purchase_PostApproval.setText(approval);
        }
    }

    public interface OnItemOnCLick {
        void onClick(String stutus, int id);
    }
}

