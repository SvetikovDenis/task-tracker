package com.denis.svetikov.tasktracker.dto.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskPostDto {

    @NotBlank(message = "Task title can't be null or blank")
    private String title;

    @NotBlank(message = "Task description can't be null or blank")
    private String description;

    @NotNull(message = "Task status id can't be null")
    private Long status;

}