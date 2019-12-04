package com.pandey.popcorn4;


import android.app.Application;
import android.content.Context;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PopApplication extends Application {

    private static PopApplication instance;

    private static FirebaseAnalytics mFirebaseAnalytics;

    private GlobalBuses globalBuses;

    public static PopApplication getInstance(){
        return instance;
    }

    public static Context getContext(){
        return instance;
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        globalBuses = new GlobalBuses();
    }


    public static FirebaseAnalytics getFirebaseAnalytics() {
        return mFirebaseAnalytics;
    }

    public GlobalBuses getGlobalBuses() {
        return globalBuses;
    }
}
