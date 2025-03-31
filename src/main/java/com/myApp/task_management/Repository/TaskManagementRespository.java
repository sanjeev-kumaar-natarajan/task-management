package com.myApp.task_management.Repository;

import com.myApp.task_management.Entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskManagementRespository extends JpaRepository<Task, Long> {

    @Query(value = "SELECT * FROM task_management.task", nativeQuery = true)
    List<Task> getTaskList();
}
