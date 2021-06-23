package com.example.carcenter.JavaClass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carcenter.Adapter.ProvinceAdapter;
import com.example.carcenter.Custom.BottomSheetSelected;
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

public class UpdateProductActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TextView company_tv;
    private TextView name_tv;
    private TextView year_tv;
    private TextView type_tv;
    private TextView fuel_tv;
    private TextView madein_tv;
    private EditText version_edt;
    private EditText kmwent_edt;
    private EditText price_edt;
    private EditText consume_edt;
    private EditText content_edt;
    private Button updateProduct_btn;

    int product_Id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);
        if (Build.VERSION.SDK_INT >= 22) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(ContextCompat.getColor(UpdateProductActivity.this, R.color.colorGrey));
        }

        init();
        EventToolbar();
        setData();
        EventOnclick();
    }


    private void EventOnclick() {
        year_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ProvinceModel> list = new ArrayList<>();
                list.add(new ProvinceModel(1, "2021"));
                list.add(new ProvinceModel(2, "2020"));
                list.add(new ProvinceModel(3, "2019"));
                list.add(new ProvinceModel(4, "2018"));
                list.add(new ProvinceModel(5, "2017"));
                list.add(new ProvinceModel(6, "2016"));
                list.add(new ProvinceModel(7, "2015"));
                list.add(new ProvinceModel(8, "2014"));
                list.add(new ProvinceModel(9, "2013"));
                list.add(new ProvinceModel(10, "2012"));
                list.add(new ProvinceModel(11, "2011"));
                list.add(new ProvinceModel(12, "2010"));
                list.add(new ProvinceModel(13, "2009"));
                list.add(new ProvinceModel(14, "2008"));
                list.add(new ProvinceModel(15, "2007"));
                list.add(new ProvinceModel(16, "2006"));
                list.add(new ProvinceModel(17, "2005"));
                list.add(new ProvinceModel(18, "2004"));
                list.add(new ProvinceModel(18, "2004"));
                list.add(new ProvinceModel(19, "2003"));
                list.add(new ProvinceModel(20, "2002"));
                list.add(new ProvinceModel(21, "2001"));
                list.add(new ProvinceModel(22, "2000"));
                BottomSheetSelected bottomSheetSelected = new BottomSheetSelected(list, new ProvinceAdapter.OnItemOnCLick() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onClick(String name) {
                        year_tv.setText(name);
                    }
                });
                bottomSheetSelected.show(getSupportFragmentManager(), bottomSheetSelected.getTag());
            }
        });

        madein_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ProvinceModel> listMadein = new ArrayList<>();
                listMadein.add(new ProvinceModel(1, "Trong nước"));
                listMadein.add(new ProvinceModel(2, "Nhập khẩu"));
                BottomSheetSelected bottomSheetSelected = new BottomSheetSelected(listMadein, new ProvinceAdapter.OnItemOnCLick() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onClick(String name) {
                        madein_tv.setText(name);
                    }
                });
                bottomSheetSelected.show(getSupportFragmentManager(), bottomSheetSelected.getTag());
            }
        });

        type_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ProvinceModel> listType = new ArrayList<>();
                listType.add(new ProvinceModel(1, "Sedan"));
                listType.add(new ProvinceModel(2, "SUV"));
                listType.add(new ProvinceModel(3, "Coupe"));
                listType.add(new ProvinceModel(4, "Crossover"));
                listType.add(new ProvinceModel(5, "Hatchback"));
                listType.add(new ProvinceModel(6, "Cabriolet"));
                listType.add(new ProvinceModel(7, "Truck"));
                listType.add(new ProvinceModel(8, "Van/Minivan"));
                listType.add(new ProvinceModel(9, "Wagon"));
                BottomSheetSelected bottomSheetSelected = new BottomSheetSelected(listType, new ProvinceAdapter.OnItemOnCLick() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onClick(String name) {
                        type_tv.setText(name);
                    }
                });
                bottomSheetSelected.show(getSupportFragmentManager(), bottomSheetSelected.getTag());
            }
        });

        updateProduct_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateProduct();
            }
        });
    }


    @SuppressLint("CheckResult")
    private void UpdateProduct() {
        String version = version_edt.getText().toString();
        String year = year_tv.getText().toString();
        String madein = madein_tv.getText().toString();
        String km = kmwent_edt.getText().toString();
        String type = type_tv.getText().toString();
        String price = price_edt.getText().toString();
        String consume = consume_edt.getText().toString();
        String content = content_edt.getText().toString();

        String query = "UPDATE products SET product_Version = '"+version+"', product_Year = '"+year+"', product_MadeIn = '"+madein+"', " +
                " product_KmWent = '"+km+"', product_Type = '"+type+"', product_Price = '"+price+"', product_Consume = '"+consume+"', " +
                " product_Content = '"+content+"'WHERE product_Id = '"+product_Id+"'";

        if (!TextUtils.isEmpty(version)) {
            if (price.length() > 3) {
                if(content.length() >=30){
                    updateProduct_btn.setEnabled(false);
                    updateProduct_btn.setTextColor(Color.argb(50, 255, 255, 255));

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
                    content_edt.setError("Nội dụng phải từ 30 - 500 ký tự!");
                }
            } else {
                price_edt.setError("Giá tiền phải từ triệu đồng trở lên!");
            }
        } else {
            version_edt.setError("Bạn phải nhập giá trị cho version!");
        }
    }


    private void setData() {
        ProductsModel productsModel = getIntent().getParcelableExtra("update_product");
        product_Id = productsModel.getProduct_Id();

        company_tv.setText(productsModel.getProduct_Company());
        name_tv.setText(productsModel.getProduct_Name());
        year_tv.setText(String.valueOf(productsModel.getProduct_Year()));
        type_tv.setText(productsModel.getProduct_Type());
        fuel_tv.setText(productsModel.getProduct_Fuel());
        madein_tv.setText(productsModel.getProduct_MadeIn());
        version_edt.setText(productsModel.getProduct_Version());
        kmwent_edt.setText(String.valueOf(productsModel.getProduct_KmWent()));
        price_edt.setText(String.valueOf(productsModel.getProduct_Price()));
        consume_edt.setText(String.valueOf(productsModel.getProduct_Consume()));
        content_edt.setText(productsModel.getProduct_Content());

    }

    private void EventToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PostManagementActivity.class));
            }
        });
    }

    private void init() {
        mToolbar = findViewById(R.id.toolbarupdate);
        company_tv = findViewById(R.id.ud_company_tv);
        name_tv = findViewById(R.id.ud_name_tv);
        year_tv = findViewById(R.id.ud_year_tv);
        type_tv = findViewById(R.id.ud_type_tv);
        fuel_tv = findViewById(R.id.ud_fuel_tv);
        madein_tv = findViewById(R.id.ud_madein_tv);
        version_edt = findViewById(R.id.ud_version_edt);
        kmwent_edt = findViewById(R.id.ud_kmwent_edt);
        price_edt = findViewById(R.id.ud_price_edt);
        consume_edt = findViewById(R.id.ud_consume_edt);
        content_edt = findViewById(R.id.ud_content_edt);
        updateProduct_btn = findViewById(R.id.ud_product_btn);
    }
}