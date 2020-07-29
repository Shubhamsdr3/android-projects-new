package com.pandey.popcorn4.data.network

import android.content.Context
import com.pandey.popcorn4.PopApplication
import com.pandey.popcorn4.RetrofitClient
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class RetrofitHelper {

    companion object {

        private fun getOkHttpClient(context: Context): OkHttpClient? {
            return OkHttpClient.Builder()
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(100, TimeUnit.SECONDS)
                    .writeTimeout(100, TimeUnit.SECONDS)
                    .addNetworkInterceptor(ChuckInterceptor(context))
                    .addInterceptor { chain: Interceptor.Chain ->
                        val original = chain.request()
                        val requestBuilder = original.newBuilder()
                                .addHeader("Accept", "application/json")
                                .addHeader("Content-Type", "application/json")
                        val request = requestBuilder.build()
                        chain.proceed(request)
                    }
                    .build()
        }

        fun getApiService(): ApiService {
            val retrofit = RetrofitClient.getInstance(getOkHttpClient(PopApplication.getContext()))
            return retrofit.create(ApiService::class.java)
        }
    }
}