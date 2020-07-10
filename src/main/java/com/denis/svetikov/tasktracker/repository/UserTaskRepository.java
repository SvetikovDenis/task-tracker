package com.denis.svetikov.tasktracker.repository;

import com.denis.svetikov.tasktracker.model.Task;
import com.denis.svetikov.tasktracker.model.UserTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTaskRepository extends JpaRepository<UserTask,Long> {


    List<UserTask> getAllByUserId(Long id);

    UserTask findUserTaskByTask(Task task);

    UserTask findUserTaskByTaskId(Long id);

    void deleteByTaskId(Long id);

    void deleteByUserId(Long id);


}
