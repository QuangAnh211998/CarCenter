package com.example.carcenter.Network;
import android.content.Context;
import com.google.gson.JsonElement;
import java.util.HashMap;
import io.reactivex.Observable;

public class APIRequest {
    public static Observable<JsonElement> getCategory(Context context) {
        HashMap<String, Object> queryMap = new HashMap<>();
        return BaseAPIRequest.getInstanceRequestV2(context).getCategory(queryMap);
    }

    public static Observable<JsonElement> getProduct(Context context) {
        HashMap<String, Object> queryMap = new HashMap<>();
        return BaseAPIRequest.getInstanceRequestV2(context).getProduct(queryMap);
    }

    public static Observable<JsonElement> getPurchase(Context context) {
        HashMap<String, Object> queryMap = new HashMap<>();
        return BaseAPIRequest.getInstanceRequestV2(context).getPurchase(queryMap);
    }
}
