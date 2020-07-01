package com.denis.svetikov.tasktracker.service.impl;

import com.denis.svetikov.tasktracker.model.Task;
import com.denis.svetikov.tasktracker.model.TaskStatus;
import com.denis.svetikov.tasktracker.repository.TaskRepository;
import com.denis.svetikov.tasktracker.service.TaskService;
import com.denis.svetikov.tasktracker.specification.SearchCriteria;
import com.denis.svetikov.tasktracker.specification.TaskSearchCriteriaSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {


    private final TaskRepository taskRepository;
    private final TaskSearchCriteriaSpecification taskSpecification;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository,TaskSearchCriteriaSpecification taskSpecification) {
        this.taskRepository = taskRepository;
        this.taskSpecification = taskSpecification;
    }


    @Override
    public List<Task> getAllByUserId(Long id) {
        return taskRepository.findAllByUserId(id);
    }

    @Override
    public List<Task> getAllByUseName(String username) {
        return taskRepository.findAllByUser_Username(username);
    }

    @Override
    public List<Task> getAll(SearchCriteria request) {
        return taskRepository.findAll(taskSpecification.getFilter(request));
    }

    @Override
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task findById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }


    @Override
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}
