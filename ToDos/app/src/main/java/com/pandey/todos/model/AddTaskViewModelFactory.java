package com.pandey.todos.model;

import com.pandey.todos.data.AppDatabase;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class AddTaskViewModelFactory extends ViewModelProvider.NewInstanceFactory
{
    private final AppDatabase appDatabase;

    private final int taskId;

    public AddTaskViewModelFactory(AppDatabase appDatabase, int taskId) {
        this.appDatabase = appDatabase;
        this.taskId = taskId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddTaskViewModel(appDatabase, taskId);

    }
}