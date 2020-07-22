package com.denis.svetikov.tasktracker.dto.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserTaskDto {

    private Long userId;
    private String username;
    private String firstName;
    private String lastName;
    private Long taskId;
    private String title;
    private String status;


    public UserTaskDto() {
    }

    @Builder
    public UserTaskDto(Long userId, String username, String firstName, String lastName, Long taskId, String title, String status) {
        this.userId = userId;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.taskId = taskId;
        this.title = title;
        this.status = status;
    }
}
