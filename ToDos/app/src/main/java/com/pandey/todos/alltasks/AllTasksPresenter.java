package com.pandey.todos.alltasks;

import com.pandey.todos.data.TaskEntry;

import java.util.List;

public class AllTasksPresenter {

    private AllTasksView allTaksView;
    private TaskRepository taskRepository;

    AllTasksPresenter(AllTasksView allTaksView, TaskRepository taskRepository) {
        this.allTaksView = allTaksView;
        this.taskRepository = taskRepository;
    }

    public void loadAllTasks() {
        List<TaskEntry> taskList = taskRepository.getAllTasks();
        if (taskList.isEmpty()) {
            allTaksView.displayNoTask();
        } else {
            allTaksView.displayAllTasks(taskList);
        }
    }

    public interface AllTasksView {
        void displayAllTasks(List<TaskEntry> taskList);
        void displayNoTask();
    }
}
