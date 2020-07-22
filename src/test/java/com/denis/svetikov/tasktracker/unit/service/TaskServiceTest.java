package com.denis.svetikov.tasktracker.unit.service;


import com.denis.svetikov.tasktracker.dto.model.TaskDto;
import com.denis.svetikov.tasktracker.mapper.TaskMapper;
import com.denis.svetikov.tasktracker.model.Task;
import com.denis.svetikov.tasktracker.model.TaskStatus;
import com.denis.svetikov.tasktracker.model.User;
import com.denis.svetikov.tasktracker.model.UserTask;
import com.denis.svetikov.tasktracker.repository.TaskRepository;
import com.denis.svetikov.tasktracker.service.TaskStatusService;
import com.denis.svetikov.tasktracker.service.UserService;
import com.denis.svetikov.tasktracker.service.UserTaskService;
import com.denis.svetikov.tasktracker.service.impl.TaskServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @Mock
    private TaskStatusService taskStatusService;

    @Mock
    private UserTaskService userTaskService;

    @Mock
    private UserService userService;

    @InjectMocks
    private TaskServiceImpl taskService;


    @Test
    public void getTaskDtoById() {

        Task task = buildTask();
        TaskDto taskDto = buildTaskDto();
        given(taskRepository.findById(2l)).willReturn(Optional.of(task));
        given(taskMapper.toDto(any(Task.class))).willReturn(taskDto);
        TaskDto foundTask = taskService.getTaskDtoById(2l);
        assertThat(foundTask).isNotNull();
        assertThat(foundTask.getTitle()).isEqualTo(taskDto.getTitle());
        assertThat(foundTask.getDescription()).isEqualTo(taskDto.getDescription());
        assertThat(foundTask.getStatusId()).isEqualTo(taskDto.getStatusId());

    }

    @Test
    public void getAllByUserId() {

        List<Task> tasks = buildTaskList();
        given(taskRepository.findAllByUserId(2l)).willReturn(tasks);
        given(taskMapper.toDto(any(Task.class))).willReturn(buildTaskDto());

        List<TaskDto> foundTasks = taskService.getAllByUserId(2l);
        assertThat(foundTasks).isNotEmpty();
        assertThat(foundTasks).hasSize(1);

    }

    @Test
    public void getAllByUseName() {

        List<Task> tasks = buildTaskList();
        given(taskRepository.findAllByUser_Username("mike2020")).willReturn(tasks);
        given(taskMapper.toDto(any(Task.class))).willReturn(buildTaskDto());

        List<TaskDto> foundTasks = taskService.getAllByUseName("mike2020");
        assertThat(foundTasks).isNotEmpty();
        assertThat(foundTasks).hasSize(1);

    }

    @Test
    public void createTaskShouldBeSuccessful() {

        String username = "mike2020";
        TaskDto taskDto = buildTaskDto();

        UsernamePasswordAuthenticationToken authenticationToken = Mockito.mock(UsernamePasswordAuthenticationToken.class);

        given(taskStatusService.getTaskStatusById(2l)).willReturn(buildTaskStatus());
        given(taskMapper.toEntity(any(TaskDto.class))).willReturn(buildTask());
        given(taskMapper.toDto(any(Task.class))).willReturn(taskDto);
        given(taskRepository.save(any(Task.class))).willReturn(buildTask());
        given(authenticationToken.getName()).willReturn(username);
        given(userService.getUserByUsername(any(String.class))).willReturn(buildUser());
        given(userTaskService.save(any(UserTask.class))).willReturn(buildUserTask());

        TaskDto createdTask = taskService.createTask(buildTaskDto(), authenticationToken);
        assertThat(createdTask).isNotNull();
        assertThat(createdTask.getTitle()).isEqualTo(taskDto.getTitle());
        assertThat(createdTask.getDescription()).isEqualTo(taskDto.getDescription());
        assertThat(createdTask.getStatusId()).isEqualTo(taskDto.getStatusId());
    }


    @Test
    public void updateTaskShouldBeSuccessful() {

        TaskDto taskDto = buildTaskDto();
        Task task = buildTask();

        given(taskRepository.findById(2l)).willReturn(Optional.of(task));
        given(taskRepository.save(task)).willReturn(task);
        given(taskMapper.toDto(any(Task.class))).willReturn(taskDto);
        doAnswer(i -> null).when(taskMapper).updateEntity(any(TaskDto.class), any(Task.class));

        TaskDto updateTask = taskService.updateTask(taskDto);

        assertThat(updateTask).isNotNull();
        assertThat(updateTask.getTitle()).isEqualTo(taskDto.getTitle());
        assertThat(updateTask.getDescription()).isEqualTo(taskDto.getDescription());
        assertThat(updateTask.getStatusId()).isEqualTo(taskDto.getStatusId());
    }

    private Task buildTask() {
        return Task
                .builder()
                .id(2l)
                .title("Fix cart bug")
                .description("Fix bug")
                .taskStatus(TaskStatus.builder().status("In progress").build())
                .build();
    }

    private TaskDto buildTaskDto() {
        return TaskDto
                .builder()
                .id(2l)
                .title("Fix cart bug")
                .description("Fix bug")
                .statusId(2l)
                .build();
    }


    private TaskStatus buildTaskStatus() {
        return TaskStatus
                .builder()
                .status("In progress")
                .build();
    }

    private User buildUser() {
        return User
                .builder()
                .id(2l)
                .username("mike2020")
                .firstName("Mike")
                .lastName("White")
                .email("mike@gmail.com")
                .password("Admin74!#")
                .build();
    }

    private UserTask buildUserTask() {
        return UserTask
                .builder()
                .user(buildUser())
                .task(buildTask())
                .build();
    }

    private List<TaskDto> buildTaskDtoList() {
        return Collections.singletonList(buildTaskDto());
    }

    private List<Task> buildTaskList() {
        return Collections.singletonList(buildTask());
    }



}
