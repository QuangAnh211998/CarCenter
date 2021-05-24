package com.example.carcenter.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carcenter.R;

import java.io.IOException;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private Context context;
    List<Uri> listImage;

    public ImageAdapter(Context context) {
        this.context = context;
    }

    public void setDataImage(List<Uri> list){
        this.listImage = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_image, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.ViewHolder viewHolder, int position) {
        Uri uri = listImage.get(position);
        if(uri == null){
            return;
        }
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
            if(bitmap != null){
                viewHolder.imageView.setImageBitmap(bitmap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if(listImage != null){
            return listImage.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image);
        }
    }
}
