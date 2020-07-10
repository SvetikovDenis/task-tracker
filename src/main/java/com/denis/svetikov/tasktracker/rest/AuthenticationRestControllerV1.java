package com.denis.svetikov.tasktracker.rest;

import com.denis.svetikov.tasktracker.dto.request.AuthenticationRequestDto;
import com.denis.svetikov.tasktracker.dto.request.UerRegisterRequestDto;
import com.denis.svetikov.tasktracker.model.User;
import com.denis.svetikov.tasktracker.security.jwt.JwtTokenProvider;
import com.denis.svetikov.tasktracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * REST controller for authentication requests (login, logout, register, etc.)
 *
 * @author Denis Svetikov
 * @version 1.0
 */

@RestController
@RequestMapping(value = "/api/v1/")
public class AuthenticationRestControllerV1 {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;


    @Autowired
    public AuthenticationRestControllerV1(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("auth/login")
    public ResponseEntity login(@Valid @RequestBody AuthenticationRequestDto requestDto) {
        try {
            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            User user = userService.getUserByUsername(username);

            if (user == null) {
                throw new UsernameNotFoundException("User with username: " + username + " not found");
            }

            String token = jwtTokenProvider.createToken(username, user.getRoles());

            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostMapping("register")
    public ResponseEntity register(@RequestBody @Valid UerRegisterRequestDto uerRegisterRequestDto) {

        User user = new User();
        user.setUsername(uerRegisterRequestDto.getUsername());
        user.setEmail(uerRegisterRequestDto.getEmail());
        user.setFirstName(uerRegisterRequestDto.getFirstName());
        user.setLastName(uerRegisterRequestDto.getLastName());
        user.setPassword(uerRegisterRequestDto.getPassword());

        userService.register(user);

        return ResponseEntity.ok("You are successfully registered,please login");

    }

}
