package com.denis.svetikov.tasktracker.rest;

import com.denis.svetikov.tasktracker.dto.TaskDto;
import com.denis.svetikov.tasktracker.dto.UserTaskDto;
import com.denis.svetikov.tasktracker.exception.db.EntityNotFoundException;
import com.denis.svetikov.tasktracker.model.Task;
import com.denis.svetikov.tasktracker.model.TaskStatus;
import com.denis.svetikov.tasktracker.model.User;
import com.denis.svetikov.tasktracker.model.UserTask;
import com.denis.svetikov.tasktracker.service.TaskStatusService;
import com.denis.svetikov.tasktracker.service.UserService;
import com.denis.svetikov.tasktracker.service.impl.TaskServiceImpl;
import com.denis.svetikov.tasktracker.service.impl.UserTaskServiceImpl;
import com.denis.svetikov.tasktracker.specification.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/api/v1/tasks/")
public class TaskRestControllerV1 {


    private final TaskServiceImpl taskService;
    private final TaskStatusService taskStatusService;
    private final UserTaskServiceImpl userTaskService;
    private final UserService userService;

    @Autowired
    public TaskRestControllerV1(TaskServiceImpl taskService,TaskStatusService taskStatusService,UserTaskServiceImpl userTaskService,UserService userService) {
        this.taskService = taskService;
        this.taskStatusService = taskStatusService;
        this.userTaskService = userTaskService;
        this.userService = userService;

    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks(SearchCriteria request){

        List<Task> tasks  = taskService.getAll(request);

        if(tasks == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<TaskDto> taskDtos = tasks.stream().map(task -> TaskDto.fromTask(task)).collect(Collectors.toList());

        return new ResponseEntity<>(taskDtos, HttpStatus.OK);
    }


    @GetMapping(value = "user", params = "id")
    public ResponseEntity<List<TaskDto>> getAllTasksByUserId(@RequestParam Long userId){

        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<Task> tasks  = taskService.getAllByUserId(userId);

        if(tasks == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<TaskDto> taskDtos = tasks.stream().map(task -> TaskDto.fromTask(task)).collect(Collectors.toList());

        return new ResponseEntity<>(taskDtos, HttpStatus.OK);
    }

    @GetMapping(value = "user",params = "username")
    public ResponseEntity<List<TaskDto>> getAllTasksByUserName(@RequestParam("username") String username){

        if (username == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<Task> tasks  = taskService.getAllByUseName(username);

        if(tasks == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<TaskDto> taskDtos = tasks.stream().map(task -> TaskDto.fromTask(task)).collect(Collectors.toList());

        return new ResponseEntity<>(taskDtos, HttpStatus.OK);
    }


    // Refactor return just DTO what about status and headers?

    @GetMapping("{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id) throws EntityNotFoundException {

        /*if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }*/

        Task task = taskService.findById(id);
        TaskDto taskDto = TaskDto.fromTask(task);

        return new ResponseEntity<>(taskDto, HttpStatus.OK);
    }


    @PostMapping
    @Transactional
    public ResponseEntity<Task> createTask(@RequestBody Task task,Principal principal){

        if(task == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        taskService.saveTask(task);

        User user = userService.findByUsername(principal.getName());

        UserTask userTask = new UserTask();
        userTask.setTask(task);
        userTask.setUser(user);

        userTaskService.save(userTask);

        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }


    @PutMapping
    public ResponseEntity<Task> updateTask(@RequestBody Task task){

        if(task == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        taskService.saveTask(task);

        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }


    @PutMapping("task/{taskId}/user/{userId}")
    public ResponseEntity<UserTaskDto> updateTaskUser(@PathVariable("taskId") Long taskId, @PathVariable("userId") Long userId){

        if(taskId == null || userId == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Task task = taskService.findById(taskId);
        User user = userService.findById(userId);

        if (task == null || user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


        UserTask userTask = userTaskService.getUserTaskByTask(task);
        userTask.setUser(user);
        userTaskService.save(userTask);

        UserTaskDto userTaskDto = UserTaskDto.userTaskDto(userTask);

        return new ResponseEntity<>(userTaskDto, HttpStatus.OK);
    }


    @PutMapping("{taskId}")
    public ResponseEntity<Task> updateStatus(@PathVariable Long taskId, @RequestParam("status") Long statusId){

        if(taskId == null || statusId == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Task task = taskService.findById(taskId);

        if (task == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        TaskStatus taskStatus = taskStatusService.getTaskStatusById(statusId);
        if (taskStatus == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        task.setStatus(taskStatus);

        taskService.saveTask(task);

        return new ResponseEntity<>(task, HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    public ResponseEntity deleteTask(@PathVariable Long id){

        if(id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        taskService.delete(id);

        return new ResponseEntity(HttpStatus.OK);
    }

}
