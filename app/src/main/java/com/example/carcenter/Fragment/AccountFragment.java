package com.example.carcenter.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.carcenter.Admin.AccountManagementActivity;
import com.example.carcenter.JavaClass.CovenientServiceActivity;
import com.example.carcenter.JavaClass.MainActivity;
import com.example.carcenter.JavaClass.MyWishlistActivity;
import com.example.carcenter.JavaClass.PostManagementActivity;
import com.example.carcenter.Admin.PostManagement_Admin_Activity;
import com.example.carcenter.JavaClass.ResetInformationActivity;
import com.example.carcenter.JavaClass.ResetPasswordActivity;
import com.example.carcenter.JavaClass.TransactionHistoryActivity;
import com.example.carcenter.Register.RegisterActivity;
import com.example.carcenter.JavaClass.SignUp_UserVipActivity;
import com.example.carcenter.R;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import static com.example.carcenter.Register.RegisterActivity.setSignUpFragment;

public class AccountFragment extends Fragment {
    private Button btn_SignIn;
    private Button btn_SignOut;
    private LinearLayout reset_password;
    private LinearLayout reset_infor;
    private LinearLayout post_management;
    private LinearLayout my_wishlist;
    private LinearLayout signup_vip;
    private LinearLayout post_management_admin;
    private LinearLayout account_management;
    private LinearLayout service_layout;
    private LinearLayout transaction_history_layout;
    private TextView userName_tv;
    private TextView userId_tv;
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
        signup_vip = view.findViewById(R.id.signup_vip_layout);
        post_management_admin = view.findViewById(R.id.postmanagement_admin_layout);
        account_management = view.findViewById(R.id.account_management_layout);
        userName_tv = view.findViewById(R.id.tv_name);
        userId_tv = view.findViewById(R.id.tv_id);
        money_tv = view.findViewById(R.id.tv_money);
        numberPost_tv = view.findViewById(R.id.tv_numberpost);
        service_layout = view.findViewById(R.id.service_layout);
        transaction_history_layout = view.findViewById(R.id.transaction_history_layout);

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
                MainActivity.CheckAccount();
                EventLinearLayout();
            }
        });

    }


    void EventLinearLayout(){
        String email = saveSignIn.getString("user_Email", "");
        reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(email)){
                    startActivity(new Intent(getContext(), ResetPasswordActivity.class));
                }else {
                    DialogSignIn();
                }
            }
        });

        reset_infor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(email)){
                    startActivity(new Intent(getContext(), ResetInformationActivity.class));
                }else {
                    DialogSignIn();
                }
            }
        });

        post_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(email)){
                    startActivity(new Intent(getContext(), PostManagementActivity.class));
                }else {
                    DialogSignIn();
                }
            }
        });

        my_wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(email)){
                    startActivity(new Intent(getContext(), MyWishlistActivity.class));
                }else {
                    DialogSignIn();
                }
            }
        });

        service_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(email)){
                    startActivity(new Intent(getContext(), CovenientServiceActivity.class));
                }else {
                    DialogSignIn();
                }
            }
        });

        transaction_history_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(email)){
                    startActivity(new Intent(getContext(), TransactionHistoryActivity.class));
                }else {
                    DialogSignIn();
                }
            }
        });

        signup_vip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(email)){
                    startActivity(new Intent(getContext(), SignUp_UserVipActivity.class));
                }else {
                    DialogSignIn();
                }
            }
        });

        post_management_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), PostManagement_Admin_Activity.class));
            }
        });

        account_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AccountManagementActivity.class));
            }
        });
    }

    @Subscriber(tag = "loginSuccess")
    private void loginSuccess(boolean b) {
        CheckData();
        EventLinearLayout();
        EventButton();
    }

    private void CheckData() {
        String email = saveSignIn.getString("user_Email", "");
        String type = saveSignIn.getString("user_Type", "");
        int id = saveSignIn.getInt("user_Id", -1);

        if (!TextUtils.isEmpty(email)) {
            btn_SignOut.setVisibility(View.VISIBLE);
            btn_SignIn.setVisibility(View.GONE);
            userName_tv.setText(saveSignIn.getString("user_Name", ""));
            userId_tv.setText("Mã tài khoản: " + id);
            money_tv.setText("0");
            if(type.equals("Admin")){
                account_management.setVisibility(View.VISIBLE);
                post_management_admin.setVisibility(View.VISIBLE);
                post_management.setVisibility(View.GONE);
                reset_password.setVisibility(View.GONE);
                reset_infor.setVisibility(View.GONE);
                my_wishlist.setVisibility(View.GONE);
                numberPost_tv.setVisibility(View.GONE);
            }else {
                account_management.setVisibility(View.GONE);
                post_management_admin.setVisibility(View.GONE);
            }

        } else {
            btn_SignIn.setVisibility(View.VISIBLE);
            btn_SignOut.setVisibility(View.GONE);
            account_management.setVisibility(View.GONE);
            post_management_admin.setVisibility(View.GONE);
            post_management.setVisibility(View.VISIBLE);
            reset_password.setVisibility(View.VISIBLE);
            reset_infor.setVisibility(View.VISIBLE);
            my_wishlist.setVisibility(View.VISIBLE);
            numberPost_tv.setVisibility(View.VISIBLE);
            userName_tv.setText("Họ tên");
            userId_tv.setText("Mã tài khoản");
            money_tv.setText("");
        }
    }

    private void DialogSignIn(){
        Dialog signInDialog = new Dialog(getContext());
        signInDialog.setContentView(R.layout.dialog_signin);
        signInDialog.setCancelable(true);
        signInDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button signIn_btn = signInDialog.findViewById(R.id.signin_dialog_btn);
        Button signUp_btn = signInDialog.findViewById(R.id.signup_dialog_btn);
        Intent registerIntent = new Intent(getContext(), RegisterActivity.class);
        signIn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInDialog.dismiss();
                setSignUpFragment = false;
                startActivity(registerIntent);
            }
        });

        signUp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInDialog.dismiss();
                setSignUpFragment = true;
                startActivity(registerIntent);
            }
        });
        signInDialog.show();
    }
}
