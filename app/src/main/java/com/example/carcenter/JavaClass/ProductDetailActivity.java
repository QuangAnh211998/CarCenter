package com.example.carcenter.JavaClass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.carcenter.Adapter.ProductImageAdapter;
import com.example.carcenter.Model.ProductsModel;
import com.example.carcenter.R;
import com.example.carcenter.common.Custom_Price;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {

    List<String> productImage_List;
    ProductImageAdapter productImageAdapter;
    ArrayList<String> image;

    private Toolbar toolbar;
    private ViewPager viewPager_Image;
    private TabLayout viewpager_tablayout;
    private FloatingActionButton add_to_Wishlist_btn;
    private FloatingActionButton floatingbtn_call;
    private TextView product_company, product_name, product_vesion, product_year, product_price;
    private TextView product_status, product_made_in, product_type, product_km_went, product_outside_color, product_inside_color;
    private TextView product_door, product_seat, product_gear, product_drive_train, product_fuel, product_consume, product_content;
    private TextView product_username;
    private TextView product_userphone;
    private TextView product_useraddress;

    private static boolean addtowishlist = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Innit();
        ActionToolBar();
        EventOnClick();
        getsetProductDetail();

        productImage_List = new ArrayList<>();
        productImage_List.addAll(image);
        productImageAdapter = new ProductImageAdapter(productImage_List);
        viewPager_Image.setAdapter(productImageAdapter);
        viewpager_tablayout.setupWithViewPager(viewPager_Image, true);


    }

    private void EventOnClick(){
        add_to_Wishlist_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addtowishlist){
                    addtowishlist = false;
                    add_to_Wishlist_btn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9f9f9f")));
                }else {
                    addtowishlist = true;
                    add_to_Wishlist_btn.setSupportImageTintList(getResources().getColorStateList(R.color.colorReb));
                }
            }
        });
    }

    private void getsetProductDetail(){

        ProductsModel productsModel = getIntent().getParcelableExtra("productDetail");

        //// get dữ liệu về
        int price = productsModel.getProduct_Price();
        image = productsModel.getProduct_Image();

        //// set dữ liệu lên textView
        product_company.setText(productsModel.getProduct_Company());
        product_name.setText(productsModel.getProduct_Name());
        product_vesion.setText(productsModel.getProduct_Version());
        product_year.setText(String.valueOf(productsModel.getProduct_Year()));
        product_price.setText(Custom_Price.format(Long.parseLong(String.valueOf(price))));
        product_status.setText(productsModel.getProduct_Status());
        product_made_in.setText(productsModel.getProduct_MadeIn());
        product_km_went.setText(String.valueOf(productsModel.getProduct_KmWent()));
        product_type.setText(productsModel.getProduct_Type());
        product_outside_color.setText(productsModel.getProduct_OutSide());
        product_inside_color.setText(productsModel.getProduct_InSide());
        product_door.setText(String.valueOf(productsModel.getProduct_Door()));
        product_seat.setText(String.valueOf(productsModel.getProduct_Seat()));
        product_drive_train.setText(productsModel.getProduct_DriveTrain());
        product_gear.setText(productsModel.getProduct_Gear());
        product_fuel.setText(productsModel.getProduct_Fuel());
        product_consume.setText(String.valueOf(productsModel.getProduct_Consume()));
        product_content.setText(productsModel.getProduct_Content());
        product_username.setText(productsModel.getProduct_UserName());
        product_userphone.setText(productsModel.getProduct_UserPhone());
        product_useraddress.setText(productsModel.getProduct_UserAddress());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.main_search){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void ActionToolBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Innit(){
        toolbar = findViewById(R.id.toolbar_productDetail);
        viewPager_Image = findViewById(R.id.product_image_viewpager);
        viewpager_tablayout = findViewById(R.id.viewpager_indicator);
        add_to_Wishlist_btn = findViewById(R.id.add_to_wishlist);
        floatingbtn_call = findViewById(R.id.floattingbtn_call);
        product_company = findViewById(R.id.product_company_tv);
        product_name = findViewById(R.id.product_name_tv);
        product_vesion = findViewById(R.id.product_version_tv);
        product_year = findViewById(R.id.product_year_tv);
        product_made_in = findViewById(R.id.product_madein_tv);
        product_status = findViewById(R.id.product_Status_tv);
        product_km_went = findViewById(R.id.product_km_tv);
        product_type = findViewById(R.id.product_type_tv);
        product_price = findViewById(R.id.product_price_tv);
        product_outside_color = findViewById(R.id.product_outside_tv);
        product_inside_color = findViewById(R.id.product_inside_tv);
        product_door = findViewById(R.id.product_door_tv);
        product_seat = findViewById(R.id.product_seat_tv);
        product_gear = findViewById(R.id.product_gear_tv);
        product_drive_train = findViewById(R.id.product_driveTrain_tv);
        product_fuel = findViewById(R.id.product_fuel_tv);
        product_consume = findViewById(R.id.product_consume_tv);
        product_content = findViewById(R.id.product_content_tv);
        product_username = findViewById(R.id.product_username_tv);
        product_userphone = findViewById(R.id.product_userphone_tv);
        product_useraddress = findViewById(R.id.product_address_tv);

    }
}