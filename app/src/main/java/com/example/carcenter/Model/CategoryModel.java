package com.example.carcenter.Model;

import java.io.Serializable;

public class CategoryModel implements Serializable {

    private int categoryId;
    private String categoryLogo;
    private String categoryName;

    public CategoryModel(int categoryId, String categoryLogo, String categoryName) {
        this.categoryId = categoryId;
        this.categoryLogo = categoryLogo;
        this.categoryName = categoryName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryLogo() {
        return categoryLogo;
    }

    public void setCategoryLogo(String categoryLogo) {
        this.categoryLogo = categoryLogo;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
