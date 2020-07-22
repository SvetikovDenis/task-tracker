package com.denis.svetikov.tasktracker.unit.controller;

import com.denis.svetikov.tasktracker.dto.model.TaskDto;
import com.denis.svetikov.tasktracker.rest.TaskRestControllerV1;
import com.denis.svetikov.tasktracker.specification.TaskSearchCriteria;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Collections;
import java.util.List;

import static com.denis.svetikov.tasktracker.rest.constant.Paths.TASKS;
import static com.denis.svetikov.tasktracker.rest.constant.Paths.VERSION;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = TaskRestControllerV1.class, secure = false)
public class TaskRestControllerV1Test {


    @Autowired
    private MockMvc mvc;

    @MockBean
    private TaskRestControllerV1 taskController;

    @Autowired
    private ObjectMapper objectMapper;



    @Test
    public void getAllTasks() throws Exception {

        List<TaskDto> tasks = buildTaskDtoList();

        given(taskController.getAllTasks(new TaskSearchCriteria(), new PageRequest(0,10)))
                .willReturn(new ResponseEntity<>(tasks, HttpStatus.OK));

        mvc.perform(get(VERSION + TASKS + "?page=0&size=10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(tasks.get(0).getId().intValue())))
                .andExpect(jsonPath("$[0].title", is(tasks.get(0).getTitle())))
                .andExpect(jsonPath("$[0].description", is(tasks.get(0).getDescription())))
                .andExpect(jsonPath("$[0].statusId", is(tasks.get(0).getStatusId().intValue())));
    }


    @Test
    public void getTaskByTaskId() throws Exception {

        TaskDto task = buildTaskDto();

        given(taskController.getTaskById(2l))
                .willReturn(new ResponseEntity<>(task, HttpStatus.OK));

        mvc.perform(get(VERSION + TASKS + "/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(task.getId().intValue())))
                .andExpect(jsonPath("$.title", is(task.getTitle())))
                .andExpect(jsonPath("$.description", is(task.getDescription())))
                .andExpect(jsonPath("$.statusId", is(task.getStatusId().intValue())));
    }



    @Test
    public void getAllTasksByUserId() throws Exception {

        List<TaskDto> tasks = buildTaskDtoList();

        given(taskController.getAllTasksByUserId(2l))
                .willReturn(new ResponseEntity<>(tasks, HttpStatus.OK));

        mvc.perform(get(VERSION + TASKS + "/user/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(tasks.get(0).getId().intValue())))
                .andExpect(jsonPath("$[0].title", is(tasks.get(0).getTitle())))
                .andExpect(jsonPath("$[0].description", is(tasks.get(0).getDescription())))
                .andExpect(jsonPath("$[0].statusId", is(tasks.get(0).getStatusId().intValue())));
    }


    @Test
    public void getAllTasksByUserName() throws Exception {

        List<TaskDto> tasks = buildTaskDtoList();

        given(taskController.getAllTasksByUserName("mike2020"))
                .willReturn(new ResponseEntity<>(tasks, HttpStatus.OK));

        mvc.perform(get(VERSION + TASKS + "/user?username=mike2020")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(tasks.get(0).getId().intValue())))
                .andExpect(jsonPath("$[0].title", is(tasks.get(0).getTitle())))
                .andExpect(jsonPath("$[0].description", is(tasks.get(0).getDescription())))
                .andExpect(jsonPath("$[0].statusId", is(tasks.get(0).getStatusId().intValue())));
    }


    @Test
    public void createTask() throws Exception {

        TaskDto taskNew = buildTaskDtoNew();
        TaskDto taskReturn = buildTaskDto();

        given(taskController.createTask(taskNew, null))
                .willReturn(new ResponseEntity<>(taskReturn, HttpStatus.OK));


        mvc.perform(post(VERSION + TASKS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(taskNew)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(taskReturn.getId().intValue())))
                .andExpect(jsonPath("$.title", is(taskReturn.getTitle())))
                .andExpect(jsonPath("$.description", is(taskReturn.getDescription())))
                .andExpect(jsonPath("$.statusId", is(taskReturn.getStatusId().intValue())));
    }


    @Test
    public void updateTask() throws Exception {

        TaskDto task = buildTaskDto();

        given(taskController.updateTask(task))
                .willReturn(new ResponseEntity<>(task, HttpStatus.CREATED));


        mvc.perform(put(VERSION + TASKS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(task)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(task.getId().intValue())))
                .andExpect(jsonPath("$.title", is(task.getTitle())))
                .andExpect(jsonPath("$.description", is(task.getDescription())))
                .andExpect(jsonPath("$.statusId", is(task.getStatusId().intValue())));
    }



    @Test
    public void updateTaskStatus() throws Exception {

        TaskDto task = buildTaskDto();

        given(taskController.updateTaskStatus(2l,1l))
                .willReturn(new ResponseEntity<>(task, HttpStatus.OK));

        mvc.perform(put(VERSION + TASKS + "task/2/status/1" )
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(task.getId().intValue())))
                .andExpect(jsonPath("$.title", is(task.getTitle())))
                .andExpect(jsonPath("$.description", is(task.getDescription())))
                .andExpect(jsonPath("$.statusId", is(task.getStatusId().intValue())));
    }


    @Test
    public void deleteTask() throws Exception {

        given(taskController.deleteTask(2l))
                .willReturn(new ResponseEntity<>(HttpStatus.NO_CONTENT));

        mvc.perform(delete(VERSION + TASKS + 2l))
                .andExpect(status().isNoContent());

    }

    private TaskDto buildTaskDto() {
        return TaskDto
                .builder()
                .id(2l)
                .title("Normalize db table")
                .description("Normalize stock table in database")
                .statusId(3l)
                .build();
    }

    private TaskDto buildTaskDtoNew(){
        return TaskDto
                .builder()
                .title("Normalize db table")
                .description("Normalize stock table in database")
                .statusId(3l)
                .build();
    }

    private List<TaskDto> buildTaskDtoList() {
        return Collections.singletonList(buildTaskDto());
    }

    private  String asJsonString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}
