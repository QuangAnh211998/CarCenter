package com.example.carcenter.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.carcenter.JavaClass.MainActivity;
import com.example.carcenter.JavaClass.PostManagementActivity;
import com.example.carcenter.JavaClass.ResetInformationActivity;
import com.example.carcenter.JavaClass.ResetPasswordActivity;
import com.example.carcenter.JavaClass.RegisterActivity;
import com.example.carcenter.JavaClass.SMSServiceActivity;
import com.example.carcenter.R;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

public class AccountFragment extends Fragment {
    private Button btn_SignIn;
    private Button btn_SignOut;
    private LinearLayout reset_password;
    private LinearLayout reset_infor;
    private LinearLayout post_management;
    private LinearLayout my_wishlist;
    private LinearLayout sms_service;
    private TextView userName_tv;
    private TextView userPhone_tv;
    private TextView numberPost_tv;
    private TextView money_tv;

    private SharedPreferences saveSignIn;
    private SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        btn_SignIn = view.findViewById(R.id.btn_signin_ac);
        btn_SignOut = view.findViewById(R.id.btn_signout_ac);
        reset_password = view.findViewById(R.id.reset_password_layout);
        reset_infor = view.findViewById(R.id.reset_infor_layout);
        post_management = view.findViewById(R.id.post_management_layout);
        my_wishlist = view.findViewById(R.id.my_wishlist_layout);
        sms_service = view.findViewById(R.id.sms_service_layout);
        userName_tv = view.findViewById(R.id.tv_name);
        userPhone_tv = view.findViewById(R.id.tv_phone);

        EventBus.getDefault().register(this);
        saveSignIn = getContext().getSharedPreferences("saveSignIn", Context.MODE_PRIVATE);
        editor = saveSignIn.edit();

        CheckData();
        EventButton();
        EventLinearLayout();
        return view;
    }

    void EventButton(){
        btn_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), RegisterActivity.class));
            }
        });

        btn_SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear();
                editor.commit();
                CheckData();
//                MainActivity.checkid();
            }
        });

    }


    void EventLinearLayout(){
        reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = saveSignIn.getString("user_Email", "");
                if(!TextUtils.isEmpty(email)){
                    startActivity(new Intent(getContext(), ResetPasswordActivity.class));
                }else {
                    startActivity(new Intent(getContext(), RegisterActivity.class));
                }
            }
        });

        reset_infor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = saveSignIn.getString("user_Email", "");
                if(!TextUtils.isEmpty(email)){
                    startActivity(new Intent(getContext(), ResetInformationActivity.class));
                }else {
                    startActivity(new Intent(getContext(), RegisterActivity.class));
                }
            }
        });

        post_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = saveSignIn.getString("user_Email", "");
                if(!TextUtils.isEmpty(email)){
                    startActivity(new Intent(getContext(), PostManagementActivity.class));
                }else {
                    startActivity(new Intent(getContext(), RegisterActivity.class));
                }
            }
        });

        sms_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = saveSignIn.getString("user_Email", "");
                if(!TextUtils.isEmpty(email)){
                    startActivity(new Intent(getContext(), SMSServiceActivity.class));
                }else {
                    startActivity(new Intent(getContext(), RegisterActivity.class));
                }
            }
        });
    }

    @Subscriber(tag = "loginSuccess")
    private void loginSuccess(boolean b) {
        CheckData();
    }

    private void CheckData() {
        String email = saveSignIn.getString("user_Email", "");
        Log.e("acc", email);
        if (!TextUtils.isEmpty(email)) {
            btn_SignOut.setVisibility(View.VISIBLE);
            btn_SignIn.setVisibility(View.GONE);
            userName_tv.setText(saveSignIn.getString("user_Name", ""));
            userPhone_tv.setText(saveSignIn.getString("user_Phone", ""));

        } else {
            btn_SignIn.setVisibility(View.VISIBLE);
            btn_SignOut.setVisibility(View.GONE);
            userName_tv.setText("Họ tên");
            userPhone_tv.setText("Số điện thoại");
        }
    }
}
