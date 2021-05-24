package com.example.carcenter.Adapter;

import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductImageAdapter extends PagerAdapter {

    private List<String> productImages_List;

    public ProductImageAdapter(List<String> productImages_List) {
        this.productImages_List = productImages_List;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView productImage = new ImageView(container.getContext());
        String image = productImages_List.get(position);
        Glide.with(container).load(image).centerCrop().into(productImage);
        container.addView(productImage, 0);

        return productImage;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
       container.removeView((ImageView)object);
    }

    @Override
    public int getCount() {
        return productImages_List.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
