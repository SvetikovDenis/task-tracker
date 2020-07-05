package com.denis.svetikov.tasktracker.dto.model;

import com.denis.svetikov.tasktracker.model.UserTask;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserTaskDto {

    private Long userId;
    private Long taskId;


    public static UserTaskDto userTaskDto(UserTask userTask) {
        UserTaskDto userTaskDto = new UserTaskDto();

        userTaskDto.setTaskId(userTask.getTask().getId());
        userTaskDto.setUserId(userTask.getUser().getId());

        return userTaskDto;
    }

}
