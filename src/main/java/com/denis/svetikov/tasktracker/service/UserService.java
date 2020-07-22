package com.denis.svetikov.tasktracker.service;

import com.denis.svetikov.tasktracker.dto.model.UserDto;
import com.denis.svetikov.tasktracker.dto.request.UserRegisterRequestDto;
import com.denis.svetikov.tasktracker.model.User;
import com.denis.svetikov.tasktracker.specification.UserSearchCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface for class {@link User}.
 *
 * @author Denis Svetikov
 * @version v2
 */

public interface UserService {

    User register(UserRegisterRequestDto user);

    List<UserDto> getAll(UserSearchCriteria request, Pageable pageable);

    UserDto getUserDtoByUsername(String username);

    User getUserByUsername(String username);

    UserDto getUserDtoById(Long id);

    User getUserById(Long id);

    UserDto updateUserDto(UserDto userDto);

    void delete(Long id);

    void delete(String username);
}
