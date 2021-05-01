package com.example.carcenter.JavaClass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.carcenter.Adapter.ProductImageAdapter;
import com.example.carcenter.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {

    List<Integer> productImage_List;
    ProductImageAdapter productImageAdapter;

    private static boolean addtowishlist = false;
    Toolbar toolbar;
    private ViewPager viewPager_Image;
    private TabLayout viewpager_tablayout;
    private FloatingActionButton add_to_Wishlist_btn;
    private FloatingActionButton floatingbtn_call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Innit();
        ActionToolBar();

        productImage_List = new ArrayList<>();
        productImage_List.add(R.drawable.vinfast);
        productImage_List.add(R.drawable.logo);
        productImage_List.add(R.drawable.toyota);
        productImage_List.add(R.drawable.vinfast);
        productImage_List.add(R.drawable.logo);
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
    }
}