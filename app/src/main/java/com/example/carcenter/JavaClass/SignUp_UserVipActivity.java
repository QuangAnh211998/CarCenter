package com.example.carcenter.JavaClass;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.example.carcenter.Adapter.ProvinceAdapter;
import com.example.carcenter.Custom.BottomSheetSelected;
import com.example.carcenter.Model.ProvinceModel;
import com.example.carcenter.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SignUp_UserVipActivity extends AppCompatActivity {

    private RadioButton radio_uservip1;
    private RadioButton radio_uservip2;
    private RadioButton radio_uservip3;
    private LinearLayout layout_vip1;
    private LinearLayout layout_vip2;
    private LinearLayout layout_vip3;
    private TextView duration_tv;
    private TextView sumMoney_tv;
    private Button btn_SignUp;
    private Toolbar toolbar;

    DecimalFormat decimalFormat;
    int sumMoney = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_uservip);
        if (Build.VERSION.SDK_INT >= 22) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(ContextCompat.getColor(SignUp_UserVipActivity.this, R.color.colorGrey));
        }

        init();
        EventToobar();
        RadioButton();
        selectDuration();

        radio_uservip1.setChecked(true);
        layout_vip1.setVisibility(View.VISIBLE);
        decimalFormat = new DecimalFormat("###,###,###");
    }


    private void selectDuration() {
        duration_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ProvinceModel> listDuration = new ArrayList<>();
                listDuration.add(new ProvinceModel(1, "1 tháng (tăng 10%)"));
                listDuration.add(new ProvinceModel(2, "3 tháng"));
                listDuration.add(new ProvinceModel(2, "6 tháng (giảm 5%)"));
                listDuration.add(new ProvinceModel(2, "1 năm (giảm 10%)"));
                BottomSheetSelected bottomSheetSelected = new BottomSheetSelected(listDuration, new ProvinceAdapter.OnItemOnCLick() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onClick(String name) {
                        duration_tv.setText(name);
                        calculateMoney();
                    }
                });
                bottomSheetSelected.show(getSupportFragmentManager(), bottomSheetSelected.getTag());
            }
        });
    }

    private void RadioButton() {
        radio_uservip1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_vip1.setVisibility(View.VISIBLE);
                layout_vip2.setVisibility(View.GONE);
                layout_vip3.setVisibility(View.GONE);
                String duration = duration_tv.getText().toString();
                if (duration.equals("1 tháng (tăng 10%)")) {
                    sumMoney = 110000;
                    sumMoney_tv.setText(decimalFormat.format(sumMoney));
                }
                if (duration.equals("3 tháng")) {
                    sumMoney = 300000;
                    sumMoney_tv.setText(decimalFormat.format(sumMoney));
                }
                if (duration.equals("6 tháng (giảm 5%)")) {
                    sumMoney = 570000;
                    sumMoney_tv.setText(decimalFormat.format(sumMoney));
                }
                if (duration.equals("1 năm (giảm 10%)")) {
                    sumMoney = 1080000;
                    sumMoney_tv.setText(decimalFormat.format(sumMoney));
                }
            }
        });

        radio_uservip2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_vip1.setVisibility(View.GONE);
                layout_vip2.setVisibility(View.VISIBLE);
                layout_vip3.setVisibility(View.GONE);
                String duration = duration_tv.getText().toString();
                if (duration.equals("1 tháng (tăng 10%)")) {
                    sumMoney = 220000;
                    sumMoney_tv.setText(decimalFormat.format(sumMoney));
                }
                if (duration.equals("3 tháng")) {
                    sumMoney = 600000;
                    sumMoney_tv.setText(decimalFormat.format(sumMoney));
                }
                if (duration.equals("6 tháng (giảm 5%)")) {
                    sumMoney = 1140000;
                    sumMoney_tv.setText(decimalFormat.format(sumMoney));
                }
                if (duration.equals("1 năm (giảm 10%)")) {
                    sumMoney = 2160000;
                    sumMoney_tv.setText(decimalFormat.format(sumMoney));
                }
            }
        });

        radio_uservip3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_vip1.setVisibility(View.GONE);
                layout_vip2.setVisibility(View.GONE);
                layout_vip3.setVisibility(View.VISIBLE);
                String duration = duration_tv.getText().toString();
                if (duration.equals("1 tháng (tăng 10%)")) {
                    sumMoney = 330000;
                    sumMoney_tv.setText(decimalFormat.format(sumMoney));
                }
                if (duration.equals("3 tháng")) {
                    sumMoney = 900000;
                    sumMoney_tv.setText(decimalFormat.format(sumMoney));
                }
                if (duration.equals("6 tháng (giảm 5%)")) {
                    sumMoney = 1710000;
                    sumMoney_tv.setText(decimalFormat.format(sumMoney));
                }
                if (duration.equals("1 năm (giảm 10%)")) {
                    sumMoney = 3240000;
                    sumMoney_tv.setText(decimalFormat.format(sumMoney));
                }
            }
        });
    }


    private void calculateMoney() {
        String duration = duration_tv.getText().toString();
        if (radio_uservip1.isChecked() && duration.equals("1 tháng (tăng 10%)")) {
            sumMoney = 110000;
            sumMoney_tv.setText(decimalFormat.format(sumMoney));
        }
        if (radio_uservip1.isChecked() && duration.equals("3 tháng")) {
            sumMoney = 300000;
            sumMoney_tv.setText(decimalFormat.format(sumMoney));
        }
        if (radio_uservip1.isChecked() && duration.equals("6 tháng (giảm 5%)")) {
            sumMoney = 570000;
            sumMoney_tv.setText(decimalFormat.format(sumMoney));
        }
        if (radio_uservip1.isChecked() && duration.equals("1 năm (giảm 10%)")) {
            sumMoney = 1080000;
            sumMoney_tv.setText(decimalFormat.format(sumMoney));
        }


        if (radio_uservip2.isChecked() && duration.equals("1 tháng (tăng 10%)")) {
            sumMoney = 220000;
            sumMoney_tv.setText(decimalFormat.format(sumMoney));
        }
        if (radio_uservip2.isChecked() && duration.equals("3 tháng")) {
            sumMoney = 600000;
            sumMoney_tv.setText(decimalFormat.format(sumMoney));
        }
        if (radio_uservip2.isChecked() && duration.equals("6 tháng (giảm 5%)")) {
            sumMoney = 1140000;
            sumMoney_tv.setText(decimalFormat.format(sumMoney));
        }
        if (radio_uservip2.isChecked() && duration.equals("1 năm (giảm 10%)")) {
            sumMoney = 2160000;
            sumMoney_tv.setText(decimalFormat.format(sumMoney));
        }


        if (radio_uservip3.isChecked() && duration.equals("1 tháng (tăng 10%)")) {
            sumMoney = 330000;
            sumMoney_tv.setText(decimalFormat.format(sumMoney));
        }
        if (radio_uservip3.isChecked() && duration.equals("3 tháng")) {
            sumMoney = 900000;
            sumMoney_tv.setText(decimalFormat.format(sumMoney));
        }
        if (radio_uservip3.isChecked() && duration.equals("6 tháng (giảm 5%)")) {
            sumMoney = 1710000;
            sumMoney_tv.setText(decimalFormat.format(sumMoney));
        }
        if (radio_uservip3.isChecked() && duration.equals("1 năm (giảm 10%)")) {
            sumMoney = 3240000;
            sumMoney_tv.setText(decimalFormat.format(sumMoney));
        }
    }


    private void EventToobar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void init() {
        radio_uservip1 = findViewById(R.id.radio_uservip1);
        radio_uservip2 = findViewById(R.id.radio_uservip2);
        radio_uservip3 = findViewById(R.id.radio_uservip3);
        layout_vip1 = findViewById(R.id.layout_vip1);
        layout_vip2 = findViewById(R.id.layout_vip2);
        layout_vip3 = findViewById(R.id.layout_vip3);
        duration_tv = findViewById(R.id.duration_tv);
        sumMoney_tv = findViewById(R.id.sumMoney_tv);
        btn_SignUp = findViewById(R.id.btn_SignUp_Uservip);
        toolbar = findViewById(R.id.toolbarUservip);
    }
}