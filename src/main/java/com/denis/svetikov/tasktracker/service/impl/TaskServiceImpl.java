package com.denis.svetikov.tasktracker.service.impl;

import com.denis.svetikov.tasktracker.dto.model.TaskDto;
import com.denis.svetikov.tasktracker.exception.db.EntityNotFoundException;
import com.denis.svetikov.tasktracker.mapper.TaskMapper;
import com.denis.svetikov.tasktracker.model.Task;
import com.denis.svetikov.tasktracker.model.TaskStatus;
import com.denis.svetikov.tasktracker.model.User;
import com.denis.svetikov.tasktracker.model.UserTask;
import com.denis.svetikov.tasktracker.repository.TaskRepository;
import com.denis.svetikov.tasktracker.service.TaskService;
import com.denis.svetikov.tasktracker.service.TaskStatusService;
import com.denis.svetikov.tasktracker.service.UserService;
import com.denis.svetikov.tasktracker.service.UserTaskService;
import com.denis.svetikov.tasktracker.specification.TaskSearchCriteria;
import com.denis.svetikov.tasktracker.specification.TaskSearchCriteriaSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TaskServiceImpl implements TaskService {


    @Autowired
    private UserService userService;
    @Autowired
    private UserTaskService userTaskService;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskSearchCriteriaSpecification taskSpecification;
    @Autowired
    private TaskStatusService taskStatusService;
    @Autowired
    private TaskMapper taskMapper;


    @Override
    public List<TaskDto> getAll(TaskSearchCriteria request, Pageable pageable) {

        Sort sort = Sort.by(Sort.Direction.fromString(
                Optional.ofNullable(request.getOrder()).orElse("desc")),
                Optional.ofNullable(request.getSort()).orElse("created"));

        Page<Task> tasks  = taskRepository.findAll(taskSpecification.getFilter(request), PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort));
        if(tasks.isEmpty()){
            log.warn("In getAll - No tasks found for search criteria " + request);
            throw new EntityNotFoundException(Task.class, "Tasks", "Get all tasks and search criteria : " + request);
        }
        log.info("In getAll - {} tasks was found for search criteria : {}",tasks.getTotalElements(),request);
        return  tasks.
                stream().
                map(taskMapper::toDto).
                collect(Collectors.toList());
    }

    @Override
    public TaskDto getTaskDtoById(Long id) {

        Task task = taskRepository.findById(id).orElse(null);
        if (task == null) {
            log.warn("In getTaskDtoById - No task found with id : {}", id);
            throw new EntityNotFoundException(Task.class, "id", id.toString());
        }
        log.info("In getTaskDtoById - Get task with id : {}", task.getId());
        return taskMapper.toDto(task);
    }

    @Override
    public Task getTaskById(Long id) {

        Task task = taskRepository.findById(id).orElse(null);
        if (task == null) {
            log.warn("In getTaskById - No task found with id : {}", id);
            throw new EntityNotFoundException(Task.class, "id", id.toString());
        }
        log.info("In getTaskById - Get task with id : {}", task.getId());
        return task;
    }

    @Override
    public List<TaskDto> getAllByUserId(Long id) {

        List<Task> tasks  = taskRepository.findAllByUserId(id);
        if(tasks.isEmpty()){
            log.warn("In getAllByUserId - No tasks found for user id : {}",id);
            throw new EntityNotFoundException(Task.class, "user id", id.toString());
        }

        log.info("In getAllByUserId - {} tasks found with user id : {}",tasks.size(),id);

        return  tasks.
                stream().
                map(taskMapper::toDto).
                collect(Collectors.toList());
    }

    @Override
    public List<TaskDto> getAllByUseName(String username) {

        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username can't be null or empty");
        }

        List<Task> tasks  = taskRepository.findAllByUser_Username(username);
        if(tasks.isEmpty()){
            log.warn("In getAllByUseName - No tasks found for username : {}",username);
            throw new EntityNotFoundException(Task.class, "username", username);
        }
        log.info("In getAllByUseName - {} tasks found for username : {}", tasks.size(),username);
        return tasks.
                stream().
                map(taskMapper::toDto).
                collect(Collectors.toList());
    }


    @Transactional
    @Override
    public TaskDto createTask(TaskDto task, Authentication authentication) {

        TaskStatus taskStatus = taskStatusService.getTaskStatusById(task.getStatusId());

        if (taskStatus == null) {
            log.warn("In createTask - No task status found with id : {}", task.getStatusId());
            throw new EntityNotFoundException(TaskStatus.class, "id", task.getStatusId().toString());
        }

        Task newTask = taskMapper.toEntity(task);
        newTask = taskRepository.save(newTask);

        log.info("In createTask - Created new task with id : {}",newTask.getId());

        User user = userService.getUserByUsername(authentication.getName());
        UserTask userTask = new UserTask();
        userTask.setTask(newTask);
        userTask.setUser(user);

        userTaskService.save(userTask);
        log.info("In createTask - Task with id : {} was assign to user with id : {}",newTask.getId(),user.getId());

        return taskMapper.toDto(newTask);
    }


    @Override
    public TaskDto updateTask(TaskDto taskDto) {

        Task task = taskRepository.findById(taskDto.getId()).orElse(null);
        if (task == null) {
            log.warn("In updateTask - No task found with id : {}",taskDto.getId());
            throw new EntityNotFoundException(Task.class, "id", taskDto.getId().toString());
        }

        taskMapper.updateEntity(taskDto,task);
        taskRepository.save(task);
        log.info("In updateTask - Task with id : {} was updated ", task.getId());
        return taskMapper.toDto(task);
    }

    @Override
    public TaskDto updateTaskStatus(Long taskId,Long statusId) {

        Task task = taskRepository.findById(taskId).orElse(null);
        if (task == null) {
            log.warn("In updateTaskStatus - No task found with id : {} ", taskId);
            throw new EntityNotFoundException(Task.class, "id", taskId.toString());
        }
        TaskStatus taskStatus = taskStatusService.getTaskStatusById(statusId);
        if (taskStatus == null) {
            log.warn("In updateTaskStatus - No task status found with id : {} ", statusId);
            throw new EntityNotFoundException(TaskStatus.class, "id", statusId.toString());
        }
        task.setStatus(taskStatus);
        task = taskRepository.save(task);
        log.info("In updateTaskStatus - For task with id : {} was updated status to : {}",taskId,statusId);
        return taskMapper.toDto(task);
    }


    @Override
    public void delete(Long id) {
        Task task = taskRepository.findById(id).orElse(null);
        if (task == null) {
            log.warn("In delete task - task with id : {} was not found",id);
            throw new EntityNotFoundException(Task.class, "id", id.toString());
        }
        taskRepository.deleteById(id);
        log.info("In delete task - Task with id : {} was deleted", id);
    }

}
