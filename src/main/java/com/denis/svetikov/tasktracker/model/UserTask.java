package com.denis.svetikov.tasktracker.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_task")
public class UserTask {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne
    @JoinColumn(name = "task_id")
    private Task task;
}
