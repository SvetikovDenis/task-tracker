package com.denis.svetikov.tasktracker.dto.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserTaskDto {

    private Long userId;
    private String username;
    private String firstName;
    private String lastName;
    private Long taskId;
    private String title;
    private String status;

}
