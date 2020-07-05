package com.denis.svetikov.tasktracker.service.impl;

import com.denis.svetikov.tasktracker.dto.model.UserDto;
import com.denis.svetikov.tasktracker.exception.db.EntityNotFoundException;
import com.denis.svetikov.tasktracker.model.Role;
import com.denis.svetikov.tasktracker.model.User;
import com.denis.svetikov.tasktracker.repository.RoleRepository;
import com.denis.svetikov.tasktracker.repository.UserRepository;
import com.denis.svetikov.tasktracker.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of {@link UserService} interface.
 * Wrapper for {@link UserRepository} + business logic.
 *
 * @author Denis Svetikov
 * @version v2
 */

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public User register(@Valid User user) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);

        User registeredUser = userRepository.save(user);

        log.info("IN register - user: {} successfully registered", registeredUser);

        return registeredUser;
    }

    @Override
    public List<UserDto> getAll(Pageable pageable) {
        Page<User> usersPage = userRepository.findAll(pageable);

        if (usersPage.getTotalElements() == 0) {
            throw new EntityNotFoundException(User.class, "Find all users", "No users found");
        }
        return usersPage.
                stream().
                map(UserDto::fromUser).
                collect(Collectors.toList());
    }

    @Override
    public UserDto getUserDtoByUsername(String username) {

        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("username can't be null or empty");
        }

        User user = userRepository.findByUsername(username);

        if (user == null) {
            log.warn("In findByUsername - no user found by username: {}", username);
            throw new EntityNotFoundException(User.class, "username", username);
        }

        UserDto userDto = UserDto.fromUser(user);

        log.info("IN findByUsername - user: {} found by username: {}", user, username);
        return userDto;
    }


    @Override
    public User getUserByUsername(String username) {

        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username can't be null or empty");
        }

        User user = userRepository.findByUsername(username);

        if (user == null) {
            log.warn("In findByUsername - no user found by username: {}", username);
            throw new EntityNotFoundException(User.class, "username", username);
        }

        log.info("IN findByUsername - user: {} found by username: {}", user, username);
        return user;
    }


    @Override
    public User getUserById(Long id) {

        if (id == null) {
            throw new IllegalArgumentException("User id can't be null");
        }

        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new EntityNotFoundException(User.class,"id", id.toString());
        }
        return user;
    }

    @Override
    public UserDto getUserDtoById(Long id) {

        if (id == null) {
            throw new IllegalArgumentException("User id can't be null");
        }

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            log.warn("In findById - no user found by id: {}", id);
            throw new EntityNotFoundException(User.class, "id", id.toString());
        }

        UserDto userDto = UserDto.fromUser(user);

        log.info("In findById - user: {} found by id: {}", user);
        return userDto;
    }


    @Override
    public UserDto updateUserDto(UserDto userDto) {

        User user = userRepository.findById(userDto.getId()).orElse(null);

        if (user == null) {
            throw new EntityNotFoundException(User.class, "user", userDto.toString());
        }

        user.setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());

        userRepository.save(user);

        return UserDto.fromUser(user);
    }


    @Override
    public User updateUser(User user) {

        User newUser  = userRepository.findById(user.getId()).orElse(null);

        if (newUser == null) {
            throw new EntityNotFoundException(User.class, "user", user.toString());
        }

        newUser.setUsername(user.getUsername());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(newUser);
        return newUser;
    }

    @Override
    public void delete(Long id) {

        if (id == null) {
            throw new IllegalArgumentException("User id can't be null");
        }

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new EntityNotFoundException(User.class, "id", id.toString());
        }
        userRepository.deleteById(id);
        log.info("IN delete - user with id: {} successfully deleted", id);
    }
}
