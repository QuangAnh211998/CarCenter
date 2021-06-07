package com.example.carcenter.JavaClass;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.carcenter.R;

public class SignUp_UserVipActivity extends AppCompatActivity {

    private RadioButton radio_uservip1;
    private RadioButton radio_uservip2;
    private RadioButton radio_uservip3;
    private LinearLayout layout_vip1;
    private LinearLayout layout_vip2;
    private LinearLayout layout_vip3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_uservip);
        if(Build.VERSION.SDK_INT>=22){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(ContextCompat.getColor(SignUp_UserVipActivity.this,R.color.colorGrey));
        }

        init();
        RadioButton();
    }


    private void RadioButton(){
        radio_uservip1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radio_uservip1.isChecked()){
                    layout_vip1.setVisibility(View.VISIBLE);
                    layout_vip2.setVisibility(View.GONE);
                    layout_vip3.setVisibility(View.GONE);
                }
            }
        });

        radio_uservip2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radio_uservip2.isChecked()) {
                    layout_vip1.setVisibility(View.GONE);
                    layout_vip2.setVisibility(View.VISIBLE);
                    layout_vip3.setVisibility(View.GONE);
                }
            }
        });

        radio_uservip3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radio_uservip3.isChecked()){
                    layout_vip1.setVisibility(View.GONE);
                    layout_vip2.setVisibility(View.GONE);
                    layout_vip3.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    private void init(){
        radio_uservip1 = findViewById(R.id.radio_uservip1);
        radio_uservip2 = findViewById(R.id.radio_uservip2);
        radio_uservip3 = findViewById(R.id.radio_uservip3);
        layout_vip1 = findViewById(R.id.layout_vip1);
        layout_vip2 = findViewById(R.id.layout_vip2);
        layout_vip3 = findViewById(R.id.layout_vip3);
    }
}