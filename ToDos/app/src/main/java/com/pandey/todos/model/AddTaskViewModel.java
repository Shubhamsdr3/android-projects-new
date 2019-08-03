package com.pandey.todos.model;

import com.pandey.todos.data.AppDatabase;
import com.pandey.todos.data.TaskEntry;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class AddTaskViewModel extends ViewModel
{
    private LiveData<TaskEntry> task;

    public LiveData<TaskEntry> getTaskEntryLiveData() {
        return task;
    }

    public AddTaskViewModel(AppDatabase appDatabase, int taskId)
    {
        task = appDatabase.taskDao().loadTaskById(taskId);

    }
}