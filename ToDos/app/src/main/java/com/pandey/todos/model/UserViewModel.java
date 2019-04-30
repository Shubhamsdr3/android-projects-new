package com.pandey.todos.model;

import com.pandey.todos.data.User;
import com.pandey.todos.data.UserDatasource;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import io.reactivex.Completable;
import io.reactivex.Flowable;

public class UserViewModel extends ViewModel {

    @Nullable
    private User mUser;

    @NonNull
    private UserDatasource mUserDatasource;


    public UserViewModel(@NonNull User mUser, @NonNull UserDatasource mUserDatasource) {
        this.mUser = mUser;
        this.mUserDatasource = mUserDatasource;
    }

    public Flowable<String> getUserName(){
        return mUserDatasource.getUser()
                .map(user -> {
                    mUser = user;
                    return user.getUserName();
                });
    }

    public Flowable<String> getUserPassword() {
        return mUserDatasource.getUser()
                .map(user -> {
                    mUser = user;
                    return user.getUserPassword();
                });
    }

    public Completable updateUser(final String userName, final String userPassword) {
        return Completable.fromAction(() -> {
            mUser = mUser  == null ?
                    new User(userName, userPassword) :
                    new User(mUser.getUserName(), mUser.getUserPassword());
            mUserDatasource.insertUser(mUser);
        });
    }

}
