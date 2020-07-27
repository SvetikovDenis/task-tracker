package com.denis.svetikov.tasktracker.service.impl;

import com.denis.svetikov.tasktracker.exception.db.EntityNotFoundException;
import com.denis.svetikov.tasktracker.model.TaskStatus;
import com.denis.svetikov.tasktracker.repository.TaskStatusRepository;
import com.denis.svetikov.tasktracker.service.TaskStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TaskStatusServiceImpl implements TaskStatusService {


    @Autowired
    private  TaskStatusRepository taskStatusRepository;


    @Override
    public TaskStatus getTaskStatusById(Long id) {
        TaskStatus taskStatus = taskStatusRepository.findById(id).orElse(null);
        if (taskStatus == null) {
            log.warn("In getTaskStatusById - No task status found with id {}",id);
            throw new EntityNotFoundException(TaskStatus.class, "id", id.toString());
        }
        log.info("In getTaskStatusById - Task status found with id {}",id);
        return taskStatus;
    }
}
