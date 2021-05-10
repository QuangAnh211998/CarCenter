package com.example.carcenter.Network;
import android.content.Context;

import com.google.gson.JsonElement;
import java.util.HashMap;
import io.reactivex.Observable;
import retrofit2.http.Field;

public class APIRequest {

    public static Observable<JsonElement> getCategory(Context context) {
        HashMap<String, Object> queryMap = new HashMap<>();
        return BaseAPIRequest.getInstanceRequestV2(context).getCategory(queryMap);
    }

    public static Observable<JsonElement> getProduct(Context context) {
        HashMap<String, Object> queryMap = new HashMap<>();
        return BaseAPIRequest.getInstanceRequestV2(context).getProduct(queryMap);
    }

    public static Observable<JsonElement> getPurchase (Context context) {
        HashMap<String, Object> queryMap = new HashMap<>();
        return BaseAPIRequest.getInstanceRequestV2(context).getPurchase(queryMap);
    }

    public static Observable<JsonElement> getProductbyCompany (Context context, String company){
        return BaseAPIRequest.getInstanceRequestV2(context).getProductbyCompany(company);
    }

    public static Observable<JsonElement> SignIn(Context context, String email, String password){
        return BaseAPIRequest.getInstanceRequestV2(context).SignIn(email, password);
    }

    public static Observable<JsonElement> SignUp(Context context, String livingarea, String name, String email, String phone,String address, String password){
        return BaseAPIRequest.getInstanceRequestV2(context).SignUp(livingarea, name, email,phone, address, password);
    }

    public static Observable<JsonElement> UpdateAndDelete(Context context, String query){
        return BaseAPIRequest.getInstanceRequestV2(context).UpdateAndDelete(query);
    }
}
