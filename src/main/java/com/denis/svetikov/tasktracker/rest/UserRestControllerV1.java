package com.denis.svetikov.tasktracker.rest;

import com.denis.svetikov.tasktracker.dto.UserDto;
import com.denis.svetikov.tasktracker.model.User;
import com.denis.svetikov.tasktracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller user connected requests.
 *
 * @author Denis Svetikov
 * @version v2
 */

@RestController
@RequestMapping(value = "/api/v1/users/")
public class UserRestControllerV1 {
    private final UserService userService;

    @Autowired
    public UserRestControllerV1(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(@PageableDefault(size = 10) Pageable pageable){

       Page<User> users  = userService.getAll(pageable);

        if(users == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<UserDto> usersDto = users.
                stream().
                map(user -> UserDto.fromUser(user)).
                collect(Collectors.toList());

        return new ResponseEntity<>(usersDto, HttpStatus.OK);
    }


    @GetMapping(value = "{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") Long id){
        User user = userService.findById(id);

        if(user == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        UserDto result = UserDto.fromUser(user);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @DeleteMapping(value = "{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity deleteUserById(@PathVariable(name = "id") Long id) {

        User user = userService.findById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        userService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PutMapping(value = "")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {

        if (userDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = userService.findById(userDto.getId());

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        user = userDto.toUser(user);

        userService.update(user);

        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }

}
