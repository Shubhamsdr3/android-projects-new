package com.pandey.popcorn4.utils;

import android.content.Context;

import com.pandey.popcorn4.network.ApiService;
import com.pandey.popcorn4.PopApplication;
import com.pandey.popcorn4.RetrofitClient;
import com.readystatesoftware.chuck.ChuckInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;

public class RetrofitHelper {

    private static OkHttpClient getOkHttpClient(Context context) {
        return new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .addNetworkInterceptor(new ChuckInterceptor(context))
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request.Builder requestBuilder = original.newBuilder()
                            .addHeader("Accept", "application/json")
                            .addHeader("Content-Type", "application/json");
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                })
                .build();
    }

    public static ApiService getApiService() {
        Retrofit retrofit = RetrofitClient.getInstance(getOkHttpClient(PopApplication.getContext()));
        return retrofit.create(ApiService.class);
    }
}

