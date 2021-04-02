package com.example.carcenter.JavaClass;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.carcenter.Fragment.Frag_Canhan;
import com.example.carcenter.Fragment.Frag_Dangtin;
import com.example.carcenter.Fragment.Frag_Salon;
import com.example.carcenter.Fragment.Frag_Tinmua;
import com.example.carcenter.Fragment.Frag_Trangchu;
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

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Frag_Trangchu()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment fragment = null;

                    switch (menuItem.getItemId()){
                        case R.id.nav_trangchu:
                            fragment = new Frag_Trangchu();
                            break;
                        case R.id.nav_tinmua:
                            fragment = new Frag_Tinmua();
                            break;
                        case R.id.nav_dangtin:
                            fragment = new Frag_Dangtin();
                            break;
                        case R.id.nav_salon:
                            fragment = new Frag_Salon();
                            break;
                        case R.id.nav_canhan:
                            fragment = new Frag_Canhan();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

                    return true;
                }
            };
}
