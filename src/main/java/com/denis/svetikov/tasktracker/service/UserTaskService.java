package com.denis.svetikov.tasktracker.service;


import com.denis.svetikov.tasktracker.dto.model.UserTaskDto;
import com.denis.svetikov.tasktracker.model.Task;
import com.denis.svetikov.tasktracker.model.UserTask;

import java.util.List;

public interface UserTaskService {

    List<UserTaskDto> getAll();

    List<UserTaskDto> getAllUserTasksByUserId(Long id);

    UserTaskDto getUserTaskDtoByTaskId(Long id);

    UserTaskDto getUserTaskDtoById(Long id);

    UserTask getUserTaskById(Long id);

    UserTask getUserTaskByTask(Task task);

    UserTask getUserTaskByTaskId(Long id);

    void save(UserTask userTask);

    UserTaskDto updateUserTask(Long taskId, Long userId);

    void deleteById(Long id);

    void deleteByTaskId(Long id);

    void deleteByUserId(Long id);

}
