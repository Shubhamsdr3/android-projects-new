package com.pandey.popcorn.utils;

import android.content.Context;

import com.pandey.popcorn.ApiService;
import com.pandey.popcorn.PopApplication;
import com.pandey.popcorn.RetrofitClient;
import com.readystatesoftware.chuck.ChuckInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class RetrofitHelper {

    private static OkHttpClient getOkHttpClient(Context context) {
        return new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .addInterceptor(new ChuckInterceptor(context))
                .build();
    }

    public static ApiService getApiService() {
        Retrofit retrofit = RetrofitClient.getInstance(getOkHttpClient(PopApplication.getContext()));
        return retrofit.create(ApiService.class);
    }
}
