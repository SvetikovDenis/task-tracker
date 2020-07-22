package com.denis.svetikov.tasktracker.service.impl;

import com.denis.svetikov.tasktracker.dto.model.UserDto;
import com.denis.svetikov.tasktracker.dto.request.UserRegisterRequestDto;
import com.denis.svetikov.tasktracker.exception.db.EntityNotFoundException;
import com.denis.svetikov.tasktracker.mapper.UserMapper;
import com.denis.svetikov.tasktracker.model.Role;
import com.denis.svetikov.tasktracker.model.User;
import com.denis.svetikov.tasktracker.repository.RoleRepository;
import com.denis.svetikov.tasktracker.repository.UserRepository;
import com.denis.svetikov.tasktracker.service.UserService;
import com.denis.svetikov.tasktracker.specification.UserSearchCriteria;
import com.denis.svetikov.tasktracker.specification.UserSearchCriteriaSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
    private final UserMapper userMapper;
    private final UserSearchCriteriaSpecification userSpecification;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder, UserMapper userMapper,UserSearchCriteriaSpecification userSpecification) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.userSpecification = userSpecification;
    }


    @Override
    public User register(UserRegisterRequestDto userRegisterRequestDto) {

        User userCandidate = userRepository.findByUsername(userRegisterRequestDto.getUsername());
        if (userCandidate != null) {
            throw new IllegalArgumentException("User with username : " + userCandidate.getUsername() + " already exists");
        }
        userCandidate = userRepository.findByEmail(userRegisterRequestDto.getEmail());
        if (userCandidate != null) {
            throw new IllegalArgumentException("User with email : " + userCandidate.getEmail() + " already exists");
        }

        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = Arrays.asList(roleUser);

        User newUser = new User();
        newUser.setUsername(userRegisterRequestDto.getUsername());
        newUser.setFirstName(userRegisterRequestDto.getFirstName());
        newUser.setLastName(userRegisterRequestDto.getLastName());
        newUser.setEmail(userRegisterRequestDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(userRegisterRequestDto.getPassword()));
        newUser.setRoles(userRoles);

        User registeredUser = userRepository.save(newUser);
        log.info("In register - user: {} successfully registered", registeredUser);
        return registeredUser;
    }

    @Override
    public List<UserDto> getAll(UserSearchCriteria request, Pageable pageable) {

        Sort sort = Sort.by(Sort.Direction.fromString(
                Optional.ofNullable(request.getOrder()).orElse("desc")),
                Optional.ofNullable(request.getSort()).orElse("created"));

        Page<User> usersPage = userRepository.findAll(userSpecification.getFilter(request), PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort));

        if (usersPage.isEmpty()) {
            throw new EntityNotFoundException(User.class, "Find all users", "No users found");
        }
        log.info("In getAll - {} users was found",usersPage.getTotalElements());
        return usersPage.
                stream().
                map(userMapper::toDto).
                collect(Collectors.toList());
    }

    @Override
    public UserDto getUserDtoByUsername(String username) {

        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("username can't be null or empty");
        }
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.warn("In getUserDtoByUsername - No user found by username: {}", username);
            throw new EntityNotFoundException(User.class, "username", username);
        }
        log.info("In getUserDtoByUsername - User with id : {} found by username: {}", user.getId(), username);
        return userMapper.toDto(user);
    }

    @Override
    public User getUserByUsername(String username) {

        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username can't be null or empty");
        }
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.warn("In getUserByUsername - No user found by username: {}", username);
            throw new EntityNotFoundException(User.class, "username", username);
        }
        log.info("In getUserByUsername - User with id : {} found by username: {}", user.getId(), username);
        return user;
    }

    @Override
    public User getUserById(Long id) {

        if (id == null) {
            throw new IllegalArgumentException("User id can't be null");
        }
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            log.warn("In getUserById - No user found with id : {}", id);
            throw new EntityNotFoundException(User.class, "id", id.toString());
        }
        log.info("In getUserById - Found user with id : ", id);
        return user;
    }

    @Override
    public UserDto getUserDtoById(Long id) {

        if (id == null) {
            throw new IllegalArgumentException("User id can't be null");
        }
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            log.warn("In getUserDtoById - no user found by id: {}", id);
            throw new EntityNotFoundException(User.class, "id", id.toString());
        }
        log.info("In getUserDtoById - user: {} found by id: {}", user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto updateUserDto(UserDto userDto) {

        User user = userRepository.findById(userDto.getId()).orElse(null);
        if (user == null) {
            log.warn("In updateUserDto - No User was found with id : {}",userDto.getId());
            throw new EntityNotFoundException(User.class, "user", userDto.toString());
        }
        userMapper.updateEntity(userDto, user);
        user = userRepository.save(user);
        log.info("In updateUserDto - User with id : {} was updated", user.getId());
        return userMapper.toDto(user);
    }

    @Override
    public void delete(Long id) {

        if (id == null) {
            throw new IllegalArgumentException("User id can't be null");
        }
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            log.warn("In delete - No user was found with id : {}",id);
            throw new EntityNotFoundException(User.class, "id", id.toString());
        }
        userRepository.deleteById(id);
        log.info("In delete - user with id: {} was deleted", id);
    }

    @Override
    public void delete(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username can't be null or empty");
        }

        User user = userRepository.findByUsername(username);

        if (user == null) {
            log.warn("In delete - No user was found with username : {}",username);
            throw new EntityNotFoundException(User.class, "username", username);
        }
        userRepository.delete(user);
        log.info("In delete - user with id: {} was deleted", user.getId());
    }
}
