package com.denis.svetikov.tasktracker.service.impl;

import com.denis.svetikov.tasktracker.dto.model.TaskDto;
import com.denis.svetikov.tasktracker.dto.model.UserTaskDto;
import com.denis.svetikov.tasktracker.exception.db.EntityNotFoundException;
import com.denis.svetikov.tasktracker.model.Task;
import com.denis.svetikov.tasktracker.model.TaskStatus;
import com.denis.svetikov.tasktracker.model.User;
import com.denis.svetikov.tasktracker.model.UserTask;
import com.denis.svetikov.tasktracker.repository.TaskRepository;
import com.denis.svetikov.tasktracker.service.TaskService;
import com.denis.svetikov.tasktracker.service.TaskStatusService;
import com.denis.svetikov.tasktracker.service.UserService;
import com.denis.svetikov.tasktracker.service.UserTaskService;
import com.denis.svetikov.tasktracker.specification.SearchCriteria;
import com.denis.svetikov.tasktracker.specification.TaskSearchCriteriaSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {


    private final UserService userService;
    private final UserTaskService userTaskService;
    private final TaskRepository taskRepository;
    private final TaskSearchCriteriaSpecification taskSpecification;
    private final TaskStatusService taskStatusService;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository,TaskSearchCriteriaSpecification taskSpecification,UserService userService,UserTaskService userTaskService,TaskStatusService taskStatusService) {
        this.taskRepository = taskRepository;
        this.taskSpecification = taskSpecification;
        this.userService = userService;
        this.userTaskService = userTaskService;
        this.taskStatusService = taskStatusService;
    }

    @Override
    public List<TaskDto> getAll(SearchCriteria request) {
        List<Task> tasks  = taskRepository.findAll(taskSpecification.getFilter(request));
        if(tasks.size() == 0){
            throw new EntityNotFoundException(Task.class, "Tasks", "Get all tasks");
        }
        return  tasks.
                stream().
                map(TaskDto::fromTask).
                collect(Collectors.toList());
    }


    @Override
    public TaskDto findById(Long id) {

        Task task = taskRepository.findById(id).orElse(null);
        if (task == null) {
            throw new EntityNotFoundException(Task.class, "id", id.toString());
        }
        return TaskDto.fromTask(task);
    }

    @Override
    public List<TaskDto> getAllByUserId(Long id) {

        List<Task> tasks  = taskRepository.findAllByUserId(id);
        if(tasks.size() == 0){
            throw new EntityNotFoundException(Task.class, "user id", id.toString());
        }

        return  tasks.
                stream().
                map(TaskDto::fromTask).
                collect(Collectors.toList());
    }

    @Override
    public List<TaskDto> getAllByUseName(String username) {

        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username can't be null or empty");
        }

        List<Task> tasks  = taskRepository.findAllByUser_Username(username);
        if(tasks.size() == 0){
            throw new EntityNotFoundException(Task.class, "username", username);
        }
        return tasks.
                stream().
                map(TaskDto::fromTask).
                collect(Collectors.toList());
    }


    @Transactional
    @Override
    public TaskDto createTask(TaskDto task, Principal principal) {

        Task newTask = new Task();
        TaskStatus taskStatus = taskStatusService.getTaskStatusById(task.getStatus());

        newTask.setStatus(taskStatus);
        newTask.setTitle(task.getTitle());
        newTask.setDescription(task.getDescription());

        taskRepository.save(newTask);

        User user = userService.getUserByUsername(principal.getName());
        UserTask userTask = new UserTask();
        userTask.setTask(newTask);
        userTask.setUser(user);

        userTaskService.save(userTask);

        return TaskDto.fromTask(newTask);
    }


    @Override
    public TaskDto updateTask(TaskDto taskDto) {

        Task task = taskRepository.findById(taskDto.getId()).orElse(null);
        if (task == null) {
            throw new EntityNotFoundException(Task.class, "task", taskDto.toString());
        }

        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task = taskRepository.save(task);
        return TaskDto.fromTask(task);
    }


    @Override
    public TaskDto updateTaskStatus(Long taskId,Long statusId) {


        Task task = taskRepository.findById(taskId).orElse(null);

        if (task == null) {
            throw new EntityNotFoundException(Task.class, "id", taskId.toString());
        }

        TaskStatus taskStatus = taskStatusService.getTaskStatusById(statusId);

        if (taskStatus == null) {
            throw new EntityNotFoundException(TaskStatus.class, "id", statusId.toString());
        }

        task.setStatus(taskStatus);

        task = taskRepository.save(task);

        return TaskDto.fromTask(task);
    }

    @Override
    public UserTaskDto updateUserTask(Long taskId, Long userId) {

        Task task = taskRepository.findById(taskId).orElse(null);

        if (task == null) {
            throw new EntityNotFoundException(Task.class, "id", taskId.toString());
        }

        User user = userService.getUserById(userId);

        if (user == null) {
            throw new EntityNotFoundException(User.class, "id", userId.toString());
        }

        UserTask userTask = userTaskService.getUserTaskByTask(task);
        userTask.setUser(user);
        userTaskService.save(userTask);

        return UserTaskDto.userTaskDto(userTask);
    }

    @Override
    public void delete(Long id) {
        Task task = taskRepository.findById(id).orElse(null);

        if (task == null) {
            throw new EntityNotFoundException(Task.class, "id", id.toString());
        }
        taskRepository.deleteById(id);
    }

}
