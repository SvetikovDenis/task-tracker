package com.denis.svetikov.tasktracker.model;

import lombok.Builder;
import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "tasks")
public class Task extends AbstractEntity  {

    @NotBlank(message = "Task title can't be null or blank")
    private String title;

    @NotBlank(message = "Task description can't be null or blank")
    private String description;

    @NotNull
    @OneToOne
    @JoinColumn(name = "task_status_id")
    private TaskStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "user_task",
            joinColumns = {@JoinColumn(name = "task_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private User user;


    public Task() {
    }

    @Builder
    public Task(Long id, String title,String description,TaskStatus taskStatus,User user) {
        this.setId(id);
        this.title = title;
        this.description = description;
        this.status = taskStatus;
        this.user = user;
    }
}
