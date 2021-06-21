package com.example.carcenter.JavaClass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carcenter.Adapter.ProvinceAdapter;
import com.example.carcenter.Adapter.PurchaseManagamentAdapter;
import com.example.carcenter.Custom.BottomSheetPrice;
import com.example.carcenter.Model.ProductsModel;
import com.example.carcenter.Model.ProvinceModel;
import com.example.carcenter.Model.PurchaseModel;
import com.example.carcenter.Network.APIRequest;
import com.example.carcenter.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UpdatePurchaseActivity extends AppCompatActivity {

    private TextView price_range_tv;
    private EditText title_tv;
    private EditText content_tv;
    private Toolbar mToolbar;
    private Button update_btn;

    int purchase_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_purchase);
        if(Build.VERSION.SDK_INT>=22){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(ContextCompat.getColor(UpdatePurchaseActivity.this,R.color.colorGrey));
        }

        init();
        EventToolbar();
        setData();
        EventOnClick();
    }


    private void EventOnClick(){
        price_range_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ProvinceModel> list = new ArrayList<>();
                list.add(new ProvinceModel(1, "Dưới 200 Triệu"));
                list.add(new ProvinceModel(2, "200 - 400 Triệu"));
                list.add(new ProvinceModel(3, "400 - 600 Triệu"));
                list.add(new ProvinceModel(4, "600 - 800 Triệu"));
                list.add(new ProvinceModel(5, "800 - 1 Tỷ"));
                list.add(new ProvinceModel(6, "Trên 1 Tỷ"));
                BottomSheetPrice bottomSheetPrice = new BottomSheetPrice(list, new ProvinceAdapter.OnItemOnCLick() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onClick(String name) {
                        price_range_tv.setText(name);
                    }
                });
                bottomSheetPrice.show(getSupportFragmentManager(), bottomSheetPrice.getTag());
            }
        });

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = title_tv.getText().toString();
                String content = content_tv.getText().toString();
                String price_range = price_range_tv.getText().toString();
                String query = "UPDATE purchase SET purchase_Title = '"+title+"', purchase_PriceRange = '"+price_range+"'," +
                        " purchase_Content = '"+content+"' WHERE purchase_Id = '"+purchase_id+"'";
                UpdatePurchase(query);
            }
        });
    }


    @SuppressLint("CheckResult")
    private void UpdatePurchase(String query){
        if(title_tv.getText().length()>=20){
            if(content_tv.getText().length()>=30){
                update_btn.setEnabled(false);
                update_btn.setTextColor(Color.argb(50, 255, 255, 255));

                APIRequest.UpdateAndDelete(getApplicationContext(),query)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(jsonElement -> {
                            Log.e("resetpass", jsonElement.toString());
                            JSONObject jsonObject = new JSONObject(jsonElement.toString());
                            String status = jsonObject.getString("status");
                            if(status.equals("success")) {
                                Toast.makeText(getApplicationContext(), "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                            }
                        }, throwable -> {
                            throwable.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Cập nhật thất bại!", Toast.LENGTH_LONG).show();
                        });

            }else {
                title_tv.setError("Tiêu đề phải từ 20-80 ký tự ");
            }
        }else {
            content_tv.setError("Nội dung phải từ 30-500 ký tự");
        }
    }


    private void EventToolbar(){
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setData(){
        PurchaseModel purchaseModel = getIntent().getParcelableExtra("update_purchase");
        purchase_id = purchaseModel.getPurchase_Id();

        title_tv.setText(purchaseModel.getPurchase_Title());
        price_range_tv.setText(purchaseModel.getPurchase_PriceRange());
        content_tv.setText(purchaseModel.getPurchase_Content());

    }

    private void init() {
        price_range_tv = findViewById(R.id.update_pricerange_tv);
        title_tv = findViewById(R.id.update_title_edt);
        content_tv = findViewById(R.id.update_content_edt);
        mToolbar = findViewById(R.id.toolbarud);
        update_btn = findViewById(R.id.update_purchase_btn);
    }
}