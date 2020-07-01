package com.denis.svetikov.tasktracker.service;

import com.denis.svetikov.tasktracker.dto.UserDto;
import com.denis.svetikov.tasktracker.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface for class {@link User}.
 *
 * @author Denis Svetikov
 * @version v2
 */

public interface UserService {

    User register(User user);

    Page<User> getAll(Pageable pageable);

    User findByUsername(String username);

    User findById(Long id);

    UserDto update(User user);

    void delete(Long id);
}
