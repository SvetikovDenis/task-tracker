package com.denis.svetikov.tasktracker.service;


import com.denis.svetikov.tasktracker.model.Task;
import com.denis.svetikov.tasktracker.model.UserTask;

import java.util.List;

public interface UserTaskService {

    List<UserTask> getAll();

    UserTask getUserTaskById(Long id);

    UserTask getUserTaskByTask(Task task);

    void save(UserTask userTask);

    void deleteById(Long id);

}
