package com.denis.svetikov.tasktracker.service.impl;

import com.denis.svetikov.tasktracker.exception.db.EntityNotFoundException;
import com.denis.svetikov.tasktracker.model.TaskStatus;
import com.denis.svetikov.tasktracker.repository.TaskStatusRepository;
import com.denis.svetikov.tasktracker.service.TaskStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskStatusServiceImpl implements TaskStatusService {


    private final TaskStatusRepository taskStatusRepository;

    @Autowired
    public TaskStatusServiceImpl(TaskStatusRepository taskStatusRepository) {
        this.taskStatusRepository = taskStatusRepository;
    }

    @Override
    public TaskStatus getTaskStatusById(Long id) {
        TaskStatus taskStatus = taskStatusRepository.findById(id).orElse(null);
        if (taskStatus == null) {
            throw new EntityNotFoundException(TaskStatus.class, "id", id.toString());
        }
        return taskStatus;
    }
}
