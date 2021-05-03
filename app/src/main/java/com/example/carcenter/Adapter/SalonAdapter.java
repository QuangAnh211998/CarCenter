package com.example.carcenter.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carcenter.Model.SalonModel;
import com.example.carcenter.R;

import java.util.List;

public class SalonAdapter extends RecyclerView.Adapter<SalonAdapter.ViewHolder> {

    private List<SalonModel> salonModelList;

    public SalonAdapter(List<SalonModel> salonModelList) {
        this.salonModelList = salonModelList;
    }

    @NonNull
    @Override
    public SalonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.salon_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SalonAdapter.ViewHolder viewHolder, int position) {
        int imageUrl = salonModelList.get(position).getSalon_image();
        String name = salonModelList.get(position).getSalon_name();
        String title = salonModelList.get(position).getSalon_title();
        String address = salonModelList.get(position).getSalon_address();
        String phone = salonModelList.get(position).getSalon_phone();

        viewHolder.setSalon_image(imageUrl);
        viewHolder.setSalon_name(name);
        viewHolder.setSalon_title(title);
        viewHolder.setSalon_address(address);
        viewHolder.setSalon_phone(phone);
    }

    @Override
    public int getItemCount() {
        return salonModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView salon_image;
        private TextView salon_name;
        private TextView salon_title;
        private TextView salon_address;
        private TextView salon_phone;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            salon_image = itemView.findViewById(R.id.salon_imageView);
            salon_name = itemView.findViewById(R.id.salonName_tv);
            salon_title = itemView.findViewById(R.id.salonTitle_tv);
            salon_address = itemView.findViewById(R.id.salonAddress_tv);
            salon_phone = itemView.findViewById(R.id.salonPhone_tv);
        }
        private void setSalon_image(int imageUrl){
            salon_image.setImageResource(imageUrl);
        }
        private void setSalon_name(String name){
            salon_name.setText(name);
        }
        private void setSalon_title(String title){
            salon_title.setText(title);
        }
        private void setSalon_address(String address){
            salon_address.setText(address);
        }
        private void setSalon_phone(String phone){
            salon_phone.setText(phone);
        }
    }
}
