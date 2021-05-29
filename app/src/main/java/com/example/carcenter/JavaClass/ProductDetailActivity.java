package com.example.carcenter.JavaClass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carcenter.Adapter.ProductImageAdapter;
import com.example.carcenter.Register.RegisterActivity;
import com.example.carcenter.Model.ProductsModel;
import com.example.carcenter.Network.APIRequest;
import com.example.carcenter.R;
import com.example.carcenter.Custom.Custom_Price;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONObject;
import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.example.carcenter.Register.RegisterActivity.setSignUpFragment;

public class ProductDetailActivity extends AppCompatActivity {

    List<String> productImage_List;
    ProductImageAdapter productImageAdapter;
    ArrayList<String> image;
    private static boolean addtowishlist = false;
    private boolean click_safe = false;
    private String phone;
    private int product_id;
    private int user_id;
    private int product_user_id;
    private String product_user_name;
    private int wishlist_product_id;

    private SharedPreferences saveSignIn;
    private SharedPreferences.Editor editor;

    private Toolbar toolbar;
    private ViewPager viewPager_Image;
    private TabLayout viewpager_tablayout;
    private FloatingActionButton add_to_Wishlist_btn;
    private FloatingActionButton floatingbtn_call;
    private TextView product_company;
    private TextView product_status;
    private TextView product_door;
    private TextView product_username;
    private TextView product_userphone;
    private TextView product_useraddress;
    private TextView product_name;
    private TextView product_vesion;
    private TextView product_year;
    private TextView product_price;
    private TextView product_made_in;
    private TextView product_type;
    private TextView product_km_went;
    private TextView product_outside_color;
    private TextView product_inside_color;
    private TextView product_seat;
    private TextView product_consume;
    private TextView product_gear;
    private TextView product_drive_train;
    private TextView product_fuel;
    private TextView product_content;
    private TextView air_Bag;
    private TextView system_ABS;
    private TextView system_EBA;
    private TextView system_ESP;
    private TextView anti_Slip;
    private TextView reverse_Warning;
    private TextView anti_theft;
    private LinearLayout layout_safe;
    private LinearLayout layout_view;
    private ImageView imageView_up;
    private ImageView imageView_down;
    private LinearLayout productbyname;
    private TextView user_name_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        if(Build.VERSION.SDK_INT>=22){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(ContextCompat.getColor(ProductDetailActivity.this,R.color.colorGrey));
        }

        EventBus.getDefault().register(this);
        saveSignIn = getSharedPreferences("saveSignIn", Context.MODE_PRIVATE);
        editor = saveSignIn.edit();
        user_id = saveSignIn.getInt("user_Id", -1);

        Init();
        ActionToolBar();
        getsetProductDetail();
        getWishlist();

        productImage_List = new ArrayList<>();
        productImage_List.addAll(image);
        productImageAdapter = new ProductImageAdapter(productImage_List);
        viewPager_Image.setAdapter(productImageAdapter);
        viewpager_tablayout.setupWithViewPager(viewPager_Image, true);

