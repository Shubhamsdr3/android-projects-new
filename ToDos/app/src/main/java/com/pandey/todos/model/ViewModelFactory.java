package com.pandey.todos.model;


import com.pandey.todos.data.User;
import com.pandey.todos.data.UserDatasource;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final UserDatasource mDataSource;
    private User user;

    public ViewModelFactory(UserDatasource dataSource) {
        mDataSource = dataSource;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {

        if (modelClass.isAssignableFrom(UserViewModel.class)) {
            return (T) new UserViewModel(user, mDataSource);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
