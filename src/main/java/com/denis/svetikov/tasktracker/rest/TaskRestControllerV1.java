package com.denis.svetikov.tasktracker.rest;

import com.denis.svetikov.tasktracker.dto.model.TaskDto;
import com.denis.svetikov.tasktracker.exception.db.EntityNotFoundException;
import com.denis.svetikov.tasktracker.service.impl.TaskServiceImpl;
import com.denis.svetikov.tasktracker.specification.TaskSearchCriteria;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
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

    @JsonView(TaskDto.StandardView.class)
    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks(TaskSearchCriteria request, Pageable pageable) throws EntityNotFoundException {
        List<TaskDto> tasks = taskService.getAll(request,pageable);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @JsonView(TaskDto.StandardView.class)
    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id) throws EntityNotFoundException {
        TaskDto task = taskService.getTaskDtoById(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @JsonView(TaskDto.StandardView.class)
    @GetMapping(value = "/user/{id}")
    public ResponseEntity<List<TaskDto>> getAllTasksByUserId(@PathVariable(name = "id") Long userId) throws EntityNotFoundException {
        List<TaskDto> tasks = taskService.getAllByUserId(userId);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @JsonView(TaskDto.StandardView.class)
    @GetMapping(value = "/user", params = "username")
    public ResponseEntity<List<TaskDto>> getAllTasksByUserName(@RequestParam("username") String username) throws EntityNotFoundException {
        List<TaskDto> tasks = taskService.getAllByUseName(username);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @JsonView(TaskDto.StandardView.class)
    @PostMapping
    public ResponseEntity<TaskDto> createTask(@Valid @RequestBody TaskDto task, Principal principal) {
        TaskDto newTask = taskService.createTask(task,principal);
        return new ResponseEntity<>(newTask, HttpStatus.CREATED);
    }

    @JsonView(TaskDto.StandardView.class)
    @PutMapping
    public ResponseEntity<TaskDto> updateTask(@Validated({TaskDto.Existing.class}) @RequestBody TaskDto task) throws EntityNotFoundException {
        TaskDto newTask =  taskService.updateTask(task);
        return new ResponseEntity<>(newTask, HttpStatus.CREATED);
    }

    @JsonView(TaskDto.StandardView.class)
    @PutMapping("/task/{taskId}/status/{statusId}")
    public ResponseEntity<TaskDto> updateTaskStatus(@PathVariable("taskId") Long taskId, @PathVariable("statusId") Long statusId) throws EntityNotFoundException {
        TaskDto task = taskService.updateTaskStatus(taskId, statusId);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteTask( @PathVariable Long id) {
        taskService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
