package com.denis.svetikov.tasktracker.dto;

import com.denis.svetikov.tasktracker.model.Task;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskDto {

    private Long id;
    private String title;
    private String description;
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
