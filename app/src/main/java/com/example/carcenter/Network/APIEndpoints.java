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
    @GET("carcenter/getCategory.php")
    Observable<JsonElement> getCategory(@QueryMap Map<String, Object> queryMap);

    @GET("carcenter/getProduct.php")
    Observable<JsonElement> getProduct(@QueryMap Map<String, Object> queryMap);

    @GET("carcenter/getPurchase.php")
    Observable<JsonElement> getPurchase(@QueryMap Map<String, Object> queryMap);

    @FormUrlEncoded
    @POST("carcenter/getProductbyCompany.php")
    Observable<JsonElement> getProductbyCompany(@Field("key") String company);

    @FormUrlEncoded
    @POST("carcenter/SignIn.php")
    Observable<JsonElement> SignIn(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("carcenter/SignUp.php")
    Observable<JsonElement> SignUp(@Field("livingArea") String livingArea, @Field("name") String name,
                                   @Field("email") String email, @Field("phone") String phone,
                                   @Field("address") String address, @Field("password") String password);

    @FormUrlEncoded
    @POST("carcenter/UpdateDelete.php")
    Observable<JsonElement> UpdateAndDelete(@Field("query") String query);

    @FormUrlEncoded
    @POST("carcenter/PostPurchase.php")
    Observable<JsonElement> PostPurchase(@Field("title") String title, @Field("price") String price, @Field("content") String content,
                                         @Field("name") String name, @Field("phone") String phone,
                                         @Field("address") String address, @Field("user_id") int user_id);

    @FormUrlEncoded
    @POST("carcenter/getMySale.php")
    Observable<JsonElement> getMyPost (@Field("key") int user_id);

    @FormUrlEncoded
    @POST("carcenter/getMyPurchase.php")
    Observable<JsonElement> getMyPurchase (@Field("key") int user_id);
}

