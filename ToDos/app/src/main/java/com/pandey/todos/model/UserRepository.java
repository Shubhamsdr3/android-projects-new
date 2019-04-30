package com.pandey.todos.model;

public interface UserRepository {

    String getEmailId();

    String getPassword();

    boolean isValidUser();
}
