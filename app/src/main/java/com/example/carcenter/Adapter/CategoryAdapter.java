package com.example.carcenter.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carcenter.Model.CategoryModel;
import com.example.carcenter.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<CategoryModel> categoryModelList;
    private OnItemOnCLick itemOnLick;

    public CategoryAdapter(List<CategoryModel> categoryModels,OnItemOnCLick itemOnLick) {
        this.categoryModelList = categoryModels;
        this.itemOnLick = itemOnLick;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder viewHolder, int position) {
        String logo = categoryModelList.get(position).getCategory_Image();
        String name = categoryModelList.get(position).getCategory_Name();

        viewHolder.setCategoryName(name);
        viewHolder.setCategoryImage(logo);


    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView categoryLogo;
        private TextView categoryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryLogo = itemView.findViewById(R.id.category_Image);
            categoryName = itemView.findViewById(R.id.category_Name);


        }

        private void setCategoryImage(String image) {
            Picasso.get().load(image).into(categoryLogo);
        }

        private void setCategoryName(String name) {
            categoryName.setText(name);

            itemView.setOnClickListener(v -> itemOnLick.onClick(name));
        }
    }

    public interface OnItemOnCLick {
        void onClick(String name);
    }
}

