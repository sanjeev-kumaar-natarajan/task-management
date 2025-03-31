package com.myApp.task_management.Mapper;

import com.myApp.task_management.Entity.Task;
import com.myApp.task_management.Entity.TaskDTO;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    public TaskDTO toTaskDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        if (task != null) {
            taskDTO.setId(task.getId());
            taskDTO.setTitle(task.getTitle());
            taskDTO.setDescription(task.getDescription());
            taskDTO.setPriority(task.getPriority());
            taskDTO.setDueDate(task.getDueDate());
            taskDTO.setCompleted(task.getCompleted());
        }
        return taskDTO;
    }

    public Task toTaskEntity(TaskDTO taskDTO) {
        Task task = new Task();
        if (taskDTO != null) {
            task.setId(taskDTO.getId());
            task.setTitle(taskDTO.getTitle());
            task.setDescription(taskDTO.getDescription());
            task.setPriority(taskDTO.getPriority());
            task.setDueDate(taskDTO.getDueDate());
            task.setCompleted(taskDTO.getCompleted());
        }
        return task;
    }

}
