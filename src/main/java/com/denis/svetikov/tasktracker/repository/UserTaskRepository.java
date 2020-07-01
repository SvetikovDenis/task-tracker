package com.denis.svetikov.tasktracker.repository;

import com.denis.svetikov.tasktracker.model.Task;
import com.denis.svetikov.tasktracker.model.UserTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTaskRepository extends JpaRepository<UserTask,Long> {

    UserTask findUserTaskByTask(Task task);

}
