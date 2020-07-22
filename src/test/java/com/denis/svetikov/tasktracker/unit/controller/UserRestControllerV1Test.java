package com.denis.svetikov.tasktracker.unit.controller;

import com.denis.svetikov.tasktracker.dto.model.UserDto;
import com.denis.svetikov.tasktracker.rest.UserRestControllerV1;
import com.denis.svetikov.tasktracker.specification.UserSearchCriteria;
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


import static com.denis.svetikov.tasktracker.rest.constant.Paths.USERS;
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
@WebMvcTest(value = UserRestControllerV1.class, secure = false)
public class UserRestControllerV1Test {

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private UserRestControllerV1 userController;


    @Test
    public void getUsers() throws Exception {

        List<UserDto> users = buildUserDtoList();

        given(userController.getAllUsers(new UserSearchCriteria(), new PageRequest(0,10)))
                .willReturn(new ResponseEntity<>(users, HttpStatus.OK));

        mvc.perform(get(VERSION + USERS + "?page=0&size=10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].id",is(users.get(0).getId().intValue())))
                .andExpect(jsonPath("$[0].username",is(users.get(0).getUsername())))
                .andExpect(jsonPath("$[0].firstName",is(users.get(0).getFirstName())))
                .andExpect(jsonPath("$[0].lastName",is(users.get(0).getLastName())))
                .andExpect(jsonPath("$[0].email",is(users.get(0).getEmail())));

    }

    @Test
    public void getUserById() throws Exception {

        UserDto user = buildUserDto();

        given(userController.getUserById(2l))
                .willReturn(new ResponseEntity<>(user, HttpStatus.OK));

        mvc.perform(get(VERSION + USERS + "/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(user.getId().intValue())))
                .andExpect(jsonPath("$.username",is(user.getUsername())))
                .andExpect(jsonPath("$.firstName",is(user.getFirstName())))
                .andExpect(jsonPath("$.lastName",is(user.getLastName())))
                .andExpect(jsonPath("$.email",is(user.getEmail())));

    }

    @Test
    public void getUserByUserName() throws Exception {

        UserDto user = buildUserDto();

        given(userController.getUserByUserName("mike2020"))
                .willReturn(new ResponseEntity<>(user, HttpStatus.OK));

        mvc.perform(get(VERSION + USERS + "?username=mike2020")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(user.getId().intValue())))
                .andExpect(jsonPath("$.username",is(user.getUsername())))
                .andExpect(jsonPath("$.firstName",is(user.getFirstName())))
                .andExpect(jsonPath("$.lastName",is(user.getLastName())))
                .andExpect(jsonPath("$.email",is(user.getEmail())));

    }


    @Test
    public void updateUser() throws Exception {

        UserDto user = buildUserDto();

        given(userController.updateUser(user))
                .willReturn(new ResponseEntity<>(user, HttpStatus.OK));

        mvc.perform(put(VERSION + USERS )
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(user.getId().intValue())))
                .andExpect(jsonPath("$.username",is(user.getUsername())))
                .andExpect(jsonPath("$.firstName",is(user.getFirstName())))
                .andExpect(jsonPath("$.lastName",is(user.getLastName())))
                .andExpect(jsonPath("$.email",is(user.getEmail())));

    }

    @Test
    public void deleteUserById() throws Exception {

        given(userController.deleteUserById(2l))
                .willReturn(new ResponseEntity<>(HttpStatus.NO_CONTENT));

        mvc.perform(delete(VERSION + USERS + "/2"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteUserByUserName() throws Exception {

        given(userController.deleteUserByUserName("mike2020"))
                .willReturn(new ResponseEntity<>(HttpStatus.NO_CONTENT));

        mvc.perform(delete(VERSION + USERS + "?username=mike2020"))
                .andExpect(status().isNoContent());
    }


    private UserDto buildUserDto() {
        return UserDto.builder()
                .id(2l)
                .username("mike2020")
                .firstName("Mike")
                .lastName("White")
                .email("mike@gmail.com")
                .build();
    }

    private List<UserDto> buildUserDtoList() {
        return Collections.singletonList(buildUserDto());
    }


    private  String asJsonString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
