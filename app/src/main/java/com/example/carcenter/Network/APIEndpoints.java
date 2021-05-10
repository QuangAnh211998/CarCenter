package com.example.carcenter.Network;
import com.google.gson.JsonElement;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface APIEndpoints {
    @GET("severs/getCategory.php")
    Observable<JsonElement> getCategory(@QueryMap Map<String, Object> queryMap);

    @GET("severs/getProduct.php")
    Observable<JsonElement> getProduct(@QueryMap Map<String, Object> queryMap);

    @GET("severs/getPurchase.php")
    Observable<JsonElement> getPurchase(@QueryMap Map<String, Object> queryMap);

    @FormUrlEncoded
    @POST("severs/getProductbyCompany.php")
    Observable<JsonElement> getProductbyCompany(@Field("key") String company);

    @FormUrlEncoded
    @POST("severs/SignIn.php")
    Observable<JsonElement> SignIn(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("severs/SignUp.php")
    Observable<JsonElement> SignUp(@Field("livingArea") String livingArea, @Field("name") String name,
                                   @Field("email") String email, @Field("phone") String phone,
                                   @Field("address") String address, @Field("password") String password);

    @FormUrlEncoded
    @POST("severs/UpdateDelete.php")
    Observable<JsonElement> UpdateAndDelete(@Field("query") String query);
}
