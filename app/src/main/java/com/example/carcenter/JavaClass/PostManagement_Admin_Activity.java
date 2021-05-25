package com.example.carcenter.JavaClass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.carcenter.Adapter.ViewPageAdapter;
import com.example.carcenter.Adapter.ViewPageManageAdapter;
import com.example.carcenter.R;
import com.google.android.material.tabs.TabLayout;

public class PostManagement_Admin_Activity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_management__admin_);

        if (Build.VERSION.SDK_INT >= 22) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(ContextCompat.getColor(PostManagement_Admin_Activity.this, R.color.colorGrey));
        }

        Innit();

        ViewPageManageAdapter viewPageManageAdapter = new ViewPageManageAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewPager.setAdapter(viewPageManageAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        setToolbar();
    }
        private void setToolbar(){
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        private void Innit(){
            mTabLayout = findViewById(R.id.tabLayout_postmanage);
            mViewPager = findViewById(R.id.viewpager_postmanage);
            mToolbar = findViewById(R.id.toolbar_PostManage);
        }
    }