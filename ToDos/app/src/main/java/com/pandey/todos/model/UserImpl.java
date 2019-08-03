package com.pandey.todos.model;

public class UserImpl implements UserRepository
{

    private String emailId;

    private String password;

    public UserImpl(String emailId, String password) {
        this.emailId = emailId;
        this.password = password;
    }

    @Override
    public String getEmailId() {
        return emailId;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isValidUser() {
        return true;
    }
}