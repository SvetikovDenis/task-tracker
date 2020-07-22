package com.denis.svetikov.tasktracker.unit.service;

import com.denis.svetikov.tasktracker.dto.model.UserDto;
import com.denis.svetikov.tasktracker.dto.request.UserRegisterRequestDto;
import com.denis.svetikov.tasktracker.mapper.UserMapper;
import com.denis.svetikov.tasktracker.model.Role;
import com.denis.svetikov.tasktracker.model.User;
import com.denis.svetikov.tasktracker.repository.RoleRepository;
import com.denis.svetikov.tasktracker.repository.UserRepository;
import com.denis.svetikov.tasktracker.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;


    @Test
    public void shouldSavedUserSuccessfully() {

        User user = buildUser();
        given(userRepository.findByUsername(user.getUsername())).willReturn(null);
        given(userRepository.findByEmail(user.getEmail())).willReturn(null);
        given(roleRepository.findByName("ROLE_USER")).willReturn(Role.builder().name("ROLE_USER").build());
        given(passwordEncoder.encode(user.getPassword())).willReturn(user.getPassword());
        given(userRepository.save(any(User.class))).willReturn(user);

        User savedUser = userService.register(buildUserRegisterDto());
        assertThat(savedUser).isNotNull();
        verify(userRepository).save(any(User.class));

    }

    @Test
    public void shouldThrowErrorWhenSaveUserWithExistingEmail() {

        User user = buildUser();
        given(userRepository.findByUsername(user.getUsername())).willReturn(null);
        given(userRepository.findByEmail(user.getEmail())).willReturn(user);
        given(roleRepository.findByName("ROLE_USER")).willReturn(Role.builder().name("ROLE_USER").build());
        given(passwordEncoder.encode(user.getPassword())).willReturn(user.getPassword());

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                userService.register(buildUserRegisterDto()));

        verify(userRepository, never()).save(any(User.class));

    }

    @Test
    public void shouldThrowErrorWhenSaveUserWithExistingUserName() {

        User user = buildUser();
        given(userRepository.findByUsername(user.getUsername())).willReturn(user);
        given(userRepository.findByEmail(user.getEmail())).willReturn(null);
        given(roleRepository.findByName("ROLE_USER")).willReturn(Role.builder().name("ROLE_USER").build());
        given(passwordEncoder.encode(user.getPassword())).willReturn(user.getPassword());

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                userService.register(buildUserRegisterDto()));

        verify(userRepository, never()).save(any(User.class));

    }

    @Test
    public void shouldSuccessfullyUpdateUser() {

        UserDto userDto = buildUserDto();
        User user = buildUser();

        given(userRepository.findById(userDto.getId())).willReturn(Optional.of(user));
        given(userRepository.save(any(User.class))).willReturn(user);
        given(userMapper.toDto(any(User.class))).willReturn(userDto);
        doAnswer((i)->null).when(userMapper).updateEntity(any(UserDto.class),any(User.class));

        UserDto updatedUser = userService.updateUserDto(userDto);
        assertThat(updatedUser).isNotNull();

    }

    @Test
    public void getUserDtoByUsername() {
        UserDto userDto = buildUserDto();
        User user = buildUser();
        given(userRepository.findByUsername("mike2020")).willReturn(user);
        given(userMapper.toDto(user)).willReturn(userDto);
        UserDto foundUser = userService.getUserDtoByUsername("mike2020");
        assertThat(foundUser).isNotNull();

    }

    @Test
    public void getUserDtoByUserId() {
        UserDto userDto = buildUserDto();
        User user = buildUser();
        given(userRepository.findById(2l)).willReturn(Optional.of(user));
        given(userMapper.toDto(user)).willReturn(userDto);
        UserDto foundUser = userService.getUserDtoById(2l);
        assertThat(foundUser).isNotNull();

    }


    @Test
    public void deleteUserById() {
        User user = buildUser();
        Long userId = 2l;

        given(userRepository.findById(any(Long.class))).willReturn(Optional.of(user));

        userService.delete(userId);
        userService.delete(userId);

        verify(userRepository,times(2)).deleteById(userId);
    }

    private User buildUser() {

        List<Role> roles = Arrays.asList(Role.builder().name("ROLE_USER").build());
        return User
                .builder()
                .username("Mike2020")
                .firstName("Mike")
                .lastName("White")
                .email("mike@gmail.com")
                .password("Mike74!#")
                .roles(roles)
                .build();
    }

    private UserDto buildUserDto() {
        return UserDto
                .builder()
                .id(2l)
                .username("Mike2020")
                .firstName("Mike")
                .lastName("White")
                .email("mike@gmail.com")
                .build();

    }

    private List<UserDto> buildUserDtoList() {
        return Collections.singletonList(buildUserDto());
    }

    private List<User> buildUserList() {
        return Collections.singletonList(buildUser());
    }

    private UserRegisterRequestDto buildUserRegisterDto() {
        return UserRegisterRequestDto
                .builder()
                .username("Mike2020")
                .firstName("Mike2020")
                .lastName("White")
                .email("mike@gmail.com")
                .password("Mike74!#")
                .build();
    }


}
