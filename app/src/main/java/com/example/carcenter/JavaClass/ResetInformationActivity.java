package com.example.carcenter.JavaClass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carcenter.Network.APIRequest;
import com.example.carcenter.R;

import org.json.JSONObject;
import org.simple.eventbus.EventBus;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ResetInformationActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView user_livingArea_edt;
    private EditText user_name_edt;
    private EditText user_phone_edt;
    private EditText user_address_edt;
    private TextView user_email_tv;
    private Button reset_infor_btn;

    private SharedPreferences saveSignIn;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetinformation);

        EventBus.getDefault().register(this);
        saveSignIn = getSharedPreferences("saveSignIn", Context.MODE_PRIVATE);
        editor = saveSignIn.edit();


        Anhxa();
        EventToolbar();
        getData();
        CheckButton();
        EventButton();

    }

    private void EventButton(){
        reset_infor_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetInfor();
            }
        });
    }

    private void  EventToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @SuppressLint("CheckResult")
    private void ResetInfor(){

        int id = saveSignIn.getInt("user_Id", 0);
        String name = user_name_edt.getText().toString();
        String phone = user_phone_edt.getText().toString();
        String livingArea = user_livingArea_edt.getText().toString();
        String address = user_address_edt.getText().toString();

        if (phone.length() ==10){
            reset_infor_btn.setEnabled(false);
            reset_infor_btn.setTextColor(Color.argb(50, 255, 255, 255));

            final String query = "UPDATE users SET user_Name = '"+name+"', user_Phone = '"+phone+"'," +
                    " user_LivingArea = '"+livingArea+"', user_Address = '"+address+"' WHERE user_Id = '"+id+"'";

            APIRequest.UpdateAndDelete(getApplication(),query)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(jsonElement -> {
                        Log.e("resetinfor", jsonElement.toString());
                        JSONObject jsonObject = new JSONObject(jsonElement.toString());
                        String status = jsonObject.getString("status");
                        if(status.equals("success")) {

                            editor.putString("user_Name", name);
                            editor.putString("user_Phone", phone);
                            editor.putString("user_LivingArea", livingArea);
                            editor.putString("user_Address", address);
                            editor.commit();
                            EventBus.getDefault().post(true, "loginSuccess");

                            Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        }
                    }, throwable -> {
                        throwable.printStackTrace();
                        Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_LONG).show();
                    });
        }else {
            user_phone_edt.setError("phải là loại 10 số!");
        }

    }

    private void CheckButton(){
        user_name_edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                CheckInput();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        user_phone_edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                CheckInput();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        user_livingArea_edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                CheckInput();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        user_address_edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                CheckInput();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void CheckInput(){
        if(!TextUtils.isEmpty(user_name_edt.getText())){
            if (!TextUtils.isEmpty(user_phone_edt.getText().toString())){
                if (!TextUtils.isEmpty(user_livingArea_edt.getText())){
                    if(!TextUtils.isEmpty(user_address_edt.getText())){
                        reset_infor_btn.setEnabled(true);
                        reset_infor_btn.setTextColor(Color.rgb(255, 255, 255));
                    }else {
                        reset_infor_btn.setEnabled(false);
                        reset_infor_btn.setTextColor(Color.argb(50, 255, 255, 255));
                    }
                }else {
                    reset_infor_btn.setEnabled(false);
                    reset_infor_btn.setTextColor(Color.argb(50, 255, 255, 255));
                }
            }else {
                reset_infor_btn.setEnabled(false);
                reset_infor_btn.setTextColor(Color.argb(50, 255, 255, 255));
            }
        }else {
            reset_infor_btn.setEnabled(false);
            reset_infor_btn.setTextColor(Color.argb(50, 255, 255, 255));
        }
    }

    private void getData(){
        String email = saveSignIn.getString("user_Email", "");
        String name = saveSignIn.getString("user_Name", "");
        String phone = saveSignIn.getString("user_Phone", "");
        String living = saveSignIn.getString("user_LivingArea", "");
        String address = saveSignIn.getString("user_Address", "");

        user_email_tv.setText(email);
        user_name_edt.setText(name);
        user_phone_edt.setText(phone);
        user_livingArea_edt.setText(living);
        user_address_edt.setText(address);

    }

    private void Anhxa(){
        toolbar = findViewById(R.id.toolbarResetUser);
        user_livingArea_edt = findViewById(R.id.infor_livingarea_edt);
        user_name_edt = findViewById(R.id.infor_name_edt);
        user_phone_edt = findViewById(R.id.infor_phone_edt);
        user_address_edt = findViewById(R.id.infor_address_edt);
        user_email_tv = findViewById(R.id.infor_email_tv);
        reset_infor_btn = findViewById(R.id.reset_infor_btn);
    }
}
