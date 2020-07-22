package com.denis.svetikov.tasktracker.model;


import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "task_status")
public class TaskStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String status;


    public TaskStatus() {
    }

    @Builder
    public TaskStatus(String status) {
        this.status = status;
    }
}
