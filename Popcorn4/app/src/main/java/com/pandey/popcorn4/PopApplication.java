package com.pandey.popcorn4;


import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.pandey.popcorn4.data.network.RetrofitHelper;
import com.pixplicity.easyprefs.library.Prefs;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class PopApplication extends Application {

    private static PopApplication instance;

    private static FirebaseAnalytics mFirebaseAnalytics;

    private GlobalBuses globalBuses;

    private static final String APP_CONFIG_KEY = "APP_CONFIG_KEY";

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.setUserId("1010");
        mFirebaseAnalytics.setUserProperty("user_id", "pandey_shubham");
        mFirebaseAnalytics.setUserProperty("user_name", "Shubham Pandey");
        globalBuses = new GlobalBuses();
        fetchApiConfig();
        initConfig();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    // Initialize the Prefs class
    private void initConfig() {
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();
    }

    @SuppressLint("CheckResult")
    private void fetchApiConfig() {
        RetrofitHelper.Companion.getApiService()
                .getAppConfig(
                        AppConfig.getConfigUrl(),
                        AppConfig.getMovieApiKey()
                ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(configResponseDto -> {
                    Prefs.putString(APP_CONFIG_KEY, new Gson().toJson(configResponseDto));
                });
    }

    public static PopApplication getInstance(){
        return instance;
    }

    public static Context getContext(){
        return instance;
    }

    public static FirebaseAnalytics getFirebaseAnalytics() {
        return mFirebaseAnalytics;
    }

    public GlobalBuses getGlobalBuses() {
        return globalBuses;
    }
}
