package com.example.carcenter.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carcenter.Adapter.UserAdapter;
import com.example.carcenter.JavaClass.PostManagementActivity;
import com.example.carcenter.Model.PurchaseModel;
import com.example.carcenter.Model.UsersModel;
import com.example.carcenter.Network.APIRequest;
import com.example.carcenter.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AccountManagementActivity extends AppCompatActivity {

    private List<UsersModel> usersModelList;
    private UserAdapter userAdapter;
    private RecyclerView mRecyclerView;
    private TextView mUser_number;
    private Toolbar mToolbar;
    private int number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_management);
        if(Build.VERSION.SDK_INT>=22){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(ContextCompat.getColor(AccountManagementActivity.this,R.color.colorGrey));

            innit();
            setToolbar();
            getDataUser();
            getCountUser();

        }
    }

    private void setToolbar(){
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



    @SuppressLint("CheckResult")
    private void getDataUser(){
        usersModelList.clear();
        APIRequest.getUser(getApplicationContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonElement -> {
                    Gson gson = new Gson();
                    ArrayList<UsersModel> usersModels = gson.fromJson(jsonElement.getAsJsonArray(), new TypeToken<ArrayList<UsersModel>>() {
                    }.getType());
                    usersModelList.addAll(usersModels);
                    userAdapter.notifyDataSetChanged();
                }, throwable -> {

                });

    }


    @SuppressLint("CheckResult")
    private void getCountUser(){
        APIRequest.getCountUser(getApplicationContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonElement -> {
                    JSONObject jsonObject = new JSONObject(jsonElement.toString());
                    number = jsonObject.getInt("COUNT(user_Id)");
                    Log.d("kkk", number+"");
                    mUser_number.setText(String.valueOf(number));
                }, throwable -> {

                });
    }

    private UserAdapter.OnItemOnCLick itemOnCLick = new UserAdapter.OnItemOnCLick() {
        @Override
        public void onClick(int id) {
            DialogTrangthai( id);
        }
    };

    private void DialogTrangthai(int user_id){
        Dialog dialog = new Dialog(AccountManagementActivity.this);
        dialog.setContentView(R.layout.dialog_user_type);

        RadioButton vip0_radio = dialog.findViewById(R.id.radio_vip0);
        RadioButton vip1_radio = dialog.findViewById(R.id.radio_vip1);
        RadioButton vip2_radio = dialog.findViewById(R.id.radio_vip2);
        Button btn_done = dialog.findViewById(R.id.btndone);
        Button btn_exit = dialog.findViewById(R.id.btnexit);

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type ="";
                if(vip0_radio.isChecked()){
                    type = "Thường";
                    UpdateUser_Type(type, user_id);
                }
                if(vip1_radio.isChecked()){
                    type = "Vip1";
                    UpdateUser_Type(type, user_id);
                }
                if(vip2_radio.isChecked()){
                    type = "Vip2";
                    UpdateUser_Type(type, user_id);
                }
                dialog.dismiss();
            }
        });
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @SuppressLint("CheckResult")
    private void UpdateUser_Type(String type,  int id ){
        String query = "UPDATE users SET user_Type = '"+type+"' WHERE user_Id ='"+id+"'";
        APIRequest.UpdateAndDelete(getApplicationContext(),query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonElement -> {
                    JSONObject jsonObject = new JSONObject(jsonElement.toString());
                    String status = jsonObject.getString("status");
                    Log.d("status", status);
                    if(status.equals("success")) {
                        getDataUser();
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Nâng cấp không thành công!", Toast.LENGTH_LONG).show();
                });
    }

    private void innit(){
        mRecyclerView = findViewById(R.id.user_recyclerView);
        mUser_number = findViewById(R.id.user_number);
        mToolbar = findViewById(R.id.toolbar_user);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        usersModelList = new ArrayList<>();
        userAdapter = new UserAdapter(usersModelList, itemOnCLick);
        mRecyclerView.setAdapter(userAdapter);
    }
}