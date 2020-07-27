package com.denis.svetikov.tasktracker.security;

import com.denis.svetikov.tasktracker.model.User;
import com.denis.svetikov.tasktracker.security.jwt.JwtUser;
import com.denis.svetikov.tasktracker.security.jwt.JwtUserFactory;
import com.denis.svetikov.tasktracker.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link UserDetailsService} interface for {@link JwtUser}.
 *
 * @author Denis Svetikov
 * @version v2
 */

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User with username: " + username + " not found");
        }

        JwtUser jwtUser = JwtUserFactory.create(user);
        log.info("In loadUserByUsername - user with username: {} successfully loaded", username);
        return jwtUser;
    }
}
