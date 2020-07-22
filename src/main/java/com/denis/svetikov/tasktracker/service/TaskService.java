package com.denis.svetikov.tasktracker.service;

import com.denis.svetikov.tasktracker.dto.model.TaskDto;
import com.denis.svetikov.tasktracker.model.Task;
import com.denis.svetikov.tasktracker.specification.TaskSearchCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface TaskService {

    List<TaskDto> getAll(TaskSearchCriteria request, Pageable pageable);

    List<TaskDto> getAllByUserId(Long id);

    List<TaskDto> getAllByUseName(String username);

    TaskDto getTaskDtoById(Long id);

    Task getTaskById(Long id);

    TaskDto createTask(TaskDto task, Authentication authentication);

    TaskDto updateTask(TaskDto task);

    TaskDto updateTaskStatus(Long taskId,Long statusId);

    void delete(Long id);


}
