package com.pandey.popcorn4;


import android.app.Application;
import android.content.Context;

public class PopApplication extends Application {

    private static PopApplication instance;

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
    }
}
