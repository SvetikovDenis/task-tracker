package com.denis.svetikov.tasktracker.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "user_task")
public class UserTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @OneToOne
    @JoinColumn(name = "task_id")
    private Task task;


    public UserTask() {
    }

    @Builder
    public UserTask(User user,Task task) {
        this.user = user;
        this.task = task;
    }
}
