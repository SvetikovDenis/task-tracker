package com.denis.svetikov.tasktracker.model;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "task_status")
public class TaskStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;

}
