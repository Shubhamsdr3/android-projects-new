package com.pandey.todos.data;


import io.reactivex.Flowable;

public class LocalUserDataSource implements UserDatasource {

    private final UserDao mUserDao;

    public LocalUserDataSource(UserDao mUserDao) {
        this.mUserDao = mUserDao;
    }

    @Override
    public Flowable<User> getUser() {
       return mUserDao.getUser();
    }

    @Override
    public void insertUser(User user) {
        mUserDao.insertUser(user);
    }

    @Override
    public void deleteUsers() {
        mUserDao.deleteUser();
    }
}