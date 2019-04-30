package com.pandey.todos.data;

import io.reactivex.Flowable;

public interface UserDatasource {

    Flowable<User> getUser();

    void insertUser(User user);

    void deleteUsers();
}
