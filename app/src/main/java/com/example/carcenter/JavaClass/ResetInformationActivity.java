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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carcenter.Adapter.ProvinceAdapter;
import com.example.carcenter.Custom.BottomSheetProvince;
import com.example.carcenter.Model.ProvinceModel;
import com.example.carcenter.Network.APIRequest;
import com.example.carcenter.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;
import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ResetInformationActivity extends AppCompatActivity {

    private List<ProvinceModel> provinceModelList;

    private Toolbar toolbar;
    private TextView user_livingArea_tv;
    private EditText user_name_edt;
    private EditText user_phone_edt;
    private EditText user_address_edt;
    private TextView user_email_tv;
    private Button reset_infor_btn;

    private SharedPreferences saveSignIn;
    private SharedPreferences.Editor editor;

    private String phonePattern = "[0]+[0-9&&[^01246]]+[0-9]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetinformation);
        if(Build.VERSION.SDK_INT>=22){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(ContextCompat.getColor(ResetInformationActivity.this,R.color.colorGrey));
        }

        EventBus.getDefault().register(this);
        saveSignIn = getSharedPreferences("saveSignIn", Context.MODE_PRIVATE);
        editor = saveSignIn.edit();

        provinceModelList = new ArrayList<>();

        Innit();
        EventToolbar();
        getData();
        getDataProvince();
        ShowBottomSheet();
        CheckButton();
        EventButton();

    }


    ////// ch???n buttom ????ng k??
    private void EventButton(){
        reset_infor_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetInfor();
            }
        });
    }



    ////// show bottom sheet
    private void ShowBottomSheet(){
        user_livingArea_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetProvince bottomSheetProvince = new BottomSheetProvince(provinceModelList, new ProvinceAdapter.OnItemOnCLick() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onClick(String name) {
                        user_livingArea_tv.setText(name);
                    }
                });
                bottomSheetProvince.show(getSupportFragmentManager(), bottomSheetProvince.getTag());
            }
        });
    }


    ////// get Province
    @SuppressLint("CheckResult")
    private void getDataProvince(){
        APIRequest.getProvince(getApplicationContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonElement -> {
                    Log.e("province",jsonElement.toString());
                    Gson gson = new Gson();
                    ArrayList<ProvinceModel> provinceModels = gson.fromJson(jsonElement.getAsJsonArray(),
                            new TypeToken<ArrayList<ProvinceModel>>(){}.getType());
                    provinceModelList.addAll(provinceModels);
                }, throwable -> {

                });
    }


    ////// s??? ki???n toolbar
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



    ////// th???c hi???n ????ng k??
    @SuppressLint("CheckResult")
    private void ResetInfor(){

        int id = saveSignIn.getInt("user_Id", 0);
        String name = user_name_edt.getText().toString();
        String phone = user_phone_edt.getText().toString();
        String livingArea = user_livingArea_tv.getText().toString();
        String address = user_address_edt.getText().toString();

        if (phone.length() ==10 && phone.matches(phonePattern)){
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

                            Toast.makeText(this, "C???p nh???t th??nh c??ng", Toast.LENGTH_SHORT).show();
                        }
                    }, throwable -> {
                        throwable.printStackTrace();
                        Toast.makeText(this, "C???p nh???t th???t b???i", Toast.LENGTH_LONG).show();
                    });
        }else {
            user_phone_edt.setError("S??T kh??ng h???p l???!");
        }

    }


    ////// check hi???n th??? buttom ????ng k??
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
        user_livingArea_tv.addTextChangedListener(new TextWatcher() {
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


    ////// b???t ??i???u ki???n hi???n th??? buttom ????ng k??
    private void CheckInput(){
        if(!TextUtils.isEmpty(user_name_edt.getText())){
            if (!TextUtils.isEmpty(user_phone_edt.getText().toString())){
                if (!TextUtils.isEmpty(user_livingArea_tv.getText())){
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


    ////// get d??? li???u user v?? ????? l??n giao di???n
    private void getData(){
        String email = saveSignIn.getString("user_Email", "");
        String name = saveSignIn.getString("user_Name", "");
        String phone = saveSignIn.getString("user_Phone", "");
        String living = saveSignIn.getString("user_LivingArea", "");
        String address = saveSignIn.getString("user_Address", "");

        user_email_tv.setText(email);
        user_name_edt.setText(name);
        user_phone_edt.setText(phone);
        user_livingArea_tv.setText(living);
        user_address_edt.setText(address);

    }



    ////// ??nh x???
    private void Innit(){
        toolbar = findViewById(R.id.toolbarResetUser);
        user_livingArea_tv = findViewById(R.id.infor_livingarea_tv);
        user_name_edt = findViewById(R.id.infor_name_edt);
        user_phone_edt = findViewById(R.id.infor_phone_edt);
        user_address_edt = findViewById(R.id.infor_address_edt);
        user_email_tv = findViewById(R.id.infor_email_tv);
        reset_infor_btn = findViewById(R.id.reset_infor_btn);
    }
}
