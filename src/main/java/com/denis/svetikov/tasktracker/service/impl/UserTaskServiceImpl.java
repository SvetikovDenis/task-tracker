package com.denis.svetikov.tasktracker.service.impl;

import com.denis.svetikov.tasktracker.dto.model.UserTaskDto;
import com.denis.svetikov.tasktracker.exception.db.EntityNotFoundException;
import com.denis.svetikov.tasktracker.mapper.UserTaskMapper;
import com.denis.svetikov.tasktracker.model.Task;
import com.denis.svetikov.tasktracker.model.User;
import com.denis.svetikov.tasktracker.model.UserTask;
import com.denis.svetikov.tasktracker.repository.UserTaskRepository;
import com.denis.svetikov.tasktracker.service.TaskService;
import com.denis.svetikov.tasktracker.service.UserService;
import com.denis.svetikov.tasktracker.service.UserTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserTaskServiceImpl implements UserTaskService {


    @Autowired
    private UserTaskRepository userTaskRepository;
    @Autowired
    private  UserTaskMapper userTaskMapper;
    @Autowired
    private  TaskService taskService;
    @Autowired
    private UserService userService;


    @Override
    public List<UserTaskDto> getAll() {

        List<UserTask> userTasks = userTaskRepository.findAll();

        if (userTasks.isEmpty()) {
            log.warn("In getAll - No user tasks was found");
            throw new EntityNotFoundException(UserTask.class, "User tasks", "Get all");
        }
        log.info("In getAll - {} user tasks was found", userTasks.size());
        return userTasks.
                stream().
                map(userTaskMapper::toDto).
                collect(Collectors.toList());
    }

    @Override
    public List<UserTaskDto> getAllUserTasksByUserId(Long id) {

        List<UserTask> userTasks = userTaskRepository.getAllByUserId(id);
        if (userTasks.isEmpty()) {
            log.warn("In getAllUserTasksByUserId - No user tasks found for user id {}" ,id);
            throw new EntityNotFoundException(UserTask.class, " user id", id.toString());
        }
        log.info("In getAllUserTasksByUserId - {} user tasks was found", userTasks.size());
        return userTasks.
                stream().
                map(userTaskMapper::toDto).
                collect(Collectors.toList());
    }

    @Override
    public UserTask getUserTaskById(Long id) {

        UserTask userTask = userTaskRepository.findById(id).orElse(null);
        if (userTask == null) {
            log.warn("In getUserTaskById - No user task found with id {}" ,id);
            throw new EntityNotFoundException(UserTask.class, " user id", id.toString());
        }
        log.info("In getUserTaskById - user task with id :  {} was found", id);

        return userTask;
    }

    @Override
    public UserTaskDto getUserTaskDtoById(Long id) {
        UserTask userTask = userTaskRepository.findById(id).orElse(null);
        if (userTask == null) {
            log.warn("In getUserTaskDtoById - No user task found with id {}" ,id);
            throw new EntityNotFoundException(UserTask.class, " user id", id.toString());
        }
        log.info("In getUserTaskDtoById - user task with id :  {} was found", id);

        return userTaskMapper.toDto(userTask);
    }

    @Override
    public UserTaskDto getUserTaskDtoByTaskId(Long id) {
        UserTask userTask = userTaskRepository.findUserTaskByTaskId(id);
        if (userTask == null) {
            log.warn("In getUserTaskDtoByTaskId - No user task found with task id : {}" ,id);
            throw new EntityNotFoundException(UserTask.class, " task id", id.toString());
        }
        log.info("In getUserTaskDtoByTaskId - user task with id :  {} was found", id);

        return userTaskMapper.toDto(userTask);
    }

    @Override
    public UserTask getUserTaskByTaskId(Long id) {

        UserTask userTask = userTaskRepository.findUserTaskByTaskId(id);
        if (userTask == null) {
            log.warn("In getUserTaskByTaskId - No user task was found by task id {}" ,id);
            throw new EntityNotFoundException(UserTask.class, " task id", id.toString());
        }
        log.info("In getUserTaskByTaskId - User Task with task id : {} was found",id);
        return userTask;
    }

    @Override
    public UserTask getUserTaskByTask(Task task) {
        return userTaskRepository.findUserTaskByTask(task);
    }

    @Override
    public UserTask save(UserTask userTask) {
       return userTaskRepository.save(userTask);
    }


    @Transactional
    @Override
    public UserTaskDto updateUserTask(Long taskId, Long userId) {

        Task task = taskService.getTaskById(taskId);
        if (task == null) {
            log.warn("In updateUserTask - No task was found with id : {}",taskId);
            throw new EntityNotFoundException(Task.class, "id", taskId.toString());
        }
        User user = userService.getUserById(userId);
        if (user == null) {
            log.warn("In updateUserTask - No user was found with id : {}",userId);
            throw new EntityNotFoundException(User.class, "id", userId.toString());
        }
        UserTask userTask = userTaskRepository.findUserTaskByTaskId(taskId);

        if (userTask == null) {
            userTask = new UserTask();
            userTask.setTask(task);
            userTask.setUser(user);
            userTaskRepository.save(userTask);
            log.info("In UpdateUserTask - Created new user task with task id : {} and user id : {}",taskId,userId);
        }
        else {
            userTask.setUser(user);
            userTaskRepository.save(userTask);
            log.info("In UpdateUserTask - for task with id : {} was assign new user with id : {}",taskId,userId);
        }

        return userTaskMapper.toDto(userTask);
    }

    @Override
    public void deleteById(Long id) {
        userTaskRepository.deleteById(id);
    }

    @Override
    public void deleteByTaskId(Long id) {
        UserTask userTask = userTaskRepository.findUserTaskByTaskId(id);
        if (userTask == null) {
            log.warn("In deleteByTaskId - No user task found by task id : {}",id);
            throw new EntityNotFoundException(UserTask.class, "task id", id.toString());
        }
        userTaskRepository.deleteByTaskId(id);
        log.info("In deleteByTaskId - User task with task id : {} was deleted", id);
    }

    @Override
    public void deleteByUserId(Long id) {
        List<UserTask> userTasks = userTaskRepository.getAllByUserId(id);
        if (userTasks.isEmpty()) {
            log.warn("In deleteByUserId - No user tasks was found by user id : {}",id);
            throw new EntityNotFoundException(UserTask.class, "user id", id.toString());
        }
        userTaskRepository.deleteByUserId(id);
        log.info("In deleteByUserId - user tasks was delete by user id : {} ", id);
    }
}
