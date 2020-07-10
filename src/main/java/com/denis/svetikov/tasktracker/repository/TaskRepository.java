package com.denis.svetikov.tasktracker.repository;

import com.denis.svetikov.tasktracker.model.Task;
import com.denis.svetikov.tasktracker.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> , JpaSpecificationExecutor<Task> {

    List<Task> findAllByUserId(Long id);

    List<Task> findAllByUser_Username(String username);

}
