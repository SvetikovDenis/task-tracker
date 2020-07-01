package com.denis.svetikov.tasktracker.model;

import com.denis.svetikov.tasktracker.dto.UserDto;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @OneToOne
    @JoinColumn(name = "task_status_id")
    private TaskStatus status;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "user_task",
            joinColumns = {@JoinColumn(name = "task_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private User user;


}
