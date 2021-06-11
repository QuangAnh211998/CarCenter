package com.example.carcenter.JavaClass;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.example.carcenter.Adapter.BottomSheetCompanyAdapter;
import com.example.carcenter.Adapter.ProductsAdapter;
import com.example.carcenter.Adapter.ProvinceAdapter;
import com.example.carcenter.Custom.BottomSheetCompany;
import com.example.carcenter.Custom.BottomSheetSelected;
import com.example.carcenter.Custom.Custom_Price;
import com.example.carcenter.Model.CompanyModel;
import com.example.carcenter.Model.ProductsModel;
import com.example.carcenter.Model.ProvinceModel;
import com.example.carcenter.Network.APIRequest;
import com.example.carcenter.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SearchActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView search_recyclerView;
    private CrystalRangeSeekbar rangeSeekbar_price;
    private CrystalRangeSeekbar rangeSeekbar_year;
    private TextView price_tv;
    private TextView year_tv;
    private TextView company_tv;
    private TextView status1_tv;
    private TextView status2_tv;
    private TextView madein1_tv;
    private TextView madein2_tv;
    private TextView outside_tv;
    private TextView type_tv;

    private List<CompanyModel> companyModelList;
    private List<ProductsModel> productsModelList;
    private ProductsAdapter productsAdapter;
    public SharedPreferences saveStatus;
    SharedPreferences.Editor editor;

    String approval = "Đã duyệt";
    String yearmax;
    String yearmin;
    String pricemax;
    String pricemin;
    String company = "Tất cả";
    String outside = "Tất cả";
    String type = "Tất cả";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        if (Build.VERSION.SDK_INT >= 22) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(ContextCompat.getColor(SearchActivity.this, R.color.colorGrey));
        }
        Innit();

        companyModelList = new ArrayList<>();
        LinearLayoutManager layoutManager_Product = new LinearLayoutManager(getApplicationContext());
        layoutManager_Product.setOrientation(LinearLayoutManager.VERTICAL);
        search_recyclerView.setLayoutManager(layoutManager_Product);
        productsModelList = new ArrayList<ProductsModel>();
        productsAdapter = new ProductsAdapter(productsModelList);
        search_recyclerView.setAdapter(productsAdapter);

        saveStatus = getSharedPreferences("saveStatus", Context.MODE_PRIVATE);
        editor = saveStatus.edit();
        ActionToolBar();
        getDataCompany();
        OnClickRangeSeekbar();
        OnClickStatus();
        OnClickMadeIn();
        OnClickCompany();
        OnClickOutside();
        OnClickType();

    }


    private void OnClickRangeSeekbar() {

        rangeSeekbar_price.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                pricemin = String.valueOf(minValue);
                pricemax = String.valueOf(maxValue);
                if (String.valueOf(minValue).equals("100000") && String.valueOf(maxValue).equals("2000000")) {
                    price_tv.setText("Không giới hạn");
                } else if (String.valueOf(minValue).equals("100000")) {
                    price_tv.setText("Đến " + Custom_Price.format(Long.parseLong(String.valueOf(maxValue))));
                } else if (String.valueOf(maxValue).equals("2000000")) {
                    price_tv.setText("Từ " + Custom_Price.format(Long.parseLong(String.valueOf(minValue))));
                } else {
                    price_tv.setText("Từ " + Custom_Price.format(Long.parseLong(String.valueOf(minValue))) + " - "
                            + Custom_Price.format(Long.parseLong(String.valueOf(maxValue))));
                }
                String status = saveStatus.getString("status", "");
                String madein = saveStatus.getString("madein", "");
                String query;
                 if (!pricemin.equals("100000") && pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                         && company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                         && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"' AND product_PostApproval ='" + approval + "'";
                    getDataProductbyKey(query);
                }else if (pricemin.equals("100000") && !pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                         && company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                         && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Price <= '"+pricemax+"' AND product_PostApproval ='" + approval + "'";
                    getDataProductbyKey(query);
                }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                         && company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                         && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' AND product_PostApproval ='"+approval+"'";
                    getDataProductbyKey(query);
                }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                         && company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                         && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHEREproduct_Price >= '"+pricemin+"' AND product_Year >= '"+yearmin+"' " +
                            "AND product_PostApproval ='" + approval + "'";
                    getDataProductbyKey(query);
                }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && yearmin.equals("2000") && !yearmax.equals("2021")
                         && company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                         && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"'AND product_Year <= '"+yearmax+"' " +
                            "AND product_PostApproval ='" + approval + "'";
                    getDataProductbyKey(query);
                }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && !yearmax.equals("2021")
                        && company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                         && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"'" +
                            " AND product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"' AND product_PostApproval ='" + approval + "'";
                    getDataProductbyKey(query);
                }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                        && !company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                         && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"'AND product_PostApproval ='"+approval+"'" +
                            " AND product_Company = '"+company+"'";
                    getDataProductbyKey(query);
                }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                        && !company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                         && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"'AND product_Year >= '"+yearmin+"' " +
                            "AND product_PostApproval ='"+approval+"'AND product_Company = '"+company+"'";
                    getDataProductbyKey(query);
                }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && yearmin.equals("2000") && !yearmax.equals("2021")
                        && !company.equals("Tất cả") && !outside.equals("Tất cả") && type.equals("Tất cả")
                         && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"' AND product_Year >= '"+yearmin+"' " +
                            "AND product_PostApproval ='"+approval+"' AND product_OutSide ='"+outside+"' AND product_Company = '"+company+"'";
                    getDataProductbyKey(query);
                }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                        && !company.equals("Tất cả") && !outside.equals("Tất cả") && !type.equals("Tất cả")
                         && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Type = '"+type+"' AND product_Price >= '"+pricemin+"'" +
                            " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"'" +
                            " AND product_OutSide ='"+outside+"' AND product_Company = '"+company+"'";
                    getDataProductbyKey(query);
                }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                        && company.equals("Tất cả") && !outside.equals("Tất cả") && !type.equals("Tất cả")
                         && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Type = '"+type+"' AND product_Price >= '"+pricemin+"'" +
                            " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"'" +
                            " AND product_OutSide ='"+outside+"'";
                    getDataProductbyKey(query);
                }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                        && company.equals("Tất cả") && outside.equals("Tất cả") && !type.equals("Tất cả")
                         && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Type = '"+type+"' AND product_Price >= '"+pricemin+"'" +
                            " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"'";
                    getDataProductbyKey(query);
                }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                        && !company.equals("Tất cả") && outside.equals("Tất cả") && !type.equals("Tất cả")
                         && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Type = '"+type+"' AND product_Price >= '"+pricemin+"'" +
                            " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' AND product_Company = '"+company+"'";
                    getDataProductbyKey(query);
                }else if(!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && !yearmax.equals("2021")
                        && company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                         && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                            " AND product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"' AND product_PostApproval ='"+approval+"'";
                    getDataProductbyKey(query);
                }else if(!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                        && company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                         && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                            " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"'";
                    getDataProductbyKey(query);
                }else if(!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                        && !company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                         && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                            " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' AND product_Company = '"+company+"'";
                    getDataProductbyKey(query);
                }else if(!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                        && !company.equals("Tất cả") && !outside.equals("Tất cả") && type.equals("Tất cả")
                         && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' AND product_Year >= '"+yearmin+"'" +
                            " AND pproduct_Company = '"+company+"' AND product_PostApproval ='"+approval+"' AND product_OutSide = '"+outside+"'";
                    getDataProductbyKey(query);
                }else if(!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                        && !company.equals("Tất cả") && !outside.equals("Tất cả") && !type.equals("Tất cả")
                         && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Type = '"+type+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                            " AND product_Year >= '"+yearmin+"' AND product_OutSide ='"+outside+"' AND product_PostApproval ='"+approval+"'" +
                            " AND product_Company = '"+company+"'";
                    getDataProductbyKey(query);
                }else if(!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                         && company.equals("Tất cả") && !outside.equals("Tất cả") && !type.equals("Tất cả")
                         && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                     query = "SELECT * FROM products WHERE product_Type = '"+type+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                             " AND product_Year >= '"+yearmin+"' AND product_OutSide ='"+outside+"' AND product_PostApproval ='"+approval+"'";
                     getDataProductbyKey(query);
                 }else if(!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                         && company.equals("Tất cả") && outside.equals("Tất cả") && !type.equals("Tất cả")
                         && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                     query = "SELECT * FROM products WHERE product_Type = '"+type+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                             " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"'";
                     getDataProductbyKey(query);
                 }else if(!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                         && company.equals("Tất cả") && !outside.equals("Tất cả") && type.equals("Tất cả")
                         && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                     query = "SELECT * FROM products WHERE product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                             " AND product_Year >= '"+yearmin+"' AND product_OutSide ='"+outside+"' AND product_PostApproval ='"+approval+"'";
                     getDataProductbyKey(query);
                 }else if(!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && !yearmax.equals("2021")
                         && !company.equals("Tất cả") && !outside.equals("Tất cả") && !type.equals("Tất cả")
                         && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                     query = "SELECT * FROM products WHERE product_Type = '"+type+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                             " AND product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"' AND product_OutSide ='"+outside+"' " +
                             "AND product_PostApproval ='"+approval+"' AND product_Company = '"+company+"'";
                     getDataProductbyKey(query);
                 }else if(!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && !yearmax.equals("2021")
                         && !company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                         && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                     query = "SELECT * FROM products WHERE product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                             " AND product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"' AND product_PostApproval ='"+approval+"' " +
                             "AND product_Company = '"+company+"'";
                     getDataProductbyKey(query);
                 }else if(!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && !yearmax.equals("2021")
                         && !company.equals("Tất cả") && !outside.equals("Tất cả") && type.equals("Tất cả")
                         && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                     query = "SELECT * FROM products WHERE product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                             " AND product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"' AND product_OutSide ='"+outside+"' " +
                             "AND product_PostApproval ='"+approval+"' AND product_Company = '"+company+"'";
                     getDataProductbyKey(query);
                 }else if(!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && !yearmax.equals("2021")
                         && !company.equals("Tất cả") && outside.equals("Tất cả") && !type.equals("Tất cả")
                         && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                     query = "SELECT * FROM products WHERE product_Type = '"+type+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                             " AND product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"' AND product_PostApproval ='"+approval+"'" +
                             " AND product_Company = '"+company+"'";
                     getDataProductbyKey(query);
                 }else if(!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && !yearmax.equals("2021")
                         && company.equals("Tất cả") && !outside.equals("Tất cả") && !type.equals("Tất cả")
                         && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                     query = "SELECT * FROM products WHERE product_Type = '"+type+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                             " AND product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"' AND product_OutSide ='"+outside+"' " +
                             "AND product_PostApproval ='"+approval+"'";
                     getDataProductbyKey(query);
                 }else if(!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && !yearmax.equals("2021")
                         && company.equals("Tất cả") && !outside.equals("Tất cả") && type.equals("Tất cả")
                         && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                     query = "SELECT * FROM products WHERE product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                             " AND product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"' AND product_OutSide ='"+outside+"' " +
                             "AND product_PostApproval ='"+approval+"'";
                     getDataProductbyKey(query);
                 }else if(!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && !yearmax.equals("2021")
                         && company.equals("Tất cả") && outside.equals("Tất cả") && !type.equals("Tất cả")
                         && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                     query = "SELECT * FROM products WHERE product_Type = '"+type+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                             " AND product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"' AND product_PostApproval ='"+approval+"'";
                     getDataProductbyKey(query);
                 }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                         && company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                         && !TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                     query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"'AND product_PostApproval ='"+approval+"'" +
                             " AND product_Status = '"+status+"'";
                     getDataProductbyKey(query);
                 }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                         && company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                         && !TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                     query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"'AND product_PostApproval ='"+approval+"'" +
                             " AND product_Status = '"+status+"' AND product_Year >= '"+yearmin+"'";
                     getDataProductbyKey(query);
                 }else if(!pricemin.equals("100000") && !pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                         && company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                         && !TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                     query = "SELECT * FROM products WHERE product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' AND product_PostApproval ='"+approval+"'" +
                             " AND product_Status = '"+status+"'";
                     getDataProductbyKey(query);
                 }else if(!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                         && company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                         && !TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                     query = "SELECT * FROM products WHERE product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' AND product_PostApproval ='"+approval+"'" +
                             " AND product_Status = '"+status+"' AND product_Year >= '"+yearmin+"'";
                     getDataProductbyKey(query);
                 }else if(!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && !yearmax.equals("2021")
                         && company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                         && !TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                     query = "SELECT * FROM products WHERE product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' AND product_PostApproval ='"+approval+"'" +
                             " AND product_Status = '"+status+"' AND product_Year BETWEEN '"+pricemin+"' AND '"+pricemax+"'";
                     getDataProductbyKey(query);
                 }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                         && !company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                         && !TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                     query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"' AND product_PostApproval ='"+approval+"'" +
                             " AND product_Status = '"+status+"' AND product_Year >='"+pricemin+"' AND product_Company = '"+company+"'";
                     getDataProductbyKey(query);
                 }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                         && !company.equals("Tất cả") && !outside.equals("Tất cả") && type.equals("Tất cả")
                         && !TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                     query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"' AND product_PostApproval ='"+approval+"'" +
                             " AND product_Status = '"+status+"' AND product_Year >='"+pricemin+"' AND product_Company = '"+company+"'" +
                             "AND product_OutSide = '"+outside+"'";
                     getDataProductbyKey(query);
                 }
                 else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                         && !company.equals("Tất cả") && !outside.equals("Tất cả") && !type.equals("Tất cả")
                         && !TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                     query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"' AND product_PostApproval ='"+approval+"'" +
                             " AND product_Status = '"+status+"' AND product_Year >='"+pricemin+"' AND product_Company = '"+company+"'" +
                             "AND product_OutSide = '"+outside+"' AND product_Type = '"+type+"'";
                     getDataProductbyKey(query);
                 }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                         && company.equals("Tất cả") && !outside.equals("Tất cả") && type.equals("Tất cả")
                         && !TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                     query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"' AND product_PostApproval ='"+approval+"'" +
                             " AND product_Status = '"+status+"' AND product_Year >='"+pricemin+"' AND product_OutSide = '"+outside+"'";
                     getDataProductbyKey(query);
                 }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                         && company.equals("Tất cả") && outside.equals("Tất cả") && !type.equals("Tất cả")
                         && !TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                     query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"' AND product_PostApproval ='"+approval+"'" +
                             " AND product_Status = '"+status+"' AND product_Year >='"+pricemin+"' AND product_Type = '"+type+"'";
                     getDataProductbyKey(query);
                 }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                         && company.equals("Tất cả") && !outside.equals("Tất cả") && !type.equals("Tất cả")
                         && !TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                     query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"' AND product_PostApproval ='"+approval+"'" +
                             " AND product_Status = '"+status+"' AND product_Year >='"+pricemin+"' AND product_Type = '"+type+"'" +
                             "AND product_OutSide = '"+outside+"'";
                     getDataProductbyKey(query);
                 }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                         && !company.equals("Tất cả") && outside.equals("Tất cả") && !type.equals("Tất cả")
                         && !TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                     query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"' AND product_PostApproval ='"+approval+"'" +
                             " AND product_Status = '"+status+"' AND product_Year >='"+pricemin+"' AND product_Company = '"+company+"'" +
                             "AND product_Type = '"+type+"'";
                     getDataProductbyKey(query);
                 }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                         && company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                         && TextUtils.isEmpty(status) && !TextUtils.isEmpty(madein)) {
                     query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"'AND product_PostApproval ='"+approval+"'" +
                             " AND product_MadeIn = '"+madein+"'";
                     getDataProductbyKey(query);
                 }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                         && company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                         && TextUtils.isEmpty(status) && !TextUtils.isEmpty(madein)) {
                     query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"'AND product_PostApproval ='"+approval+"'" +
                             " AND product_MadeIn = '"+madein+"' AND product_Year >= '"+yearmin+"'";
                     getDataProductbyKey(query);
                 }else if(!pricemin.equals("100000") && !pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                         && company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                         && TextUtils.isEmpty(status) && !TextUtils.isEmpty(madein)) {
                     query = "SELECT * FROM products WHERE product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' AND product_PostApproval ='"+approval+"'" +
                             " AND product_MadeIn = '"+madein+"'";
                     getDataProductbyKey(query);
                 }else if(!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                         && company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                         && TextUtils.isEmpty(status) && !TextUtils.isEmpty(madein)) {
                     query = "SELECT * FROM products WHERE product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' AND product_PostApproval ='"+approval+"'" +
                             " AND product_MadeIn = '"+madein+"' AND product_Year >= '"+yearmin+"'";
                     getDataProductbyKey(query);
                 }else if(!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && !yearmax.equals("2021")
                         && company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                         && TextUtils.isEmpty(status) && !TextUtils.isEmpty(madein)) {
                     query = "SELECT * FROM products WHERE product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' AND product_PostApproval ='"+approval+"'" +
                             " AND product_MadeIn = '"+madein+"' AND product_Year BETWEEN '"+pricemin+"' AND '"+pricemax+"'";
                     getDataProductbyKey(query);
                 }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                         && !company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                         && TextUtils.isEmpty(status) && !TextUtils.isEmpty(madein)) {
                     query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"' AND product_PostApproval ='"+approval+"'" +
                             " AND product_MadeIn = '"+madein+"' AND product_Year >='"+pricemin+"' AND product_Company = '"+company+"'";
                     getDataProductbyKey(query);
                 }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                         && !company.equals("Tất cả") && !outside.equals("Tất cả") && type.equals("Tất cả")
                         && TextUtils.isEmpty(status) && !TextUtils.isEmpty(madein)) {
                     query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"' AND product_PostApproval ='"+approval+"'" +
                             " AND product_MadeIn = '"+madein+"' AND product_Year >='"+pricemin+"' AND product_Company = '"+company+"'" +
                             "AND product_OutSide = '"+outside+"'";
                     getDataProductbyKey(query);
                 }
                 else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                         && !company.equals("Tất cả") && !outside.equals("Tất cả") && !type.equals("Tất cả")
                         && TextUtils.isEmpty(status) && !TextUtils.isEmpty(madein)) {
                     query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"' AND product_PostApproval ='"+approval+"'" +
                             " AND product_MadeIn = '"+madein+"' AND product_Year >='"+pricemin+"' AND product_Company = '"+company+"'" +
                             "AND product_OutSide = '"+outside+"' AND product_Type = '"+type+"'";
                     getDataProductbyKey(query);
                 }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                         && company.equals("Tất cả") && !outside.equals("Tất cả") && type.equals("Tất cả")
                         && TextUtils.isEmpty(status) && !TextUtils.isEmpty(madein)) {
                     query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"' AND product_PostApproval ='"+approval+"'" +
                             " AND product_MadeIn = '"+madein+"' AND product_Year >='"+pricemin+"' AND product_OutSide = '"+outside+"'";
                     getDataProductbyKey(query);
                 }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                         && company.equals("Tất cả") && outside.equals("Tất cả") && !type.equals("Tất cả")
                         && TextUtils.isEmpty(status) && !TextUtils.isEmpty(madein)) {
                     query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"' AND product_PostApproval ='"+approval+"'" +
                             " AND product_MadeIn = '"+madein+"' AND product_Year >='"+pricemin+"' AND product_Type = '"+type+"'";
                     getDataProductbyKey(query);
                 }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                         && company.equals("Tất cả") && !outside.equals("Tất cả") && !type.equals("Tất cả")
                         && TextUtils.isEmpty(status) && !TextUtils.isEmpty(madein)) {
                     query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"' AND product_PostApproval ='"+approval+"'" +
                             " AND product_MadeIn = '"+madein+"' AND product_Year >='"+pricemin+"' AND product_Type = '"+type+"'" +
                             "AND product_OutSide = '"+outside+"'";
                     getDataProductbyKey(query);
                 }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                         && !company.equals("Tất cả") && outside.equals("Tất cả") && !type.equals("Tất cả")
                         && TextUtils.isEmpty(status) && !TextUtils.isEmpty(madein)) {
                     query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"' AND product_PostApproval ='"+approval+"'" +
                             " AND product_MadeIn = '"+madein+"' AND product_Year >='"+pricemin+"' AND product_Company = '"+company+"'" +
                             "AND product_Type = '"+type+"'";
                     getDataProductbyKey(query);
                 }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                         && company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                         && !TextUtils.isEmpty(madein) && !TextUtils.isEmpty(status)) {
                     query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"' AND product_PostApproval ='"+approval+"'" +
                             " AND product_MadeIn = '"+madein+"' AND product_Status = '"+status+"'";
                     getDataProductbyKey(query);
                 }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                         && company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                         && !TextUtils.isEmpty(madein) && !TextUtils.isEmpty(status)) {
                     query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"' AND product_PostApproval ='"+approval+"'" +
                             " AND product_MadeIn = '"+madein+"' AND product_Year >='"+pricemin+"' AND product_Status = '"+status+"'";
                     getDataProductbyKey(query);
                 }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                         && !company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                         && !TextUtils.isEmpty(madein) && !TextUtils.isEmpty(status)) {
                     query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"' AND product_PostApproval ='"+approval+"'" +
                             " AND product_MadeIn = '"+madein+"' AND product_Year >='"+pricemin+"' AND product_Status = '"+status+"'" +
                             "AND product_Company = '"+company+"'";
                     getDataProductbyKey(query);
                 }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                         && !company.equals("Tất cả") && !outside.equals("Tất cả") && type.equals("Tất cả")
                         && !TextUtils.isEmpty(madein) && !TextUtils.isEmpty(status)) {
                     query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"' AND product_PostApproval ='"+approval+"'" +
                             " AND product_MadeIn = '"+madein+"' AND product_Year >='"+pricemin+"' AND product_Status = '"+status+"'" +
                             " AND product_Company = '"+company+"' AND product_OutSide = '"+outside+"'";
                     getDataProductbyKey(query);
                 }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                         && !company.equals("Tất cả") && !outside.equals("Tất cả") && !type.equals("Tất cả")
                         && !TextUtils.isEmpty(madein) && !TextUtils.isEmpty(status)) {
                     query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"' AND product_PostApproval ='"+approval+"'" +
                             " AND product_MadeIn = '"+madein+"' AND product_Year >='"+pricemin+"' AND product_Status = '"+status+"'" +
                             " AND product_Company = '"+company+"' AND product_OutSide = '"+outside+"' AND product_Type = '"+type+"'";
                     getDataProductbyKey(query);
                 }
            }
        });

        rangeSeekbar_year.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                yearmin = String.valueOf(minValue);
                yearmax = String.valueOf(maxValue);
                if (String.valueOf(minValue).equals("2000") && String.valueOf(maxValue).equals("2021")) {
                    year_tv.setText("Không giới hạn");
                } else if (String.valueOf(maxValue).equals("2021")) {
                    year_tv.setText("Từ năm " + String.valueOf(minValue));
                } else if (String.valueOf(minValue).equals("2000")) {
                    year_tv.setText("Đến năm " + String.valueOf(maxValue));
                } else {
                    year_tv.setText("Từ năm " + String.valueOf(minValue) + " - " + String.valueOf(maxValue));
                }
                String status = saveStatus.getString("status", "");
                String madein = saveStatus.getString("madein", "");
                String query;
                if (!yearmin.equals("2000") && yearmax.equals("2021") && pricemin.equals("100000") && pricemax.equals("2000000")
                        && company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                        && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Year >= '"+yearmin+"' AND product_PostApproval ='" + approval + "'";
                    getDataProductbyKey(query);
                }else if (yearmin.equals("2000") && !yearmax.equals("2021") && pricemin.equals("100000") && pricemax.equals("2000000")
                        && company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                        && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Year <= '"+yearmax+"' AND product_PostApproval ='" + approval + "'";
                    getDataProductbyKey(query);
                }else if (!yearmin.equals("2000") && !yearmax.equals("2021") && pricemin.equals("100000") && pricemax.equals("2000000")
                        && company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                        && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"' AND product_PostApproval ='"+approval+"'";
                    getDataProductbyKey(query);
                }else if (!yearmin.equals("2000") && yearmax.equals("2021") && pricemin.equals("100000") && !pricemax.equals("2000000")
                        && company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                        && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHEREproduct_Price <= '"+pricemax+"' AND product_Year >= '"+yearmin+"' " +
                            "AND product_PostApproval ='" + approval + "'";
                    getDataProductbyKey(query);
                }else if (!yearmin.equals("2000") && yearmax.equals("2021") && !pricemin.equals("100000") && pricemax.equals("2000000")
                        && company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                        && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"' AND product_PostApproval ='"+approval+"'" +
                            " AND product_Year >= '"+yearmin+"'";
                    getDataProductbyKey(query);
                }else if (!yearmin.equals("2000") && !yearmax.equals("2021") && !pricemin.equals("100000") && !pricemax.equals("2000000")
                        && company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                        && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                            " AND product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"' AND product_PostApproval ='" + approval + "'";
                    getDataProductbyKey(query);
                }else if(!yearmin.equals("2000") && !yearmax.equals("2021") && !pricemin.equals("100000") && pricemax.equals("2000000")
                        && company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                        && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Year BETWEEN'"+yearmin+"' AND '"+yearmax+"' " +
                            "AND product_PostApproval ='"+approval+"' AND product_Price >= '"+pricemin+"'";
                    getDataProductbyKey(query);
                }else if(!yearmin.equals("2000") && yearmax.equals("2021") && pricemin.equals("100000") && pricemax.equals("2000000")
                        && !company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                        && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Year >= '"+yearmin+"' " +
                            "AND product_PostApproval ='"+approval+"' AND product_Company = '"+company+"'";
                    getDataProductbyKey(query);
                }else if(!yearmin.equals("2000") && yearmax.equals("2021") && !pricemin.equals("100000") && pricemax.equals("2000000")
                        && !company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                        && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"' AND product_Year >= '"+yearmin+"'" +
                            "AND product_PostApproval ='"+approval+"' AND product_Company = '"+company+"'";
                    getDataProductbyKey(query);
                }else if(!yearmin.equals("2000") && yearmax.equals("2021") && !pricemin.equals("100000") && pricemax.equals("2000000")
                        && !company.equals("Tất cả") && !outside.equals("Tất cả") && type.equals("Tất cả")
                        && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"' AND product_Year >= '"+yearmin+"' " +
                            "AND product_PostApproval ='"+approval+"'AND product_OutSide ='"+outside+"' AND product_Company = '"+company+"'";
                    getDataProductbyKey(query);
                }else if(!yearmin.equals("2000") && yearmax.equals("2021") && !pricemin.equals("100000") && pricemax.equals("2000000")
                        && !company.equals("Tất cả") && !outside.equals("Tất cả") && !type.equals("Tất cả")
                        && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Type = '"+type+"' AND product_Price >= '"+pricemin+"'" +
                            " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"'" +
                            " AND product_OutSide ='"+outside+"' AND product_Company = '"+company+"'";
                    getDataProductbyKey(query);
                }else if(!yearmin.equals("2000") && yearmax.equals("2021") && !pricemin.equals("100000") && pricemax.equals("2000000")
                        && company.equals("Tất cả") && !outside.equals("Tất cả") && type.equals("Tất cả")
                        && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_OutSide = '"+outside+"' AND product_Price >= '"+pricemin+"'" +
                            " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"'";
                    getDataProductbyKey(query);
                }else if(!yearmin.equals("2000") && yearmax.equals("2021") && !pricemin.equals("100000") && pricemax.equals("2000000")
                        && company.equals("Tất cả") && outside.equals("Tất cả") && !type.equals("Tất cả")
                        && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Type = '"+type+"' AND product_Price >= '"+pricemin+"'" +
                            " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"'";
                    getDataProductbyKey(query);
                }else if(!yearmin.equals("2000") && yearmax.equals("2021") && !pricemin.equals("100000") && pricemax.equals("2000000")
                        && !company.equals("Tất cả") && outside.equals("Tất cả") && !type.equals("Tất cả")
                        && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"' AND product_Company = '"+company+"'" +
                            " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' AND product_Type = '"+type+"'";
                    getDataProductbyKey(query);
                }else if(!yearmin.equals("2000") && yearmax.equals("2021") && !pricemin.equals("100000") && pricemax.equals("2000000")
                        && company.equals("Tất cả") && !outside.equals("Tất cả") && !type.equals("Tất cả")
                        && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"' AND product_OutSide = '"+outside+"'" +
                            " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' AND product_Type = '"+type+"'";
                    getDataProductbyKey(query);
                }else if(!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                        && !company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                        && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                            " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' AND product_Company = '"+company+"'";
                    getDataProductbyKey(query);
                }else if(!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                        && !company.equals("Tất cả") && !outside.equals("Tất cả") && type.equals("Tất cả")
                        && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' AND product_Year >= '"+yearmin+"'" +
                            " AND pproduct_Company = '"+company+"' AND product_PostApproval ='"+approval+"' AND product_OutSide = '"+outside+"'";
                    getDataProductbyKey(query);
                }else if(!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                        && !company.equals("Tất cả") && !outside.equals("Tất cả") && !type.equals("Tất cả")
                        && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Type = '"+type+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                            " AND product_Year >= '"+yearmin+"' AND product_OutSide ='"+outside+"' AND product_PostApproval ='"+approval+"'" +
                            " AND product_Company = '"+company+"'";
                    getDataProductbyKey(query);
                }else if(!yearmin.equals("2000") && !yearmax.equals("2021") && !pricemin.equals("100000") && pricemax.equals("2000000")
                        && !company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                        && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"'" +
                            " AND product_Price >= '"+pricemin+"' AND product_Company ='"+company+"' AND product_PostApproval ='"+approval+"'";
                    getDataProductbyKey(query);
                }else if(!yearmin.equals("2000") && !yearmax.equals("2021") && !pricemin.equals("100000") && pricemax.equals("2000000")
                        && !company.equals("Tất cả") && !outside.equals("Tất cả") && type.equals("Tất cả")
                        && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"'" +
                            "AND product_Company = '"+company+"' AND product_OutSide = '"+outside+"'" +
                            "AND product_Price >= '"+pricemin+"' AND product_PostApproval ='"+approval+"'";
                    getDataProductbyKey(query);
                }else if(!yearmin.equals("2000") && !yearmax.equals("2021") && !pricemin.equals("100000") && pricemax.equals("2000000")
                        && !company.equals("Tất cả") && !outside.equals("Tất cả") && !type.equals("Tất cả")
                        && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"'" +
                            " AND product_Price >= '"+yearmin+"' AND product_OutSide ='"+outside+"' AND product_Type = '"+type+"' " +
                            "AND product_Company = '"+company+"' AND product_PostApproval ='"+approval+"'";
                    getDataProductbyKey(query);
                }else if(!yearmin.equals("2000") && !yearmax.equals("2021") && !pricemin.equals("100000") && pricemax.equals("2000000")
                        && company.equals("Tất cả") && !outside.equals("Tất cả") && type.equals("Tất cả")
                        && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"'" +
                            " AND product_Price >= '"+yearmin+"' AND product_OutSide ='"+outside+"'" +
                            "AND product_PostApproval ='"+approval+"'";
                    getDataProductbyKey(query);
                }else if(!yearmin.equals("2000") && !yearmax.equals("2021") && !pricemin.equals("100000") && pricemax.equals("2000000")
                        && company.equals("Tất cả") && !outside.equals("Tất cả") && !type.equals("Tất cả")
                        && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"'" +
                            " AND product_Price >= '"+yearmin+"' AND product_OutSide ='"+outside+"' AND product_Type = '"+type+"' " +
                            "AND product_PostApproval ='"+approval+"'";
                    getDataProductbyKey(query);
                }else if(!yearmin.equals("2000") && !yearmax.equals("2021") && !pricemin.equals("100000") && pricemax.equals("2000000")
                        && company.equals("Tất cả") && outside.equals("Tất cả") && !type.equals("Tất cả")
                        && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"'" +
                            " AND product_Price >= '"+yearmin+"'AND product_Type = '"+type+"' " +
                            " AND product_PostApproval ='"+approval+"'";
                    getDataProductbyKey(query);
                }else if(!yearmin.equals("2000") && !yearmax.equals("2021") && !pricemin.equals("100000") && pricemax.equals("2000000")
                        && !company.equals("Tất cả") && outside.equals("Tất cả") && !type.equals("Tất cả")
                        && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"'" +
                            " AND product_Price >= '"+yearmin+"' AND product_Type = '"+type+"' " +
                            "AND product_Company = '"+company+"' AND product_PostApproval ='"+approval+"'";
                    getDataProductbyKey(query);
                }else if(!yearmin.equals("2000") && !yearmax.equals("2021") && !pricemin.equals("100000") && !pricemax.equals("2000000")
                        && !company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                        && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                            " AND product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"' AND product_Company ='"+company+"' " +
                            "AND product_PostApproval ='"+approval+"'";
                    getDataProductbyKey(query);
                }else if(!yearmin.equals("2000") && !yearmax.equals("2021") && !pricemin.equals("100000") && !pricemax.equals("2000000")
                        && !company.equals("Tất cả") && !outside.equals("Tất cả") && type.equals("Tất cả")
                        && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                            " AND product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"' AND product_OutSide ='"+outside+"' " +
                            "AND product_Company = '"+company+"'AND product_PostApproval ='"+approval+"'";
                    getDataProductbyKey(query);
                }else if(!yearmin.equals("2000") && !yearmax.equals("2021") && !pricemin.equals("100000") && !pricemax.equals("2000000")
                        && !company.equals("Tất cả") && !outside.equals("Tất cả") && !type.equals("Tất cả")
                        && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Type = '"+type+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                            " AND product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"' AND product_PostApproval ='"+approval+"'" +
                            " AND product_Company = '"+company+"' AND product_OutSide = '"+outside+"'";
                    getDataProductbyKey(query);
                }else if(!yearmin.equals("2000") && !yearmax.equals("2021") && !pricemin.equals("100000") && !pricemax.equals("2000000")
                        && company.equals("Tất cả") && !outside.equals("Tất cả") && type.equals("Tất cả")
                        && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                            " AND product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"' AND product_PostApproval ='"+approval+"'" +
                            " AND product_OutSide = '"+outside+"'";
                    getDataProductbyKey(query);
                }else if(!yearmin.equals("2000") && !yearmax.equals("2021") && !pricemin.equals("100000") && !pricemax.equals("2000000")
                        && company.equals("Tất cả") && !outside.equals("Tất cả") && !type.equals("Tất cả")
                        && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Type = '"+type+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                            " AND product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"' AND product_PostApproval ='"+approval+"'" +
                            " AND product_OutSide = '"+outside+"'";
                    getDataProductbyKey(query);
                }else if(!yearmin.equals("2000") && !yearmax.equals("2021") && !pricemin.equals("100000") && !pricemax.equals("2000000")
                        && company.equals("Tất cả") && outside.equals("Tất cả") && !type.equals("Tất cả")
                        && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Type = '"+type+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                            " AND product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"' AND product_PostApproval ='"+approval+"'";
                    getDataProductbyKey(query);
                }else if(!yearmin.equals("2000") && yearmax.equals("2021") && pricemin.equals("100000") && pricemax.equals("2000000")
                        && company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                        && !TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Year >= '"+yearmin+"'AND product_PostApproval ='"+approval+"'" +
                            " AND product_Status = '"+status+"'";
                    getDataProductbyKey(query);
                }else if(!yearmin.equals("2000") && yearmax.equals("2021") && !pricemin.equals("100000") && pricemax.equals("2000000")
                        && company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                        && !TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"'AND product_PostApproval ='"+approval+"'" +
                            " AND product_Status = '"+status+"' AND product_Year >= '"+yearmin+"'";
                    getDataProductbyKey(query);
                }else if(yearmin.equals("2000") && !yearmax.equals("2021") && pricemin.equals("100000") && pricemax.equals("2000000")
                        && company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                        && !TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"' AND product_PostApproval ='"+approval+"'" +
                            " AND product_Status = '"+status+"'";
                    getDataProductbyKey(query);
                }else if(!yearmin.equals("2000") && yearmax.equals("2021") && !pricemin.equals("100000") && pricemax.equals("2000000")
                        && company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                        && !TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"' AND product_PostApproval ='"+approval+"'" +
                            " AND product_Status = '"+status+"' AND product_Price >= '"+pricemin+"'";
                    getDataProductbyKey(query);
                }else if(!yearmin.equals("2000") && yearmax.equals("2021") && !pricemin.equals("100000") && pricemax.equals("2000000")
                        && !company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                        && !TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"' AND product_PostApproval ='"+approval+"'" +
                            " AND product_Status = '"+status+"' AND product_Year >='"+pricemin+"' AND product_Company = '"+company+"'";
                    getDataProductbyKey(query);
                }else if(!yearmin.equals("2000") && yearmax.equals("2021") && !pricemin.equals("100000") && pricemax.equals("2000000")
                        && !company.equals("Tất cả") && !outside.equals("Tất cả") && type.equals("Tất cả")
                        && !TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"' AND product_PostApproval ='"+approval+"'" +
                            " AND product_Status = '"+status+"' AND product_Year >='"+pricemin+"' AND product_Company = '"+company+"'" +
                            "AND product_OutSide = '"+outside+"'";
                    getDataProductbyKey(query);
                }
                else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                        && !company.equals("Tất cả") && !outside.equals("Tất cả") && !type.equals("Tất cả")
                        && !TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"' AND product_PostApproval ='"+approval+"'" +
                            " AND product_Status = '"+status+"' AND product_Year >='"+pricemin+"' AND product_Company = '"+company+"'" +
                            "AND product_OutSide = '"+outside+"' AND product_Type = '"+type+"'";
                    getDataProductbyKey(query);
                }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                        && company.equals("Tất cả") && !outside.equals("Tất cả") && type.equals("Tất cả")
                        && !TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"' AND product_PostApproval ='"+approval+"'" +
                            " AND product_Status = '"+status+"' AND product_Year >='"+pricemin+"' AND product_OutSide = '"+outside+"'";
                    getDataProductbyKey(query);
                }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                        && company.equals("Tất cả") && outside.equals("Tất cả") && !type.equals("Tất cả")
                        && !TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"' AND product_PostApproval ='"+approval+"'" +
                            " AND product_Status = '"+status+"' AND product_Year >='"+pricemin+"' AND product_Type = '"+type+"'";
                    getDataProductbyKey(query);
                }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                        && company.equals("Tất cả") && !outside.equals("Tất cả") && !type.equals("Tất cả")
                        && !TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"' AND product_PostApproval ='"+approval+"'" +
                            " AND product_Status = '"+status+"' AND product_Year >='"+pricemin+"' AND product_Type = '"+type+"'" +
                            "AND product_OutSide = '"+outside+"'";
                    getDataProductbyKey(query);
                }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                        && !company.equals("Tất cả") && outside.equals("Tất cả") && !type.equals("Tất cả")
                        && !TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"' AND product_PostApproval ='"+approval+"'" +
                            " AND product_Status = '"+status+"' AND product_Year >='"+pricemin+"' AND product_Company = '"+company+"'" +
                            "AND product_Type = '"+type+"'";
                    getDataProductbyKey(query);
                }else if(!yearmin.equals("2000") && yearmax.equals("2021") && pricemin.equals("100000") && pricemax.equals("2000000")
                        && company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                        && TextUtils.isEmpty(status) && !TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Year >= '"+yearmin+"'AND product_PostApproval ='"+approval+"'" +
                            " AND product_MadeIn = '"+madein+"'";
                    getDataProductbyKey(query);
                }else if(!yearmin.equals("2000") && yearmax.equals("2021") && !pricemin.equals("100000") && pricemax.equals("2000000")
                        && company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                        && TextUtils.isEmpty(status) && !TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"'AND product_PostApproval ='"+approval+"'" +
                            " AND product_MadeIn = '"+madein+"' AND product_Year >= '"+yearmin+"'";
                    getDataProductbyKey(query);
                }else if(yearmin.equals("2000") && !yearmax.equals("2021") && pricemin.equals("100000") && pricemax.equals("2000000")
                        && company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                        && TextUtils.isEmpty(status) && !TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"' AND product_PostApproval ='"+approval+"'" +
                            " AND product_MadeIn = '"+madein+"'";
                    getDataProductbyKey(query);
                }else if(!yearmin.equals("2000") && yearmax.equals("2021") && !pricemin.equals("100000") && pricemax.equals("2000000")
                        && company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                        && TextUtils.isEmpty(status) && !TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"' AND product_PostApproval ='"+approval+"'" +
                            " AND product_MadeIn = '"+madein+"' AND product_Price >= '"+pricemin+"'";
                    getDataProductbyKey(query);
                }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                        && !company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                        && TextUtils.isEmpty(status) && !TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"' AND product_PostApproval ='"+approval+"'" +
                            " AND product_MadeIn = '"+madein+"' AND product_Year >='"+pricemin+"' AND product_Company = '"+company+"'";
                    getDataProductbyKey(query);
                }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                        && !company.equals("Tất cả") && !outside.equals("Tất cả") && type.equals("Tất cả")
                        && TextUtils.isEmpty(status) && !TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"' AND product_PostApproval ='"+approval+"'" +
                            " AND product_MadeIn = '"+madein+"' AND product_Year >='"+pricemin+"' AND product_Company = '"+company+"'" +
                            "AND product_OutSide = '"+outside+"'";
                    getDataProductbyKey(query);
                }
                else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                        && !company.equals("Tất cả") && !outside.equals("Tất cả") && !type.equals("Tất cả")
                        && TextUtils.isEmpty(status) && !TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"' AND product_PostApproval ='"+approval+"'" +
                            " AND product_MadeIn = '"+madein+"' AND product_Year >='"+pricemin+"' AND product_Company = '"+company+"'" +
                            "AND product_OutSide = '"+outside+"' AND product_Type = '"+type+"'";
                    getDataProductbyKey(query);
                }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                        && company.equals("Tất cả") && !outside.equals("Tất cả") && type.equals("Tất cả")
                        && TextUtils.isEmpty(status) && !TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"' AND product_PostApproval ='"+approval+"'" +
                            " AND product_MadeIn = '"+madein+"' AND product_Year >='"+pricemin+"' AND product_OutSide = '"+outside+"'";
                    getDataProductbyKey(query);
                }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                        && company.equals("Tất cả") && outside.equals("Tất cả") && !type.equals("Tất cả")
                        && TextUtils.isEmpty(status) && !TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"' AND product_PostApproval ='"+approval+"'" +
                            " AND product_MadeIn = '"+madein+"' AND product_Year >='"+pricemin+"' AND product_Type = '"+type+"'";
                    getDataProductbyKey(query);
                }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                        && company.equals("Tất cả") && !outside.equals("Tất cả") && !type.equals("Tất cả")
                        && TextUtils.isEmpty(status) && !TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"' AND product_PostApproval ='"+approval+"'" +
                            " AND product_MadeIn = '"+madein+"' AND product_Year >='"+pricemin+"' AND product_Type = '"+type+"'" +
                            "AND product_OutSide = '"+outside+"'";
                    getDataProductbyKey(query);
                }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                        && !company.equals("Tất cả") && outside.equals("Tất cả") && !type.equals("Tất cả")
                        && TextUtils.isEmpty(status) && !TextUtils.isEmpty(madein)) {
                    query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"' AND product_PostApproval ='"+approval+"'" +
                            " AND product_MadeIn = '"+madein+"' AND product_Year >='"+pricemin+"' AND product_Company = '"+company+"'" +
                            "AND product_Type = '"+type+"'";
                    getDataProductbyKey(query);
                }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                        && company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                        && !TextUtils.isEmpty(madein) && !TextUtils.isEmpty(status)) {
                    query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"' AND product_PostApproval ='"+approval+"'" +
                            " AND product_MadeIn = '"+madein+"' AND product_Status = '"+status+"'";
                    getDataProductbyKey(query);
                }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                        && company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                        && !TextUtils.isEmpty(madein) && !TextUtils.isEmpty(status)) {
                    query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"' AND product_PostApproval ='"+approval+"'" +
                            " AND product_MadeIn = '"+madein+"' AND product_Year >='"+pricemin+"' AND product_Status = '"+status+"'";
                    getDataProductbyKey(query);
                }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                        && !company.equals("Tất cả") && outside.equals("Tất cả") && type.equals("Tất cả")
                        && !TextUtils.isEmpty(madein) && !TextUtils.isEmpty(status)) {
                    query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"' AND product_PostApproval ='"+approval+"'" +
                            " AND product_MadeIn = '"+madein+"' AND product_Year >='"+pricemin+"' AND product_Status = '"+status+"'" +
                            "AND product_Company = '"+company+"'";
                    getDataProductbyKey(query);
                }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                        && !company.equals("Tất cả") && !outside.equals("Tất cả") && type.equals("Tất cả")
                        && !TextUtils.isEmpty(madein) && !TextUtils.isEmpty(status)) {
                    query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"' AND product_PostApproval ='"+approval+"'" +
                            " AND product_MadeIn = '"+madein+"' AND product_Year >='"+pricemin+"' AND product_Status = '"+status+"'" +
                            " AND product_Company = '"+company+"' AND product_OutSide = '"+outside+"'";
                    getDataProductbyKey(query);
                }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                        && !company.equals("Tất cả") && !outside.equals("Tất cả") && !type.equals("Tất cả")
                        && !TextUtils.isEmpty(madein) && !TextUtils.isEmpty(status)) {
                    query = "SELECT * FROM products WHERE product_Price >= '"+pricemin+"' AND product_PostApproval ='"+approval+"'" +
                            " AND product_MadeIn = '"+madein+"' AND product_Year >='"+pricemin+"' AND product_Status = '"+status+"'" +
                            " AND product_Company = '"+company+"' AND product_OutSide = '"+outside+"' AND product_Type = '"+type+"'";
                    getDataProductbyKey(query);
                }
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void OnClickStatus() {
        status1_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status1_tv.setBackground(getDrawable(R.drawable.boder_tv_left));
                status2_tv.setBackgroundColor(getColor(R.color.colorWhite));
                editor.putString("status", "Xe mới");
                editor.commit();
                checkGetDataStatus();
            }
        });

        status2_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status1_tv.setBackgroundColor(getColor(R.color.colorWhite));
                status2_tv.setBackground(getDrawable(R.drawable.boder_tv_right));
                editor.putString("status", "Xe cũ");
                editor.commit();
                checkGetDataStatus();
            }
        });

    }


    @SuppressLint("ClickableViewAccessibility")
    private void OnClickMadeIn() {
        madein1_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                madein1_tv.setBackground(getDrawable(R.drawable.boder_tv_left));
                madein2_tv.setBackgroundColor(getColor(R.color.colorWhite));
                editor.putString("madein", "Trong nước");
                editor.commit();
                checkGetDataMadeIn();
            }
        });

        madein2_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                madein1_tv.setBackgroundColor(getColor(R.color.colorWhite));
                madein2_tv.setBackground(getDrawable(R.drawable.boder_tv_right));
                editor.putString("madein", "Nhập khẩu");
                editor.commit();
                checkGetDataMadeIn();
            }
        });

    }


    private void OnClickType() {
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
                        type = name;
                        String status = saveStatus.getString("status", "");
                        String madein = saveStatus.getString("madein", "");
                        String query;
                        if (price_tv.getText().equals("Không giới hạn") && year_tv.getText().equals("Không giới hạn") &&
                                company.equals("Tất cả") && outside.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Type = '"+type+"' AND product_PostApproval ='" + approval + "'";
                            getDataProductbyKey(query);
                        }else if (price_tv.getText().equals("Không giới hạn") && year_tv.getText().equals("Không giới hạn") &&
                                !company.equals("Tất cả") && outside.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Type = '"+type+"' AND product_Company ='"+company+"'" +
                                    " AND product_PostApproval ='" + approval + "'";
                            getDataProductbyKey(query);
                        }else if (price_tv.getText().equals("Không giới hạn") && year_tv.getText().equals("Không giới hạn") &&
                                company.equals("Tất cả") && !outside.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Type = '"+type+"' AND product_OutSide ='"+outside+"'" +
                                    "AND product_PostApproval ='" + approval + "'";
                            getDataProductbyKey(query);
                        }else if (price_tv.getText().equals("Không giới hạn") && year_tv.getText().equals("Không giới hạn") &&
                                !company.equals("Tất cả") && !outside.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Type = '"+type+"' AND product_OutSide ='"+outside+"'" +
                                    "AND product_Company = '"+company+"' AND product_PostApproval ='"+approval+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && year_tv.getText().equals("Không giới hạn")
                                && company.equals("Tất cả") && outside.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Type = '"+type+"' AND product_Price >= '"+pricemin+"' " +
                                    " AND product_PostApproval ='" + approval + "'";
                            getDataProductbyKey(query);
                        }else if (pricemin.equals("100000") && !pricemax.equals("2000000") && year_tv.getText().equals("Không giới hạn")
                                && company.equals("Tất cả") && outside.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Type = '"+type+"' AND product_Price <= '"+pricemax+"' " +
                                    " AND product_PostApproval ='" + approval + "'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && year_tv.getText().equals("Không giới hạn")
                                && company.equals("Tất cả") && outside.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Type = '"+type+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'  " +
                                    " AND product_PostApproval ='" + approval + "'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && company.equals("Tất cả") && outside.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Type = '"+type+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='" + approval + "'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && yearmin.equals("2000") && !yearmax.equals("2021")
                                && company.equals("Tất cả") && outside.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Type = '"+type+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                                    " AND product_Year <= '"+yearmax+"' AND product_PostApproval ='" + approval + "'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && company.equals("Tất cả") && outside.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Type = '"+type+"' AND product_Price >= '"+pricemin+"'" +
                                    " AND product_Year >= '"+yearmax+"' AND product_PostApproval ='"+approval+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && company.equals("Tất cả") && !outside.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Type = '"+type+"' AND product_Price >= '"+pricemin+"'" +
                                    " AND product_Year >= '"+yearmax+"' AND product_PostApproval ='"+approval+"' AND product_OutSide = '"+outside+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && !company.equals("Tất cả") && outside.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Type = '"+type+"' AND product_Price >= '"+pricemin+"'" +
                                    " AND product_Year >= '"+yearmax+"' AND product_PostApproval ='"+approval+"' AND product_Company = '"+company+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && !company.equals("Tất cả") && !outside.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Type = '"+type+"' AND product_Price >= '"+pricemin+"'" +
                                    " AND product_Year >= '"+yearmax+"' AND product_PostApproval ='"+approval+"'" +
                                    "AND product_Company = '"+company+"' AND product_OutSide = '"+outside+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && !yearmax.equals("2021")
                                && company.equals("Tất cả") && outside.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Type = '"+type+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                                    " AND product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"' AND product_PostApproval ='" + approval + "'";
                            getDataProductbyKey(query);
                        }else if(!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && !yearmax.equals("2021")
                                && !company.equals("Tất cả") && outside.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Type = '"+type+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                                    " AND product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"' AND product_PostApproval ='"+approval+"'" +
                                    " AND product_Company = '"+company+"'";
                            getDataProductbyKey(query);
                        }else if(!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && !yearmax.equals("2021")
                                && company.equals("Tất cả") && !outside.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Type = '"+type+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                                    " AND product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"' AND product_PostApproval ='"+approval+"'" +
                                    " AND product_OutSide ='"+outside+"'";
                            getDataProductbyKey(query);
                        }else if(!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && !yearmax.equals("2021")
                                && !company.equals("Tất cả") && !outside.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Type = '"+type+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                                    " AND product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"' AND product_PostApproval ='"+approval+"'" +
                                    " AND product_OutSide ='"+outside+"' AND product_Company = '"+company+"'";
                            getDataProductbyKey(query);
                        }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && !yearmax.equals("2021")
                                && !company.equals("Tất cả") && !outside.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Type = '"+type+"' AND product_Price >= '"+pricemin+"'" +
                                    " AND product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"' AND product_PostApproval ='"+approval+"'" +
                                    " AND product_OutSide ='"+outside+"' AND product_Company = '"+company+"'";
                            getDataProductbyKey(query);
                        }else if(pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && !yearmax.equals("2021")
                                && !company.equals("Tất cả") && !outside.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Type = '"+type+"' AND product_Price <= '"+pricemax+"'" +
                                    " AND product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"' AND product_PostApproval ='"+approval+"'" +
                                    " AND product_OutSide ='"+outside+"' AND product_Company = '"+company+"'";
                            getDataProductbyKey(query);
                        }else if(!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && !company.equals("Tất cả") && !outside.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Type = '"+type+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"'" +
                                    " AND product_OutSide ='"+outside+"' AND product_Company = '"+company+"'";
                            getDataProductbyKey(query);
                        }else if(!pricemin.equals("100000") && !pricemax.equals("2000000") && yearmin.equals("2000") && !yearmax.equals("2021")
                                && !company.equals("Tất cả") && !outside.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Type = '"+type+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                                    " AND product_Year <= '"+yearmax+"' AND product_PostApproval ='"+approval+"'" +
                                    " AND product_OutSide ='"+outside+"' AND product_Company = '"+company+"'";
                            getDataProductbyKey(query);
                        }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && !yearmax.equals("2021")
                                && !company.equals("Tất cả") && outside.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Type = '"+type+"' AND product_Price >= '"+pricemin+"'" +
                                    " AND product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"' AND product_PostApproval ='"+approval+"'" +
                                    " AND product_Company = '"+company+"'";
                            getDataProductbyKey(query);
                        }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && !yearmax.equals("2021")
                                && company.equals("Tất cả") && !outside.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Type = '"+type+"' AND product_Price >= '"+pricemin+"'" +
                                    " AND product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"' AND product_PostApproval ='"+approval+"'" +
                                    " AND product_OutSide ='"+outside+"'";
                            getDataProductbyKey(query);
                        }else if(!pricemin.equals("100000") && !pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                                && !company.equals("Tất cả") && outside.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Type = '"+type+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                                    " AND product_Company = '"+company+"'  AND product_PostApproval ='"+approval+"'";
                            getDataProductbyKey(query);
                        }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && !yearmax.equals("2021")
                                && company.equals("Tất cả") && outside.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Type = '"+type+"' AND product_Price >= '"+pricemin+"'" +
                                    " AND product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"' AND product_PostApproval ='"+approval+"'";
                            getDataProductbyKey(query);
                        }else if(!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && !company.equals("Tất cả") && outside.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Type = '"+type+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                                    " AND product_Year >= '"+yearmin+"' AND pproduct_Company = '"+company+"' AND product_PostApproval ='"+approval+"'";
                            getDataProductbyKey(query);
                        }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && !company.equals("Tất cả") && !outside.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Type = '"+type+"' AND product_Price >='"+pricemin+"' " +
                                    "AND product_company = '"+company+"' AND product_Year >= '"+yearmin+"' AND product_OutSide ='"+outside+"' " +
                                    "AND product_PostApproval ='"+approval+"'";
                            getDataProductbyKey(query);
                        }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                                && !company.equals("Tất cả") && !outside.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Type = '"+type+"' AND product_Price >= '"+pricemin+"'" +
                                    " AND product_OutSide ='"+outside+"' AND product_Company ='"+company+"' AND product_PostApproval ='"+approval+"'";
                            getDataProductbyKey(query);
                        }else if(pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && !company.equals("Tất cả") && !outside.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Type = '"+type+"' AND product_Price <= '"+pricemax+"'" +
                                    " AND product_OutSide ='"+outside+"' AND product_PostApproval ='"+approval+"'" +
                                    "AND product_Year >= '"+yearmin+"' AND product_Company ='"+company+"'";
                            getDataProductbyKey(query);
                        }
                        else if (pricemin.equals("100000") && pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                                && company.equals("Tất cả") && outside.equals("Tất cả") && !TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Type ='"+name+"' AND product_Status = '"+status+"'" +
                                    " AND product_PostApproval ='" + approval + "'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                                && company.equals("Tất cả") && outside.equals("Tất cả") && !TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Type ='"+name+"' AND product_Price >= '"+pricemin+"'" +
                                    " AND product_Status = '"+status+"' AND product_PostApproval ='" + approval + "'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && company.equals("Tất cả") && outside.equals("Tất cả") && !TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Type ='"+name+"' AND product_Price >= '"+pricemin+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' AND product_Status = '"+status+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && company.equals("Tất cả") && outside.equals("Tất cả") && !TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Type ='"+name+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' AND product_Status = '"+status+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && !company.equals("Tất cả") && outside.equals("Tất cả") && !TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Type ='"+name+"' product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' " +
                                    "AND product_Status = '"+status+"' AND product_OutSide = '"+outside+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && company.equals("Tất cả") && !outside.equals("Tất cả") && !TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_OutSide ='"+outside+"' product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' " +
                                    "AND product_Status = '"+status+"' AND product_Type = '"+type+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && !company.equals("Tất cả") && !outside.equals("Tất cả") && !TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+company+"' product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' " +
                                    "AND product_Status = '"+status+"' AND product_OutSide = '"+outside+"' AND product_Type = '"+type+"'";
                            getDataProductbyKey(query);
                        }else if (pricemin.equals("100000") && pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                                && company.equals("Tất cả") && outside.equals("Tất cả") && !TextUtils.isEmpty(madein) && TextUtils.isEmpty(status)) {
                            query = "SELECT * FROM products WHERE product_Type ='"+name+"' AND product_MadeIn = '"+madein+"'" +
                                    " AND product_PostApproval ='" + approval + "'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                                && company.equals("Tất cả") && outside.equals("Tất cả") && !TextUtils.isEmpty(madein) && TextUtils.isEmpty(status)) {
                            query = "SELECT * FROM products WHERE product_Type ='"+name+"' AND product_Price >= '"+pricemin+"'" +
                                    " AND product_MadeIn = '"+madein+"' AND product_PostApproval ='" + approval + "'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && company.equals("Tất cả") && outside.equals("Tất cả") && !TextUtils.isEmpty(madein) && TextUtils.isEmpty(status)) {
                            query = "SELECT * FROM products WHERE product_Type ='"+name+"' AND product_Price >= '"+pricemin+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' AND product_MadeIn = '"+madein+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && company.equals("Tất cả") && outside.equals("Tất cả") && !TextUtils.isEmpty(madein) && TextUtils.isEmpty(status)) {
                            query = "SELECT * FROM products WHERE product_Type ='"+name+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' AND product_MadeIn = '"+madein+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && !company.equals("Tất cả") && outside.equals("Tất cả") && !TextUtils.isEmpty(madein) && TextUtils.isEmpty(status)) {
                            query = "SELECT * FROM products WHERE product_Type ='"+name+"' product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' " +
                                    "AND product_MadeIn = '"+madein+"' AND product_OutSide = '"+outside+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && company.equals("Tất cả") && !outside.equals("Tất cả") && !TextUtils.isEmpty(madein) && TextUtils.isEmpty(status)) {
                            query = "SELECT * FROM products WHERE product_OutSide ='"+outside+"' product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' " +
                                    "AND product_MadeIn = '"+madein+"' AND product_Type = '"+type+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && !company.equals("Tất cả") && !outside.equals("Tất cả") && !TextUtils.isEmpty(status) && !TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+company+"' product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' " +
                                    "AND product_MadeIn = '"+madein+"' AND product_OutSide = '"+outside+"' AND product_Type = '"+type+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && company.equals("Tất cả") && outside.equals("Tất cả") && !TextUtils.isEmpty(status) && !TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Type ='"+name+"' product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' " +
                                    "AND product_MadeIn = '"+madein+"' AND product_Status = '"+status+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && !company.equals("Tất cả") && outside.equals("Tất cả") && !TextUtils.isEmpty(status) && !TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Type ='"+name+"' product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' " +
                                    "AND product_MadeIn = '"+madein+"' AND product_Status = '"+status+"' AND product_OutSide = '"+outside+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && company.equals("Tất cả") && !outside.equals("Tất cả") && !TextUtils.isEmpty(status) && !TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_OutSide ='"+outside+"' product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' " +
                                    "AND product_MadeIn = '"+madein+"' AND product_Status = '"+status+"' AND product_Type = '"+type+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && !company.equals("Tất cả") && !outside.equals("Tất cả") && !TextUtils.isEmpty(status) && !TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+company+"' product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' AND product_Type = '"+type+"' " +
                                    "AND product_MadeIn = '"+madein+"' AND product_Status = '"+status+"' AND product_OutSide = '"+outside+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && !company.equals("Tất cả") && !outside.equals("Tất cả") && !TextUtils.isEmpty(status) && !TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+company+"' product_Price >= '"+pricemin+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' AND product_Type = '"+type+"' " +
                                    "AND product_MadeIn = '"+madein+"' AND product_Status = '"+status+"' AND product_OutSide = '"+outside+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && company.equals("Tất cả") && !outside.equals("Tất cả") && !TextUtils.isEmpty(status) && !TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_OutSide ='"+outside+"' product_Price >= '"+pricemin+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' AND product_Type = '"+type+"' " +
                                    "AND product_MadeIn = '"+madein+"' AND product_Status = '"+status+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && !company.equals("Tất cả") && outside.equals("Tất cả") && !TextUtils.isEmpty(status) && !TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+company+"' product_Price >= '"+pricemin+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' " +
                                    "AND product_MadeIn = '"+madein+"' AND product_Status = '"+status+"' AND product_Type ='"+name+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && company.equals("Tất cả") && outside.equals("Tất cả") && !TextUtils.isEmpty(status) && !TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Type ='"+name+"' product_Price >= '"+pricemin+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' " +
                                    "AND product_MadeIn = '"+madein+"' AND product_Status = '"+status+"'";
                            getDataProductbyKey(query);
                        }
                    }
                });
                bottomSheetSelected.show(getSupportFragmentManager(), bottomSheetSelected.getTag());
            }
        });
    }


    private void OnClickOutside() {
        outside_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ProvinceModel> listColor = new ArrayList<>();
                listColor.add(new ProvinceModel(1, "Bạc"));
                listColor.add(new ProvinceModel(2, "Cát"));
                listColor.add(new ProvinceModel(3, "Ghi"));
                listColor.add(new ProvinceModel(4, "Hồng"));
                listColor.add(new ProvinceModel(5, "Đỏ"));
                listColor.add(new ProvinceModel(6, "Trắng"));
                listColor.add(new ProvinceModel(7, "Đen"));
                listColor.add(new ProvinceModel(8, "Vàng"));
                listColor.add(new ProvinceModel(9, "Cam"));
                listColor.add(new ProvinceModel(10, "Kem"));
                listColor.add(new ProvinceModel(11, "Nâu"));
                listColor.add(new ProvinceModel(12, "Tím"));
                listColor.add(new ProvinceModel(13, "Xanh"));
                listColor.add(new ProvinceModel(14, "Xám"));
                BottomSheetSelected bottomSheetSelected = new BottomSheetSelected(listColor, new ProvinceAdapter.OnItemOnCLick() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onClick(String name) {
                        outside_tv.setText(name);
                        outside = name;
                        String status = saveStatus.getString("status", "");
                        String madein = saveStatus.getString("madein", "");
                        String query;
                        if (price_tv.getText().equals("Không giới hạn") && year_tv.getText().equals("Không giới hạn") &&
                                company.equals("Tất cả") && type.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_OutSide ='"+outside+"' AND product_PostApproval ='" + approval + "'";
                            getDataProductbyKey(query);
                        }else if (price_tv.getText().equals("Không giới hạn") && year_tv.getText().equals("Không giới hạn") &&
                                !company.equals("Tất cả") && type.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_OutSide ='"+outside+"' AND product_Company ='"+company+"'" +
                                    " AND product_PostApproval ='" + approval + "'";
                            getDataProductbyKey(query);
                        }else if (price_tv.getText().equals("Không giới hạn") && year_tv.getText().equals("Không giới hạn") &&
                                company.equals("Tất cả") && !type.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_OutSide ='"+outside+"' AND product_Type ='"+type+"'" +
                                    "AND product_PostApproval ='" + approval + "'";
                            getDataProductbyKey(query);
                        }else if (price_tv.getText().equals("Không giới hạn") && year_tv.getText().equals("Không giới hạn") &&
                                !company.equals("Tất cả") && !type.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_OutSide ='"+outside+"' AND product_Type ='" + type + "'" +
                                    "AND product_Company = '"+company+"' AND product_PostApproval ='"+approval+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && year_tv.getText().equals("Không giới hạn")
                                && company.equals("Tất cả") && type.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_OutSide ='"+outside+"' AND product_Price >= '"+pricemin+"' " +
                                    " AND product_PostApproval ='" + approval + "'";
                            getDataProductbyKey(query);
                        }else if (pricemin.equals("100000") && !pricemax.equals("2000000") && year_tv.getText().equals("Không giới hạn")
                                && company.equals("Tất cả") && type.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_OutSide ='"+outside+"' AND product_Price <= '"+pricemax+"' " +
                                    " AND product_PostApproval ='" + approval + "'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && year_tv.getText().equals("Không giới hạn")
                                && company.equals("Tất cả") && type.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_OutSide ='"+outside+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'  " +
                                    " AND product_PostApproval ='" + approval + "'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && company.equals("Tất cả") && type.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_OutSide ='"+outside+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='" + approval + "'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && yearmin.equals("2000") && !yearmax.equals("2021")
                                && company.equals("Tất cả") && type.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_OutSide ='"+outside+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                                    " AND product_Year <= '"+yearmax+"' AND product_PostApproval ='" + approval + "'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && !yearmax.equals("2021")
                                && company.equals("Tất cả") && type.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_OutSide ='"+outside+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                                    " AND product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"' AND product_PostApproval ='" + approval + "'";
                            getDataProductbyKey(query);
                        }else if(!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && !yearmax.equals("2021")
                                && !company.equals("Tất cả") && type.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_OutSide ='"+outside+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                                    " AND product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"' AND product_PostApproval ='"+approval+"'" +
                                    " AND product_Company = '"+company+"'";
                            getDataProductbyKey(query);
                        }else if(!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && !yearmax.equals("2021")
                                && company.equals("Tất cả") && !type.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_OutSide ='"+outside+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                                    " AND product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"' AND product_PostApproval ='"+approval+"'" +
                                    " AND product_Type = '"+type+"'";
                            getDataProductbyKey(query);
                        }else if(!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && !yearmax.equals("2021")
                                && !company.equals("Tất cả") && !type.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_OutSide ='"+outside+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                                    " AND product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"' AND product_PostApproval ='"+approval+"'" +
                                    " AND product_Type = '"+type+"' AND product_Company = '"+company+"'";
                            getDataProductbyKey(query);
                        }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && !yearmax.equals("2021")
                                && !company.equals("Tất cả") && !type.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_OutSide ='"+outside+"' AND product_Price >= '"+pricemin+"'" +
                                    " AND product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"' AND product_PostApproval ='"+approval+"'" +
                                    " AND product_Type = '"+type+"' AND product_Company = '"+company+"'";
                            getDataProductbyKey(query);
                        }else if(pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && !yearmax.equals("2021")
                                && !company.equals("Tất cả") && !type.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_OutSide ='"+outside+"' AND product_Price <= '"+pricemax+"'" +
                                    " AND product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"' AND product_PostApproval ='"+approval+"'" +
                                    " AND product_Type = '"+type+"' AND product_Company = '"+company+"'";
                            getDataProductbyKey(query);
                        }else if(!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && !company.equals("Tất cả") && !type.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_OutSide ='"+outside+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"'" +
                                    " AND product_Type = '"+type+"' AND product_Company = '"+company+"'";
                            getDataProductbyKey(query);
                        }else if(!pricemin.equals("100000") && !pricemax.equals("2000000") && yearmin.equals("2000") && !yearmax.equals("2021")
                                && !company.equals("Tất cả") && !type.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_OutSide ='"+outside+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                                    " AND product_Year <= '"+yearmax+"' AND product_PostApproval ='"+approval+"'" +
                                    " AND product_Type = '"+type+"' AND product_Company = '"+company+"'";
                            getDataProductbyKey(query);
                        }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && !yearmax.equals("2021")
                                && !company.equals("Tất cả") && type.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_OutSide ='"+outside+"' AND product_Price >= '"+pricemin+"'" +
                                    " AND product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"' AND product_PostApproval ='"+approval+"'" +
                                    " AND product_Company = '"+company+"'";
                            getDataProductbyKey(query);
                        }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && !yearmax.equals("2021")
                                && company.equals("Tất cả") && !type.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_OutSide ='"+outside+"' AND product_Price >= '"+pricemin+"'" +
                                    " AND product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"' AND product_PostApproval ='"+approval+"'" +
                                    " AND product_Type = '"+type+"'";
                            getDataProductbyKey(query);
                        }else if(!pricemin.equals("100000") && !pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                                && !company.equals("Tất cả") && type.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_OutSide ='"+outside+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                                    " AND product_Company = '"+company+"'  AND product_PostApproval ='"+approval+"'";
                            getDataProductbyKey(query);
                        }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && company.equals("Tất cả") && type.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_OutSide ='"+outside+"' AND product_Price >= '"+pricemin+"'" +
                                    " AND product_Year >= '"+yearmin+"'  AND product_PostApproval ='"+approval+"'";
                            getDataProductbyKey(query);
                        }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && !company.equals("Tất cả") && type.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_OutSide ='"+outside+"' AND product_Price >= '"+pricemin+"'" +
                                    " AND product_Company = '"+company+"' AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"'";
                            getDataProductbyKey(query);
                        }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && company.equals("Tất cả") && !type.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_OutSide ='"+outside+"' AND product_Price >= '"+pricemin+"'" +
                                    " AND product_Year >= '"+yearmin+"' product_Type = '"+type+"'  AND product_PostApproval ='"+approval+"'";
                            getDataProductbyKey(query);
                        }
                        else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && !yearmax.equals("2021")
                                && company.equals("Tất cả") && type.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_OutSide ='"+outside+"' AND product_Price >= '"+pricemin+"'" +
                                    " AND product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"' AND product_PostApproval ='"+approval+"'";
                            getDataProductbyKey(query);
                        }else if(!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && !company.equals("Tất cả") && type.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_OutSide ='"+outside+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                                    " AND product_Year >= '"+yearmin+"' AND pproduct_Company = '"+company+"' AND product_PostApproval ='"+approval+"'";
                            getDataProductbyKey(query);
                        }else if(!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && company.equals("Tất cả") && !type.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_OutSide ='"+outside+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                                    " AND product_Year >= '"+yearmin+"' AND product_Type = '"+type+"' AND product_PostApproval ='"+approval+"'";
                            getDataProductbyKey(query);
                        }else if (pricemin.equals("100000") && pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                                && company.equals("Tất cả") && type.equals("Tất cả") && !TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_OutSide ='"+name+"' AND product_Status = '"+status+"'" +
                                    " AND product_PostApproval ='" + approval + "'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                                && company.equals("Tất cả") && type.equals("Tất cả") && !TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_OutSide ='"+name+"' AND product_Price >= '"+pricemin+"'" +
                                    " AND product_Status = '"+status+"' AND product_PostApproval ='" + approval + "'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && company.equals("Tất cả") && type.equals("Tất cả") && !TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_OutSide ='"+name+"' AND product_Price >= '"+pricemin+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' AND product_Status = '"+status+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && company.equals("Tất cả") && type.equals("Tất cả") && !TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_OutSide ='"+name+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' AND product_Status = '"+status+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && !company.equals("Tất cả") && type.equals("Tất cả") && !TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+company+"' product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' " +
                                    "AND product_Status = '"+status+"' AND product_OutSide = '"+outside+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && company.equals("Tất cả") && !type.equals("Tất cả") && !TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_OutSide ='"+name+"' product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' " +
                                    "AND product_Status = '"+status+"' AND product_Type = '"+type+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && !company.equals("Tất cả") && !type.equals("Tất cả") && !TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+company+"' product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' " +
                                    "AND product_Status = '"+status+"' AND product_OutSide = '"+outside+"' AND product_Type = '"+type+"'";
                            getDataProductbyKey(query);
                        }else if (pricemin.equals("100000") && pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                                && company.equals("Tất cả") && type.equals("Tất cả") && !TextUtils.isEmpty(madein) && TextUtils.isEmpty(status)) {
                            query = "SELECT * FROM products WHERE product_OutSide ='"+name+"' AND product_MadeIn = '"+madein+"'" +
                                    " AND product_PostApproval ='" + approval + "'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                                && company.equals("Tất cả") && type.equals("Tất cả") && !TextUtils.isEmpty(madein) && TextUtils.isEmpty(status)) {
                            query = "SELECT * FROM products WHERE product_OutSide ='"+name+"' AND product_Price >= '"+pricemin+"'" +
                                    " AND product_MadeIn = '"+madein+"' AND product_PostApproval ='" + approval + "'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && company.equals("Tất cả") && type.equals("Tất cả") && !TextUtils.isEmpty(madein) && TextUtils.isEmpty(status)) {
                            query = "SELECT * FROM products WHERE product_OutSide ='"+name+"' AND product_Price >= '"+pricemin+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' AND product_MadeIn = '"+madein+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && company.equals("Tất cả") && type.equals("Tất cả") && !TextUtils.isEmpty(madein) && TextUtils.isEmpty(status)) {
                            query = "SELECT * FROM products WHERE product_OutSide ='"+name+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' AND product_MadeIn = '"+madein+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && !company.equals("Tất cả") && type.equals("Tất cả") && !TextUtils.isEmpty(madein) && TextUtils.isEmpty(status)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+company+"' product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' " +
                                    "AND product_MadeIn = '"+madein+"' AND product_OutSide = '"+outside+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && company.equals("Tất cả") && !type.equals("Tất cả") && !TextUtils.isEmpty(madein) && TextUtils.isEmpty(status)) {
                            query = "SELECT * FROM products WHERE product_OutSide ='"+name+"' product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' " +
                                    "AND product_MadeIn = '"+madein+"' AND product_Type = '"+type+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && !company.equals("Tất cả") && !type.equals("Tất cả") && !TextUtils.isEmpty(status) && TextUtils.isEmpty(status)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+company+"' product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' " +
                                    "AND product_MadeIn = '"+madein+"' AND product_OutSide = '"+outside+"' AND product_Type = '"+type+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && company.equals("Tất cả") && type.equals("Tất cả") && !TextUtils.isEmpty(status) && !TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_OutSide ='"+name+"' product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' " +
                                    "AND product_MadeIn = '"+madein+"' AND product_Status = '"+status+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && !company.equals("Tất cả") && type.equals("Tất cả") && !TextUtils.isEmpty(status) && !TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+company+"' product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' " +
                                    "AND product_MadeIn = '"+madein+"' AND product_Status = '"+status+"' AND product_OutSide = '"+outside+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && company.equals("Tất cả") && !type.equals("Tất cả") && !TextUtils.isEmpty(status) && !TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_OutSide ='"+name+"' product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' " +
                                    "AND product_MadeIn = '"+madein+"' AND product_Status = '"+status+"' AND product_Type = '"+type+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && !company.equals("Tất cả") && !type.equals("Tất cả") && !TextUtils.isEmpty(status) && !TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+company+"' product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' AND product_Type = '"+type+"' " +
                                    "AND product_MadeIn = '"+madein+"' AND product_Status = '"+status+"' AND product_OutSide = '"+outside+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && !company.equals("Tất cả") && !type.equals("Tất cả") && !TextUtils.isEmpty(status) && !TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+company+"' product_Price >= '"+pricemin+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' AND product_Type = '"+type+"' " +
                                    "AND product_MadeIn = '"+madein+"' AND product_Status = '"+status+"' AND product_OutSide = '"+outside+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && company.equals("Tất cả") && !type.equals("Tất cả") && !TextUtils.isEmpty(status) && !TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_OutSide ='"+name+"' product_Price >= '"+pricemin+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' AND product_Type = '"+type+"' " +
                                    "AND product_MadeIn = '"+madein+"' AND product_Status = '"+status+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && !company.equals("Tất cả") && type.equals("Tất cả") && !TextUtils.isEmpty(status) && !TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+company+"' product_Price >= '"+pricemin+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' " +
                                    "AND product_MadeIn = '"+madein+"' AND product_Status = '"+status+"' AND product_OutSide = '"+outside+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && company.equals("Tất cả") && type.equals("Tất cả") && !TextUtils.isEmpty(status) && !TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_OutSide ='"+name+"' product_Price >= '"+pricemin+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' " +
                                    "AND product_MadeIn = '"+madein+"' AND product_Status = '"+status+"'";
                            getDataProductbyKey(query);
                        }
                    }
                });
                bottomSheetSelected.show(getSupportFragmentManager(), bottomSheetSelected.getTag());
            }
        });
    }


    private void OnClickCompany() {
        company_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetCompany bottomSheetCompany = new BottomSheetCompany(companyModelList, new BottomSheetCompanyAdapter.OnItemOnCLick() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onClick(String name, int id) {
                        company_tv.setText(name);
                        company = name;
                        String status = saveStatus.getString("status", "");
                        String madein = saveStatus.getString("madein", "");
                        Log.d("anh", status);
                        Log.d("anh", madein);
                        String query;
                        if (price_tv.getText().equals("Không giới hạn") && year_tv.getText().equals("Không giới hạn") &&
                                outside.equals("Tất cả") && type.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Company ='" + name + "' AND product_PostApproval ='" + approval + "'";
                            getDataProductbyKey(query);
                        }else if (price_tv.getText().equals("Không giới hạn") && year_tv.getText().equals("Không giới hạn") &&
                                !outside.equals("Tất cả") && type.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Company ='" + name + "' AND product_OutSide ='"+outside+"'" +
                                    " AND product_PostApproval ='" + approval + "'";
                            getDataProductbyKey(query);
                        }else if (price_tv.getText().equals("Không giới hạn") && year_tv.getText().equals("Không giới hạn") &&
                                outside.equals("Tất cả") && !type.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Company ='" +name+ "' AND product_Type ='"+type+"'" +
                                    "AND product_PostApproval ='" + approval + "'";
                            getDataProductbyKey(query);
                        }else if (price_tv.getText().equals("Không giới hạn") && year_tv.getText().equals("Không giới hạn") &&
                                !outside.equals("Tất cả") && !type.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Company ='" + name + "' AND product_Type ='" + type + "'" +
                                    "AND product_OutSide = '"+outside+"' AND product_PostApproval ='"+approval+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && year_tv.getText().equals("Không giới hạn")
                                && outside.equals("Tất cả") && type.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+name+"' AND product_Price >= '"+pricemin+"' " +
                                    " AND product_PostApproval ='" + approval + "'";
                            getDataProductbyKey(query);
                        }else if (pricemin.equals("100000") && !pricemax.equals("2000000") && year_tv.getText().equals("Không giới hạn")
                                && outside.equals("Tất cả") && type.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+name+"' AND product_Price <= '"+pricemax+"' " +
                                    " AND product_PostApproval ='" + approval + "'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && year_tv.getText().equals("Không giới hạn")
                                && outside.equals("Tất cả") && type.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+name+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'  " +
                                    " AND product_PostApproval ='" + approval + "'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && outside.equals("Tất cả") && type.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+name+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='" + approval + "'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && yearmin.equals("2000") && !yearmax.equals("2021")
                                && outside.equals("Tất cả") && type.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+name+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                                    " AND product_Year <= '"+yearmax+"' AND product_PostApproval ='" + approval + "'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && !yearmax.equals("2021")
                                && outside.equals("Tất cả") && type.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+name+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                                    " AND product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"' AND product_PostApproval ='" + approval + "'";
                            getDataProductbyKey(query);
                        }else if(!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && !yearmax.equals("2021")
                                && !outside.equals("Tất cả") && type.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+name+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                                    " AND product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"' AND product_PostApproval ='"+approval+"'" +
                                    " AND product_OutSide = '"+outside+"'";
                            getDataProductbyKey(query);
                        }else if(!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && !yearmax.equals("2021")
                                && outside.equals("Tất cả") && !type.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+name+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                                    " AND product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"' AND product_PostApproval ='"+approval+"'" +
                                    " AND product_Type = '"+type+"'";
                            getDataProductbyKey(query);
                        }else if(!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && !yearmax.equals("2021")
                                && !outside.equals("Tất cả") && !type.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+name+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                                    " AND product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"' AND product_PostApproval ='"+approval+"'" +
                                    " AND product_Type = '"+type+"' AND product_OutSide = '"+outside+"'";
                            getDataProductbyKey(query);
                        }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && !yearmax.equals("2021")
                                && !outside.equals("Tất cả") && !type.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+name+"' AND product_Price >= '"+pricemin+"'" +
                                    " AND product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"' AND product_PostApproval ='"+approval+"'" +
                                    " AND product_Type = '"+type+"' AND product_OutSide = '"+outside+"'";
                            getDataProductbyKey(query);
                        }else if(pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && !yearmax.equals("2021")
                                && !outside.equals("Tất cả") && !type.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+name+"' AND product_Price <= '"+pricemax+"'" +
                                    " AND product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"' AND product_PostApproval ='"+approval+"'" +
                                    " AND product_Type = '"+type+"' AND product_OutSide = '"+outside+"'";
                            getDataProductbyKey(query);
                        }else if(!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && !outside.equals("Tất cả") && !type.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+name+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"'" +
                                    " AND product_Type = '"+type+"' AND product_OutSide = '"+outside+"'";
                            getDataProductbyKey(query);
                        }else if(!pricemin.equals("100000") && !pricemax.equals("2000000") && yearmin.equals("2000") && !yearmax.equals("2021")
                                && !outside.equals("Tất cả") && !type.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+name+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                                    " AND product_Year <= '"+yearmax+"' AND product_PostApproval ='"+approval+"'" +
                                    " AND product_Type = '"+type+"' AND product_OutSide = '"+outside+"'";
                            getDataProductbyKey(query);
                        }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && !yearmax.equals("2021")
                                && !outside.equals("Tất cả") && type.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+name+"' AND product_Price >= '"+pricemin+"'" +
                                    " AND product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"' AND product_PostApproval ='"+approval+"'" +
                                    " AND product_OutSide = '"+outside+"'";
                            getDataProductbyKey(query);
                        }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && !yearmax.equals("2021")
                                && outside.equals("Tất cả") && !type.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+name+"' AND product_Price >= '"+pricemin+"'" +
                                    " AND product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"' AND product_PostApproval ='"+approval+"'" +
                                    " AND product_Type = '"+type+"'";
                            getDataProductbyKey(query);
                        }else if(!pricemin.equals("100000") && !pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                                && !outside.equals("Tất cả") && type.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+name+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                                    " AND product_OutSide = '"+outside+"'  AND product_PostApproval ='"+approval+"'";
                            getDataProductbyKey(query);
                        }else if(!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && !yearmax.equals("2021")
                                && outside.equals("Tất cả") && type.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+name+"' AND product_Price >= '"+pricemin+"'" +
                                    " AND product_Year BETWEEN '"+yearmin+"' AND '"+yearmax+"' AND product_PostApproval ='"+approval+"'";
                            getDataProductbyKey(query);
                        }else if(!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && !outside.equals("Tất cả") && type.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+name+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                                    " AND product_Year >= '"+yearmin+"' AND product_OutSide = '"+outside+"' AND product_PostApproval ='"+approval+"'";
                            getDataProductbyKey(query);
                        }else if(!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && outside.equals("Tất cả") && !type.equals("Tất cả") && TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+name+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"'" +
                                    " AND product_Year >= '"+yearmin+"' AND product_Type = '"+type+"' AND product_PostApproval ='"+approval+"'";
                            getDataProductbyKey(query);

                        }else if (pricemin.equals("100000") && pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                                && outside.equals("Tất cả") && type.equals("Tất cả") && !TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+name+"' AND product_Status = '"+status+"'" +
                                    " AND product_PostApproval ='" + approval + "'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                                && outside.equals("Tất cả") && type.equals("Tất cả") && !TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+name+"' AND product_Price >= '"+pricemin+"'" +
                                    " AND product_Status = '"+status+"' AND product_PostApproval ='" + approval + "'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && outside.equals("Tất cả") && type.equals("Tất cả") && !TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+name+"' AND product_Price >= '"+pricemin+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' AND product_Status = '"+status+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && outside.equals("Tất cả") && type.equals("Tất cả") && !TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+name+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' AND product_Status = '"+status+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && !outside.equals("Tất cả") && type.equals("Tất cả") && !TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+name+"' product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' " +
                                    "AND product_Status = '"+status+"' AND product_OutSide = '"+outside+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && outside.equals("Tất cả") && !type.equals("Tất cả") && !TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+name+"' product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' " +
                                    "AND product_Status = '"+status+"' AND product_Type = '"+type+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && !outside.equals("Tất cả") && !type.equals("Tất cả") && !TextUtils.isEmpty(status) && TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+name+"' product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' " +
                                    "AND product_Status = '"+status+"' AND product_OutSide = '"+outside+"' AND product_Type = '"+type+"'";
                            getDataProductbyKey(query);
                        }else if (pricemin.equals("100000") && pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                                && outside.equals("Tất cả") && type.equals("Tất cả") && !TextUtils.isEmpty(madein) && TextUtils.isEmpty(status)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+name+"' AND product_MadeIn = '"+madein+"'" +
                                    " AND product_PostApproval ='" + approval + "'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                                && outside.equals("Tất cả") && type.equals("Tất cả") && !TextUtils.isEmpty(madein) && TextUtils.isEmpty(status)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+name+"' AND product_Price >= '"+pricemin+"'" +
                                    " AND product_MadeIn = '"+madein+"' AND product_PostApproval ='" + approval + "'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && outside.equals("Tất cả") && type.equals("Tất cả") && !TextUtils.isEmpty(madein) && TextUtils.isEmpty(status)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+name+"' AND product_Price >= '"+pricemin+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' AND product_MadeIn = '"+madein+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && outside.equals("Tất cả") && type.equals("Tất cả") && !TextUtils.isEmpty(madein) && TextUtils.isEmpty(status)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+name+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' AND product_MadeIn = '"+madein+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && !outside.equals("Tất cả") && type.equals("Tất cả") && !TextUtils.isEmpty(madein) && TextUtils.isEmpty(status)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+name+"' product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' " +
                                    "AND product_MadeIn = '"+madein+"' AND product_OutSide = '"+outside+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && outside.equals("Tất cả") && !type.equals("Tất cả") && !TextUtils.isEmpty(madein) && TextUtils.isEmpty(status)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+name+"' product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' " +
                                    "AND product_MadeIn = '"+madein+"' AND product_Type = '"+type+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && !outside.equals("Tất cả") && !type.equals("Tất cả") && !TextUtils.isEmpty(status) && TextUtils.isEmpty(status)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+name+"' product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' " +
                                    "AND product_MadeIn = '"+madein+"' AND product_OutSide = '"+outside+"' AND product_Type = '"+type+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && outside.equals("Tất cả") && type.equals("Tất cả") && !TextUtils.isEmpty(status) && !TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+name+"' product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' " +
                                    "AND product_MadeIn = '"+madein+"' AND product_Status = '"+status+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && !outside.equals("Tất cả") && type.equals("Tất cả") && !TextUtils.isEmpty(status) && !TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+name+"' product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' " +
                                    "AND product_MadeIn = '"+madein+"' AND product_Status = '"+status+"' AND product_OutSide = '"+outside+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && outside.equals("Tất cả") && !type.equals("Tất cả") && !TextUtils.isEmpty(status) && !TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+name+"' product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' " +
                                    "AND product_MadeIn = '"+madein+"' AND product_Status = '"+status+"' AND product_Type = '"+type+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && !outside.equals("Tất cả") && !type.equals("Tất cả") && !TextUtils.isEmpty(status) && !TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+name+"' product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' AND product_Type = '"+type+"' " +
                                    "AND product_MadeIn = '"+madein+"' AND product_Status = '"+status+"' AND product_OutSide = '"+outside+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && !outside.equals("Tất cả") && !type.equals("Tất cả") && !TextUtils.isEmpty(status) && !TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+name+"' product_Price >= '"+pricemin+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' AND product_Type = '"+type+"' " +
                                    "AND product_MadeIn = '"+madein+"' AND product_Status = '"+status+"' AND product_OutSide = '"+outside+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && outside.equals("Tất cả") && !type.equals("Tất cả") && !TextUtils.isEmpty(status) && !TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+name+"' product_Price >= '"+pricemin+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' AND product_Type = '"+type+"' " +
                                    "AND product_MadeIn = '"+madein+"' AND product_Status = '"+status+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && !outside.equals("Tất cả") && type.equals("Tất cả") && !TextUtils.isEmpty(status) && !TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+name+"' product_Price >= '"+pricemin+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' " +
                                    "AND product_MadeIn = '"+madein+"' AND product_Status = '"+status+"' AND product_OutSide = '"+outside+"'";
                            getDataProductbyKey(query);
                        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                                && outside.equals("Tất cả") && type.equals("Tất cả") && !TextUtils.isEmpty(status) && !TextUtils.isEmpty(madein)) {
                            query = "SELECT * FROM products WHERE product_Company ='"+name+"' product_Price >= '"+pricemin+"' " +
                                    " AND product_Year >= '"+yearmin+"' AND product_PostApproval ='"+approval+"' " +
                                    "AND product_MadeIn = '"+madein+"' AND product_Status = '"+status+"'";
                            getDataProductbyKey(query);
                        }
                    }
                });
                bottomSheetCompany.show(getSupportFragmentManager(), bottomSheetCompany.getTag());
            }
        });
    }


    private void checkGetDataStatus(){
        String status = saveStatus.getString("status", "");
        String madein = saveStatus.getString("madein", "");
        String query;
        if (price_tv.getText().equals("Không giới hạn") && year_tv.getText().equals("Không giới hạn") &&
                outside.equals("Tất cả") && type.equals("Tất cả") && company.equals("Tất cả") && TextUtils.isEmpty(madein)) {
            query = "SELECT * FROM products WHERE product_Status ='"+status+"' AND product_PostApproval ='" + approval + "'";
            getDataProductbyKey(query);
        }else if (pricemin.equals("100000") && pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                && outside.equals("Tất cả") && type.equals("Tất cả") && !company.equals("Tất cả") && TextUtils.isEmpty(madein)) {
            query = "SELECT * FROM products WHERE product_Status = '"+status+"' AND product_Company ='"+company+"' " +
                    "AND product_PostApproval ='"+approval+"'";
            getDataProductbyKey(query);
        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                && outside.equals("Tất cả") && type.equals("Tất cả") && company.equals("Tất cả") && TextUtils.isEmpty(madein)) {
            query = "SELECT * FROM products WHERE product_Status = '"+status+"' AND product_Price >= '"+pricemin+"' " +
                    "AND product_PostApproval ='"+approval+"'";
            getDataProductbyKey(query);
        }else if (pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                && outside.equals("Tất cả") && type.equals("Tất cả") && company.equals("Tất cả") && TextUtils.isEmpty(madein)) {
            query = "SELECT * FROM products WHERE product_Status = '"+status+"' AND product_Year >= '"+yearmin+"' " +
                    "AND product_PostApproval ='"+approval+"'";
            getDataProductbyKey(query);
        }else if (pricemin.equals("100000") && pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                && !outside.equals("Tất cả") && type.equals("Tất cả") && company.equals("Tất cả") && TextUtils.isEmpty(madein)) {
            query = "SELECT * FROM products WHERE product_Status = '"+status+"' AND product_PostApproval ='"+approval+"' " +
                    "AND product_OutSide = '"+outside+"'";
            getDataProductbyKey(query);
        }else if (pricemin.equals("100000") && pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                && outside.equals("Tất cả") && !type.equals("Tất cả") && company.equals("Tất cả") && TextUtils.isEmpty(madein)) {
            query = "SELECT * FROM products WHERE product_Status = '"+status+"' AND product_PostApproval ='"+approval+"' " +
                    "AND product_Type = '"+type+"'";
            getDataProductbyKey(query);
        }else if (pricemin.equals("100000") && pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                && outside.equals("Tất cả") && type.equals("Tất cả") && company.equals("Tất cả") && !TextUtils.isEmpty(madein)) {
            query = "SELECT * FROM products WHERE product_Status = '"+status+"' AND product_PostApproval ='"+approval+"' " +
                    " AND product_MadeIn = '"+madein+"'";
            getDataProductbyKey(query);
        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                && outside.equals("Tất cả") && type.equals("Tất cả") && !company.equals("Tất cả") && TextUtils.isEmpty(madein)) {
            query = "SELECT * FROM products WHERE product_Status = '"+status+"' AND product_PostApproval ='"+approval+"' " +
                    " AND product_Price >= '"+pricemin+"' AND product_Company = '"+company+"'";
            getDataProductbyKey(query);
        }else if (pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                && outside.equals("Tất cả") && type.equals("Tất cả") && !company.equals("Tất cả") && TextUtils.isEmpty(madein)) {
            query = "SELECT * FROM products WHERE product_Status = '"+status+"' AND product_PostApproval ='"+approval+"' " +
                    " AND product_Year >= '"+yearmin+"' AND product_Company = '"+company+"'";
            getDataProductbyKey(query);
        }else if (pricemin.equals("100000") && pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                && !outside.equals("Tất cả") && type.equals("Tất cả") && !company.equals("Tất cả") && TextUtils.isEmpty(madein)) {
            query = "SELECT * FROM products WHERE product_Status = '"+status+"' AND product_PostApproval ='"+approval+"' " +
                    " AND product_OutSide >= '"+outside+"' AND product_Company = '"+company+"'";
            getDataProductbyKey(query);
        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                && outside.equals("Tất cả") && type.equals("Tất cả") && !company.equals("Tất cả") && TextUtils.isEmpty(madein)) {
            query = "SELECT * FROM products WHERE product_Status = '"+status+"' AND product_PostApproval ='"+approval+"' " +
                    " AND product_Year >= '"+yearmin+"' AND product_Price >= '"+pricemin+"' AND product_Company = '"+company+"'";
            getDataProductbyKey(query);
        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                && !outside.equals("Tất cả") && type.equals("Tất cả") && !company.equals("Tất cả") && TextUtils.isEmpty(madein)) {
            query = "SELECT * FROM products WHERE product_Status = '"+status+"' AND product_PostApproval ='"+approval+"' " +
                    " AND product_Year >= '"+yearmin+"' AND product_Price >= '"+pricemin+"' AND product_Company = '"+company+"'" +
                    "AND product_OutSide = '"+outside+"'";
            getDataProductbyKey(query);
        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                && !outside.equals("Tất cả") && type.equals("Tất cả") && !company.equals("Tất cả") && !TextUtils.isEmpty(madein)) {
            query = "SELECT * FROM products WHERE product_Status = '"+status+"' AND product_PostApproval ='"+approval+"' " +
                    " AND product_Year >= '"+yearmin+"' AND product_Price >= '"+pricemin+"' AND product_Company = '"+company+"'" +
                    "AND product_OutSide = '"+outside+"' AND product_MadeIn = '"+madein+"'";
            getDataProductbyKey(query);
        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                && !outside.equals("Tất cả") && !type.equals("Tất cả") && !company.equals("Tất cả") && !TextUtils.isEmpty(madein)) {
            query = "SELECT * FROM products WHERE product_Status = '"+status+"' AND product_PostApproval ='"+approval+"' " +
                    " AND product_Year >= '"+yearmin+"' AND product_Price >= '"+pricemin+"' AND product_Company = '"+company+"'" +
                    "AND product_OutSide = '"+outside+"' AND product_MadeIn = '"+madein+"' AND product_Type ='"+type+"'";
            getDataProductbyKey(query);
        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                && !outside.equals("Tất cả") && type.equals("Tất cả") && !company.equals("Tất cả") && !TextUtils.isEmpty(madein)) {
            query = "SELECT * FROM products WHERE product_Status = '"+status+"' AND product_PostApproval ='"+approval+"' " +
                    " AND product_Year >= '"+yearmin+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' AND product_Company = '"+company+"'" +
                    "AND product_OutSide = '"+outside+"' AND product_MadeIn = '"+madein+"'";
            getDataProductbyKey(query);
        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                && !outside.equals("Tất cả") && type.equals("Tất cả") && !company.equals("Tất cả") && TextUtils.isEmpty(madein)) {
            query = "SELECT * FROM products WHERE product_Status = '"+status+"' AND product_PostApproval ='"+approval+"' " +
                    " AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' AND product_Company = '"+company+"'" +
                    "AND product_OutSide = '"+outside+"'";
            getDataProductbyKey(query);
        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                && !outside.equals("Tất cả") && type.equals("Tất cả") && !company.equals("Tất cả") && TextUtils.isEmpty(madein)) {
            query = "SELECT * FROM products WHERE product_Status = '"+status+"' AND product_PostApproval ='"+approval+"' " +
                    " AND product_Year >= '"+yearmin+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' AND product_Company = '"+company+"'" +
                    "AND product_OutSide = '"+outside+"'";
            getDataProductbyKey(query);
        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                && outside.equals("Tất cả") && type.equals("Tất cả") && company.equals("Tất cả") && TextUtils.isEmpty(madein)) {
            query = "SELECT * FROM products WHERE product_Status = '"+status+"' AND product_PostApproval ='"+approval+"' " +
                    " AND product_Year >= '"+yearmin+"' AND product_Price >= '"+pricemin+"'";
            getDataProductbyKey(query);
        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                && !outside.equals("Tất cả") && type.equals("Tất cả") && company.equals("Tất cả") && TextUtils.isEmpty(madein)) {
            query = "SELECT * FROM products WHERE product_Status = '"+status+"' AND product_PostApproval ='"+approval+"' " +
                    " AND product_Price >= '"+pricemin+"' AND product_OutSide = '"+outside+"' ";
            getDataProductbyKey(query);
        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                && !outside.equals("Tất cả") && type.equals("Tất cả") && company.equals("Tất cả") && TextUtils.isEmpty(madein)) {
            query = "SELECT * FROM products WHERE product_Status = '"+status+"' AND product_PostApproval ='"+approval+"' " +
                    " AND product_Year >= '"+yearmin+"' AND product_Price >= '"+pricemin+"' AND product_OutSide = '"+outside+"' ";
            getDataProductbyKey(query);
        }else if (pricemin.equals("100000") && pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                && outside.equals("Tất cả") && type.equals("Tất cả") && !company.equals("Tất cả") && !TextUtils.isEmpty(madein)) {
            query = "SELECT * FROM products WHERE product_Status = '"+status+"' AND product_PostApproval ='"+approval+"' " +
                    " AND product_Company = '"+company+"' AND product_MadeIn = '"+madein+"'";
            getDataProductbyKey(query);
        }else if (pricemin.equals("100000") && pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                && !outside.equals("Tất cả") && type.equals("Tất cả") && !company.equals("Tất cả") && !TextUtils.isEmpty(madein)) {
            query = "SELECT * FROM products WHERE product_Status = '"+status+"' AND product_PostApproval ='"+approval+"' " +
                    "AND product_Company = '"+company+"' AND product_OutSide = '"+outside+"' AND product_MadeIn = '"+madein+"' ";
            getDataProductbyKey(query);
        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                && !outside.equals("Tất cả") && type.equals("Tất cả") && !company.equals("Tất cả") && !TextUtils.isEmpty(madein)) {
            query = "SELECT * FROM products WHERE product_Status = '"+status+"' AND product_PostApproval ='"+approval+"' " +
                    "AND product_Price >= '"+pricemin+"' AND product_Company = '"+company+"'" +
                    "AND product_OutSide = '"+outside+"' AND product_MadeIn = '"+madein+"' ";
            getDataProductbyKey(query);
        }else if (pricemin.equals("100000") && pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                && outside.equals("Tất cả") && !type.equals("Tất cả") && company.equals("Tất cả") && !TextUtils.isEmpty(madein)) {
            query = "SELECT * FROM products WHERE product_Status = '"+status+"' AND product_PostApproval ='"+approval+"' " +
                    "AND product_MadeIn = '"+madein+"' AND product_Type ='"+type+"'";
            getDataProductbyKey(query);
        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                && outside.equals("Tất cả") && type.equals("Tất cả") && company.equals("Tất cả") && !TextUtils.isEmpty(madein)) {
            query = "SELECT * FROM products WHERE product_Status = '"+status+"' AND product_PostApproval ='"+approval+"' " +
                    "AND product_Price >= '"+pricemin+"' AND product_MadeIn = '"+madein+"' ";
            getDataProductbyKey(query);
        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                && outside.equals("Tất cả") && type.equals("Tất cả") && company.equals("Tất cả") && !TextUtils.isEmpty(madein)) {
            query = "SELECT * FROM products WHERE product_Status = '"+status+"' AND product_PostApproval ='"+approval+"' " +
                    " AND product_Year >= '"+yearmin+"' AND product_Price >= '"+pricemin+"' AND product_MadeIn = '"+madein+"'";
            getDataProductbyKey(query);
        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                && outside.equals("Tất cả") && type.equals("Tất cả") && company.equals("Tất cả") && !TextUtils.isEmpty(madein)) {
            query = "SELECT * FROM products WHERE product_Status = '"+status+"' AND product_PostApproval ='"+approval+"' " +
                    " AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' AND product_MadeIn = '"+madein+"'";
            getDataProductbyKey(query);
        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                && outside.equals("Tất cả") && type.equals("Tất cả") && company.equals("Tất cả") && !TextUtils.isEmpty(madein)) {
            query = "SELECT * FROM products WHERE product_Status = '"+status+"' AND product_PostApproval ='"+approval+"' " +
                    " AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' AND product_MadeIn = '"+madein+"'" +
                    " AND product_Year >= '"+yearmin+"' ";
            getDataProductbyKey(query);
        }else if (pricemin.equals("100000") && pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                && !outside.equals("Tất cả") && !type.equals("Tất cả") && !company.equals("Tất cả") && !TextUtils.isEmpty(madein)) {
            query = "SELECT * FROM products WHERE product_Status = '"+status+"' AND product_PostApproval ='"+approval+"' " +
                    "AND product_Company = '"+company+"' AND product_OutSide = '"+outside+"' "+
                    "AND product_MadeIn = '"+madein+"' AND product_Type ='"+type+"'";
            getDataProductbyKey(query);
        }else if (pricemin.equals("100000") && pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                && outside.equals("Tất cả") && !type.equals("Tất cả") && !company.equals("Tất cả") && !TextUtils.isEmpty(madein)) {
            query = "SELECT * FROM products WHERE product_Status = '"+status+"' AND product_PostApproval ='"+approval+"' " +
                    "AND product_Company = '"+company+"' AND product_MadeIn = '"+madein+"' AND product_Type ='"+type+"'";
            getDataProductbyKey(query);
        }
    }


    private void checkGetDataMadeIn(){
        String status = saveStatus.getString("status", "");
        String madein = saveStatus.getString("madein", "");
        String query;
        if (price_tv.getText().equals("Không giới hạn") && year_tv.getText().equals("Không giới hạn") &&
                outside.equals("Tất cả") && type.equals("Tất cả") && company.equals("Tất cả") && TextUtils.isEmpty(status)) {
            query = "SELECT * FROM products WHERE product_MadeIn ='"+madein+"' AND product_PostApproval ='" + approval + "'";
            getDataProductbyKey(query);
        }else if (pricemin.equals("100000") && pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                && outside.equals("Tất cả") && type.equals("Tất cả") && !company.equals("Tất cả") && TextUtils.isEmpty(status)) {
            query = "SELECT * FROM products WHERE product_MadeIn ='"+madein+"' AND product_Company ='"+company+"' " +
                    "AND product_PostApproval ='"+approval+"'";
            getDataProductbyKey(query);
        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                && outside.equals("Tất cả") && type.equals("Tất cả") && company.equals("Tất cả") && TextUtils.isEmpty(status)) {
            query = "SELECT * FROM products WHERE product_MadeIn ='"+madein+"' AND product_Price >= '"+pricemin+"' " +
                    "AND product_PostApproval ='"+approval+"'";
            getDataProductbyKey(query);
        }else if (pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                && outside.equals("Tất cả") && type.equals("Tất cả") && company.equals("Tất cả") && TextUtils.isEmpty(status)) {
            query = "SELECT * FROM products WHERE product_MadeIn ='"+madein+"' AND product_Year >= '"+yearmin+"' " +
                    "AND product_PostApproval ='"+approval+"'";
            getDataProductbyKey(query);
        }else if (pricemin.equals("100000") && pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                && !outside.equals("Tất cả") && type.equals("Tất cả") && company.equals("Tất cả") && TextUtils.isEmpty(status)) {
            query = "SELECT * FROM products WHERE product_MadeIn ='"+madein+"' AND product_PostApproval ='"+approval+"' " +
                    "AND product_OutSide = '"+outside+"'";
            getDataProductbyKey(query);
        }else if (pricemin.equals("100000") && pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                && outside.equals("Tất cả") && !type.equals("Tất cả") && company.equals("Tất cả") && TextUtils.isEmpty(status)) {
            query = "SELECT * FROM products WHERE product_MadeIn ='"+madein+"' AND product_PostApproval ='"+approval+"' " +
                    "AND product_Type = '"+type+"'";
            getDataProductbyKey(query);
        }else if (pricemin.equals("100000") && pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                && outside.equals("Tất cả") && type.equals("Tất cả") && company.equals("Tất cả") && !TextUtils.isEmpty(status)) {
            query = "SELECT * FROM products WHERE product_Status = '"+status+"' AND product_PostApproval ='"+approval+"' " +
                    " AND product_MadeIn = '"+madein+"'";
            getDataProductbyKey(query);
        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                && outside.equals("Tất cả") && type.equals("Tất cả") && !company.equals("Tất cả") && TextUtils.isEmpty(status)) {
            query = "SELECT * FROM products WHERE product_MadeIn ='"+madein+"' AND product_PostApproval ='"+approval+"' " +
                    " AND product_Price >= '"+pricemin+"' AND product_Company = '"+company+"'";
            getDataProductbyKey(query);
        }else if (pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                && outside.equals("Tất cả") && type.equals("Tất cả") && !company.equals("Tất cả") && TextUtils.isEmpty(status)) {
            query = "SELECT * FROM products WHERE product_MadeIn ='"+madein+"' AND product_PostApproval ='"+approval+"' " +
                    " AND product_Year >= '"+yearmin+"' AND product_Company = '"+company+"'";
            getDataProductbyKey(query);
        }else if (pricemin.equals("100000") && pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                && !outside.equals("Tất cả") && type.equals("Tất cả") && !company.equals("Tất cả") && TextUtils.isEmpty(status)) {
            query = "SELECT * FROM products WHERE product_MadeIn ='"+madein+"' AND product_PostApproval ='"+approval+"' " +
                    " AND product_OutSide >= '"+outside+"' AND product_Company = '"+company+"'";
            getDataProductbyKey(query);
        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                && outside.equals("Tất cả") && type.equals("Tất cả") && !company.equals("Tất cả") && TextUtils.isEmpty(status)) {
            query = "SELECT * FROM products WHERE product_MadeIn ='"+madein+"' AND product_PostApproval ='"+approval+"' " +
                    " AND product_Year >= '"+yearmin+"' AND product_Price >= '"+pricemin+"' AND product_Company = '"+company+"'";
            getDataProductbyKey(query);
        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                && !outside.equals("Tất cả") && type.equals("Tất cả") && !company.equals("Tất cả") && TextUtils.isEmpty(status)) {
            query = "SELECT * FROM products WHERE product_MadeIn ='"+madein+"' AND product_PostApproval ='"+approval+"' " +
                    " AND product_Year >= '"+yearmin+"' AND product_Price >= '"+pricemin+"' AND product_Company = '"+company+"'" +
                    "AND product_OutSide = '"+outside+"'";
            getDataProductbyKey(query);
        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                && !outside.equals("Tất cả") && type.equals("Tất cả") && !company.equals("Tất cả") && !TextUtils.isEmpty(status)) {
            query = "SELECT * FROM products WHERE product_Status = '"+status+"' AND product_PostApproval ='"+approval+"' " +
                    " AND product_Year >= '"+yearmin+"' AND product_Price >= '"+pricemin+"' AND product_Company = '"+company+"'" +
                    "AND product_OutSide = '"+outside+"' AND product_MadeIn = '"+madein+"'";
            getDataProductbyKey(query);
        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                && !outside.equals("Tất cả") && !type.equals("Tất cả") && !company.equals("Tất cả") && !TextUtils.isEmpty(status)) {
            query = "SELECT * FROM products WHERE product_MadeIn ='"+madein+"' AND product_PostApproval ='"+approval+"' " +
                    " AND product_Year >= '"+yearmin+"' AND product_Price >= '"+pricemin+"' AND product_Company = '"+company+"'" +
                    "AND product_OutSide = '"+outside+"' AND product_MadeIn = '"+madein+"' AND product_Type ='"+type+"'";
            getDataProductbyKey(query);
        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                && !outside.equals("Tất cả") && type.equals("Tất cả") && !company.equals("Tất cả") && !TextUtils.isEmpty(status)) {
            query = "SELECT * FROM products WHERE product_Status = '"+status+"' AND product_PostApproval ='"+approval+"' " +
                    " AND product_Year >= '"+yearmin+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' AND product_Company = '"+company+"'" +
                    "AND product_OutSide = '"+outside+"' AND product_MadeIn = '"+madein+"'";
            getDataProductbyKey(query);
        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                && !outside.equals("Tất cả") && type.equals("Tất cả") && !company.equals("Tất cả") && TextUtils.isEmpty(status)) {
            query = "SELECT * FROM products WHERE product_MadeIn ='"+madein+"' AND product_PostApproval ='"+approval+"' " +
                    " AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' AND product_Company = '"+company+"'" +
                    "AND product_OutSide = '"+outside+"'";
            getDataProductbyKey(query);
        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                && !outside.equals("Tất cả") && type.equals("Tất cả") && !company.equals("Tất cả") && TextUtils.isEmpty(status)) {
            query = "SELECT * FROM products WHERE product_MadeIn ='"+madein+"' AND product_PostApproval ='"+approval+"' " +
                    " AND product_Year >= '"+yearmin+"' AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' AND product_Company = '"+company+"'" +
                    "AND product_OutSide = '"+outside+"'";
            getDataProductbyKey(query);
        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                && outside.equals("Tất cả") && type.equals("Tất cả") && company.equals("Tất cả") && TextUtils.isEmpty(status)) {
            query = "SELECT * FROM products WHERE product_MadeIn ='"+madein+"' AND product_PostApproval ='"+approval+"' " +
                    " AND product_Year >= '"+yearmin+"' AND product_Price >= '"+pricemin+"'";
            getDataProductbyKey(query);
        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                && !outside.equals("Tất cả") && type.equals("Tất cả") && company.equals("Tất cả") && TextUtils.isEmpty(status)) {
            query = "SELECT * FROM products WHERE product_MadeIn ='"+madein+"' AND product_PostApproval ='"+approval+"' " +
                    " AND product_Price >= '"+pricemin+"' AND product_OutSide = '"+outside+"' ";
            getDataProductbyKey(query);
        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                && !outside.equals("Tất cả") && type.equals("Tất cả") && company.equals("Tất cả") && TextUtils.isEmpty(status)) {
            query = "SELECT * FROM products WHERE product_MadeIn ='"+madein+"' AND product_PostApproval ='"+approval+"' " +
                    " AND product_Year >= '"+yearmin+"' AND product_Price >= '"+pricemin+"' AND product_OutSide = '"+outside+"' ";
            getDataProductbyKey(query);
        }else if (pricemin.equals("100000") && pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                && outside.equals("Tất cả") && type.equals("Tất cả") && !company.equals("Tất cả") && !TextUtils.isEmpty(status)) {
            query = "SELECT * FROM products WHERE product_Status = '"+status+"' AND product_PostApproval ='"+approval+"' " +
                    " AND product_Company = '"+company+"' AND product_MadeIn = '"+madein+"'";
            getDataProductbyKey(query);
        }else if (pricemin.equals("100000") && pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                && !outside.equals("Tất cả") && type.equals("Tất cả") && !company.equals("Tất cả") && !TextUtils.isEmpty(status)) {
            query = "SELECT * FROM products WHERE product_Status = '"+status+"' AND product_PostApproval ='"+approval+"' " +
                    "AND product_Company = '"+company+"' AND product_OutSide = '"+outside+"' AND product_MadeIn = '"+madein+"' ";
            getDataProductbyKey(query);
        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                && !outside.equals("Tất cả") && type.equals("Tất cả") && !company.equals("Tất cả") && !TextUtils.isEmpty(status)) {
            query = "SELECT * FROM products WHERE product_Status = '"+status+"' AND product_PostApproval ='"+approval+"' " +
                    "AND product_Price >= '"+pricemin+"' AND product_Company = '"+company+"'" +
                    "AND product_OutSide = '"+outside+"' AND product_MadeIn = '"+madein+"' ";
            getDataProductbyKey(query);
        }else if (pricemin.equals("100000") && pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                && outside.equals("Tất cả") && !type.equals("Tất cả") && company.equals("Tất cả") && !TextUtils.isEmpty(status)) {
            query = "SELECT * FROM products WHERE product_Status = '"+status+"' AND product_PostApproval ='"+approval+"' " +
                    "AND product_MadeIn = '"+madein+"' AND product_Type ='"+type+"'";
            getDataProductbyKey(query);
        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                && outside.equals("Tất cả") && type.equals("Tất cả") && company.equals("Tất cả") && !TextUtils.isEmpty(status)) {
            query = "SELECT * FROM products WHERE product_Status = '"+status+"' AND product_PostApproval ='"+approval+"' " +
                    "AND product_Price >= '"+pricemin+"' AND product_MadeIn = '"+madein+"' ";
            getDataProductbyKey(query);
        }else if (!pricemin.equals("100000") && pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                && outside.equals("Tất cả") && type.equals("Tất cả") && company.equals("Tất cả") && !TextUtils.isEmpty(status)) {
            query = "SELECT * FROM products WHERE product_Status = '"+status+"' AND product_PostApproval ='"+approval+"' " +
                    " AND product_Year >= '"+yearmin+"' AND product_Price >= '"+pricemin+"' AND product_MadeIn = '"+madein+"'";
            getDataProductbyKey(query);
        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                && outside.equals("Tất cả") && type.equals("Tất cả") && company.equals("Tất cả") && !TextUtils.isEmpty(status)) {
            query = "SELECT * FROM products WHERE product_Status = '"+status+"' AND product_PostApproval ='"+approval+"' " +
                    " AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' AND product_MadeIn = '"+madein+"'";
            getDataProductbyKey(query);
        }else if (!pricemin.equals("100000") && !pricemax.equals("2000000") && !yearmin.equals("2000") && yearmax.equals("2021")
                && outside.equals("Tất cả") && type.equals("Tất cả") && company.equals("Tất cả") && !TextUtils.isEmpty(status)) {
            query = "SELECT * FROM products WHERE product_Status = '"+status+"' AND product_PostApproval ='"+approval+"' " +
                    " AND product_Price BETWEEN '"+pricemin+"' AND '"+pricemax+"' AND product_MadeIn = '"+madein+"'" +
                    " AND product_Year >= '"+yearmin+"' ";
            getDataProductbyKey(query);
        }else if (pricemin.equals("100000") && pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                && !outside.equals("Tất cả") && !type.equals("Tất cả") && !company.equals("Tất cả") && !TextUtils.isEmpty(status)) {
            query = "SELECT * FROM products WHERE product_Status = '"+status+"' AND product_PostApproval ='"+approval+"' " +
                    "AND product_Company = '"+company+"' AND product_OutSide = '"+outside+"' "+
                    "AND product_MadeIn = '"+madein+"' AND product_Type ='"+type+"'";
            getDataProductbyKey(query);
        }else if (pricemin.equals("100000") && pricemax.equals("2000000") && yearmin.equals("2000") && yearmax.equals("2021")
                && outside.equals("Tất cả") && !type.equals("Tất cả") && !company.equals("Tất cả") && !TextUtils.isEmpty(status)) {
            query = "SELECT * FROM products WHERE product_Status = '"+status+"' AND product_PostApproval ='"+approval+"' " +
                    "AND product_Company = '"+company+"' AND product_MadeIn = '"+madein+"' AND product_Type ='"+type+"'";
            getDataProductbyKey(query);
        }
    }


    ////// get dữ liệu hãng xe
    @SuppressLint("CheckResult")
    private void getDataCompany() {
        APIRequest.getAllCompany(getApplicationContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonElement -> {
                    Gson gson = new Gson();
                    ArrayList<CompanyModel> companyModels = gson.fromJson(jsonElement.getAsJsonArray(), new TypeToken<ArrayList<CompanyModel>>() {
                    }.getType());
                    companyModelList.addAll(companyModels);
                }, throwable -> {

                });
    }

    ////// get dữ liệu product khi click
    @SuppressLint("CheckResult")
    public void getDataProductbyKey(String query) {
        productsModelList.clear();
        APIRequest.getProductbyKey(getApplicationContext(), query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonElement -> {
                    Gson gson = new Gson();
                    ArrayList<ProductsModel> productsModels = gson.fromJson(jsonElement.getAsJsonArray(), new TypeToken<ArrayList<ProductsModel>>() {
                    }.getType());
                    productsModelList.addAll(productsModels);
                    productsAdapter.notifyDataSetChanged();
                    if (productsModels.get(0).getProduct_Company() != null) {
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Không có xe nào đang bán", Toast.LENGTH_LONG).show();
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        editor.clear();
        editor.commit();
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Innit() {
        toolbar = findViewById(R.id.toolbarseach);
        search_recyclerView = findViewById(R.id.search_recyclerView);
        rangeSeekbar_price = findViewById(R.id.rangeSeekbar_price);
        rangeSeekbar_year = findViewById(R.id.rangeSeekbar_year);
        price_tv = findViewById(R.id.search_price_tv);
        year_tv = findViewById(R.id.search_year_tv);
        company_tv = findViewById(R.id.search_company_tv);
        status1_tv = findViewById(R.id.search_status1_tv);
        status2_tv = findViewById(R.id.search_status2_tv);
        madein1_tv = findViewById(R.id.search_madein1_tv);
        madein2_tv = findViewById(R.id.search_madein2_tv);
        outside_tv = findViewById(R.id.search_outside_tv);
        type_tv = findViewById(R.id.search_type_tv);
    }
}