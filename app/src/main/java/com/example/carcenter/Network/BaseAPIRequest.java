package com.example.carcenter.Network;

import android.content.Context;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseAPIRequest {
    public static APIEndpoints getInstanceRequestV2(Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.6:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(APIEndpoints.class);
    }
}
