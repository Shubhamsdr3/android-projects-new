package com.pandey.todos.model;

import android.content.Context;

import com.pandey.todos.data.LocalUserDataSource;
import com.pandey.todos.data.UserDatasource;
import com.pandey.todos.data.UsersDatabase;

public class Injection {

    public static UserDatasource provideUserDataSource(Context context) {
        UsersDatabase database = UsersDatabase.getInstance(context);
        return new LocalUserDataSource(database.userDao());
    }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        UserDatasource dataSource = provideUserDataSource(context);
        return new ViewModelFactory(dataSource);

    }
}