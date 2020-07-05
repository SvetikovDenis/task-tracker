package com.denis.svetikov.tasktracker.dto.model;

import com.denis.svetikov.tasktracker.model.Task;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskDto {

    @NotNull(message = "Task id can't be null")
    private Long id;

    @NotBlank(message = "Task title can't be null or blank")
    private String title;

    @NotBlank(message = "Task description can't be null or blank")
    private String description;

    @NotNull(message = "Task status id can't be null")
    private Long status;


    public static TaskDto fromTask(Task task) {

        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setTitle(task.getTitle());
        taskDto.setDescription(task.getDescription());
        taskDto.setStatus(task.getStatus().getId());

        return taskDto;
    }


}
