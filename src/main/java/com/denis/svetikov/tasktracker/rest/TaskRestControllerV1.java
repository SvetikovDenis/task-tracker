package com.denis.svetikov.tasktracker.rest;

import com.denis.svetikov.tasktracker.dto.model.TaskDto;
import com.denis.svetikov.tasktracker.dto.model.UserTaskDto;
import com.denis.svetikov.tasktracker.exception.db.EntityNotFoundException;
import com.denis.svetikov.tasktracker.service.impl.TaskServiceImpl;
import com.denis.svetikov.tasktracker.specification.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping(value = "/api/v1/tasks")
public class TaskRestControllerV1 {


    private final TaskServiceImpl taskService;

    @Autowired
    public TaskRestControllerV1(TaskServiceImpl taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks(SearchCriteria request) throws EntityNotFoundException {
        List<TaskDto> tasks = taskService.getAll(request);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id) throws EntityNotFoundException {
        TaskDto task = taskService.findById(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }


    @GetMapping(value = "/user/{id}")
    public ResponseEntity<List<TaskDto>> getAllTasksByUserId(@PathVariable(name = "id") Long userId) throws EntityNotFoundException {
        List<TaskDto> tasks = taskService.getAllByUserId(userId);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping(value = "/user", params = "username")
    public ResponseEntity<List<TaskDto>> getAllTasksByUserName(@RequestParam("username") String username) throws EntityNotFoundException {
        List<TaskDto> tasks = taskService.getAllByUseName(username);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@Valid @RequestBody TaskDto task, Principal principal) {
        TaskDto newTask = taskService.createTask(task,principal);
        return new ResponseEntity<>(newTask, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<TaskDto> updateTask(@Valid @RequestBody TaskDto task) throws EntityNotFoundException {
        TaskDto newTask =  taskService.updateTask(task);
        return new ResponseEntity<>(newTask, HttpStatus.CREATED);
    }

    @PutMapping("/task/{taskId}/user/{userId}")
    public ResponseEntity<UserTaskDto> updateTaskUser(@PathVariable("taskId") Long taskId, @PathVariable("userId") Long userId) throws EntityNotFoundException{
        UserTaskDto userTaskDto = taskService.updateUserTask(taskId,userId);
        return new ResponseEntity<>(userTaskDto, HttpStatus.OK);
    }


    @PutMapping("/task/{taskId}/status/{statusId}")
    public ResponseEntity<TaskDto> updateTaskStatus( @PathVariable Long taskId, @PathVariable("statusId") Long statusId) throws EntityNotFoundException {
        TaskDto task = taskService.updateTaskStatus(taskId, statusId);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteTask( @PathVariable Long id) {
        taskService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
