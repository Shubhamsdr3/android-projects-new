package com.pandey.todos.alltasks;

import java.util.Date;

import androidx.annotation.NonNull;

public class Task {

    @NonNull
    private int id;

    @NonNull
    private String description;

    @NonNull
    private int priority;

    @NonNull
    private Date updatedAt;

    @NonNull
    private String taskType;

    public Task(@NonNull  int id, @NonNull String description,@NonNull int priority,@NonNull Date updatedAt, @NonNull String taskType) {
        this.id = id;
        this.description = description;
        this.priority = priority;
        this.updatedAt = updatedAt;
        this.taskType = taskType;
    }

    public int getId() {
        return id;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    @NonNull
    public int getPriority() {
        return priority;
    }

    @NonNull
    public Date getUpdatedAt() {
        return updatedAt;
    }
    @NonNull
    public String getTaskType() {
        return taskType;
    }

    @NonNull
    public void setId(int id) {
        this.id = id;
    }
    @NonNull
    public void setDescription(String description) {
        this.description = description;
    }

    @NonNull
    public void setPriority(int priority) {
        this.priority = priority;
    }

    @NonNull
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @NonNull
    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }
}