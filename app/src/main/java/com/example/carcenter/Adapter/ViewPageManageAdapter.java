package com.example.carcenter.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.carcenter.Admin.PostPurchaseFragment;
import com.example.carcenter.Admin.PostSaleFragment;
import com.example.carcenter.Fragment.MyPurchaseFragment;
import com.example.carcenter.Fragment.MySaleFragment;

public class ViewPageManageAdapter extends FragmentStatePagerAdapter {

        public ViewPageManageAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new PostSaleFragment();
                case 1:
                    return new PostPurchaseFragment();
                default:
                    return new PostSaleFragment();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            String title = "";
            switch (position){
                case 0:
                    title = "Bài bán";
                    break;
                case 1:
                    title = "Bài mua";
                    break;
            }
            return title;
        }
    }
