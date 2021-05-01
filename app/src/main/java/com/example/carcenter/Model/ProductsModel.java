package com.example.carcenter.Model;

import java.io.Serializable;

public class ProductsModel implements Serializable {

    private int productId;
    private int productImage;
    private String productCompany;
    private String productName;
    private String productVersion;
    private String productYear;
    private String productMadeIn;
    private String productStatus;
    private int productKmWent;
    private String productType;
    private String productPrice;
    private String productUserName;
    private String productUserLivingAre;

    public ProductsModel(int productId, int productImage, String productCompany, String productName, String productVersion, String productYear,
                         String productMadeIn, String productStatus, int productKmWent, String productType, String productPrice,
                         String productUserName, String productUserLivingAre) {
        this.productId = productId;
        this.productImage = productImage;
        this.productCompany = productCompany;
        this.productName = productName;
        this.productVersion = productVersion;
        this.productYear = productYear;
        this.productMadeIn = productMadeIn;
        this.productStatus = productStatus;
        this.productKmWent = productKmWent;
        this.productType = productType;
        this.productPrice = productPrice;
        this.productUserName = productUserName;
        this.productUserLivingAre = productUserLivingAre;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getProductImage() {
        return productImage;
    }

    public void setProductImage(int productImage) {
        this.productImage = productImage;
    }

    public String getProductCompany() {
        return productCompany;
    }

    public void setProductCompany(String productCompany) {
        this.productCompany = productCompany;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductVersion() {
        return productVersion;
    }

    public void setProductVersion(String productVersion) {
        this.productVersion = productVersion;
    }

    public String getProductYear() {
        return productYear;
    }

    public void setProductYear(String productYear) {
        this.productYear = productYear;
    }

    public String getProductMadeIn() {
        return productMadeIn;
    }

    public void setProductMadeIn(String productMadeIn) {
        this.productMadeIn = productMadeIn;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }

    public int getProductKmWent() {
        return productKmWent;
    }

    public void setProductKmWent(int productKmWent) {
        this.productKmWent = productKmWent;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductUserName() {
        return productUserName;
    }

    public void setProductUserName(String productUserName) {
        this.productUserName = productUserName;
    }

    public String getProductUserLivingAre() {
        return productUserLivingAre;
    }

    public void setProductUserLivingAre(String productUserLivingAre) {
        this.productUserLivingAre = productUserLivingAre;
    }
}
