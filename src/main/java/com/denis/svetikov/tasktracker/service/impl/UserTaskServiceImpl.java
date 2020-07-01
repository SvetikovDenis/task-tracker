package com.denis.svetikov.tasktracker.service.impl;

import com.denis.svetikov.tasktracker.model.Task;
import com.denis.svetikov.tasktracker.model.User;
import com.denis.svetikov.tasktracker.model.UserTask;
import com.denis.svetikov.tasktracker.repository.UserTaskRepository;
import com.denis.svetikov.tasktracker.service.UserTaskService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTaskServiceImpl implements UserTaskService {


    private final UserTaskRepository userTaskRepository;

    public UserTaskServiceImpl(UserTaskRepository userTaskRepository) {
        this.userTaskRepository = userTaskRepository;
    }

    @Override
    public List<UserTask> getAll() {
        return userTaskRepository.findAll();
    }

    @Override
    public UserTask getUserTaskById(Long id) {
        return userTaskRepository.getOne(id);
    }

    @Override
    public UserTask getUserTaskByTask(Task task) {
        return userTaskRepository.findUserTaskByTask(task);
    }

    @Override
    public void save(UserTask userTask) {
        userTaskRepository.save(userTask);
    }

    @Override
    public void deleteById(Long id) {
        userTaskRepository.deleteById(id);
    }
}
