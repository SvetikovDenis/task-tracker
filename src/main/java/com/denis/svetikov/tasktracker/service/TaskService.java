package com.denis.svetikov.tasktracker.service;

import com.denis.svetikov.tasktracker.dto.model.TaskDto;
import com.denis.svetikov.tasktracker.dto.model.UserTaskDto;
import com.denis.svetikov.tasktracker.specification.SearchCriteria;

import java.security.Principal;
import java.util.List;

public interface TaskService {

    List<TaskDto> getAll(SearchCriteria request);

    List<TaskDto> getAllByUserId(Long id);

    List<TaskDto> getAllByUseName(String username);

    TaskDto findById(Long id);

    TaskDto createTask(TaskDto task, Principal principal);

    TaskDto updateTask(TaskDto task);

    UserTaskDto updateUserTask(Long taskId, Long userId);

    TaskDto updateTaskStatus(Long taskId,Long statusId);

    void delete(Long id);


}
