package com.example.carcenter.Network;
import com.google.gson.JsonElement;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;

public interface APIEndpoints {
    @GET("carcenter/getCompany.php")
    Observable<JsonElement> getCompany(@QueryMap Map<String, Object> queryMap);

    @GET("carcenter/getAllCompany.php")
    Observable<JsonElement> getAllCompany(@QueryMap Map<String, Object> queryMap);

    @FormUrlEncoded
    @POST("carcenter/getCategory.php")
    Observable<JsonElement> getCategory(@Field("key") int id);

    @GET("carcenter/getProduct.php")
    Observable<JsonElement> getProduct(@QueryMap Map<String, Object> queryMap);

    @GET("carcenter/getProvince.php")
    Observable<JsonElement> getProvince(@QueryMap Map<String, Object> queryMap);

    @GET("carcenter/getPurchase.php")
    Observable<JsonElement> getPurchase(@QueryMap Map<String, Object> queryMap);

    @FormUrlEncoded
    @POST("carcenter/getProductbyCompany.php")
    Observable<JsonElement> getProductbyKey(@Field("query") String query);

    @FormUrlEncoded
    @POST("carcenter/getPurchasebyKey.php")
    Observable<JsonElement> getPurchasebyKey(@Field("query") String query);

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

    @FormUrlEncoded
    @POST("carcenter/postWishlist.php")
    Observable<JsonElement> postWishlist (@Field("user_id") int user_id, @Field("product_id") int product_id);

    @FormUrlEncoded
    @POST("carcenter/getWishlist.php")
    Observable<JsonElement> getWishlist (@Field("key") int user_id);

    @FormUrlEncoded
    @POST("carcenter/Search.php")
    Observable<JsonElement> Search (@Field("key") String key);

    @Multipart
    @POST("carcenter/uploadImage.php")
    Call<String> uploadImage(@Part MultipartBody.Part image);
}

