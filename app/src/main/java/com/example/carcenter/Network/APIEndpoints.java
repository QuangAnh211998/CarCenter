package com.example.carcenter.Network;
import com.google.gson.JsonElement;

import java.util.List;
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
    Observable<JsonElement> PostPurchase(@Field("title") String title,
                                         @Field("price") String price,
                                         @Field("content") String content,
                                         @Field("name") String name,
                                         @Field("phone") String phone,
                                         @Field("address") String address,
                                         @Field("user_id") int user_id,
                                         @Field("approval") String approval);

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
    Observable<JsonElement> getWishlist (@Field("user_id") int user_id,
                                         @Field("product_id") int product_id);

    @FormUrlEncoded
    @POST("carcenter/getDataWishlist.php")
    Observable<JsonElement> getDataWishlist (@Field("key") int user_id);

    @GET("carcenter/getUser.php")
    Observable<JsonElement> getUser(@QueryMap Map<String, Object> queryMap);

    @GET("carcenter/getCountUser.php")
    Observable<JsonElement> getCountUser(@QueryMap Map<String, Object> queryMap);

    @FormUrlEncoded
    @POST("carcenter/getCountMySale.php")
    Observable<JsonElement> getCountMySale(@Field("userid") int user_id);

    @FormUrlEncoded
    @POST("carcenter/getCountMyPurchase.php")
    Observable<JsonElement> getCountMyPurchase(@Field("userid") int user_id);

    @FormUrlEncoded
    @POST("carcenter/PostImage.php")
    Observable<JsonElement> postImage (@Field("image_url") String url,
                                       @Field("product_id") String product_id);

    @FormUrlEncoded
    @POST("carcenter/getImage.php")
    Observable<JsonElement> getImage (@Field("key") int id);

    @Multipart
    @POST("carcenter/uploadImage.php")
    Call<String> uploadImage(@Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST("carcenter/PostProduct.php")
    Observable<JsonElement> postProduct (@Field("company") String company,
                                         @Field("name") String name,
                                         @Field("version") String version,
                                         @Field("year") String year,
                                         @Field("madein") String madein,
                                         @Field("status") String status,
                                         @Field("km") String km,
                                         @Field("type") String type,
                                         @Field("price") String price,
                                         @Field("outside") String outside,
                                         @Field("inside") String inside,
                                         @Field("door") String door,
                                         @Field("seat") String seat,
                                         @Field("gear") String gear,
                                         @Field("drive") String drive,
                                         @Field("fuel") String fuel,
                                         @Field("consume") String consume,
                                         @Field("image1") String image1,
                                         @Field("image2") String image2,
                                         @Field("image3") String image3,
                                         @Field("image4") String image4,
                                         @Field("image5") String image5,
                                         @Field("content") String content,
                                         @Field("uName") String uName,
                                         @Field("uPhone") String uPhone,
                                         @Field("uAddress") String uAddress,
                                         @Field("uLiving") String uLiving,
                                         @Field("airbag") String airbag,
                                         @Field("abs") String abs,
                                         @Field("eba") String eba,
                                         @Field("esp") String esp,
                                         @Field("antislip") String antislip,
                                         @Field("reverse") String reverse,
                                         @Field("antitheft") String antitheft,
                                         @Field("userid") int userid,
                                         @Field("approval") String approval);
}

