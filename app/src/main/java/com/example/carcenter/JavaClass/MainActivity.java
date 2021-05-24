package com.example.carcenter.JavaClass;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.carcenter.Fragment.AccountFragment;
import com.example.carcenter.Fragment.PostFragment;
import com.example.carcenter.Fragment.SalonFragment;
import com.example.carcenter.Fragment.Purchasefragment;
import com.example.carcenter.Fragment.HomeFragment;
import com.example.carcenter.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.simple.eventbus.EventBus;

public class MainActivity extends AppCompatActivity {

    private static BottomNavigationView bottomNavigation;

    private SharedPreferences saveSignIn;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT>=22){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.colorGrey));
        }

        EventBus.getDefault().register(this);
        saveSignIn = getSharedPreferences("saveSignIn", Context.MODE_PRIVATE);
        editor = saveSignIn.edit();

        bottomNavigation = findViewById(R.id.btn_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment fragment = null;

                    switch (menuItem.getItemId()){
                        case R.id.nav_home:
                            fragment = new HomeFragment();
                            break;
                        case R.id.nav_purchase:
                            fragment = new Purchasefragment();
                            break;
                        case R.id.nav_post:
                                fragment = new PostFragment();
                            break;
                        case R.id.nav_salon:
                            fragment = new SalonFragment();
                            break;
                        case R.id.nav_account:
                            fragment = new AccountFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

                    return true;
                }
            };
}
