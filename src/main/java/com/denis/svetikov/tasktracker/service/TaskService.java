package com.denis.svetikov.tasktracker.service;

import com.denis.svetikov.tasktracker.model.Task;
import com.denis.svetikov.tasktracker.model.TaskStatus;
import com.denis.svetikov.tasktracker.specification.SearchCriteria;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TaskService {

    List<Task> getAll(SearchCriteria request);

    List<Task> getAllByUserId(Long id);

    List<Task> getAllByUseName(String username);

    Task findById(Long id);

    Task saveTask(Task task);

    void delete(Long id);


}
