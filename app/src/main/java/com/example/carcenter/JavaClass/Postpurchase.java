package com.example.carcenter.JavaClass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carcenter.Network.APIRequest;
import com.example.carcenter.R;

import org.json.JSONObject;
import org.simple.eventbus.EventBus;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Postpurchase extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView postpurchase_price_range;
    private EditText postpurchase_content;
    private EditText postpurchase_title;
    private Button postpurchase_btn;
    private Spinner spinner_price;

    private SharedPreferences saveSignIn;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postpurchase);

        if(Build.VERSION.SDK_INT>=22){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(ContextCompat.getColor(Postpurchase.this,R.color.colorGrey));
        }

        EventBus.getDefault().register(this);
        saveSignIn = getSharedPreferences("saveSignIn", Context.MODE_PRIVATE);
        editor = saveSignIn.edit();


        Anhxa();
        CheckButton();
        Eventclick();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }



    //////
    void Eventclick(){

        String[] listprice = new String[]{"Dưới 200 Triệu","200 - 400 Triệu","400 - 600 Triệu","600 - 800 Triệu","800 - 1 Tỷ","Trên 1 Tỷ"};

        final ArrayAdapter<String> arrprice = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listprice);
        spinner_price.setAdapter(arrprice);
        spinner_price.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                postpurchase_price_range.setText(arrprice.getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        postpurchase_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              PostPurchase();
            }
        });
    }

    @SuppressLint("CheckResult")
    private void PostPurchase(){
        String title = postpurchase_title.getText().toString();
        String content = postpurchase_content.getText().toString();
        String price = postpurchase_price_range.getText().toString();
        String name = saveSignIn.getString("user_Name", "");
        String phone = saveSignIn.getString("user_Phone", "");
        String address = saveSignIn.getString("user_Address", "");
        int user_id = saveSignIn.getInt("user_Id", -1);

        if(postpurchase_title.length()>=20){
            if(postpurchase_content.length()>=30){
                postpurchase_btn.setEnabled(false);
                postpurchase_btn.setTextColor(Color.argb(50, 255, 255, 255));

                APIRequest.PostPurchase(getApplication(),title, price, content, name, phone, address, user_id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(jsonElement -> {
                            Log.e("purchase", jsonElement.toString());
                            JSONObject jsonObject = new JSONObject(jsonElement.toString());
                            String status = jsonObject.getString("status");
                            if(status.equals("success")) {
                                Toast.makeText(this, "Đăng bài thành công", Toast.LENGTH_SHORT).show();
                            }
                        }, throwable -> {
                            throwable.printStackTrace();
                            Toast.makeText(this, "Đăng bài thất bại", Toast.LENGTH_LONG).show();
                        });

            }else {
                postpurchase_title.setError("Tiêu đề phải từ 20-80 ký tự ");
            }
        }else {
            postpurchase_content.setError("Nội dung phải từ 30-500 ký tự");
        }
    }

    private void CheckButton(){
        postpurchase_price_range.addTextChangedListener(new TextWatcher() {
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
        postpurchase_title.addTextChangedListener(new TextWatcher() {
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
        postpurchase_content.addTextChangedListener(new TextWatcher() {
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
        if(!TextUtils.isEmpty(postpurchase_price_range.getText())){
            if (!TextUtils.isEmpty(postpurchase_title.getText().toString())){
                if (!TextUtils.isEmpty(postpurchase_content.getText())){
                        postpurchase_btn.setEnabled(true);
                        postpurchase_btn.setTextColor(Color.rgb(255, 255, 255));
                }else {
                    postpurchase_btn.setEnabled(false);
                    postpurchase_btn.setTextColor(Color.argb(50, 255, 255, 255));
                }
            }else {
                postpurchase_btn.setEnabled(false);
                postpurchase_btn.setTextColor(Color.argb(50, 255, 255, 255));
            }
        }else {
            postpurchase_btn.setEnabled(false);
            postpurchase_btn.setTextColor(Color.argb(50, 255, 255, 255));
        }
    }


    void Anhxa(){
        toolbar = findViewById(R.id.toolbarPostPurchase);
        postpurchase_price_range = findViewById(R.id.price_range_tv);
        postpurchase_title = findViewById(R.id.purchase_title_edt);
        postpurchase_content = findViewById(R.id.purchase_content_edt);
        postpurchase_btn = findViewById(R.id.postpurchase_btn);
        spinner_price = findViewById(R.id.spinner_price);
    }
}
