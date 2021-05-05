package com.example.carcenter.Network;
import com.google.gson.JsonElement;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface APIEndpoints {
    @GET("/severs/getCategory.php")
    Observable<JsonElement> getCategory(@QueryMap Map<String, Object> queryMap);

    @GET("/severs/getProduct.php")
    Observable<JsonElement> getProduct(@QueryMap Map<String, Object> queryMap);

    @GET("/severs/getPurchase.php")
    Observable<JsonElement> getPurchase(@QueryMap Map<String, Object> queryMap);
}
