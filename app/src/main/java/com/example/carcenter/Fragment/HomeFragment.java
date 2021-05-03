package com.example.carcenter.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carcenter.Adapter.CategoryAdapter;
import com.example.carcenter.Adapter.ProductsAdapter;
import com.example.carcenter.Model.CategoryModel;
import com.example.carcenter.Model.ProductsModel;
import com.example.carcenter.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    public static final String TAG = "tagHomeFragment";
    private List<CategoryModel> categoryModelList;
    private CategoryAdapter categoryAdapter;
    private RecyclerView category_RecyclerView;

    private List<ProductsModel> productsModelList;
    private ProductsAdapter productsAdapter;
    private RecyclerView product_RecyclerView;
    private TextView product_Title;
    private Button viewAll_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_home, viewGroup, false);

        ////////// Category
        category_RecyclerView = view.findViewById(R.id.category_recyclerView);
        LinearLayoutManager layoutManagerCategory = new LinearLayoutManager(getActivity());
        layoutManagerCategory.setOrientation(LinearLayoutManager.HORIZONTAL);
        category_RecyclerView.setLayoutManager(layoutManagerCategory);

        categoryModelList = new ArrayList<CategoryModel>();
        categoryModelList.add(new CategoryModel(1, "link", "Vinfast"));
        categoryModelList.add(new CategoryModel(2, "link", "Audi"));
        categoryModelList.add(new CategoryModel(3, "link", "BMW"));
        categoryModelList.add(new CategoryModel(4, "link", "Honda"));
        categoryModelList.add(new CategoryModel(5, "link", "Huyndai"));
        categoryModelList.add(new CategoryModel(6, "link", "Kia"));
        categoryModelList.add(new CategoryModel(7, "link", "Toyota"));

        categoryAdapter = new CategoryAdapter(categoryModelList);
        category_RecyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();
        ////////// Category

        ////////// Products
        product_Title = view.findViewById(R.id.productTitle_tv);
        viewAll_btn = view.findViewById(R.id.btn_viewAll);
        product_RecyclerView = view.findViewById(R.id.productRecyclerView);
        LinearLayoutManager layoutManager_Product = new LinearLayoutManager(getActivity());
        layoutManager_Product.setOrientation(LinearLayoutManager.VERTICAL);
        product_RecyclerView.setLayoutManager(layoutManager_Product);

        productsModelList = new ArrayList<ProductsModel>();
        productsModelList.add(new ProductsModel(1,R.drawable.vinfast,"Vinfast", "Lux A 2.0", "Pemidum AT 2.0",
                "2020", "Trong nước", "Xe cũ", 100, "Sedan",
                "1 Tỷ 990 Triệu", "Vo Quang Anh", "Hà Nội"));
        productsModelList.add(new ProductsModel(2,R.drawable.vinfast,"Vinfast", "Lux SA 2.0", "",
                "2021", "Trong nước", "Xe cũ", 120, "Sedan",
                "1 Tỷ 20 Triệu", "Quang Anh", "Nghệ An"));
        productsModelList.add(new ProductsModel(3,R.drawable.vinfast,"Vinfast", "Lux A 2.0", "",
                "2020", "Trong nước", "Xe cũ", 100, "Sedan",
                "900 Triệu", "Vo Quang Anh", "Hà Nội"));
        productsModelList.add(new ProductsModel(2,R.drawable.vinfast,"Vinfast", "Lux SA 2.0", "",
                "2021", "Trong nước", "Xe cũ", 120, "Sedan",
                "1 Tỷ 20 Triệu", "Quang Anh", "Nghệ An"));

        productsAdapter = new ProductsAdapter(productsModelList);
        product_RecyclerView.setAdapter(productsAdapter);
        productsAdapter.notifyDataSetChanged();
        ////////// Products

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
