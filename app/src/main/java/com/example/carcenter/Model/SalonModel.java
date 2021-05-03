package com.example.carcenter.Model;

public class SalonModel {

    private int salon_id;
    private int salon_image;
    private String salon_name;
    private String salon_title;
    private String salon_address;
    private String salon_phone;
    private String salon_email;
    private String salon_website;

    public SalonModel(int salon_id, int salon_image, String salon_name, String salon_title, String salon_address, String salon_phone,
                      String salon_email, String salon_website) {
        this.salon_id = salon_id;
        this.salon_image = salon_image;
        this.salon_name = salon_name;
        this.salon_title = salon_title;
        this.salon_address = salon_address;
        this.salon_phone = salon_phone;
        this.salon_email = salon_email;
        this.salon_website = salon_website;
    }

    public int getSalon_id() {
        return salon_id;
    }

    public void setSalon_id(int salon_id) {
        this.salon_id = salon_id;
    }

    public int getSalon_image() {
        return salon_image;
    }

    public void setSalon_image(int salon_image) {
        this.salon_image = salon_image;
    }

    public String getSalon_name() {
        return salon_name;
    }

    public void setSalon_name(String salon_name) {
        this.salon_name = salon_name;
    }

    public String getSalon_title() {
        return salon_title;
    }

    public void setSalon_title(String salon_title) {
        this.salon_title = salon_title;
    }

    public String getSalon_address() {
        return salon_address;
    }

    public void setSalon_address(String salon_address) {
        this.salon_address = salon_address;
    }

    public String getSalon_phone() {
        return salon_phone;
    }

    public void setSalon_phone(String salon_phone) {
        this.salon_phone = salon_phone;
    }

    public String getSalon_email() {
        return salon_email;
    }

    public void setSalon_email(String salon_email) {
        this.salon_email = salon_email;
    }

    public String getSalon_website() {
        return salon_website;
    }

    public void setSalon_website(String salon_website) {
        this.salon_website = salon_website;
    }
}
