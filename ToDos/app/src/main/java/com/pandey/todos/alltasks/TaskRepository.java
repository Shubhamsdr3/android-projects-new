package com.pandey.todos.alltasks;


import com.pandey.todos.data.TaskEntry;

import java.util.List;

interface TaskRepository {

    List<TaskEntry> getAllTasks();
}
