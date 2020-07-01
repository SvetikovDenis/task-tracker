package com.denis.svetikov.tasktracker.service.impl;

import com.denis.svetikov.tasktracker.dto.UserDto;
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
    public Page<User> getAll(Pageable pageable) {
        Page<User> result = userRepository.findAll(pageable);
        /*log.info("IN getAll - {} users found", result.size());*/
        return result;
    }

    @Override
    public User findByUsername(String username) {
        User result = userRepository.findByUsername(username);
        log.info("IN findByUsername - user: {} found by username: {}", result, username);
        return result;
    }

    @Override
    public User findById(Long id) {
        User result = userRepository.findById(id).orElse(null);

        if (result == null) {
            log.warn("IN findById - no user found by id: {}", id);
            return null;
        }

        log.info("IN findById - user: {} found by id: {}", result);
        return result;
    }

    /**
     * REFACTOR!!
     * @param user
     * @return
     */

    @Override
    public UserDto update(User user) {

        User result = userRepository.findById(user.getId()).orElse(null);

        if (result == null) {
            log.warn("IN findById - no user found by id: {}", user.getId());
            return null;
        }

        userRepository.save(result);

        return UserDto.fromUser(result);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
        log.info("IN delete - user with id: {} successfully deleted");
    }
}
