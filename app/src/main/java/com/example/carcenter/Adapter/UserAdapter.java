package com.example.carcenter.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carcenter.Admin.AccountManagementActivity;
import com.example.carcenter.Model.UsersModel;
import com.example.carcenter.Network.APIRequest;
import com.example.carcenter.R;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONObject;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<UsersModel> usersModelList;
    private UserAdapter.OnItemOnCLick itemOnLick;

    public UserAdapter(List<UsersModel> usersModelList, OnItemOnCLick itemOnLick) {
        this.usersModelList = usersModelList;
        this.itemOnLick = itemOnLick;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_user, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder viewHolder, int position) {

        String id = String.valueOf(usersModelList.get(position).getUser_Id());
        String type1 = usersModelList.get(position).getUser_Type();
        String email = usersModelList.get(position).getUser_Email();
        String name = usersModelList.get(position).getUser_Name();
        String phone = usersModelList.get(position).getUser_Phone();
        String address = usersModelList.get(position).getUser_Address();
        String date = usersModelList.get(position).getUser_Create_day();

        viewHolder.setmUser_id(id);
        viewHolder.setmUser_type(type1);
        viewHolder.setmUser_email(email);
        viewHolder.setmUser_name(name);
        viewHolder.setmUser_phone(phone);
        viewHolder.setmUser_address(address);
        viewHolder.setmUser_date(date);

        viewHolder.mUser_imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemOnLick.onClick(usersModelList.get(position).getUser_Id());
            }
        });

    }

    @Override
    public int getItemCount() {
        return usersModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mUser_id;
        private TextView mUser_type;
        private TextView mUser_email;
        private TextView mUser_name;
        private TextView mUser_phone;
        private TextView mUser_address;
        private TextView mUser_date;
        private ImageButton mUser_imagebutton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mUser_id = itemView.findViewById(R.id.user_id);
            mUser_type = itemView.findViewById(R.id.user_type);
            mUser_email = itemView.findViewById(R.id.user_email);
            mUser_name = itemView.findViewById(R.id.user_name);
            mUser_phone = itemView.findViewById(R.id.user_phone);
            mUser_address = itemView.findViewById(R.id.user_address);
            mUser_date = itemView.findViewById(R.id.user_date);
            mUser_imagebutton = itemView.findViewById(R.id.imageButton_usertype);
        }
        private void setmUser_id(String id){
            mUser_id.setText("Mã TK: "+id);
        }
        private void setmUser_type(String type){
            mUser_type.setText(type);
        }
        private void setmUser_email(String email){
            mUser_email.setText(email);
        }
        private void setmUser_name(String name){
            mUser_name.setText(name);
        }
        private void setmUser_phone(String phone){
            mUser_phone.setText(phone);
        }
        private void setmUser_address(String address){
            mUser_address.setText(address);
        }
        private void setmUser_date(String date){
            mUser_date.setText(date);
        }
    }
    public interface OnItemOnCLick {
        void onClick(int id);
    }
}
