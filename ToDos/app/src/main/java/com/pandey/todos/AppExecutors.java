package com.pandey.todos;

import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;

public class AppExecutors
{
    private static final Object LOCK = new Object();

    private static AppExecutors instance;

    private final Executor diskIO;

    private final Executor mainThread;

    private final Executor networkIO;

    public AppExecutors(Executor diskIO, Executor mainThread, Executor networkIO) {
        this.diskIO = diskIO;
        this.mainThread = mainThread;
        this.networkIO = networkIO;
    }


    public static AppExecutors getInstance()
    {
        Log.i("AppExecutors", "Inside app executors..");
        if(instance == null)
        {
            synchronized (LOCK)
            {
                instance = new AppExecutors(Executors.newSingleThreadExecutor(),
                        Executors.newFixedThreadPool(3),
                        new MainThreadExecutor());
            }
        }
        return instance;
    }

    public Executor diskIO() {
        return diskIO;
    }

    public Executor mainThread() {
        return mainThread;
    }

    public Executor networkIO() {
        return networkIO;
    }

    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }


}