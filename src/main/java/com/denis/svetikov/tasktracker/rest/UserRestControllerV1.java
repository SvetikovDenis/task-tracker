package com.denis.svetikov.tasktracker.rest;

import com.denis.svetikov.tasktracker.dto.model.UserDto;
import com.denis.svetikov.tasktracker.exception.db.EntityNotFoundException;
import com.denis.svetikov.tasktracker.service.UserService;
import com.denis.svetikov.tasktracker.specification.UserSearchCriteria;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller user connected requests.
 *
 * @author Denis Svetikov
 * @version v2
 */

@RestController
@RequestMapping(value = "/api/v1/users")
public class UserRestControllerV1 {
    private final UserService userService;

    @Autowired
    public UserRestControllerV1(UserService userService) {
        this.userService = userService;
    }

    @JsonView(UserDto.StandardView.class)
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(UserSearchCriteria request, Pageable pageable) throws EntityNotFoundException {
        List<UserDto> users = userService.getAll(request,pageable);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @JsonView(UserDto.StandardView.class)
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") Long id) throws EntityNotFoundException {
        UserDto user = userService.getUserDtoById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @JsonView(UserDto.StandardView.class)
    @GetMapping(params = "username")
    public ResponseEntity<UserDto> getUserByUserName(@RequestParam(name = "username") String username) throws EntityNotFoundException {
        UserDto user = userService.getUserDtoByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @JsonView(UserDto.StandardView.class)
    @PutMapping
    public ResponseEntity<UserDto> updateUser(@Validated(UserDto.Existing.class) @RequestBody UserDto userDto) throws EntityNotFoundException {
        userDto = userService.updateUserDto(userDto);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteUserById(@PathVariable(name = "id") Long id) throws EntityNotFoundException {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping()
    public ResponseEntity deleteUserByUserName(@RequestParam(name = "username") String username) throws EntityNotFoundException {
        userService.delete(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
