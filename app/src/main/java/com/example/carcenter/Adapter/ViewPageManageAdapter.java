package com.example.carcenter.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.carcenter.Admin.PurchaseManagementFragment;
import com.example.carcenter.Admin.SaleManagementFragment;

public class ViewPageManageAdapter extends FragmentStatePagerAdapter {

        public ViewPageManageAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new SaleManagementFragment();
                case 1:
                    return new PurchaseManagementFragment();
                default:
                    return new SaleManagementFragment();
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
