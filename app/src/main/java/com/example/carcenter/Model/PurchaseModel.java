package com.example.carcenter.Model;

public class PurchaseModel {

    private String purchase_title;
    private String purchase_price_range;
    private String purchase_content;
    private String purchase_userName;
    private String purchase_userPhone;
    private String purchase_userAddress;

    public PurchaseModel(String purchase_title, String purchase_price_range, String purchase_content, String purchase_userName,
                         String purchase_userPhone, String purchase_userAddress) {
        this.purchase_title = purchase_title;
        this.purchase_price_range = purchase_price_range;
        this.purchase_content = purchase_content;
        this.purchase_userName = purchase_userName;
        this.purchase_userPhone = purchase_userPhone;
        this.purchase_userAddress = purchase_userAddress;
    }

    public String getPurchase_title() {
        return purchase_title;
    }

    public void setPurchase_title(String purchase_title) {
        this.purchase_title = purchase_title;
    }

    public String getPurchase_price_range() {
        return purchase_price_range;
    }

    public void setPurchase_price_range(String purchase_price_range) {
        this.purchase_price_range = purchase_price_range;
    }

    public String getPurchase_content() {
        return purchase_content;
    }

    public void setPurchase_content(String purchase_content) {
        this.purchase_content = purchase_content;
    }

    public String getPurchase_userName() {
        return purchase_userName;
    }

    public void setPurchase_userName(String purchase_userName) {
        this.purchase_userName = purchase_userName;
    }

    public String getPurchase_userPhone() {
        return purchase_userPhone;
    }

    public void setPurchase_userPhone(String purchase_userPhone) {
        this.purchase_userPhone = purchase_userPhone;
    }

    public String getPurchase_userAddress() {
        return purchase_userAddress;
    }

    public void setPurchase_userAddress(String purchase_userAddress) {
        this.purchase_userAddress = purchase_userAddress;
    }
}
