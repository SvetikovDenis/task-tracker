package com.denis.svetikov.tasktracker.service.impl;

import com.denis.svetikov.tasktracker.model.TaskStatus;
import com.denis.svetikov.tasktracker.repository.TaskStatusRepository;
import com.denis.svetikov.tasktracker.service.TaskStatusService;
import org.springframework.stereotype.Service;

@Service
public class TaskStatusServiceImpl implements TaskStatusService {


    private final TaskStatusRepository taskStatusRepository;

    public TaskStatusServiceImpl(TaskStatusRepository taskStatusRepository) {
        this.taskStatusRepository = taskStatusRepository;
    }

    @Override
    public TaskStatus getTaskStatusById(Long id) {
        return taskStatusRepository.findById(id).orElse(null);
    }
}
