package com.denis.svetikov.tasktracker.unit.controller;

import com.denis.svetikov.tasktracker.dto.model.UserTaskDto;
import com.denis.svetikov.tasktracker.rest.UserTaskRestControllerV1;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static com.denis.svetikov.tasktracker.rest.constant.Paths.USER_TASKS;
import static com.denis.svetikov.tasktracker.rest.constant.Paths.VERSION;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserTaskRestControllerV1.class, secure = false)
public class UserTaskRestControllerV1Test {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserTaskRestControllerV1 userTaskController;


    @Test
    public void getAllTasks() throws Exception {

        List<UserTaskDto> userTasks = buildUserTaskDtoList();

        given(userTaskController.getAll())
                .willReturn(new ResponseEntity<>(userTasks, HttpStatus.OK));

        mvc.perform(get(VERSION + USER_TASKS)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].userId", is(userTasks.get(0).getUserId().intValue())))
                .andExpect(jsonPath("$[0].username", is(userTasks.get(0).getUsername())))
                .andExpect(jsonPath("$[0].firstName", is(userTasks.get(0).getFirstName())))
                .andExpect(jsonPath("$[0].taskId", is(userTasks.get(0).getTaskId().intValue())))
                .andExpect(jsonPath("$[0].title", is(userTasks.get(0).getTitle())))
                .andExpect(jsonPath("$[0].status", is(userTasks.get(0).getStatus())));
    }

    @Test
    public void getUserTaskByTaskId() throws Exception {

        UserTaskDto userTask= buildUserTaskDto();

        given(userTaskController.getUserTaskByTaskId(2l))
                .willReturn(new ResponseEntity<>(userTask, HttpStatus.OK));

        mvc.perform(get(VERSION + USER_TASKS + "task/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", is(userTask.getUserId().intValue())))
                .andExpect(jsonPath("$.username", is(userTask.getUsername())))
                .andExpect(jsonPath("$.firstName", is(userTask.getFirstName())))
                .andExpect(jsonPath("$.taskId", is(userTask.getTaskId().intValue())))
                .andExpect(jsonPath("$.title", is(userTask.getTitle())))
                .andExpect(jsonPath("$.status", is(userTask.getStatus())));
    }



    @Test
    public void getAllUserTasksByUserId() throws Exception {

        List<UserTaskDto> userTasks = buildUserTaskDtoList();

        given(userTaskController.getAllUserTasksByUserId(2l))
                .willReturn(new ResponseEntity<>(userTasks, HttpStatus.OK));

        mvc.perform(get(VERSION + USER_TASKS + "user/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].userId", is(userTasks.get(0).getUserId().intValue())))
                .andExpect(jsonPath("$[0].username", is(userTasks.get(0).getUsername())))
                .andExpect(jsonPath("$[0].firstName", is(userTasks.get(0).getFirstName())))
                .andExpect(jsonPath("$[0].taskId", is(userTasks.get(0).getTaskId().intValue())))
                .andExpect(jsonPath("$[0].title", is(userTasks.get(0).getTitle())))
                .andExpect(jsonPath("$[0].status", is(userTasks.get(0).getStatus())));
    }


    @Test
    public void updateUserTask() throws Exception {

        UserTaskDto userTask= buildUserTaskDto();

        given(userTaskController.updateUserTask(2l,2l))
                .willReturn(new ResponseEntity<>(userTask, HttpStatus.OK));

        mvc.perform(put(VERSION + USER_TASKS + "/task/2/user/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", is(userTask.getUserId().intValue())))
                .andExpect(jsonPath("$.username", is(userTask.getUsername())))
                .andExpect(jsonPath("$.firstName", is(userTask.getFirstName())))
                .andExpect(jsonPath("$.taskId", is(userTask.getTaskId().intValue())))
                .andExpect(jsonPath("$.title", is(userTask.getTitle())))
                .andExpect(jsonPath("$.status", is(userTask.getStatus())));
    }

    @Test
    public void deleteUserTaskByTaskId() throws Exception {


        given(userTaskController.deleteUserTaskByTaskId(2l))
                .willReturn(new ResponseEntity<>(HttpStatus.NO_CONTENT));

        mvc.perform(delete(VERSION + USER_TASKS + "/task/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteUserTaskByUserId() throws Exception {


        given(userTaskController.deleteUserTaskByUserId(2l))
                .willReturn(new ResponseEntity<>(HttpStatus.NO_CONTENT));

        mvc.perform(delete(VERSION + USER_TASKS + "/user/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }


    private UserTaskDto buildUserTaskDto() {
        return UserTaskDto.builder()
                .userId(2l)
                .username("mike2020")
                .firstName("Mike")
                .lastName("White")
                .taskId(5l)
                .title("Fix cart bug")
                .status("In Progress")
                .build();
    }

    private List<UserTaskDto> buildUserTaskDtoList() {
        return Collections.singletonList(buildUserTaskDto());
    }

}
