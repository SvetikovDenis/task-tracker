package com.denis.svetikov.tasktracker.dto.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskDto extends AbstractDto{

    @NotBlank(groups = {New.class}, message = "Task title can't be null or blank")
    @JsonView({StandardView.class,DetailsView.class})
    private String title;

    @NotBlank(groups = {New.class}, message = "Task description can't be null or blank")
    @JsonView({StandardView.class,DetailsView.class})
    private String description;

    @NotNull(groups = {New.class}, message = "Task status id can't be null")
    @JsonView({StandardView.class,DetailsView.class}) 
    private Long statusId;

    public TaskDto() {
    }


    @Builder
    public TaskDto(Long id,String title,String description,Long statusId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.statusId = statusId;
    }

}
