package com.example.carcenter.Network;
import android.content.Context;

import com.google.gson.JsonElement;
import java.util.HashMap;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;

public class APIRequest {

    public static Observable<JsonElement> getCompany(Context context) {
        HashMap<String, Object> queryMap = new HashMap<>();
        return BaseAPIRequest.getInstanceRequestV2(context).getCompany(queryMap);
    }

    public static Observable<JsonElement> getAllCompany(Context context) {
        HashMap<String, Object> queryMap = new HashMap<>();
        return BaseAPIRequest.getInstanceRequestV2(context).getAllCompany(queryMap);
    }

    public static Observable<JsonElement> getCategory (Context context, int id){
        return BaseAPIRequest.getInstanceRequestV2(context).getCategory(id);
    }

    public static Observable<JsonElement> getProduct(Context context) {
        HashMap<String, Object> queryMap = new HashMap<>();
        return BaseAPIRequest.getInstanceRequestV2(context).getProduct(queryMap);
    }

    public static Observable<JsonElement> getProvince(Context context) {
        HashMap<String, Object> queryMap = new HashMap<>();
        return BaseAPIRequest.getInstanceRequestV2(context).getProvince(queryMap);
    }

    public static Observable<JsonElement> getPurchase (Context context) {
        HashMap<String, Object> queryMap = new HashMap<>();
        return BaseAPIRequest.getInstanceRequestV2(context).getPurchase(queryMap);
    }

    public static Observable<JsonElement> getProductbyKey (Context context, String query){
        return BaseAPIRequest.getInstanceRequestV2(context).getProductbyKey(query);
    }

    public static Observable<JsonElement> getPurchasebyKey (Context context, String company){
        return BaseAPIRequest.getInstanceRequestV2(context).getPurchasebyKey(company);
    }

    public static Observable<JsonElement> SignIn(Context context, String email, String password){
        return BaseAPIRequest.getInstanceRequestV2(context).SignIn(email, password);
    }

    public static Observable<JsonElement> SignUp(Context context, String livingarea, String name, String email, String phone,String address, String password){
        return BaseAPIRequest.getInstanceRequestV2(context).SignUp(livingarea, name, email,phone, address, password);
    }

    public static Observable<JsonElement> PostPurchase(Context context, String title, String price, String content, String name,
                                                       String phone, String address, int user_id){
        return BaseAPIRequest.getInstanceRequestV2(context).PostPurchase(title, price, content, name, phone, address, user_id);
    }

    public static Observable<JsonElement> getMyPost(Context context, int user_id){
        return BaseAPIRequest.getInstanceRequestV2(context).getMyPost(user_id);
    }

    public static Observable<JsonElement> getMyPurchase(Context context, int user_id){
        return BaseAPIRequest.getInstanceRequestV2(context).getMyPurchase(user_id);
    }

    public static Observable<JsonElement> UpdateAndDelete(Context context, String query){
        return BaseAPIRequest.getInstanceRequestV2(context).UpdateAndDelete(query);
    }

    public static Observable<JsonElement> postWishlist(Context context, int user_id, int product_id){
        return BaseAPIRequest.getInstanceRequestV2(context).postWishlist(user_id, product_id);
    }

    public static Observable<JsonElement> getWishlist(Context context, int user_id){
        return BaseAPIRequest.getInstanceRequestV2(context).getWishlist(user_id);
    }

    public static Observable<JsonElement> Search(Context context, String key){
        return BaseAPIRequest.getInstanceRequestV2(context).Search(key);
    }

    public static Call<String> uploadImage(Context context, MultipartBody.Part image){
        return BaseAPIRequest.getInstanceRequestV2(context).uploadImage(image);
    }
}
