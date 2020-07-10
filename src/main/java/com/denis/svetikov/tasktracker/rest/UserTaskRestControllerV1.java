package com.denis.svetikov.tasktracker.rest;

import com.denis.svetikov.tasktracker.dto.model.UserTaskDto;
import com.denis.svetikov.tasktracker.exception.db.EntityNotFoundException;
import com.denis.svetikov.tasktracker.service.UserTaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/api/v1/user-task")
public class UserTaskRestControllerV1 {


    private final UserTaskService userTaskService;

    public UserTaskRestControllerV1(UserTaskService userTaskService) {
        this.userTaskService = userTaskService;
    }

    @GetMapping
    public ResponseEntity<List<UserTaskDto>> getAll() throws EntityNotFoundException {
        List<UserTaskDto> userTasks = userTaskService.getAll();
        return new ResponseEntity<>(userTasks,HttpStatus.OK);
    }

    @GetMapping("/task/{id}")
    public ResponseEntity<UserTaskDto> getUserTaskByTaskId(@PathVariable Long id) throws EntityNotFoundException {
        UserTaskDto userTask = userTaskService.getUserTaskDtoByTaskId(id);
        return new ResponseEntity<>(userTask,HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<UserTaskDto>> getAllUserTasksByUserId(@PathVariable Long id) throws EntityNotFoundException {
        List<UserTaskDto> userTasks = userTaskService.getAllUserTasksByUserId(id);
        return new ResponseEntity<>(userTasks,HttpStatus.OK);
    }

    @PutMapping("/task/{taskId}/user/{userId}")
    public ResponseEntity<UserTaskDto> updateUserTask(@PathVariable("taskId") Long taskId, @PathVariable("userId") Long userId) throws EntityNotFoundException{
        UserTaskDto userTaskDto = userTaskService.updateUserTask(taskId,userId);
        return new ResponseEntity<>(userTaskDto, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/task/{id}")
    public ResponseEntity deleteUserTaskByTaskId(@PathVariable Long id) throws EntityNotFoundException {
        userTaskService.deleteByTaskId(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Transactional
    @DeleteMapping("/user/{id}")
    public ResponseEntity deleteUserTaskByUserId(@PathVariable Long id) throws EntityNotFoundException {
        userTaskService.deleteByUserId(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}
