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
import android.widget.Toast;

import com.example.carcenter.Network.APIRequest;
import com.example.carcenter.R;

import org.json.JSONObject;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ResetPasswordActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText password_edt;
    private EditText password_new_edt;
    private EditText confirmpassword_new_edt;
    private Button reset_password_btn;

    private SharedPreferences saveSignIn;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);

        EventBus.getDefault().register(this);
        saveSignIn = getSharedPreferences("saveSignIn", Context.MODE_PRIVATE);
        editor = saveSignIn.edit();

        Anhxa();
        CheckButton();
        EventToobar();
        EventButton();

    }

    private void EventToobar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void EventButton(){
        reset_password_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdatePassWord();
            }
        });
    }


    private void CheckButton(){
        password_edt.addTextChangedListener(new TextWatcher() {
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
        password_new_edt.addTextChangedListener(new TextWatcher() {
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
        confirmpassword_new_edt.addTextChangedListener(new TextWatcher() {
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

    @SuppressLint("CheckResult")
    private void UpdatePassWord(){
        String user_password = saveSignIn.getString("user_PassWord", "");
        int user_id = saveSignIn.getInt("user_Id", 0);
        String password_new = password_new_edt.getText().toString();

        if(password_edt.getText().toString().equals(user_password)){
            if(password_new.equals(confirmpassword_new_edt.getText().toString())){
                reset_password_btn.setEnabled(false);
                reset_password_btn.setTextColor(Color.argb(50, 255, 255, 255));

                String query = "UPDATE users SET user_PassWord = '" +password_new+ "' WHERE user_Id ='" +user_id+ "'";

                APIRequest.UpdateAndDelete(getApplication(),query)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(jsonElement -> {
                            Log.e("resetpass", jsonElement.toString());
                            JSONObject jsonObject = new JSONObject(jsonElement.toString());
                            String status = jsonObject.getString("status");
                            if(status.equals("success")) {

                                editor.putString("user_PassWord", password_new);
                                editor.commit();
                                EventBus.getDefault().post(true, "loginSuccess");

                                Toast.makeText(this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                            }
                        }, throwable -> {
                            throwable.printStackTrace();
                            Toast.makeText(this, "Đổi mật khẩu thất bại", Toast.LENGTH_LONG).show();
                        });

            }else {
                confirmpassword_new_edt.setError("Mật khẩu không trùng khớp!");
            }
        }else {
            password_edt.setError("Mật khẩu cũ không đúng!");
        }
    }

    private void CheckInput(){
        if(!TextUtils.isEmpty(password_edt.getText().toString())){
            if (password_new_edt.length()>=8){
                if (!TextUtils.isEmpty(confirmpassword_new_edt.getText().toString())){
                    reset_password_btn.setEnabled(true);
                    reset_password_btn.setTextColor(Color.rgb(255, 255, 255));
                }else {
                    reset_password_btn.setEnabled(false);
                    reset_password_btn.setTextColor(Color.argb(50, 255, 255, 255));
                }
            }else {
                reset_password_btn.setEnabled(false);
                reset_password_btn.setTextColor(Color.argb(50, 255, 255, 255));
            }
        }else {
            reset_password_btn.setEnabled(false);
            reset_password_btn.setTextColor(Color.argb(50, 255, 255, 255));
        }
    }

    private void Anhxa(){
        toolbar = findViewById(R.id.toolbarResetPassWord);
        password_edt = findViewById(R.id.password_edt);
        password_new_edt = findViewById(R.id.password_new_edt);
        confirmpassword_new_edt = findViewById(R.id.confirmpassword_new_edt);
        reset_password_btn = findViewById(R.id.resetpassword_btn);
    }
}
