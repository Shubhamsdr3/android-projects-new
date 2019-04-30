package com.pandey.todos.model;

import android.app.Application;
import android.util.Log;

import com.pandey.todos.data.AppDatabase;
import com.pandey.todos.data.TaskEntry;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import io.reactivex.Observable;

public class MainViewModel extends AndroidViewModel
{
    Observable<List<TaskEntry>> tasks;

    private final static String LOG_TAG = MainViewModel.class.getSimpleName();

    public MainViewModel(@NonNull Application application)
    {
        super(application);

        AppDatabase appDatabase = AppDatabase.getInstance(this.getApplication());
        Log.i(LOG_TAG, "Actively retrieving data from view model..");

        tasks =  appDatabase.taskDao().loadAllTasks();
    }

    public Observable<List<TaskEntry>> getAllTasks() {
        return tasks;
    }
}