package com.example.carcenter.JavaClass;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.carcenter.Fragment.AccountFragment;
import com.example.carcenter.Fragment.PostFragment;
import com.example.carcenter.Fragment.SalonFragment;
import com.example.carcenter.Fragment.Purchasefragment;
import com.example.carcenter.Fragment.HomeFragment;
import com.example.carcenter.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private static BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                        case R.id.nav_trangchu:
                            fragment = new HomeFragment();
                            break;
                        case R.id.nav_tinmua:
                            fragment = new Purchasefragment();
                            break;
                        case R.id.nav_dangtin:
                            fragment = new PostFragment();
                            break;
                        case R.id.nav_salon:
                            fragment = new SalonFragment();
                            break;
                        case R.id.nav_canhan:
                            fragment = new AccountFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

                    return true;
                }
            };
}
