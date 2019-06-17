package com.pandey.popcorn;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String NEWS_BASE_URL = "https://newsapi.org/v2/";

    private static Retrofit ourInstance ;
    public static Retrofit getInstance(OkHttpClient okHttpClient) {

        if(ourInstance == null) {
            ourInstance = new Retrofit.Builder()
                    .baseUrl(NEWS_BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return ourInstance;
    }

    private RetrofitClient() {
    }

}
