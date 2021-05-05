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
    private TextView product_company, product_name, product_vesion, product_year, product_price, product_status;
    private TextView product_year2, product_made_in, product_type, product_km_went, product_outside_color, product_inside_color;
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
        String company = productsModel.getProduct_Company();
        String name = productsModel.getProduct_Name();
        String version = productsModel.getProduct_Version();
        int year = productsModel.getProduct_Year();
        String madeIn = productsModel.getProduct_MadeIn();
        String status = productsModel.getProduct_Status();
        int km_went = productsModel.getProduct_KmWent();
        String type = productsModel.getProduct_Type();
        int price = productsModel.getProduct_Price();
        String outside = productsModel.getProduct_OutSide();
        String inside = productsModel.getProduct_InSide();
        int door = productsModel.getProduct_Door();
        int seat = productsModel.getProduct_Seat();
        String drivetrain = productsModel.getProduct_DriveTrain();
        String gear = productsModel.getProduct_Gear();
        String fuel = productsModel.getProduct_Fuel();
        int consume = productsModel.getProduct_Consume();
        String content = productsModel.getProduct_Content();
        String username = productsModel.getProduct_UserName();
        String userphone = productsModel.getProduct_UserPhone();
        String useraddress = productsModel.getProduct_UserAddress();
        image = productsModel.getProduct_Image();

        //// set dữ liệu lên textView
        product_company.setText(company);
        product_name.setText(name);
        product_vesion.setText(version);
        product_year.setText(String.valueOf(year));
        product_price.setText(Custom_Price.format(Long.parseLong(String.valueOf(price))));
        product_status.setText(status);
        product_year2.setText(String.valueOf(year));
        product_made_in.setText(madeIn);
        product_km_went.setText(String.valueOf(km_went));
        product_type.setText(type);
        product_outside_color.setText(outside);
        product_inside_color.setText(inside);
        product_door.setText(String.valueOf(door));
        product_seat.setText(String.valueOf(seat));
        product_drive_train.setText(drivetrain);
        product_gear.setText(gear);
        product_fuel.setText(fuel);
        product_consume.setText(String.valueOf(consume));
        product_content.setText(content);
        product_username.setText(username);
        product_userphone.setText(userphone);
        product_useraddress.setText(useraddress);

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
        product_year2 = findViewById(R.id.product_Year2_tv);
        product_made_in = findViewById(R.id.product_madein_tv);
        product_status = findViewById(R.id.product_status_tv);
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