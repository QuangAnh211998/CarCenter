package com.example.carcenter.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carcenter.Model.PurchaseModel;
import com.example.carcenter.R;

import java.util.List;

public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.ViewHolder> {

    private Context context;
    private List<PurchaseModel> purchaseModelList;

    public PurchaseAdapter(Context context, List<PurchaseModel> purchaseModelList) {
        this.context = context;
        this.purchaseModelList = purchaseModelList;
    }

    @NonNull
    @Override
    public PurchaseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_purchase, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseAdapter.ViewHolder viewHolder, int position) {
        String title = purchaseModelList.get(position).getPurchase_Title();
        String price_range = purchaseModelList.get(position).getPurchase_PriceRange();
        String content = purchaseModelList.get(position).getPurchase_Content();
        String username = purchaseModelList.get(position).getPurchase_UserName();
        String userphone = purchaseModelList.get(position).getPurchase_UserPhone();
        String useraddress = purchaseModelList.get(position).getPurchase_UserAddress();

        viewHolder.setPurchase_title(title);
        viewHolder.setPurchase_price_range(price_range);
        viewHolder.setPurchase_content(content);
        viewHolder.setPurchase_userName(username);
        viewHolder.setPurchase_userPhone(userphone);
        viewHolder.setPurchase_userAddress(useraddress);

        viewHolder.purchase_userPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogCallPhone(userphone);
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
        private TextView purchase_userName;
        private TextView purchase_userPhone;
        private TextView purchase_userAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            purchase_title = itemView.findViewById(R.id.purchase_title_tv);
            purchase_price_range = itemView.findViewById(R.id.purchase_priceRange_tv);
            purchase_content = itemView.findViewById(R.id.purchase_content_tv);
            purchase_userName = itemView.findViewById(R.id.purchase_Name_tv);
            purchase_userPhone = itemView.findViewById(R.id.purchase_userPhone_tv);
            purchase_userAddress = itemView.findViewById(R.id.purchase_userAddress_tv);
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
        private void setPurchase_userPhone(String userPhone){
            purchase_userPhone.setText(userPhone);
        }
        private void setPurchase_userAddress(String userAddress){
            purchase_userAddress.setText(userAddress);
        }
    }

    private void DialogCallPhone(String phone){
        Dialog callDialog = new Dialog(context);
        callDialog.setContentView(R.layout.dialog_callphone);
        callDialog.setCancelable(true);
        callDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button callDialog_btn = callDialog.findViewById(R.id.call_dialog_btn);
        Button exitDialog_btn = callDialog.findViewById(R.id.exit_dialog_btn);
        TextView phone_dialog = callDialog.findViewById(R.id.dialog_phone);
        phone_dialog.setText(phone);
        callDialog_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDialog.dismiss();
                Intent call_intent = new Intent(Intent.ACTION_CALL);
                call_intent.setData(Uri.parse("tel:"+phone));
                context.startActivity(call_intent);
            }
        });

        exitDialog_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDialog.dismiss();
            }
        });
        callDialog.show();
    }
}