        EventOnClick();

    }




    private void EventOnClick(){
        add_to_Wishlist_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = saveSignIn.getString("user_Email", "");
                if(!TextUtils.isEmpty(email)){
                    if(addtowishlist){
                        addtowishlist = false;
                        add_to_Wishlist_btn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9f9f9f")));
                        DeleteWishlist();
                    }else {
                        PostWishlist();
                    }
                }else {
                    DialogSignIn();
                }
            }
        });


        floatingbtn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent call_intent = new Intent(Intent.ACTION_CALL);
                call_intent.setData(Uri.parse("tel:"+phone));
                startActivity(call_intent);
            }
        });


        product_userphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogCallPhone(phone);
            }
        });

        productbyname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDetailActivity.this, ProductbyUserActivity.class);
                intent.putExtra("user_id", product_user_id);
                intent.putExtra("user_name", product_user_name);
                startActivity(intent);
            }
        });


        layout_safe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click_safe){
                    click_safe = false;
                    layout_view.setVisibility(View.GONE);
                    imageView_down.setVisibility(View.VISIBLE);
                    imageView_up.setVisibility(View.GONE);
                }else {
                    click_safe = true;
                    layout_view.setVisibility(View.VISIBLE);
                    imageView_down.setVisibility(View.GONE);
                    imageView_up.setVisibility(View.VISIBLE);
                }
            }
        });
    }



    private void getsetProductDetail(){

        ProductsModel productsModel = getIntent().getParcelableExtra("productDetail");

        //// get dữ liệu về
        product_user_id = productsModel.getUser_Id();
        product_user_name = productsModel.getUser_Name();
        product_id = productsModel.getProduct_Id();
        int price = productsModel.getProduct_Price();
        image = productsModel.getProduct_Image();
        phone = productsModel.getUser_Phone();
        int airbag = productsModel.getSystem_Air_Bag();

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
        product_username.setText(productsModel.getUser_Name());
        product_userphone.setText(phone);
        product_useraddress.setText(productsModel.getUser_Address());
        air_Bag.setText(String.valueOf(airbag+" Túi"));
        system_ABS.setText(productsModel.getSystem_ABS());
        system_EBA.setText(productsModel.getSystem_EBA());
        system_ESP.setText(productsModel.getSystem_ESP());
        anti_Slip.setText(productsModel.getSystem_Anti_Slip());
        anti_theft.setText(productsModel.getSystem_Anti_theft());
        reverse_Warning.setText(productsModel.getSystem_Reverse_Warning());
        user_name_tv.setText(productsModel.getUser_Name());

    }



    private void DialogCallPhone(String phone){
        Dialog callDialog = new Dialog(ProductDetailActivity.this);
        callDialog.setContentView(R.layout.dialog_callphone);
        callDialog.setCancelable(true);
        callDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button callDialog_btn = callDialog.findViewById(R.id.call_dialog_btn);
        Button exitDialog_btn = callDialog.findViewById(R.id.exit_dialog_btn);
        TextView phone_dialog = callDialog.findViewById(R.id.dialog_phone);
        phone_dialog.setText(phone);
        callDialog_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDialog.dismiss();
                Intent call_intent = new Intent(Intent.ACTION_CALL);
                call_intent.setData(Uri.parse("tel:"+phone));
                startActivity(call_intent);
            }
        });

        exitDialog_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDialog.dismiss();
            }
        });
        callDialog.show();
    }


    private void DialogSignIn(){
        Dialog signInDialog = new Dialog(this);
        signInDialog.setContentView(R.layout.dialog_signin);
        signInDialog.setCancelable(true);
        signInDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button signIn_btn = signInDialog.findViewById(R.id.signin_dialog_btn);
        Button signUp_btn = signInDialog.findViewById(R.id.signup_dialog_btn);
        Intent registerIntent = new Intent(this, RegisterActivity.class);
        signIn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInDialog.dismiss();
                setSignUpFragment = false;
                startActivity(registerIntent);
            }
        });

        signUp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInDialog.dismiss();
                setSignUpFragment = true;
                startActivity(registerIntent);
            }
        });
        signInDialog.show();
    }



    @SuppressLint("CheckResult")
    private void PostWishlist(){
        APIRequest.postWishlist(getApplication(),user_id, product_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonElement -> {
                    JSONObject jsonObject = new JSONObject(jsonElement.toString());
                    String status = jsonObject.getString("status");
                    if(status.equals("success")) {
                        addtowishlist = true;
                        add_to_Wishlist_btn.setSupportImageTintList(getResources().getColorStateList(R.color.colorReb));
                        Toast.makeText(this, "Lưu thành công", Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    Toast.makeText(this, "Lưu thất bại", Toast.LENGTH_LONG).show();
                });
    }

    @SuppressLint("CheckResult")
    private void DeleteWishlist(){
        String query = "DELETE FROM wishlist WHERE user_Id ='"+user_id+"' AND product_Id = '"+product_id+"'";
        APIRequest.UpdateAndDelete(getApplicationContext(),query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonElement -> {
                    Log.e("resetpass", jsonElement.toString());
                    JSONObject jsonObject = new JSONObject(jsonElement.toString());
                    String status = jsonObject.getString("status");
                    if(status.equals("success")) {
                        Toast.makeText(this, "Bỏ lưu thành công", Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    Toast.makeText(this, "Bỏ lưu thất bại", Toast.LENGTH_LONG).show();
                });
    }

    @SuppressLint("CheckResult")
    private void getWishlist(){
        APIRequest.getWishlist(getApplicationContext(), user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonElement -> {
                    JSONObject jsonObject = new JSONObject(jsonElement.toString());
                    wishlist_product_id = jsonObject.getInt("product_Id");
                    if(wishlist_product_id == product_id){
                        addtowishlist=true;
                        add_to_Wishlist_btn.setSupportImageTintList(getResources().getColorStateList(R.color.colorReb));
                    }else {
                        addtowishlist=false;
                        add_to_Wishlist_btn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9f9f9f")));
                    }
                }, throwable -> {

                });
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.main_search){
            startActivity(new Intent(getApplicationContext(), SearchActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private void Init(){
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
        product_status = findViewById(R.id.product_Price_tv);
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
        air_Bag = findViewById(R.id.air_Bag_tv);
        system_ABS = findViewById(R.id.abs_tv);
        system_EBA = findViewById(R.id.eba_tv);
        system_ESP = findViewById(R.id.esp_tv);
        anti_Slip = findViewById(R.id.anti_Slip_tv);
        reverse_Warning = findViewById(R.id.reverse_Warning_tv);
        anti_theft = findViewById(R.id.anti_theft_tv);
        layout_safe = findViewById(R.id.layout_safe);
        layout_view = findViewById(R.id.layoutview);
        imageView_up = findViewById(R.id.imageView_up);
        imageView_down = findViewById(R.id.imageView_down);
        user_name_tv = findViewById(R.id.user_name_tv);
        productbyname = findViewById(R.id.productbyename_layout);

    }
}